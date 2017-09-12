package com.leafo3.web.service.impl;

import com.leafo3.dto.request.CreateAccount;
import com.leafo3.dto.response.AuthenticationResult;
import com.leafo3.dto.response.error.ErrorCode;
import com.leafo3.dto.response.error.InternalServerError;
import com.leafo3.web.core.crypt.EncryptionService;
import com.leafo3.web.core.util.Leafo3Utils;
import com.leafo3.web.data.entity.Account;
import com.leafo3.web.data.repository.AccountRepository;
import com.leafo3.web.service.AccountService;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.ws.rs.core.Response;
import java.util.Date;

public class AccountServiceImpl implements AccountService {

    @Inject
    private AccountRepository accountRepository;

    @Inject
    private Leafo3Utils leafo3Utils;

    @Inject
    private EncryptionService encryptionService;

    @Override
    public Response createNewAccount(CreateAccount createAccount) throws PersistenceException {
        if (createAccount == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new InternalServerError("No account data was provided", leafo3Utils.formatDate(new Date()), ErrorCode.INVALID_REQUEST_BODY))
                    .build();
        } else if (StringUtils.isBlank(createAccount.getName())) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new InternalServerError("No account name was provided", leafo3Utils.formatDate(new Date()), ErrorCode.INVALID_REQUEST_PROPERTY))
                    .build();
        } else if (StringUtils.isBlank(createAccount.getIsoCode())){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new InternalServerError("No account ISO Code was provided", leafo3Utils.formatDate(new Date()), ErrorCode.INVALID_REQUEST_PROPERTY))
                    .build();
        } else if(StringUtils.isBlank(createAccount.getEmail())){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new InternalServerError("No account email was provided", leafo3Utils.formatDate(new Date()), ErrorCode.INVALID_REQUEST_PROPERTY))
                    .build();
        } else if(StringUtils.isBlank(createAccount.getFcmId())){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new InternalServerError("No account Firebase cloud messaging id was provided", leafo3Utils.formatDate(new Date()), ErrorCode.INVALID_REQUEST_PROPERTY))
                    .build();
        } else if(StringUtils.isBlank(createAccount.getPassword())){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new InternalServerError("No account password was provided", leafo3Utils.formatDate(new Date()), ErrorCode.INVALID_REQUEST_PROPERTY))
                    .build();
        }
        Account account = new Account();
        account.setName(createAccount.getName());
        account.setEmail(createAccount.getEmail());
        account.setIsoCode(createAccount.getIsoCode());
        account.setFcmId(createAccount.getFcmId());
        account.setPassword(createAccount.getPassword());
        account.setRegistrationDate(leafo3Utils.formatDate(new Date()));
        account.setToken(encryptionService.createAccessToken());
        account.setRegistrationDate(leafo3Utils.formatDate(new Date()));
        accountRepository.createNewAccount(account);

        //todo: add token here, etc
        return Response.ok(new AuthenticationResult(account.getToken(), leafo3Utils.formatDate(new Date()), true))
                .build();
    }
}
