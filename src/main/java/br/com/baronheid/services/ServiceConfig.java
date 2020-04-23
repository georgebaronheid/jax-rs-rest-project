package main.java.br.com.baronheid.services;

import org.glassfish.jersey.server.ResourceConfig;

public class ServiceConfig extends ResourceConfig   {
    public ServiceConfig(){
//        Jersey will use the following package to search for suppliers of service to the requests
        packages("br.com.baronheid.services");
    }
}
