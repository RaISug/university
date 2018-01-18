package com.intercity.application.pojo;

public class CsrfBean {

  private String headerName;
  private String xsrfToken;
  
  public String getHeaderName() {
    return headerName;
  }
  
  public void setHeaderName(String headerName) {
    this.headerName = headerName;
  }
  
  public String getXsrfToken() {
    return xsrfToken;
  }
  
  public void setXsrfToken(String xsrfToken) {
    this.xsrfToken = xsrfToken;
  }
  
}
