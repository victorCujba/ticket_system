package it.webformat.ticketsystem.exceprions;

public class IdMustNotBeNullException extends RuntimeException {
    public IdMustNotBeNullException() {
        super("Id must not be null. You sent a dto without an id present");
    }

    public IdMustNotBeNullException(String message) {
        super(message);
    }
}
