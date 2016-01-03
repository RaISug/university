package com.radoslav.microclimate.service.dbaccessors;

import java.util.List;

import javax.persistence.EntityManager;

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
      throw new InternalServerErrorException("Unexpected exception occurred while trying to retrieve all statistic data.");
    }
  }
}
