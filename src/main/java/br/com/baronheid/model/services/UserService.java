package main.java.br.com.baronheid.model.services;

import main.java.br.com.baronheid.model.dao.UserDAOImpl;
import main.java.br.com.baronheid.model.dao.interfaces.UserDAO;
import main.java.br.com.baronheid.model.entity.User;
import main.java.br.com.baronheid.model.entity.wrapper.Users;

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
        users = new Users(userDAO.listObjects());
        return users;
    }

    public User searchUserById(Integer id) {
        User search = null;
        search = userDAO.search(id);
        return search;
    }

    public void deleteUserById(Integer id) {
        try {
            userDAO.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Response createUser(User user) {
        User userToCreate = userDAO.register(user);
        URI uri = UriBuilder.fromPath("/users/{id}")
                .build(userToCreate.getUserId());
        return Response.created(uri).entity(userToCreate).build();
    }
}
