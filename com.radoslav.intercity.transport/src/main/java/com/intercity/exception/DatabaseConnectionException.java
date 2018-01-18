package com.intercity.exception;

public class DatabaseConnectionException extends RestApiException {
  private static final long serialVersionUID = -3082431052083326335L;
  
  public DatabaseConnectionException(String message, int statusCode, Throwable exception) {
    super(message, statusCode, exception);
  }

}
