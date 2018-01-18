package com.intercity.application.pojo;


public class SearchBean {

  private String departureTime;
  private String startDestination;
  private String endDestination;
  private String transportType;

  public String getDepartureTime() {
    return departureTime;
  }

  public void setDepartureTime(String departureTime) {
    this.departureTime = departureTime;
  }
  
  public String getStartDestination() {
    return startDestination;
  }
  
  public void setStartDestination(String startDestination) {
    this.startDestination = startDestination;
  }
  
  public String getEndDestination() {
    return endDestination;
  }
  
  public void setEndDestination(String endDestination) {
    this.endDestination = endDestination;
  }

  public String getTransportType() {
    return transportType;
  }

  public void setTransportType(String transportType) {
    this.transportType = transportType;
  }
  
}
