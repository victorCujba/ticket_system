package it.webformat.ticketsystem.exceprions;

public class IdMustBeNullException extends RuntimeException {

    public IdMustBeNullException() {
        super("Id must be null. You sent a dto with an id already present");
    }

    public IdMustBeNullException(String message) {
        super(message);
    }
}
