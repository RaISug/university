package com.moutain.microclimate.service.exceptions;

public class BadRequestException extends MicroclimateException {

  private static final long serialVersionUID = 1L;
  private static final int SC_HTTP_BAD_REQUEST = 400;

  public BadRequestException(String exceptionMessage) {
    super(exceptionMessage);
  }
  
  public BadRequestException(String exceptionMessage, Throwable exception) {
    super(exceptionMessage, exception);
  }

  @Override
  public int getStatusCode() {
    return SC_HTTP_BAD_REQUEST;
  }

}
