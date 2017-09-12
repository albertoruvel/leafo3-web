package com.leafo3.web.data.repository;

import com.leafo3.web.data.entity.Account;

import javax.persistence.PersistenceException;

public interface AccountRepository {
    public void createNewAccount(Account account)throws PersistenceException;
    public Account findAccountWithCredentials(String email, String password) throws PersistenceException;
    public Account findAccountWithAuthenticationToken(String token)throws PersistenceException;
}
