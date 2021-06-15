package it.azzalinferrati.semanticanalysis.exception;

public class MultipleDeclarationException extends Exception {
	private static final long serialVersionUID = 7576060673962484397L;

	public MultipleDeclarationException(String message) {
        super(message);
    }
}
