package main.java.br.com.baronheid.controller;


import main.java.br.com.baronheid.model.entity.User;
import main.java.br.com.baronheid.model.entity.wrapper.Users;
import main.java.br.com.baronheid.model.services.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/control")
public class UserController {

    private final UserService userService = new UserService();

    @GET
    @Path("/hello")
    @Produces("text/plain;charset=utf-8")
    public String helloWorld(){
        return userService.helloWorldRequest();
    }

    @GET
    @Path("/users")
    public Users getAllUsers(){
        return userService.getAllUsers();
    }

    @GET
    @Path("/users/{id}")
    public User searchUserById(@PathParam("id") Integer id) {
        return userService.searchUserById(id);
    }

    @DELETE
    @Path("/users/{id}")
    public void removeUserById(@PathParam("id") Integer id) {
        userService.deleteUserById(id);
    }

    @POST
    @Path("/users")
    public Response createUser(User user) {
        return userService.createUser(user);
    }
}
