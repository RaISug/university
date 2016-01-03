package com.radoslav.microclimate.service.exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.radoslav.microclimate.service.beans.ExceptionEntity;
import com.radoslav.microclimate.service.helpers.Constants;

@Provider
public class MicroclimateExceptionMapper implements ExceptionMapper<Exception>{

  public Response toResponse(Exception exception) {
    ExceptionEntity entity = new ExceptionEntity();
    
    if (exception instanceof MicroclimateException) {
      MicroclimateException microclimateException = (MicroclimateException) exception;
      
      entity.setStatusCode(microclimateException.getStatusCode());
      entity.setExceptionDescription(microclimateException.getMessage());
      
      return Response
                .status(microclimateException.getStatusCode())
                .header(Constants.ERROR_DESCRIPTION_HEADER, microclimateException.getMessage())
                .type(MediaType.APPLICATION_JSON)
                .entity(entity)
                .build();
    }
    
    entity.setStatusCode(500);
    entity.setExceptionDescription(exception.getMessage());
    
    return Response
              .status(Status.INTERNAL_SERVER_ERROR)
              .header(Constants.ERROR_DESCRIPTION_HEADER, exception.getMessage() == null ? "Unexpected exception occured at backend." : exception.getMessage())
              .type(MediaType.APPLICATION_JSON)
              .entity(entity)
              .build();
  }

}
