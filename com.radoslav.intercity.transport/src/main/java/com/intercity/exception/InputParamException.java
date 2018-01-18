package com.intercity.exception;

public class InputParamException extends RestApiException {
  private static final long serialVersionUID = 1L;

  public InputParamException(String exceptionMessage, int statusCode) {
    super(exceptionMessage, statusCode);
  }
}
