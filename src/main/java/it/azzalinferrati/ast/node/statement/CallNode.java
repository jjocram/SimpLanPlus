package it.azzalinferrati.ast.node.statement;

import it.azzalinferrati.ast.node.IdNode;
import it.azzalinferrati.ast.node.LhsNode;
import it.azzalinferrati.ast.node.Node;
import it.azzalinferrati.ast.node.declaration.DecFunNode;
import it.azzalinferrati.ast.node.expression.DereferenceExpNode;
import it.azzalinferrati.ast.node.expression.ExpNode;
import it.azzalinferrati.ast.node.type.FunTypeNode;
import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Effect;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.STEntry;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Represents the invocation of a function in the AST.
 */
public class CallNode implements Node {
    final private IdNode id;
    final private List<ExpNode> params;
    private int currentNestingLevel;

    // Blocks infinite recursion in recursive function effect analysis.
    private boolean firstCheck;

    private String callerFunctionIdentifier;

    public CallNode(IdNode id, List<ExpNode> params) {
        this.id = id;
        this.params = params;
        this.firstCheck = true;
    }

    public IdNode getId() {
        return id;
    }

    @Override
    public String toPrint(String indent) {
        String parametersToPrint = params.stream().map((argNode) -> argNode.toPrint(indent)).reduce("(", (arg1, arg2) -> (arg1.equals("(") ? "(" : (arg1 + ", ")) + arg2);
        parametersToPrint += ")";
        return indent + "Call:\t" + id.toPrint(indent) + parametersToPrint;
    }

    @Override
    public String toString() {
        return toPrint("");
    }

    public void setCallerFunctionIdentifier(String callerFunctionIdentifier) {
        this.callerFunctionIdentifier = callerFunctionIdentifier;
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        TypeNode idType = id.typeCheck();

        if (!(idType instanceof FunTypeNode)) {
            throw new TypeCheckingException("ID " + id + " is not a function identifier.");
        }

        FunTypeNode funType = (FunTypeNode) idType;

        List<TypeNode> formalFunArgTypes = funType.getParams();
        List<TypeNode> actualFunArgTypes = new ArrayList<>();

        for (ExpNode exp : params) {
            actualFunArgTypes.add(exp.typeCheck());
        }

        for (int i = 0, size = formalFunArgTypes.size(); i < size; i++) {
            if (!(formalFunArgTypes.get(i).equals(actualFunArgTypes.get(i)))) {
                throw new TypeCheckingException("In function " + id + " expected argument of type " + formalFunArgTypes.get(i) + ", got " + actualFunArgTypes.get(i) + ".");
            }
        }

        return funType.getReturned();
    }

    @Override
    public String codeGeneration() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("; BEGIN ").append(this).append("\n");
        buffer.append("push $fp ;we are preparing to call a function, push old $fp\n"); // Push old $fp.
        buffer.append("push $sp\n"); // Push old stack pointer.
        buffer.append("mv $bsp $sp\n"); // Update base stack pointer to the new place.
        buffer.append("addi $t1 $bsp 2\n");
        buffer.append("sw $t1 0($bsp)\n");

        buffer.append("subi $sp $sp 1 ;create space for RA\n");

        if (callerFunctionIdentifier != null && callerFunctionIdentifier.endsWith(id.getIdentifier())) {
            // Recursive call
            buffer.append("lw $al 0($fp)"); // 0($fp) is the value of AL in the previous frame
        } else {
            // There is no a caller (the main called the function) or this is not a recursive call
            buffer.append("mv $al $fp\n"); // $fp is the address of AL in the previous frame
        }


        for (int i = 0; i < (currentNestingLevel - id.getNestingLevel()); i++) {
            buffer.append("lw $al 0($al)\n");
        }
        buffer.append("push $al\n");

        params.forEach(param -> {
            buffer.append(param.codeGeneration());
            buffer.append("push $a0 ;push of ").append(param).append("\n");
        });

        // $fp = $sp - 1
        buffer.append("mv $fp $sp ;update $fp\n");
        buffer.append("addi $fp $fp ").append(params.size()).append(" ;fix $fp position to the bottom of the new frame\n");

        buffer.append("jal ").append(id.getIdentifier()).append(" ;jump to function (this automatically set $ra to the next instruction)\n");

        buffer.append("; END ").append(this).append("\n");

        return buffer.toString();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        errors.addAll(id.checkSemantics(env));
        if (!errors.isEmpty()) {
            // If the identifier is not found we cannot proceed with the semantic analysis.
            return errors;
        }
        params.forEach((p) -> errors.addAll(p.checkSemantics(env)));
        currentNestingLevel = env.getNestingLevel();

        var formalFunArgLen = ((FunTypeNode) id.getSTEntry().getType()).getParams().size();
        var actualFunArgLen = params.size();

        if (formalFunArgLen != actualFunArgLen) {
            errors.add(new SemanticError("The number of actual parameters do not match that of the formal parameters of function " + id + "."));
        }

        return errors;
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        /*
        \gamma |- id : id.getSTEntry().getType() DONE(errors.addAll(id.checkSemantics(env));)
        \sigma(id) = \sigma_0 -> \sigma_1 DONE
        \sigma_1(y_i) != ERROR for each value passed as value DONE

        \sigma'  = \sigma[z_i |-> z_i.effectInSigma |> RW for each variable used in the parameters passed as value] DONE
        \sigma'' = \par[u_i |-> u_i.effectInSigma |> x_i.effectInSigma_1 for each value passed as reference] DONE
        ---------------------------------------------------
         \sigma |- id(params) : update(\sigma', \sigma'') DONE
        */
        ArrayList<SemanticError> errors = new ArrayList<>();

        errors.addAll(id.checkEffects(env));
        params.forEach((p) -> errors.addAll(p.checkEffects(env)));

        if (firstCheck) {
            firstCheck = false;
            DecFunNode functionNode = id.getSTEntry().getFunctionNode();
            List<List<Effect>> paramsEffects = new ArrayList<>();

            // Creating the statuses of the variables given as input to the function call.
            // If actual parameters are expressions not instance of DereferenceExpNode, Effect.READ_WRITE is the status given.
            for (ExpNode expNode : params) {
                List<Effect> paramEffects = new ArrayList<>();
                if (expNode instanceof DereferenceExpNode) {
                    int maxDereferenceLevel = expNode.variables().get(0).getId().getSTEntry().getMaxDereferenceLevel();
                    for (int derefLvl = 0; derefLvl < maxDereferenceLevel; derefLvl++) {
                        paramEffects.add(new Effect(expNode.variables().get(0).getId().getStatus(derefLvl)));
                    }
                } else {
                    paramEffects.add(new Effect(Effect.READ_WRITE));
                }

                paramsEffects.add(paramEffects);
            }

            errors.addAll(functionNode.checkEffectsWithArgs(env, paramsEffects));
        }

        if (!errors.isEmpty()) {
            return errors;
        }

        // Checking that parameters inside the function do not result in error statuses.
        List<Integer> indexesOfNotPointers = IntStream
                .range(0, params.size())
                .filter(i -> !((params.get(i) instanceof DereferenceExpNode) && ((DereferenceExpNode) params.get(i)).isPointerType()))
                .boxed()
                .collect(Collectors.toList());
        List<List<Effect>> effects = id.getSTEntry().getFunctionStatus();
        for (int i : indexesOfNotPointers) {
            if (effects.get(i).stream().anyMatch(e -> e.equals(Effect.ERROR))) {
                errors.add(new SemanticError("The function parameter " + params.get(i) + " was used erroneously inside the body of " + id.getIdentifier() + "."));
            }
        }

        // Setting all variables inside expressions to be read/write.
        Environment e1 = new Environment(env); // Creating a copy of the environment.

        List<LhsNode> varsInExpressions = params.stream()
                .filter(param -> !((param instanceof DereferenceExpNode) && ((DereferenceExpNode) param).isPointerType()))
                .flatMap(param -> param.variables().stream())
                .collect(Collectors.toList());

        for (var variable : varsInExpressions) {
            var entryInE1 = e1.safeLookup(variable.getId().getIdentifier());
            entryInE1.setVariableStatus(Effect.seq(entryInE1.getVariableStatus(0), Effect.READ_WRITE), 0);
        }

        Environment e2 = new Environment();
        List<Integer> indexesOfPointers = IntStream
                .range(0, params.size())
                .filter(i -> (params.get(i) instanceof DereferenceExpNode) && ((DereferenceExpNode) params.get(i)).isPointerType())
                .boxed()
                .collect(Collectors.toList());
        List<Environment> resultingEnvironments = new ArrayList<>();

        for (var i : indexesOfPointers) {
            // [u1 |-> seq] par [u2 |-> seq] par ... par [um |-> seq]
            // {[u1 |-> seq], [u2 |-> seq], ..., [um |-> seq]}
            Environment mthEnv = new Environment();
            mthEnv.pushNewScope();

            LhsNode pointer = params.get(i).variables().get(0); // Always exists only once pointer in the list of variables of this parameter.

            STEntry entry = mthEnv.addUniqueNewDeclaration(pointer.getId().getIdentifier(), pointer.getId().getSTEntry().getType());
            for (int derefLvl = 0; derefLvl < pointer.getId().getSTEntry().getMaxDereferenceLevel(); derefLvl++) {
                Effect u_iEffect = env.safeLookup(pointer.getId().getIdentifier()).getVariableStatus(derefLvl);
                Effect x_iEffect = effects.get(i).get(derefLvl);
                Effect seq = Effect.seq(u_iEffect, x_iEffect);

                entry.setVariableStatus(seq, derefLvl);
            }

            resultingEnvironments.add(mthEnv);
        }

        if (resultingEnvironments.size() > 0) {
            e2 = resultingEnvironments.get(0);
            for (int i = 1; i < resultingEnvironments.size(); i++) {
                e2 = Environment.par(e2, resultingEnvironments.get(i));
            }
        }

        Environment updatedEnv = Environment.update(e1, e2);
        env.replace(updatedEnv);
        errors.addAll(env.getEffectErrors());

        return errors;
    }

    public List<LhsNode> variables() {
        return params.stream().flatMap(exp -> exp.variables().stream()).collect(Collectors.toList());
    }
}
