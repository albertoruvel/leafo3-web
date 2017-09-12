package com.leafo3.web.resource.producer;

import com.leafo3.web.core.security.AuthenticatedUser;
import com.leafo3.web.data.repository.AccountRepository;
import com.leafo3.web.resource.config.annotation.AuthenticationDetails;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

@RequestScoped
public class AuthenticatedUserProducer {

    @Produces
    @RequestScoped
    @AuthenticationDetails
    private AuthenticatedUser currentAuthenticatedUser;

    @Inject
    private AccountRepository accountRepository;

    public void handleAuthenticationEvent(@Observes @AuthenticationDetails AuthenticatedUser authenticatedUser) {
        if (authenticatedUser == null) {
            this.currentAuthenticatedUser = new AuthenticatedUser(null);
        } else {
            this.currentAuthenticatedUser = authenticatedUser;
        }

    }

}
