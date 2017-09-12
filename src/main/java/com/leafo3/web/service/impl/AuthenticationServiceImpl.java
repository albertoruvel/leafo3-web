package com.leafo3.web.service.impl;

import com.leafo3.dto.request.AuthenticationRequest;
import com.leafo3.dto.response.AuthenticationResult;
import com.leafo3.web.core.crypt.EncryptionService;
import com.leafo3.web.core.security.ApplicationScopedRole;
import com.leafo3.web.core.util.Leafo3Utils;
import com.leafo3.web.data.entity.Account;
import com.leafo3.web.data.repository.AccountRepository;
import com.leafo3.web.service.AuthenticationService;
import org.apache.log4j.Logger;

import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

@Singleton
public class AuthenticationServiceImpl implements AuthenticationService {

    @Inject
    private AccountRepository accountRepository;

    @Inject
    private EncryptionService encryptionService;

    private final Logger log = Logger.getLogger(getClass());

    @Inject
    private Leafo3Utils leafo3Utils;

    @Override
    public Response authenticate(AuthenticationRequest request) throws IllegalArgumentException {
        try {
            Account account = accountRepository.findAccountWithCredentials(request.getEmail(), request.getPassword());
            if (account != null) {
                return Response.ok(new AuthenticationResult(account.getToken(), leafo3Utils.formatDate(new Date()), true)).build();
            } else {
                return unauthorized();
            }

        } catch (PersistenceException ex) {
            return unauthorized();
        } catch(EJBTransactionRolledbackException ex){
            return unauthorized();
        }

    }

    private Response unauthorized(){
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(new AuthenticationResult(null, leafo3Utils.formatDate(new Date()), false))
                .build();
    }

    @Override
    public void checkUserPermissions(List<ApplicationScopedRole> securityRoles, String userId) throws Exception {

    }

    @Override
    public boolean isAccessTokenValid(String token) throws NoResultException {
        return accountRepository.findAccountWithAuthenticationToken(token)  != null;
    }
}
