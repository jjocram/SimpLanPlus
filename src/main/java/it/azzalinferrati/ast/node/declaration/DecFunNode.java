package it.azzalinferrati.ast.node.declaration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import it.azzalinferrati.ast.node.ArgNode;
import it.azzalinferrati.ast.node.IdNode;
import it.azzalinferrati.ast.node.Node;
import it.azzalinferrati.ast.node.statement.BlockNode;
import it.azzalinferrati.ast.node.type.FunTypeNode;
import it.azzalinferrati.ast.node.type.PointerTypeNode;
import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Effect;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.STEntry;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.MultipleDeclarationException;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

/**
 * <p>Represents a function declaration in the AST.</p>
 *
 * <p><strong>Type checking</strong>: {@code null} (it has no type) if the returned type matches the returned type declared in the function definition, otherwise throws an error.</p>
 * <p><strong>Semantic analysis</strong>: updates the current environment with the function definition (throws an error if already existent), pushes a new scope with the function arguments in it, then disallows the scope creation and finally checks the block for semantic errors.</p>
 * <p><strong>Code generation</strong>: Pushes the <strong>$ra</strong>, generates the code for the block, gets and removes the <strong>$ra</strong> from the stack, removes all the arguments from the stack, removes the <strong>$al</strong> from the stack, loads the old <strong>$fp</strong> and finally jumps to the instruction pointed by <strong>$ra</strong>.</p>
 */
public class DecFunNode extends DeclarationNode {
    final private TypeNode type;
    final private IdNode funId;
    final private List<ArgNode> args;
    final private BlockNode block;
    final private FunTypeNode funType; // Used in semantic analysis

    public DecFunNode(final TypeNode type, final IdNode id, final List<ArgNode> args, final BlockNode block) {
        this.type = type;
        this.funId = id;
        this.args = args;
        this.block = block;

        List<TypeNode> argsType = args.stream().map(ArgNode::getType).collect(Collectors.toList());
        funType = new FunTypeNode(argsType, type);
    }


    @Override
    public String toPrint(String indent) {
        final String declaration = indent + "Fun. dec:\t" + funId + " : "
                + args.stream().map((arg) -> "(" + arg + ")").reduce("",
                (arg1, arg2) -> (arg1.isEmpty() ? "" : (arg1 + " x ")) + arg2)
                + " -> " + type;
        final String body = indent + "Fun. body:\n" + block.toPrint(indent);

        return declaration + "\n" + body;
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        if (type instanceof PointerTypeNode) {
            throw new TypeCheckingException("Functions cannot return pointers.");
        }
        if (!Node.isSubtype(type, block.typeCheck())) {
            throw new TypeCheckingException("Statements inside the function declaration do not return expression of type: " + type + ".");
        }
        return null; // Nothing to return
    }

    @Override
    public String codeGeneration() {
        StringBuilder buffer = new StringBuilder();
        String functionLabel = funId.getId();
        String endFunctionLabel = "end" + functionLabel;

        buffer.append("; BEGIN ").append(this.toString(), 0, this.toString().indexOf("Fun. body"));

        buffer.append(functionLabel).append(":\n");

        buffer.append("sw $ra -1($bsp) ;save in the memory cell above old SP the return address\n");

        block.setEndFunctionLabel(endFunctionLabel);
        buffer.append(block.codeGeneration());

        buffer.append(endFunctionLabel).append(":\n");
        buffer.append("lw $ra -1($bsp); load in $RA the return address saved \n");

        buffer.append("lw $fp 1($bsp)\n");
        buffer.append("lw $sp 0($bsp)\n"); //restore old stack pointer
        buffer.append("addi $bsp $fp 2\n"); //restore address of old base stack pointer
        buffer.append("jr $ra\n");

        buffer.append("; END ").append(this.toString(), 0, this.toString().indexOf("Fun. body"));

        return buffer.toString();
    }

    public ArrayList<SemanticError> checkEffects(Environment env, List<List<Effect>> effects) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        env.pushNewScope();

        for (int argIndex = 0; argIndex < args.size(); argIndex++) {
            var arg = args.get(argIndex);
            var stEntry = env.addUniqueNewDeclaration(arg.getId().getId(), arg.getType());
                for (int derefLvl = 0; derefLvl < stEntry.getMaxDereferenceLevel(); derefLvl++) {
                    stEntry.setVariableStatus(new Effect(effects.get(argIndex).get(derefLvl)), derefLvl);
                }
            arg.getId().setEntry(stEntry);
        }

        STEntry innerFunEntry = env.addUniqueNewDeclaration(funId.getId(), funType); // Adding the function to the current scope for non-mutual recursive calls.
        innerFunEntry.setFunctionNode(this);
        block.disallowScopeCreation();

        Environment old_env = new Environment(env);
        List<List<Effect>> old_effects = new ArrayList<>();
        for (var status : innerFunEntry.getFunctionStatus()) {
            old_effects.add(new ArrayList<>(status));
        }

        checkBlockAndUpdateArgs(env, innerFunEntry, errors);

        boolean different_funType = !innerFunEntry.getFunctionStatus().equals(old_effects);

        while (different_funType) {
            env.replace(old_env);

            var funEntry = env.safeLookup(funId.getId());

            for (int argIndex = 0; argIndex < args.size(); argIndex++) {
                var argEntry = env.safeLookup(args.get(argIndex).getId().getId());
                var argStatuses = innerFunEntry.getFunctionStatus().get(argIndex);

                for (int derefLvl = 0; derefLvl < argEntry.getMaxDereferenceLevel(); derefLvl++) {
                    funEntry.setParamStatus(argIndex, argStatuses.get(derefLvl), derefLvl);
                }
            }

            old_effects = new ArrayList<>(innerFunEntry.getFunctionStatus());

            checkBlockAndUpdateArgs(env, innerFunEntry, errors);

            different_funType = !innerFunEntry.getFunctionStatus().equals(old_effects);
        }

        env.popScope();

        var idEntry = env.safeLookup(funId.getId());
        for (int argIndex = 0; argIndex < args.size(); argIndex++) {
            //Update ID in previous scope
            var argStatuses = innerFunEntry.getFunctionStatus().get(argIndex);

            for (int derefLvl = 0; derefLvl < argStatuses.size(); derefLvl++) {
                idEntry.setParamStatus(argIndex, argStatuses.get(derefLvl), derefLvl);
            }
        }

        return errors;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        try {
            funId.setEntry(env.addNewDeclaration(funId.getId(), funType)); // \Sigma_{FUN}
            funId.getSTEntry().setFunctionNode(this);
            env.pushNewScope();

            for (ArgNode arg : args) {
                var stEntry = env.addNewDeclaration(arg.getId().getId(), arg.getType());

                arg.getId().setEntry(stEntry);
            }

            STEntry innerFunEntry = env.addNewDeclaration(funId.getId(), funType); // Adding the function to the current scope for non-mutual recursive calls.
            innerFunEntry.setFunctionNode(this);
            block.disallowScopeCreation();

            env.popScope();

            List<List<Effect>> effectAtBeginning = new ArrayList<>();
            for (ArgNode argNode : args) {
                List<Effect> argEffects = new ArrayList<>();
                for (int derefLvl = 0; derefLvl < argNode.getId().getSTEntry().getMaxDereferenceLevel(); derefLvl++) {
                    argEffects.add(new Effect(Effect.READ_WRITE));
                }
                effectAtBeginning.add(argEffects);
            }

            errors.addAll(checkEffects(env, effectAtBeginning));

        } catch (MultipleDeclarationException exception) {
            errors.add(new SemanticError(exception.getMessage()));
        }
        return errors;
    }

    private void checkBlockAndUpdateArgs(Environment env, STEntry innerFunEntry, ArrayList<SemanticError> errors) {
        errors.addAll(block.checkSemantics(env));
        for (int argIndex = 0; argIndex < args.size(); argIndex++) {
            var argEntry = env.safeLookup(args.get(argIndex).getId().getId());

            for (int derefLvl = 0; derefLvl < argEntry.getMaxDereferenceLevel(); derefLvl++) {
                funId.getSTEntry().setParamStatus(argIndex, argEntry.getVariableStatus(derefLvl), derefLvl);
                innerFunEntry.setParamStatus(argIndex, argEntry.getVariableStatus(derefLvl), derefLvl);
            }
        }
    }

}
