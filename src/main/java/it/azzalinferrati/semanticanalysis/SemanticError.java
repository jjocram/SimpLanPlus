package it.azzalinferrati.semanticanalysis;

/**
 * Error returned by {@code Node::checkSemantics(Environment env)}.
 * Contains a message to show the user which problems were encountered.
 */
public class SemanticError {
    /**
     * Message to show the user.
     */
    public final String msg;

    /**
     * Constructor for a SemanticError.
     * @param msg
     */
    public SemanticError(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return msg;
    }
}
