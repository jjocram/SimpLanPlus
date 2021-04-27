package it.azzalinferrati.semanticanalysis;

public class SemanticError {
    public final String msg;

    public SemanticError(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return msg;
    }
}
