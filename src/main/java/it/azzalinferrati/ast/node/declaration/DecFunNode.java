package it.azzalinferrati.ast.node.declaration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import it.azzalinferrati.ast.node.ArgNode;
import it.azzalinferrati.ast.node.IdNode;
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
        if (!(type.equals(block.typeCheck()))) {
            throw new TypeCheckingException("Statements inside the function declaration do not return expression of type: " + type + ".");
        }
        return null; // Nothing to return
    }

    @Override
    public String codeGeneration() {
        StringBuilder buffer = new StringBuilder();
        String functionLabel = funId.getIdentifier();
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

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        try {
            funId.setEntry(env.addNewDeclaration(funId.getIdentifier(), funType)); // \Sigma_{FUN}
            env.pushNewScope();

            for (ArgNode arg : args) {
                arg.getId().setEntry(env.addNewDeclaration(arg.getId().getIdentifier(), arg.getType()));
            }

            env.addNewDeclaration(funId.getIdentifier(), funType); // Adding the function to the current scope for non-mutual recursive calls.
            block.disallowScopeCreation();

            errors.addAll(block.checkSemantics(env));

            env.popScope();
        } catch (MultipleDeclarationException exception) {
            errors.add(new SemanticError(exception.getMessage()));
        }

        return errors;
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        env.addEntry(funId.getIdentifier(), funId.getSTEntry());
        funId.getSTEntry().setFunctionNode(this);

        List<List<Effect>> effectAtBeginning = new ArrayList<>();
        for (ArgNode argNode : args) {
            List<Effect> argEffects = new ArrayList<>();
            int maxDerefLvl = argNode.getId().getSTEntry().getMaxDereferenceLevel();
            for (int derefLvl = 0; derefLvl < maxDerefLvl; derefLvl++) {
                argEffects.add(new Effect(Effect.READ_WRITE));
            }
            effectAtBeginning.add(argEffects);
        }

        errors.addAll(checkEffectsWithArgs(env, effectAtBeginning));

        return errors;
    }

    /**
     * Performs the effect analysis through a Fix Point Method giving {@code effects} as initial statuses to the function arguments.
     * 
     * @param env the environment before the function call or definition.
     * @param effects the list of effects applied to the function arguments.
     * @return a list of {@code SemanticError}.
     */
    public ArrayList<SemanticError> checkEffectsWithArgs(Environment env, List<List<Effect>> effects) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        env.pushNewScope();

        for (int argIndex = 0; argIndex < args.size(); argIndex++) {
            var arg = args.get(argIndex);
            env.addEntry(arg.getId().getIdentifier(), arg.getId().getSTEntry());
            var argEntry = arg.getId().getSTEntry();
            for (int derefLvl = 0; derefLvl < argEntry.getMaxDereferenceLevel(); derefLvl++) {
                // effects.get(argIndex).get(derefLvl) is the status of the argIndex-th argument at the dereference level derefLvl
                // given as this method parameter.  
                argEntry.setVariableStatus(new Effect(effects.get(argIndex).get(derefLvl)), derefLvl);
            }
        }

        STEntry innerFunEntry = env.addUniqueNewDeclaration(funId.getIdentifier(), funType); // Adding the function to the current scope for non-mutual recursive calls.
        innerFunEntry.setFunctionNode(this);
        block.disallowScopeCreation();

        Environment old_env = new Environment(env);
        List<List<Effect>> old_effects = new ArrayList<>();
        for (var status : innerFunEntry.getFunctionStatus()) {
            old_effects.add(new ArrayList<>(status));
        }

        errors.addAll(checkBlockAndUpdateArgs(env, innerFunEntry)); // env is updated after this call.

        boolean different_funType = !innerFunEntry.getFunctionStatus().equals(old_effects);

        while (different_funType) {
            // The environment is replaced with that saved before calling checkBlockAndUpdateArgs,
            // but the new function argument statuses are set up.
            env.replace(old_env);

            var funEntry = env.safeLookup(funId.getIdentifier());

            for (int argIndex = 0; argIndex < args.size(); argIndex++) {
                var argEntry = env.safeLookup(args.get(argIndex).getId().getIdentifier());
                var argStatuses = innerFunEntry.getFunctionStatus().get(argIndex);

                for (int derefLvl = 0; derefLvl < argEntry.getMaxDereferenceLevel(); derefLvl++) {
                    funEntry.setParamStatus(argIndex, argStatuses.get(derefLvl), derefLvl);
                }
            }

            old_effects = new ArrayList<>(innerFunEntry.getFunctionStatus());

            errors.addAll(checkBlockAndUpdateArgs(env, innerFunEntry));

            different_funType = !innerFunEntry.getFunctionStatus().equals(old_effects);
        }

        env.popScope();

        // Setting the computed statuses in the function arguments and saving them in the Symbol Table entry of the function funId.
        var idEntry = env.safeLookup(funId.getIdentifier());
        for (int argIndex = 0; argIndex < args.size(); argIndex++) {
            //Update ID in previous scope
            var argStatuses = innerFunEntry.getFunctionStatus().get(argIndex);

            for (int derefLvl = 0; derefLvl < argStatuses.size(); derefLvl++) {
                idEntry.setParamStatus(argIndex, argStatuses.get(derefLvl), derefLvl);
            }
        }

        return errors;
    }

    /**
     * Executes the semantic analysis on the block, updating the function argument arguments in {@code env} and {@code innerFunEntry}
     * @param env the environment before the semantic check.
     * @param innerFunEntry the function entry in the Symbol Table at nesting level equivalent to that of the arguments of the same function.
     * @return the semantic errors encountered.
     */
    private ArrayList<SemanticError> checkBlockAndUpdateArgs(Environment env, STEntry innerFunEntry) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        errors.addAll(block.checkEffects(env));
        for (int argIndex = 0; argIndex < args.size(); argIndex++) {
            var argEntry = env.safeLookup(args.get(argIndex).getId().getIdentifier());

            for (int derefLvl = 0; derefLvl < argEntry.getMaxDereferenceLevel(); derefLvl++) {
                funId.getSTEntry().setParamStatus(argIndex, argEntry.getVariableStatus(derefLvl), derefLvl);
                innerFunEntry.setParamStatus(argIndex, argEntry.getVariableStatus(derefLvl), derefLvl);
            }
        }
        
        return errors;
    }

}
