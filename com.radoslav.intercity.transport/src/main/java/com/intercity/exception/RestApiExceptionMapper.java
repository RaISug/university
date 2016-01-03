package com.intercity.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.intercity.database.entity.ExceptionEntity;

@Provider
public class RestApiExceptionMapper implements ExceptionMapper<Exception>{

  public Response toResponse(Exception exception) {
    ExceptionEntity entity = new ExceptionEntity();
    
    if (exception instanceof RestApiException) {
      RestApiException restApiException = (RestApiException) exception;
      
      entity.setStatusCode(restApiException.getStatusCode());
      entity.setExceptionDescription(restApiException.getMessage());
      
      return Response.status(restApiException.getStatusCode())
          .header("ErrorDescription", restApiException.getMessage())
          .type(MediaType.APPLICATION_JSON).entity(entity).build();
    }
    
    entity.setStatusCode(500);
    entity.setExceptionDescription(exception.getMessage());
    return Response.status(Status.INTERNAL_SERVER_ERROR)
        .header("ErrorDescription", exception.getMessage() == null ? "Backend exception" : exception.getMessage())
        .type(MediaType.APPLICATION_JSON).entity(entity).build();
  }

}
