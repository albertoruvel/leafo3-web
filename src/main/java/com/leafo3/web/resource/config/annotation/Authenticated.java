package com.leafo3.web.resource.config.annotation;

import com.leafo3.web.core.security.ApplicationScopedRole;
import javax.ws.rs.NameBinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@NameBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Authenticated {
    ApplicationScopedRole[] roles() default {};
}
