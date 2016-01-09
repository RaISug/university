package com.radoslav.microclimate.service.beans;

import java.util.Date;


public class StatisticBean {

  private long id;
  
  private float temperature;
  
  private float rainfall;
  
  private float humidity;
  
  private float snowCover;
  
  private float windSpeed;
  
  private String weather;
  
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

  public String getWeather() {
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

  public void setId(long id) {
    this.id = id;
  }

  public void setTemperature(float temperature) {
    this.temperature = temperature;
  }

  public void setRainfall(float rainfall) {
    this.rainfall = rainfall;
  }

  public void setHumidity(float humidity) {
    this.humidity = humidity;
  }

  public void setSnowCover(float snowCover) {
    this.snowCover = snowCover;
  }

  public void setWindSpeed(float windSpeed) {
    this.windSpeed = windSpeed;
  }

  public void setWeather(String weather) {
    this.weather = weather;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public void setDate(Date date) {
    this.date = date;
  }
  
}
