package it.azzalinferrati.ast.node.declaration;

import java.util.ArrayList;

import it.azzalinferrati.ast.node.IdNode;
import it.azzalinferrati.ast.node.Node;
import it.azzalinferrati.ast.node.expression.ExpNode;
import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Effect;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.MultipleDeclarationException;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

/**
 * <p>Represents a variable declaration in the AST.</p>
 * 
 * <p><strong>Type checking</strong>: null (it has no type) if the type if the expression matches the type of the variable declared, otherwise throws an error.</p>
 * <p><strong>Semantic analysis</strong>: checks the expression for errors, updates the current environment with the variable definition (throws an error if already existent), updates the current scope and sets the status in the Symbol Table.</p>
 * <p><strong>Code generation</strong>: Generates the code for the expression and saves its content in $a0, pushes $a0 onto the stack. If the expression does not exist then just the stack pointer is moved by 1 position.</p>
 */
public class DecVarNode extends DeclarationNode {

    final private TypeNode type;
    final private IdNode id;
    final private ExpNode exp;

    public DecVarNode(final TypeNode type, final IdNode id, final ExpNode exp) {
        this.type = type;
        this.id = id;
        this.exp = exp;
    }

    @Override
    public String toPrint(String indent) {
        final String declaration = indent
                + "Variable\t>> " + id.toPrint(" ") + " : " + type.toPrint("");
        /* A declaration could be
            - type ID;
            - type ID = exp
            Exp is optional
        */
        final String body = exp != null ? ("\t- initialized with: " + exp.toPrint("")) : "";


        return declaration + "\t- in the AR will be at offset: " + id.getOffset() + body;
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        if(exp == null) {
            // Only declaration of variable occurs (e.g. "int a;")
            return null;
        }
        
        TypeNode expType = exp.typeCheck();

        if(Node.isSubtype(expType, type)) {
            return null;
        }

        throw new TypeCheckingException("Expression: " + exp.toPrint("") + " cannot be assigned to " + id.toPrint("") + " of type " + type.toPrint(""));
    }

    @Override
    public String codeGeneration() {
        if (exp == null) {
            return "addi $sp $sp -1\n";
        }

        // Declaration with initialization
        StringBuffer buffer = new StringBuffer();
        buffer.append(exp.codeGeneration());
        buffer.append("push $a0\n");

        return buffer.toString();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> errors = new ArrayList<>();
        
        if(exp != null) {
            errors.addAll(exp.checkSemantics(env));
        }

        try {
            id.setEntry(env.addNewDeclaration(id.getId(), type));
            if(exp != null) {
                id.setStatus(Effect.READ_WRITE);
            }
        } catch (MultipleDeclarationException exception) {
            errors.add(new SemanticError(exception.getMessage()));
        }

        return errors;
    }
}
