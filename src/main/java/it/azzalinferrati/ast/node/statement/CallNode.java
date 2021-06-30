package it.azzalinferrati.ast.node.statement;

import it.azzalinferrati.ast.node.IdNode;
import it.azzalinferrati.ast.node.Node;
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

    public CallNode(IdNode id, List<ExpNode> params) {
        this.id = id;
        this.params = params;
    }

    public IdNode getId() {
        return id;
    }

    @Override
    public String toPrint(String indent) {
        String parametersToPrint = params.stream().map((argNode) -> argNode.toPrint(indent)).reduce("(", (arg1, arg2) -> (arg1.equals("(") ? "(" : (arg1 + ",")) + arg2);
        parametersToPrint += ")";
        return indent + "call " + id.toPrint(indent) + parametersToPrint;
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        TypeNode idType = id.typeCheck();

        if (!(idType instanceof FunTypeNode)) {
            throw new TypeCheckingException("ID " + id.toPrint("") + " is not a function identifier");
        }

        FunTypeNode funType = (FunTypeNode) idType;

        List<TypeNode> formalFunArgTypes = funType.getParams();
        List<TypeNode> actualFunArgTypes = new ArrayList<>();

        for (ExpNode exp : params) {
            actualFunArgTypes.add(exp.typeCheck());
        }

        for (int i = 0, size = formalFunArgTypes.size(); i < size; i++) {
            if (!Node.isSubtype(formalFunArgTypes.get(i), actualFunArgTypes.get(i))) {
                throw new TypeCheckingException("In function " + id.toPrint("") + " expected argument of type " + formalFunArgTypes.get(i).toPrint("") + ", got " + actualFunArgTypes.get(i).toPrint(""));
            }
        }

        return funType.getReturned();
    }

    @Override
    public String codeGeneration() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("push $fp ;we are preparing to call a function, push old $fp\n"); // push old $fp
        buffer.append("push $sp\n"); //push old stack pointer
        buffer.append("mv $bsp $sp\n"); //update base stack pointer to the new place //TODO: look at BlockNode
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
            buffer.append("push $a0 ;push of ").append(param.toPrint("")).append("\n");
        });

        // $fp = $sp - 1
        buffer.append("mv $fp $sp ;update $fp\n");
        buffer.append("addi $fp $fp ").append(params.size()).append(" ;fix $fp position to the bottom of the new frame\n");

        buffer.append("jal ").append(id.getId()).append(" ;jump to function (this automatically set $ra to the next instruction)\n");

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
        params.forEach((p) -> errors.addAll(p.checkSemantics(env)));
        currentNestingLevel = env.getNestingLevel();

        var formalFunArgLen = ((FunTypeNode) id.getSTEntry().getType()).getParams().size();
        var actualFunArgLen = params.size();

        if(formalFunArgLen != actualFunArgLen) {
            errors.add(new SemanticError("The number of actual parameters do not match that of the formal parameters of function " + id));
        }

        if (!errors.isEmpty()) {
            return errors;
        }

        // Checking that parameters inside the function do not result in error statuses.
        List<Integer> indexesOfNotPointers = IntStream
                .range(0, params.size())
                .filter(i -> !(params.get(i) instanceof DereferenceExpNode))
                .boxed()
                .collect(Collectors.toList());
        List<Effect> effects = id.getSTEntry().getFunctionStatus();
        for (int i : indexesOfNotPointers) {
            if (effects.get(i).equals(Effect.ERROR)) {
                errors.add(new SemanticError("The function parameter " + params.get(i) + " was used erroneously inside the body of " + id.getId()));
            }
        }

        // Setting all variables inside expressions to be read/write.
        Environment e1 = new Environment(env); // Creating a copy of the environment.

        List<IdNode> varsInExpressions = params.stream()
                .filter(param -> !((param instanceof DereferenceExpNode) && ((DereferenceExpNode) param).isPointerType()))
                .flatMap(param -> param.variables().stream())
                .collect(Collectors.toList());

        for (var variable : varsInExpressions) {
            var entryInE1 = e1.safeLookup(variable.getId());
            entryInE1.setVariableStatus(Effect.seq(entryInE1.getVariableStatus(), Effect.READ_WRITE));
        }

        Environment e2 = new Environment();
        List<Integer> indexesOfPointers = IntStream
                .range(0, params.size())
                .filter(i -> (params.get(i) instanceof DereferenceExpNode) && ((DereferenceExpNode) params.get(i)).isPointerType())
                .boxed()
                .collect(Collectors.toList());
        List<Environment> resultingEnvironments = new ArrayList<>();

        for(var i : indexesOfPointers) {
            // [u1 |-> seq] par [u2 |-> seq] par ... par [um |-> seq]
            // {[u1 |-> seq], [u2 |-> seq], ..., [um |-> seq]}
            Environment mthEnv = new Environment();
            mthEnv.pushNewScope();

            IdNode pointer = params.get(i).variables().stream().findFirst().get(); //always exists only once pointer in the list of variables of this parameter

            Effect u_iEffect = env.safeLookup(pointer.getId()).getVariableStatus();
            Effect x_iEffect = effects.get(i);
            Effect seq = Effect.seq(u_iEffect, x_iEffect);

            STEntry entry = mthEnv.addUniqueNewDeclaration(pointer.getId(), pointer.getSTEntry().getType());
            entry.setVariableStatus(seq);

            resultingEnvironments.add(mthEnv);
        }

        if(resultingEnvironments.size() > 0) {
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

    public List<IdNode> variables() {
        return params.stream().flatMap(exp -> exp.variables().stream()).collect(Collectors.toList());
    }
}
