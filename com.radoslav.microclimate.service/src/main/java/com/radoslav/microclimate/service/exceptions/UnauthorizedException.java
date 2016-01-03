package com.radoslav.microclimate.service.exceptions;


public class UnauthorizedException extends MicroclimateException {

  private static final long serialVersionUID = 1L;
  private static final int HTTP_UNAUTHORIZED = 401;

  public UnauthorizedException(String exceptionMessage) {
    super(exceptionMessage);
  }
  
  public UnauthorizedException(String exceptionMessage, Throwable exception) {
    super(exceptionMessage, exception);
  }

  @Override
  public int getStatusCode() {
    return HTTP_UNAUTHORIZED;
  }

}
