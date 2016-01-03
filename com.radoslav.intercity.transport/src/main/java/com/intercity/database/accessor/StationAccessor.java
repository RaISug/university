package com.intercity.database.accessor;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.intercity.database.connection.Connector;
import com.intercity.database.entity.Station;
import com.intercity.exception.DatabaseConnectionException;
import com.intercity.exception.DatabaseResultException;

public class StationAccessor {

  public Station getStationById(int stationId) throws DatabaseConnectionException, DatabaseResultException {
    EntityManager entityManager = null;
    try {
      entityManager = Connector.getEntityManager();
      Query query = entityManager.createNamedQuery("Station.getById")
          .setParameter("id", stationId);
      
      Station station = (Station) query.getSingleResult();
      if (station == null) {
        throw new DatabaseResultException("No data.", 204);
      }
      return station;
    } finally {
      if (entityManager != null) {
        entityManager.close();
      }
    }
  }
  
  @SuppressWarnings("unchecked")
  public List<Station> getCities() throws DatabaseConnectionException, DatabaseResultException {
    EntityManager entityManager = null;
    try {
      entityManager = Connector.getEntityManager();
      Query query = entityManager.createNamedQuery("Station.getCities");
      
      List<Station> stations = query.getResultList();
      if (stations == null || stations.size() == 0) {
        throw new DatabaseResultException("No data.", 204);
      }
      return stations;
    } finally {
      if (entityManager != null) {
        entityManager.close();
      }
    }
  }
}
