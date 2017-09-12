package com.leafo3.web.service;

import com.leafo3.dto.request.AuthenticationRequest;
import com.leafo3.web.core.security.ApplicationScopedRole;

import javax.persistence.NoResultException;
import javax.ws.rs.core.Response;
import java.util.List;

public interface AuthenticationService {
    public Response authenticate(AuthenticationRequest request)throws IllegalArgumentException;
    public void checkUserPermissions(List<ApplicationScopedRole> securityRoles, String userId) throws Exception;
    public boolean isAccessTokenValid(String token) throws NoResultException;
}
