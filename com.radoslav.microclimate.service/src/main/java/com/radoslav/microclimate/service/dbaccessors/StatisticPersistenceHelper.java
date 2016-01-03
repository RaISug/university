package com.radoslav.microclimate.service.dbaccessors;

import java.util.List;

import javax.persistence.EntityManager;

import com.radoslav.microclimate.service.beans.StatisticBean;
import com.radoslav.microclimate.service.entities.Statistic;
import com.radoslav.microclimate.service.exceptions.InternalServerErrorException;

public class StatisticPersistenceHelper {

  private EntityManager entityManager;

  public StatisticPersistenceHelper(EntityManager entityManager) {
    this.entityManager = entityManager;
  }
  
  public List<Statistic> getAll() throws InternalServerErrorException {
    try {
      return Statistic.findAll(entityManager);
    } catch (Exception exception) {
      throw new InternalServerErrorException("Unexpected exception occurred while trying to retrieve all newEntry data.");
    }
  }

  public void persistStatisticData(final StatisticBean statisticBean) throws Exception {
    TransactionHandler.handleTransaction(entityManager, new CriticalSection<Statistic>() {

      private Statistic newEntry = null;
      
      public void executeQuery() {
        newEntry = createStatisticInstance(statisticBean);
        Statistic.persistEntity(entityManager, newEntry);
      }
      
      public Statistic getResult() {
        return newEntry;
      }
      
    });
  }

  private Statistic createStatisticInstance(final StatisticBean statisticBean) {
    float temperature = Float.parseFloat(statisticBean.getTemperature());
    float rainfall = Float.parseFloat(statisticBean.getRainfall());
    float humidity = Float.parseFloat(statisticBean.getHumidity());
    float snowCover = Float.parseFloat(statisticBean.getSnowCover());
    float windSpeed = Float.parseFloat(statisticBean.getWindSpeed());
    String type = statisticBean.getType();
    
    return new Statistic(temperature, rainfall, humidity, snowCover, windSpeed, type);
  }
}
