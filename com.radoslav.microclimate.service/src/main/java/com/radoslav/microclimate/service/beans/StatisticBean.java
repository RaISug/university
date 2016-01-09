package com.radoslav.microclimate.service.beans;

import java.util.Date;


public class StatisticBean {

  private long id;
  
  private float temperature;
  
  private float rainfall;
  
  private float humidity;
  
  private float snowCover;
  
  private float windSpeed;
  
  private int weather;
  
  private double latitude;
  
  private double longitude;
  
  private Date date;

  public long getId() {
    return id;
  }
  
  public float getTemperature() {
    return temperature;
  }

  public float getRainfall() {
    return rainfall;
  }

  public float getHumidity() {
    return humidity;
  }

  public float getSnowCover() {
    return snowCover;
  }

  public float getWindSpeed() {
    return windSpeed;
  }

  public int getWeather() {
    return weather;
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public Date getDate() {
    return date;
  }
  
}
