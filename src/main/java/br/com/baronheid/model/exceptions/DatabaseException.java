package main.java.br.com.baronheid.model.exceptions;

public class DatabaseException extends RuntimeException {

    public DatabaseException(){}

    public DatabaseException(String message) {
        super(message);
    }
}
