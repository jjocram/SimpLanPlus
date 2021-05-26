package it.azzalinferrati.semanticanalysis.exception;

public class ReadWriteAfterDeleteException extends Exception{
    private static final long serialVersionUID = 1L;

    public ReadWriteAfterDeleteException(String message) {
        super(message);
    }
}
