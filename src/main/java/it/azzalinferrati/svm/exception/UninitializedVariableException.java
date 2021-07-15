package it.azzalinferrati.svm.exception;

public class UninitializedVariableException extends Exception {
    private static final long serialVersionUID = -5281879084371473217L;

    public UninitializedVariableException(String message) {
        super(message);
    }
}
