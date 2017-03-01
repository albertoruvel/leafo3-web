package com.leafo3.exception.mapper;

import com.leafo3.data.dto.ErrorResponse;
import com.leafo3.exception.LeafO3Exception;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Alberto Rubalcaba <arubalcaba@24hourfit.com>
 */
@Provider
public class LeafO3ExceptionMapper implements ExceptionMapper<LeafO3Exception>{

    @Inject
    private Logger log;
    
    public Response toResponse(LeafO3Exception exception) {
        log.severe(exception.getMessage());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("Internal Server Error", "500"))
                .build();
    }
    
}
