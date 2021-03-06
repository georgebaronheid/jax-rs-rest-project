package main.java.br.com.baronheid.config.jersey;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

public class ServiceConfig extends ResourceConfig   {
    public ServiceConfig(){
//        Jersey will use the following package to search for suppliers of service to the requests
        packages("br.com.baronheid.model.services");
        register(RolesAllowedDynamicFeature.class);
    }
}
