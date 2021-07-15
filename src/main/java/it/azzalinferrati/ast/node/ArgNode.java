package it.azzalinferrati.ast.node;

import java.util.ArrayList;

import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

/**
 * <p>Represents an argument (variable or pointer) of a function declaration in the AST.</p>
 *
 * <p><strong>Type checking</strong>: {@code null} (it has no type).</p>
 * <p><strong>Semantic analysis</strong>: empty (it cannot return semantic errors).</p>
 * <p><strong>Code generation</strong>: Increments <strong>$sp</strong> by 1.</p>
 */
public class ArgNode implements Node {

    final private TypeNode type;
    final private IdNode id;

    public ArgNode(final TypeNode type, final IdNode id) {
        this.type = type;
        this.id = id;
    }

    public TypeNode getType() {
        return type;
    }

    public IdNode getId() {
        return id;
    }

    @Override
    public String toPrint(String indent) {
        return indent + id + " : " + type + ", " + id.getOffset();
    }

    @Override
    public String toString() {
        return toPrint("");
    }

    @Override
    public Node typeCheck() throws TypeCheckingException {
        return null; // Nothing to return
    }

    @Override
    public String codeGeneration() {
        String begin = "; BEGIN " + this + "\n";
        String end = "; END " + this + "\n";
        return begin + "addi $sp $sp 1 ; allocates space on the stack for the argument " + id + "\n" + end;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<>();
    }

}
