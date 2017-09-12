package com.leafo3.web.resource.config;

import com.leafo3.web.resource.filter.AuthenticationFilter;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Priorities;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Configurable;
import javax.ws.rs.core.Context;

@ApplicationPath("rest")
public class AppConfig extends Application{

    public AppConfig(@Context Configurable configurable){
        configurable.register(AuthenticationFilter.class, Priorities.AUTHENTICATION);
    }
}
