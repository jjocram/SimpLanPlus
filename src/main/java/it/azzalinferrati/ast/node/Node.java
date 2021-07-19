package it.azzalinferrati.ast.node;

import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

import java.util.ArrayList;

/**
 * <p>Represents a generic node in the AST.</p>
 */
public interface Node {
    /**
     * Returns a string represention of the node.
     *
     * @param indent characters to prepend to the string version of the node
     * @return the string version of the node
     */
    String toPrint(String indent);

    /**
     * Returns the type of the node in the Symbol Table if it is an identifier,
     * the type of the expression if it is an expression. Types can be found in:
     * {@code it.azzalinferrati.ast.node.type}
     *
     * @return the type of the identifier or expression
     * @throws TypeCheckingException if the node is not a leaf in the AST and children of this node do not match the type.
     */
    Node typeCheck() throws TypeCheckingException;

    /**
     * Generates the Assembly code for the <strong>SimpLanPlus Virtual Machine (SVM)</strong>.
     *
     * @return the generated Assembly code
     */
    String codeGeneration();

    /**
     * Checks the subtree rooted in this node for semantic errors (Semantic Analysis and Effect Analysis errors).
     *
     * @param env the environment at this point in the AST (both environments Γ)
     * @return a list of {@code SemanticError}
     */
    ArrayList<SemanticError> checkSemantics(Environment env);

    /**
     * Checks the subtree rooted in this node for effects analysis errors.
     *
     * @param env the environment at this point in the AST (both environments Γ and Σ)
     * @return a list of {@code SemanticError}
     */
    ArrayList<SemanticError> checkEffects(Environment env);
}
