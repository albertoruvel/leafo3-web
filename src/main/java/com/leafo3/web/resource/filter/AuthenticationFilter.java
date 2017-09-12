package com.leafo3.web.resource.filter;

import com.leafo3.web.core.security.AuthenticatedUser;
import com.leafo3.web.data.repository.AccountRepository;
import com.leafo3.web.resource.config.annotation.Authenticated;
import com.leafo3.web.resource.config.annotation.AuthenticationDetails;
import com.leafo3.web.service.AccountService;
import com.leafo3.web.service.AuthenticationService;
import org.apache.log4j.Logger;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Authenticated
public class AuthenticationFilter implements ContainerRequestFilter {

    private final Logger log = Logger.getLogger(getClass());

    @Inject
    private AuthenticationService authenticationService;

    @Inject
    private AccountRepository accountRepository;

    @Inject
    @AuthenticationDetails
    private Event<AuthenticatedUser> authenticatedUserId;

    private static final String AUTHENTICATION_HEADER_PREFIX = "Bearer ";

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        String authenticationToken = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authenticationToken != null && authenticationToken.startsWith(AUTHENTICATION_HEADER_PREFIX)) {
            final String token = authenticationToken.split(AUTHENTICATION_HEADER_PREFIX)[1];
            try {
                //check if access token exists
                if (authenticationService.isAccessTokenValid(token)) {
                    //check access token expiration date
                    setSecurityContext(false, token);
                    return;
                } else{
                    abortUnauthorized(containerRequestContext);
                }
            } catch (NoResultException ex) {
                abortUnauthorized(containerRequestContext);
            } catch (Exception ex) {
                log.error("Error searching user token", ex);
                abortUnauthorized(containerRequestContext);
            }
        } else {
            abortUnauthorized(containerRequestContext);
        }
    }

    private void setSecurityContext(boolean isAdmin, String securityToken) {
        final String userId = accountRepository.findAccountWithAuthenticationToken(securityToken).getId();
        final AuthenticatedUser authenticatedUser = new AuthenticatedUser(userId);
        //fire authentication details event
        authenticatedUserId.fire(authenticatedUser);
    }

    private void abortUnauthorized(ContainerRequestContext containerRequestContext) {
        containerRequestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .build()
        );
    }
}
