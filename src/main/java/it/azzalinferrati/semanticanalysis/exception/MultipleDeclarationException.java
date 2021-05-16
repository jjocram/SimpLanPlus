package it.azzalinferrati.semanticanalysis.exception;

public class MultipleDeclarationException extends Exception{
    private static final long serialVersionUID = 1L;
    
    public MultipleDeclarationException(String message) {
        super(message);
    }
}
