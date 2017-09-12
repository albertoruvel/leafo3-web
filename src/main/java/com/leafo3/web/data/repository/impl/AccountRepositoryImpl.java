package com.leafo3.web.data.repository.impl;

import com.leafo3.web.data.entity.Account;
import com.leafo3.web.data.repository.AccountRepository;

import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

@Stateless
public class AccountRepositoryImpl extends AbstractRepository implements AccountRepository {
    @Override
    public void createNewAccount(Account account) throws PersistenceException {
        entityManager.persist(account);
    }

    @Override
    public Account findAccountWithCredentials(String email, String password) throws PersistenceException {
        return (Account)entityManager.createQuery("SELECT a FROM Account a WHERE a.email = :email AND a.password = :pass")
                .setParameter("email", email)
                .setParameter("pass", password)
                .getSingleResult();
    }

    @Override
    public Account findAccountWithAuthenticationToken(String token) throws PersistenceException {
        return (Account)entityManager.createQuery("SELECT a FROM Account a WHERE a.authenticationToken = :authToken")
                .setParameter("authToken", token)
                .getSingleResult();
    }
}
