package it.azzalinferrati.semanticanalysis.exception;

public class MissingDeclarationException extends Exception{
    private static final long serialVersionUID = 1L;
    
    public MissingDeclarationException(String message) {
        super(message);
    }
}
