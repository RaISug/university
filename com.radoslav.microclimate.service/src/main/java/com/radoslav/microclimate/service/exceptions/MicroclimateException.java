package com.radoslav.microclimate.service.exceptions;

public abstract class MicroclimateException extends Exception {

  private static final long serialVersionUID = 1L;
  
  public MicroclimateException(String exceptionMessage) {
    super(exceptionMessage);
  }
  
  public MicroclimateException(String exceptionMessage, Throwable exception) {
    super(exceptionMessage, exception);
  }

  public abstract int getStatusCode();
}
