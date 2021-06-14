package it.azzalinferrati.semanticanalysis.exception;

public class ReadWriteAfterDeleteException extends Exception {
    public ReadWriteAfterDeleteException(String message) {
        super(message);
    }
}
