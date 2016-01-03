package com.moutain.microclimate.service.exceptions;

public class InternalServerErrorException extends MicroclimateException {

  private static final long serialVersionUID = 1L;
  private static final int HTTP_INTERNAL_SERVER_ERROR = 500;

  public InternalServerErrorException(String exceptionMessage) {
    super(exceptionMessage);
  }
  
  public InternalServerErrorException(String exceptionMessage, Throwable exception) {
    super(exceptionMessage, exception);
  }

  @Override
  public int getStatusCode() {
    return HTTP_INTERNAL_SERVER_ERROR;
  }

}
