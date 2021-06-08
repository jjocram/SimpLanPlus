package it.azzalinferrati.ast.node.declaration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import it.azzalinferrati.LabelManager;
import it.azzalinferrati.ast.node.ArgNode;
import it.azzalinferrati.ast.node.IdNode;
import it.azzalinferrati.ast.node.Node;
import it.azzalinferrati.ast.node.statement.BlockNode;
import it.azzalinferrati.ast.node.type.FunTypeNode;
import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.MultipleDeclarationException;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

/**
 * <p>Represents a function declaration in the AST.</p>
 * 
 * <p><strong>Type checking</strong>: null (it has no type) if the returned type matches the returned type declared in the function definition, otherwise throws an error.</p>
 * <p><strong>Semantic analysis</strong>: updates the current environment with the function definition (throws an error if already existent), pushes a new scope with the function arguments in it, then disallows the scope creation and finally checks the block for semantic errors.</p>
 * <p><strong>Code generation</strong>: Pushes the $ra, generates the code for the block, gets and removes the $ra from the stack, removes all the arguments from the stack, removes the $al from the stack, loads the old $fp and finally jumps to the instruction pointed by $ra.</p>
 */
public class DecFunNode implements Node {
    final private TypeNode type;
    final private IdNode id;
    final private List<ArgNode> args;
    final private BlockNode block;
    final private FunTypeNode funType; // Used in semantic analysis

    public DecFunNode(final TypeNode type, final IdNode id, final List<ArgNode> args, final BlockNode block) {
        this.type = type;
        this.id = id;
        this.args = args;
        this.block = block;

        List<TypeNode> argsType = args.stream().map(ArgNode::getType).collect(Collectors.toList());
        funType = new FunTypeNode(argsType, type);
    }

    @Override
    public String toPrint(String indent) {
        final String declaration = indent + "Function dec\t>> " + id.toPrint(" ") + " : "
                + args.stream().map((arg) -> "(" + arg.toPrint("") + ")").reduce("",
                        (arg1, arg2) -> (arg1.isEmpty() ? "" : (arg1 + " X ")) + arg2)
                + " -> " + type.toPrint("");
        final String body = indent + "Function body\t>>\n" + block.toPrint(indent);

        return declaration + "\n" + body;
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        if (!Node.isSubtype(type, block.typeCheck())) {
            // Error
            throw new TypeCheckingException("Statements inside the function declaration do not return expression of type: " + type.toPrint(""));
        }
        return null; // Nothing to return
    }

    @Override
    public String codeGeneration() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(id.getId()).append(":\n");
        buffer.append("push $ra\n");
        buffer.append(block.codeGeneration());
        buffer.append("lw $ra 0($sp)\n");
        buffer.append("pop\n"); //pop $ra
        buffer.append("addi $sp $sp ").append(args.size()).append("\n"); // pop arguments
        buffer.append("pop ;pop $al");
        buffer.append("lw $fp 0($sp)\n");
        buffer.append("pop\n");
        buffer.append("jr $ra\n");

        return buffer.toString();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        try {
            id.setEntry(env.addNewDeclaration(id.getId(), funType)); // \Sigma_{FUN}

            env.pushNewScope();

            for(ArgNode arg: args) {
                var stEntry = env.addNewDeclaration(arg.getId().getId(), arg.getType());
                arg.getId().setEntry(stEntry);
            } // \Sigma_0

            env.addNewDeclaration(id.getId(), funType); // Adding the function to the current scope for non-mutual recursive calls.

            block.disallowScopeCreation();
            errors.addAll(block.checkSemantics(env));
            // block.allowScopeCreation();

            env.popScope();
        } catch (MultipleDeclarationException exception) {
            errors.add(new SemanticError(exception.getMessage()));
        }
        return errors;
    }

}
