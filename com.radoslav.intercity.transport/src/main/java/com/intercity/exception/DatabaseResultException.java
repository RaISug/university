package com.intercity.exception;

public class DatabaseResultException extends RestApiException {
  private static final long serialVersionUID = 1L;
  
  public DatabaseResultException(String exceptionMessage, int statusCode) {
    super(exceptionMessage, statusCode);
  }
}
