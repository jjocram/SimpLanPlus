package it.azzalinferrati.semanticanalysis.exception;

public class MissingDeclarationException extends Exception{
	private static final long serialVersionUID = -6051915590444327173L;

	public MissingDeclarationException(String message) {
        super(message);
    }
}
