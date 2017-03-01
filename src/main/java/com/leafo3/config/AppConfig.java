package com.leafo3.config;

import java.util.logging.Logger;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author Alberto Rubalcaba <arubalcaba@24hourfit.com>
 */
@ApplicationPath("rest")
public class AppConfig extends Application{
    @Produces
    public Logger logBean(InjectionPoint ip){
        return Logger.getLogger(ip.getMember().getDeclaringClass().getName());
    }
}
