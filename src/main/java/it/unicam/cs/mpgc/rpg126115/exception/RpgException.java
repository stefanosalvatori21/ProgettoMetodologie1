package it.unicam.cs.mpgc.rpg126115.exception;

public class RpgException extends RuntimeException {

    public RpgException(String message) {
        super(message);
    }

    public RpgException(String message, Throwable cause) {
        super(message, cause);
    }
}
