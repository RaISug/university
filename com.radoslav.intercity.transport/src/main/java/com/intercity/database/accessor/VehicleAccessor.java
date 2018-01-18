package com.intercity.database.accessor;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.intercity.database.connection.Connector;
import com.intercity.database.entity.Vehicle;
import com.intercity.exception.DatabaseConnectionException;
import com.intercity.exception.DatabaseResultException;

public class VehicleAccessor {

  public Vehicle getVehicleById(int vehicleId) throws DatabaseConnectionException, DatabaseResultException {
    EntityManager entityManager = null;
    try {
      entityManager = Connector.getEntityManager();
      Query query = entityManager
          .createNamedQuery("Vehicle.findVehicleById")
          .setParameter("id", vehicleId);
      Vehicle vehicle = (Vehicle) query.getSingleResult();
      if (vehicle == null) {
        throw new DatabaseResultException("No data.", 204);
      }
      return vehicle;
    } finally {
      if (entityManager != null) {
        entityManager.close();
      }
    }
  }
  
}
