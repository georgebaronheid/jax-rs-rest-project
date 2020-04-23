package main.java.br.com.baronheid.model;

import javax.ws.rs.WebApplicationException;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(){}

    public EntityNotFoundException(String message) {
        super(message);
    }

    public WebApplicationException badRequest(){
        throw new WebApplicationException(404);
    }

}
