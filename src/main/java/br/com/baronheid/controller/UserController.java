package main.java.br.com.baronheid.controller;


import main.java.br.com.baronheid.model.entity.User;
import main.java.br.com.baronheid.model.entity.wrapper.Users;
import main.java.br.com.baronheid.model.services.UserService;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/control")
@PermitAll
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
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
    @RolesAllowed("guest")
    public Users getAllUsers(){
        return userService.getAllUsers();
    }

    @GET
    @Path("/users/{id}")
    @RolesAllowed("guest")
    public User searchUserById(@PathParam("id") Integer id) {
        return userService.searchUserById(id);
    }

    @DELETE
    @Path("/users/{id}")
    @RolesAllowed("user")
    public void removeUserById(@PathParam("id") Integer id) {
        userService.deleteUserById(id);
    }

    @POST
    @Path("/users/")
    @RolesAllowed("user")
    public Response createUser(User user) {
        return userService.createUser(user);
    }

    @PUT
    @Path("/users/{id}")
    @RolesAllowed("user")
    public User updateUser(@PathParam("id") Integer id, User receivedUser) {
        return userService.updateUser(id, receivedUser);
    }
}
