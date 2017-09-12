package com.leafo3.web.service;

import com.leafo3.dto.request.CreateAccount;

import javax.persistence.PersistenceException;
import javax.ws.rs.core.Response;

public interface AccountService {
    public Response createNewAccount(CreateAccount createAccount)throws PersistenceException;
}
