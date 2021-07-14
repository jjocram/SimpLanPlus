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
public class DecFunNode implements Node {
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
        if(type instanceof PointerTypeNode) {
            throw new TypeCheckingException("Functions cannot return pointers.");
        }
        if (!Node.isSubtype(type, block.typeCheck())) {
            // Error
            throw new TypeCheckingException("Statements inside the function declaration do not return expression of type: " + type + ".");
        }
        return null; // Nothing to return
    }

    @Override
    public String codeGeneration() {
        StringBuilder buffer = new StringBuilder();
        String functionLabel = funId.getId();
        String endFunctionLabel = "end" + functionLabel;

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

        return buffer.toString();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        try {
            funId.setEntry(env.addNewDeclaration(funId.getId(), funType)); // \Sigma_{FUN}
            env.pushNewScope();

            for (ArgNode arg : args) {
                var stEntry = env.addNewDeclaration(arg.getId().getId(), arg.getType());
                for(int derefLvl = 0; derefLvl < stEntry.getMaxDereferenceLevel(); derefLvl++) {
                    stEntry.setVariableStatus(new Effect(Effect.READ_WRITE), derefLvl);
                }
                arg.getId().setEntry(stEntry);
            } // \Sigma_0

            STEntry innerFunEntry = env.addNewDeclaration(funId.getId(), funType); // Adding the function to the current scope for non-mutual recursive calls.
            block.disallowScopeCreation();

            Environment old_env = new Environment(env);
            List<List<Effect>> old_effects = new ArrayList<>();
            for (var status : innerFunEntry.getFunctionStatus()) {
                old_effects.add(new ArrayList<>(status));
            }

            errors.addAll(block.checkSemantics(env));
            for (int argIndex = 0; argIndex < args.size(); argIndex++) {
                var argEntry = env.safeLookup(args.get(argIndex).getId().getId());

                for (int derefLvl = 0; derefLvl < argEntry.getMaxDereferenceLevel(); derefLvl++) {
                    funId.getSTEntry().setParamStatus(argIndex, argEntry.getVariableStatus(derefLvl), derefLvl);
                    innerFunEntry.setParamStatus(argIndex, argEntry.getVariableStatus(derefLvl), derefLvl);
                }
            }

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

                errors.addAll(block.checkSemantics(env));
                for (int argIndex = 0; argIndex < args.size(); argIndex++) {
                    var argEntry = env.safeLookup(args.get(argIndex).getId().getId());

                    for (int derefLvl = 0; derefLvl < argEntry.getMaxDereferenceLevel(); derefLvl++) {
                        funId.getSTEntry().setParamStatus(argIndex, argEntry.getVariableStatus(derefLvl), derefLvl);
                        innerFunEntry.setParamStatus(argIndex, argEntry.getVariableStatus(derefLvl), derefLvl);
                    }
                }
             
                different_funType = !innerFunEntry.getFunctionStatus().equals(old_effects);
            }

            env.popScope();

            var idEntry = env.safeLookup(funId.getId());
            for (int argIndex = 0; argIndex< args.size(); argIndex++) {
                //Update ID in previous scope
                var argStatuses = innerFunEntry.getFunctionStatus().get(argIndex);

                for (int derefLvl = 0; derefLvl < argStatuses.size(); derefLvl++) {
                    idEntry.setParamStatus(argIndex, argStatuses.get(derefLvl), derefLvl);
                }
            }
        } catch (MultipleDeclarationException exception) {
            errors.add(new SemanticError(exception.getMessage()));
        }
        return errors;
    }

}
