package com.radoslav.microclimate.service.entities;

import java.io.Serializable;
import java.util.Date;
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

import com.radoslav.microclimate.service.beans.StatisticBean;

@Entity
@Table(name="statistics")
@NamedQueries({
    @NamedQuery(name="Statistic.findAll", query="SELECT s FROM Statistic s"),
    @NamedQuery(name="Statistic.updateEntity", query="UPDATE Statistic s SET s.temperature = :temperature,"
                    + " s.rainfall = :rainfall, s.humidity = :humidity, s.snowCover = :snowCover,"
                    + " s.windSpeed = :windSpeed, s.type = :type, s.latitude = :latitude,"
                    + " s.longitude = :longitude, s.gatheredOn = :date WHERE s.id = :id"),
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
  
  private int type;

  private double latitude;
  
  private double longitude;
  
  @Column(name="gathered_on")
  private Date gatheredOn;
  
  @Column(name="updated_on")
  private Date updatedOn;
  
  public Statistic() {
    
  }
  
  public Statistic(StatisticBean statisticBean) throws Exception {
    this.temperature = statisticBean.getTemperature();
    this.rainfall = statisticBean.getRainfall();
    this.humidity = statisticBean.getHumidity();
    this.snowCover = statisticBean.getSnowCover();
    this.windSpeed = statisticBean.getWindSpeed();
    this.type = statisticBean.getType();
    this.latitude = statisticBean.getLatitude();
    this.longitude = statisticBean.getLongitude();
    this.gatheredOn = statisticBean.getDate();
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

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }
  
  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public Date getGatheredOn() {
    return gatheredOn;
  }

  public void setGatheredOn(Date gatheredOn) {
    this.gatheredOn = gatheredOn;
  }

  public Date getUpdatedOn() {
    return updatedOn;
  }

  public void setUpdatedOn(Date updatedOn) {
    this.updatedOn = updatedOn;
  }

  @SuppressWarnings("unchecked")
  public static List<Statistic> find(EntityManager entityManager, StatisticBean statisticBean) throws Exception {
    String query = "SELECT * FROM statistics WHERE 1 = 1";
    
    if (statisticBean.getTemperature() != 0f) {
      query += " AND temperature = :temperature";
    }
    
    if (statisticBean.getRainfall() != 0f) {
      query += " AND rainfall = :rainfall";
    }
    
    if (statisticBean.getHumidity() != 0f) {
      query += " AND humidity = :humidity";
    }
    
    if (statisticBean.getSnowCover() != 0f) {
      query += " AND snow_cover = :snowCover";
    }
    
    if (statisticBean.getWindSpeed() != 0f) {
      query += " AND wind_speed = :windSpeed";
    }
    
    if (statisticBean.getType() != 0) {
      query += " AND type = :type";
    }
    
    if (statisticBean.getLatitude() != 0L) {
      query += " AND latitude = :latitude";
    }
    
    if (statisticBean.getLongitude() != 0L) {
      query += " AND longitude = :longitude";
    }
    
    if (statisticBean.getDate() != null) {
      query += " AND gathered_on = :date";
    }
    
    return entityManager
            .createNativeQuery(query, Statistic.class)
            .setParameter("temperature", statisticBean.getTemperature())
            .setParameter("rainfall", statisticBean.getRainfall())
            .setParameter("humidity", statisticBean.getHumidity())
            .setParameter("snowCover", statisticBean.getSnowCover())
            .setParameter("windSpeed", statisticBean.getWindSpeed())
            .setParameter("type", statisticBean.getType())
            .setParameter("latitude", statisticBean.getLatitude())
            .setParameter("longitude", statisticBean.getLongitude())
            .setParameter("date", statisticBean.getDate())
            .getResultList();
  }
  
  public static void persistEntity(EntityManager entityManager, Statistic newEntry) {
    entityManager.persist(newEntry);
  }

  public static Statistic findStatisticById(EntityManager entityManager, long id) {
    return entityManager.find(Statistic.class, id);
  }
  
  public static int updateEntity(EntityManager entityManager, StatisticBean statisticBean, long id) throws Exception {
    Query query = entityManager
                    .createNamedQuery("Statistic.updateEntity")
                    .setParameter("id", id)
                    .setParameter("temperature", statisticBean.getTemperature())
                    .setParameter("rainfall", statisticBean.getRainfall())
                    .setParameter("humidity", statisticBean.getHumidity())
                    .setParameter("snowCover", statisticBean.getSnowCover())
                    .setParameter("windSpeed", statisticBean.getWindSpeed())
                    .setParameter("type", statisticBean.getType())
                    .setParameter("latitude", statisticBean.getLatitude())
                    .setParameter("longitude", statisticBean.getLongitude())
                    .setParameter("date", statisticBean.getDate());
    
    return query.executeUpdate();
  }
  
  public static int deleteEntity(EntityManager entityManager, long id) {
    Query query = entityManager.createNamedQuery("Statistic.deleteEntityById").setParameter("id", id);
    
    return query.executeUpdate();
  }
  
}
