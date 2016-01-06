package com.radoslav.microclimate.service.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Query;
import javax.persistence.Table;

@Entity
@Table(name="statistics")
@NamedQueries({
    @NamedQuery(name="Statistic.findAll", query="SELECT s FROM Statistic s"),
    @NamedQuery(name="Statistic.updateEntity", query="UPDATE Statistic s SET s.temperature = :temperature,"
                    + " s.rainfall = :rainfall, s.humidity = :humidity, s.snowCover = :snowCover,"
                    + " s.windSpeed = :windSpeed, s.type = :type WHERE s.id = :id"),
    @NamedQuery(name="Statistic.deleteEntityById", query="DELETE FROM Statistic s WHERE s.id = :id")
})
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

  public Statistic() {
    
  }
  
  public Statistic(float temperature, float rainfall, float humidity, float snowCover, float windSpeed, String type) {
    this.temperature = temperature;
    this.rainfall = rainfall;
    this.humidity = humidity;
    this.snowCover = snowCover;
    this.windSpeed = windSpeed;
    this.type = type;
  }
  
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
  
  public static List<Statistic> findAll(EntityManager entityManager) {
    return entityManager.createNamedQuery("Statistic.findAll", Statistic.class).getResultList();
  }
  
  public static void persistEntity(EntityManager entityManager, Statistic newEntry) {
    entityManager.persist(newEntry);
  }

  public static Statistic findStatisticById(EntityManager entityManager, long id) {
    return entityManager.find(Statistic.class, id);
  }
  
  public static int updateEntity(EntityManager entityManager, long id, float temperature, float rainfall,
      float humidity, float snowCover, float windSpeed, String type) {
    Query query = entityManager
                    .createNamedQuery("Statistic.updateEntity")
                    .setParameter(":id", id)
                    .setParameter(":temperature", temperature)
                    .setParameter(":rainfall", rainfall)
                    .setParameter(":humidity", humidity)
                    .setParameter(":snowCover", snowCover)
                    .setParameter(":windSpeed", windSpeed)
                    .setParameter(":type", type);
    
    return query.executeUpdate();
  }
  
  public static int deleteEntity(EntityManager entityManager, long id) {
    Query query = entityManager.createNamedQuery("Statistic.deleteEntityById").setParameter("id", id);
    
    return query.executeUpdate();
  }
  
}
