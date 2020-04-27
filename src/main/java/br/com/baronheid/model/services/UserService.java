package main.java.br.com.baronheid.model.services;

import main.java.br.com.baronheid.model.dao.UserDAOImpl;
import main.java.br.com.baronheid.model.dao.interfaces.UserDAO;
import main.java.br.com.baronheid.model.entity.User;
import main.java.br.com.baronheid.model.entity.wrapper.Users;
import main.java.br.com.baronheid.model.exceptions.DatabaseException;
import main.java.br.com.baronheid.model.exceptions.EntityNotFoundException;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class UserService {

    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAOImpl();
    }

    public String helloWorldRequest() {
        return "Hello World - Olá - Acentuação";
    }

    public Users getAllUsers() {
        Users users = null;
        try {
            users = new Users(userDAO.listObjects());
        } catch (EntityNotFoundException entityNotFoundException) {
            entityNotFoundException.badRequest();
        } catch (InternalServerErrorException e) {
            throw new WebApplicationException(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public User searchUserById(Integer id) {
        User search = null;
        try {
            search = userDAO.search(id);
        } catch (EntityNotFoundException e) {
            e.badRequest();
        } catch (Exception e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
        return search;
    }

    public void deleteUserById(Integer id) {
        try {
            userDAO.delete(id);
        } catch (EntityNotFoundException e) {
            e.badRequest();
        } catch (Exception e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }


    public Response createUser(User user) {
        try {
            User userToCreate = userDAO.register(user);
            URI uri = UriBuilder.fromPath("/users/{id}")
                    .build(userToCreate.getUserId());
            return Response.created(uri).entity(userToCreate).build();
        } catch (DatabaseException databaseException) {
            throw new WebApplicationException(Response.Status.CONFLICT);
        } catch (Exception exception) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }

    }
}
