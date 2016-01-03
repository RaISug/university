package com.radoslav.microclimate.service.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="statistics")
public class Statistic implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE)
  private long id;
  
  private float temperature;
  
  private float rainfall;
  
  private float humidity;
  
  @Column(name="snow_cover")
  private float snowCover;
  
  @Column(name="wind_speed")
  private float windSpeed;
  
  private String type;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public float getTemperature() {
    return temperature;
  }

  public void setTemperature(float temperature) {
    this.temperature = temperature;
  }

  public float getRainfall() {
    return rainfall;
  }

  public void setRainfall(float rainfall) {
    this.rainfall = rainfall;
  }

  public float getHumidity() {
    return humidity;
  }

  public void setHumidity(float humidity) {
    this.humidity = humidity;
  }

  public float getSnowCover() {
    return snowCover;
  }

  public void setSnowCover(float snowCover) {
    this.snowCover = snowCover;
  }

  public float getWindSpeed() {
    return windSpeed;
  }

  public void setWindSpeed(float windSpeed) {
    this.windSpeed = windSpeed;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
  
}
