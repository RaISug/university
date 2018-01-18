package com.intercity.exception;

public class EntityAlreadyExistException extends RestApiException {
  private static final long serialVersionUID = 1L;
  
  public EntityAlreadyExistException(String exceptionMessage, int statusCode) {
    super(exceptionMessage, statusCode);
  }

}
