package com.leafo3.web.resource;

import com.leafo3.dto.request.CreateAccount;
import com.leafo3.web.service.AccountService;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("account")
public class AccountResource {

    @Inject
    private AccountService accountService;

    @POST
    @Path("signup")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response signUp(CreateAccount createAccount)throws PersistenceException {
        return accountService.createNewAccount(createAccount);
    }
}
