package com.intercity.exception;

public class RestApiException extends Exception {

  private static final long serialVersionUID = 1L;
  private int statusCode;

  public RestApiException(String exceptionMessage, int statusCode) {
    super(exceptionMessage);
    this.statusCode = statusCode;
  }
  
  public RestApiException(String exceptionMessage, int statusCode, Throwable exception) {
    super(exceptionMessage, exception);
    this.statusCode = statusCode;
  }

  public int getStatusCode() {
    return this.statusCode;
  }
}
