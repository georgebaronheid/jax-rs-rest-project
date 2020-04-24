package main.java.br.com.baronheid.model.services;

import main.java.br.com.baronheid.model.EntityNotFoundException;
import main.java.br.com.baronheid.model.dao.UserDAOImpl;
import main.java.br.com.baronheid.model.dao.interfaces.UserDAO;
import main.java.br.com.baronheid.model.entity.wrapper.Users;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.WebApplicationException;

//Path that the service will listen to
public class UserService {

    private final UserDAO userDAO = new UserDAOImpl();

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
}
