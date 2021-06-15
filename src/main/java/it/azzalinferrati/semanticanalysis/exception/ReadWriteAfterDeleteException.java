package it.azzalinferrati.semanticanalysis.exception;

public class ReadWriteAfterDeleteException extends Exception {
	private static final long serialVersionUID = 9003898563787432279L;

	public ReadWriteAfterDeleteException(String message) {
        super(message);
    }
}
