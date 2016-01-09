package com.radoslav.microclimate.service.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.eclipse.persistence.annotations.Convert;
import org.eclipse.persistence.annotations.Converter;

import com.radoslav.microclimate.service.beans.StatisticBean;
import com.radoslav.microclimate.service.beans.Weather;
import com.radoslav.microclimate.service.converters.WeatherConverter;

@Entity
@Table(name="statistics")
@NamedQueries({
    @NamedQuery(name="Statistic.findAll", query="SELECT s FROM Statistic s"),
    @NamedQuery(name="Statistic.updateEntity", query="UPDATE Statistic s SET s.temperature = :temperature,"
                    + " s.rainfall = :rainfall, s.humidity = :humidity, s.snowCover = :snowCover,"
                    + " s.windSpeed = :windSpeed, s.weather = :weather, s.latitude = :latitude,"
                    + " s.longitude = :longitude, s.gatheredOn = :gatheredOn, s.updatedOn = :updatedOn"
                    + " WHERE s.id = :id"),
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
  
  @Convert(value="weather")
  @Converter(name = "weather", converterClass = WeatherConverter.class)
  @Enumerated(EnumType.STRING)
  private Weather weather;

  private double latitude;
  
  private double longitude;
  
  @Temporal(TemporalType.DATE)
  @Column(name="gathered_on")
  private Date gatheredOn;
  
  @Temporal(TemporalType.DATE)
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
    this.weather = Weather.fromString(statisticBean.getWeather());
    this.latitude = statisticBean.getLatitude();
    this.longitude = statisticBean.getLongitude();
    this.gatheredOn = statisticBean.getDate();
    this.updatedOn = Calendar.getInstance().getTime();
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

  public Weather getWeather() {
    return weather;
  }

  public void setWeather(Weather weather) {
    this.weather = weather;
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
    
    if (statisticBean.getId() != 0L) {
      query += " AND id = ?1";
    }
    
    if (statisticBean.getTemperature() != 0f) {
      query += " AND temperature = ?2";
    }
    
    if (statisticBean.getRainfall() != 0f) {
      query += " AND rainfall = ?3";
    }
    
    if (statisticBean.getHumidity() != 0f) {
      query += " AND humidity = ?4";
    }
    
    if (statisticBean.getSnowCover() != 0f) {
      query += " AND snow_cover = ?5";
    }
    
    if (statisticBean.getWindSpeed() != 0f) {
      query += " AND wind_speed = ?6";
    }
    
    if (statisticBean.getWeather() != null) {
      query += " AND weather = ?7";
    }
    
    if (statisticBean.getLatitude() != 0L) {
      query += " AND latitude = ?8";
    }
    
    if (statisticBean.getLongitude() != 0L) {
      query += " AND longitude = ?9";
    }
    
    if (statisticBean.getDate() != null) {
      query += " AND gathered_on = ?10";
    }
    
    return entityManager
            .createNativeQuery(query, Statistic.class)
            .setParameter(1, statisticBean.getId())
            .setParameter(2, statisticBean.getTemperature())
            .setParameter(3, statisticBean.getRainfall())
            .setParameter(4, statisticBean.getHumidity())
            .setParameter(5, statisticBean.getSnowCover())
            .setParameter(6, statisticBean.getWindSpeed())
            .setParameter(7, statisticBean.getWeather())
            .setParameter(8, statisticBean.getLatitude())
            .setParameter(9, statisticBean.getLongitude())
            .setParameter(10, statisticBean.getDate())
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
                    .setParameter("weather", Weather.fromString(statisticBean.getWeather()))
                    .setParameter("latitude", statisticBean.getLatitude())
                    .setParameter("longitude", statisticBean.getLongitude())
                    .setParameter("gatheredOn", statisticBean.getDate())
                    .setParameter("updatedOn", Calendar.getInstance().getTime());
    
    return query.executeUpdate();
  }
  
  public static int deleteEntity(EntityManager entityManager, long id) {
    Query query = entityManager.createNamedQuery("Statistic.deleteEntityById").setParameter("id", id);
    
    return query.executeUpdate();
  }
  
}
