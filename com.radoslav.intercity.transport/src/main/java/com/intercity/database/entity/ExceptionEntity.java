package com.intercity.database.entity;

public class ExceptionEntity {

  private int statusCode;
  private String exceptionDescription;
  
  public int getStatusCode() {
    return statusCode;
  }
  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }
  public String getExceptionDescription() {
    return exceptionDescription;
  }
  public void setExceptionDescription(String exceptionDescription) {
    this.exceptionDescription = exceptionDescription;
  }
  
}
