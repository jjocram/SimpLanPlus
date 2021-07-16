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
 * <p>Represents the invocation of a function in the AST.</p>
 *
 * <p><strong>Type checking</strong>: the type returned by the function if the function body returns a matching value, throws a type checking exception if the body of the function does not match the returned type.</p>
 * <p><strong>Semantic analysis</strong>: it checks the existence of the function in the Symbol Table, checks that all the actual arguments are correct and finally performs the Effects Analysis..</p>
 * <p><strong>Code generation</strong>: Pushes the <strong>$fp</strong> onto the stack, goes through the static chain and retrieves the correct <strong>$al</strong> to be pushed onto the stack, generates the code for all the arguments (in order), pushes their value onto the stack, moves the <strong>$fp</strong> back to the previously pushed <strong>$al</strong> and finally jumps to the function definition code.</p>
 */
public class CallNode implements Node {
    final private IdNode id;
    final private List<ExpNode> params;
    private int currentNestingLevel;

    private boolean firstCheck;

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
            if (!Node.isSubtype(formalFunArgTypes.get(i), actualFunArgTypes.get(i))) {
                throw new TypeCheckingException("In function " + id + " expected argument of type " + formalFunArgTypes.get(i) + ", got " + actualFunArgTypes.get(i) + ".");
            }
        }

        return funType.getReturned();
    }

    @Override
    public String codeGeneration() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("; BEGIN ").append(this).append("\n");
        buffer.append("push $fp ;we are preparing to call a function, push old $fp\n"); // push old $fp
        buffer.append("push $sp\n"); //push old stack pointer
        buffer.append("mv $bsp $sp\n"); //update base stack pointer to the new place
        buffer.append("addi $t1 $bsp 2\n");
        buffer.append("sw $t1 0($bsp)\n");

        buffer.append("subi $sp $sp 1 ;create space for RA\n");

        buffer.append("lw $al 0($fp)\n");
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

        buffer.append("jal ").append(id.getId()).append(" ;jump to function (this automatically set $ra to the next instruction)\n");

        buffer.append("; END ").append(this).append("\n");

        return buffer.toString();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        /*
        \gamma |- id : id.getSTEntry().getType() DONE(errors.addAll(id.checkSemantics(env));)
        \sigma(id) = \sigma_0 -> \sigma_1 DONE
        \sigma_1(y_i) != ERROR per ogni parmetro passato per valore DONE

        \sigma'  = \sigma[z_i |-> z_i.effectInSigma |> RW per ogni variabile utilizzato nei parametri passati per valore] DONE
        \sigma'' = \par[u_i |-> u_i.effectInSigma |> x_i.effectInSigma_1 per ogni variabile passata per riferimento] TBD
        ---------------------------------------------------
         \sigma |- id(params) : update(\sigma', \sigma'') TBD
        */

        ArrayList<SemanticError> errors = new ArrayList<>();

        errors.addAll(id.checkSemantics(env));
        if (!errors.isEmpty()) {
            // If the identifier is not found we cannot proceed with the effect analysis
            return errors;
        }
        params.forEach((p) -> errors.addAll(p.checkSemantics(env)));
        currentNestingLevel = env.getNestingLevel();

        var formalFunArgLen = ((FunTypeNode) id.getSTEntry().getType()).getParams().size();
        var actualFunArgLen = params.size();

        if (formalFunArgLen != actualFunArgLen) {
            errors.add(new SemanticError("The number of actual parameters do not match that of the formal parameters of function " + id + "."));
        }

        if (!errors.isEmpty()) {
            return errors;
        }

        if (firstCheck) {
            firstCheck = false;
            DecFunNode functionNode = id.getSTEntry().getFunctionNode();
            List<List<Effect>> paramsEffects = new ArrayList<>();

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

            errors.addAll(functionNode.checkEffects(env, paramsEffects));
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
                errors.add(new SemanticError("The function parameter " + params.get(i) + " was used erroneously inside the body of " + id.getId() + "."));
            }
        }

        // Setting all variables inside expressions to be read/write.
        Environment e1 = new Environment(env); // Creating a copy of the environment.

        List<LhsNode> varsInExpressions = params.stream()
                .filter(param -> !((param instanceof DereferenceExpNode) && ((DereferenceExpNode) param).isPointerType()))
                .flatMap(param -> param.variables().stream())
                .collect(Collectors.toList());

        for (var variable : varsInExpressions) {
            var entryInE1 = e1.safeLookup(variable.getId().getId());
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

            LhsNode pointer = params.get(i).variables().get(0); //always exists only once pointer in the list of variables of this parameter

            STEntry entry = mthEnv.addUniqueNewDeclaration(pointer.getId().getId(), pointer.getId().getSTEntry().getType());
            for (int derefLvl = 0; derefLvl < pointer.getId().getSTEntry().getMaxDereferenceLevel(); derefLvl++) {
                Effect u_iEffect = env.safeLookup(pointer.getId().getId()).getVariableStatus(derefLvl);
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
