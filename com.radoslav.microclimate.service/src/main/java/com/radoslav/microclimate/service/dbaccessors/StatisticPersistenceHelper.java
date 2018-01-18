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
  
  public List<Statistic> findStatisticData(StatisticBean statisticBean) throws InternalServerErrorException {
    try {
      return Statistic.find(entityManager, statisticBean);
    } catch (Exception exception) {
      throw new InternalServerErrorException("Unexpected exception occurred "
          + "while trying to retrieve data. Additional information: [" + exception.getMessage() + "]");
    }
  }

  public void persistStatisticData(final StatisticBean statisticBean) throws Exception {
    TransactionHandler.handleTransaction(entityManager, new CriticalSection<Statistic>() {

      private Statistic newEntry = null;
      
      public void executeQuery() throws Exception {
        try {
          newEntry = new Statistic(statisticBean);
          Statistic.persistEntity(entityManager, newEntry);
        } catch (Exception exception) {
          throw new InternalServerErrorException("Failed to create statistic entry.");
        }
      }
      
      public Statistic getResult() {
        return newEntry;
      }
      
    });
  }

  public void updateStatisticData(final StatisticBean statisticBean, final long id) throws Exception {
    TransactionHandler.handleTransaction(entityManager, new CriticalSection<Integer>() {

      private int updatedRows = 0; 
      
      public void executeQuery() throws Exception {
        updatedRows = Statistic.updateEntity(entityManager, statisticBean, id);
      }
      
      public Integer getResult() {
        return updatedRows;
      }
      
    });
  }

  public void deleteStatisticData(final long id) throws Exception {
    TransactionHandler.handleTransaction(entityManager, new CriticalSection<Integer>() {

      private int deletedRows = 0; 
      
      public void executeQuery() {
        deletedRows = Statistic.deleteEntity(entityManager, id);
      }
      
      public Integer getResult() {
        return deletedRows;
      }
      
    });
  }

}
