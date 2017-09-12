package com.leafo3.web.resource;

import com.leafo3.dto.request.AuthenticationRequest;
import com.leafo3.web.service.AuthenticationService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("oauth")
public class SecurityResource {

    @Inject
    private AuthenticationService authenticationService;

    @Path("authenticate")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticate(AuthenticationRequest request){
        return authenticationService.authenticate(request);
    }
}
