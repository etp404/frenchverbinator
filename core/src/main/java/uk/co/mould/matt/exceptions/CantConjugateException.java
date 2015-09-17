package uk.co.mould.matt.exceptions;

public final class CantConjugateException extends Exception {
    public CantConjugateException(String message) {
        super(message);
    }

    public CantConjugateException(Throwable cause) {
        super(cause);
    }
}
