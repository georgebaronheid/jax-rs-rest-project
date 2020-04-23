package main.java.br.com.baronheid.controller;


import main.java.br.com.baronheid.model.services.UserService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/control")
public class UserController {
    
    private final UserService userService = new UserService();

    @GET
    @Path("/hello")
    @Produces("text/plain;charset=utf-8")
    public String helloWorld(){
        return userService.helloWorldRequest();
    }
}
