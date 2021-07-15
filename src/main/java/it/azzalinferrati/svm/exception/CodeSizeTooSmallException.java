package it.azzalinferrati.svm.exception;

public class CodeSizeTooSmallException extends Exception {
    private static final long serialVersionUID = 242854246450867524L;

    public CodeSizeTooSmallException(String message) {
        super(message);
    }
}
