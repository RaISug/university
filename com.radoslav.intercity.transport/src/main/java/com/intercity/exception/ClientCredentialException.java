package com.intercity.exception;

public class ClientCredentialException extends RestApiException {
  
  private static final long serialVersionUID = 1427345543668267178L;
  
  public ClientCredentialException(String message, int statusCode) {
    super(message, statusCode);
  }
  
}
