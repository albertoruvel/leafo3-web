package com.leafo3.web.core.exception.mapper;

import com.leafo3.dto.response.error.ErrorCode;
import com.leafo3.dto.response.error.InternalServerError;
import com.leafo3.web.core.util.Leafo3Utils;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Date;

@Provider
public class PersistenceExceptionMapper implements ExceptionMapper<PersistenceException>{

    @Inject
    private Leafo3Utils leafo3Utils;

    private final Logger log = Logger.getLogger(getClass());

    @Override
    public Response toResponse(PersistenceException e) {
        log.error("Caught unhandled PersistenceException", e);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new InternalServerError("There has been an error, our bad =(",
                        leafo3Utils.formatDate(new Date()), ErrorCode.PERSISTENCE))
                .build();
    }
}
