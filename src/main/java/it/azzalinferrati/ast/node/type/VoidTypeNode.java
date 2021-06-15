package it.azzalinferrati.ast.node.type;

import java.util.ArrayList;

import it.azzalinferrati.ast.node.Node;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

/**
 * <p>Represents a {@code void} type token in the AST.</p>
 *
 * <p><strong>Type checking</strong>: {@code null}.</p>
 * <p><strong>Semantic analysis</strong>: empty</p>
 * <p><strong>Code generation</strong>: empty.</p>
 */
public class VoidTypeNode extends TypeNode {

    @Override
    public String toPrint(String indent) {
        return indent + "void";
    }

    @Override
    public Node typeCheck() throws TypeCheckingException {
        return null; // Nothing to return
    }

    @Override
    public String codeGeneration() {
        return "";
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<>();
    }
    
}
