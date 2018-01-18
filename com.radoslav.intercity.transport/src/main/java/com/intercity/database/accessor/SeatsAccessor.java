package com.intercity.database.accessor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.google.gson.Gson;
import com.intercity.application.pojo.ReservedSeats;
import com.intercity.application.pojo.ReservedSeats.SeatData;
import com.intercity.database.connection.Connector;
import com.intercity.database.entity.Seat;
import com.intercity.exception.DatabaseConnectionException;
import com.intercity.exception.DatabaseResultException;

public class SeatsAccessor {
  
  public void reserveSeat(String date, int routeId, int vehicleId, int seatNumber, String clientName, int clientId) throws DatabaseConnectionException, DatabaseResultException {
    EntityManager entityManager = null;
    try {
      Seat seat = getReservedSeatsByRouteAndDate(date, routeId, vehicleId);

      entityManager = Connector.getEntityManager();
      entityManager.getTransaction().begin();
      
      String reservedSeats = seat.getReservedSeats();
      if (isSeatAlreadyReserved(reservedSeats, seatNumber)) {
        throw new DatabaseResultException("Database content conflict.", 409);
      }
      
      ReservedSeats seats = addSeat(reservedSeats, seatNumber, clientName, clientId);
      Query query = entityManager.createNamedQuery("Seat.updateReservedSeats")
          .setParameter("reservedSeats", new Gson().toJson(seats))
          .setParameter("id", seat.getId());
      
      int updatedRows = query.executeUpdate();
      if (updatedRows == 0) {
        throw new DatabaseResultException("Failed to execute update statement.", 500);
      }
      
      entityManager.getTransaction().commit();
    } finally {
      if (entityManager != null) {
        if (entityManager.getTransaction().isActive() == true) {
          entityManager.getTransaction().rollback();
        }
        entityManager.close();
      }
    }
  }
  
  @SuppressWarnings("unchecked")
  public Seat getReservedSeatsByRouteAndDate(String departureDate, int routeId, int vehicleId) throws DatabaseConnectionException, DatabaseResultException {
    EntityManager entityManager = null;
    try {
      entityManager = Connector.getEntityManager();
      
      Query query = entityManager.createNamedQuery("Seat.getRouteReservedSeats")
          .setParameter("date", departureDate)
          .setParameter("routeId", routeId);

      List<Seat> seats = query.getResultList();
      return seats == null || seats.size() == 0 ? createAndReturnSeatEntry(departureDate, routeId, vehicleId) : seats.get(0);
    } finally {
      if (entityManager != null) {
        entityManager.close();
      }
    }
  }
  
  public void removeSeat(String departureDate, int routeId, int vehicleId, int clientId) throws DatabaseConnectionException, DatabaseResultException {
    EntityManager entityManager = null;
    try {
      Seat seat = getReservedSeatsByRouteAndDate(departureDate, routeId, vehicleId);

      entityManager = Connector.getEntityManager();
      entityManager.getTransaction().begin();
      
      String reservedSeats = seat.getReservedSeats();
      ReservedSeats seats = removeSeatFromReservedSeats(reservedSeats, clientId);
      
      Query query = entityManager.createNamedQuery("Seat.updateReservedSeats")
          .setParameter("reservedSeats", new Gson().toJson(seats))
          .setParameter("id", seat.getId());
      
      int updatedRows = query.executeUpdate();
      if (updatedRows == 0) {
        throw new DatabaseResultException("Failed to execute update statement.", 500);
      }
      
      entityManager.getTransaction().commit();
    } finally {
      if (entityManager != null) {
        if (entityManager.getTransaction().isActive() == true) {
          entityManager.getTransaction().rollback();
        }
        entityManager.close();
      }
    }
  }
  
  private ReservedSeats removeSeatFromReservedSeats(String seats, int clientId) {
    ReservedSeats reservedSeats = new Gson().fromJson(seats, ReservedSeats.class);

    if (reservedSeats == null) {
      throw new RuntimeException();
    }
    
    List<SeatData> seatList = reservedSeats.getSeats();
    Iterator<SeatData> iterator = seatList.iterator();
    while (iterator.hasNext()) {
      SeatData seatData = iterator.next();
      if (seatData.getClientId() == clientId) {
        iterator.remove();
      }
    }
    return reservedSeats;
  }
  
  private Seat createAndReturnSeatEntry(String departureDate, int routeId, int vehicleId) throws DatabaseConnectionException, DatabaseResultException {
    EntityManager entityManager = null;
    try {
      entityManager = Connector.getEntityManager();
      entityManager.getTransaction().begin();

      Seat seatEntry = createSeatEntry(departureDate, routeId, vehicleId);
      
      entityManager.persist(seatEntry);
      entityManager.getTransaction().commit();
      
      return getReservedSeatsByRouteAndDate(departureDate, routeId, vehicleId);
    } finally {
      if (entityManager != null) {
        if (entityManager.getTransaction().isActive()) {
          entityManager.getTransaction().rollback();
        }
        entityManager.close();
      }
    }
  }
  
  private Seat createSeatEntry(String departureDate, int routeId, int vehicleId) {
    Seat seat = new Seat();
    seat.setNotes("");
    seat.setRouteId(routeId);
    seat.setReservedSeats("");
    seat.setDate(departureDate);
    seat.setVehicleId(vehicleId);
    return seat;
  }
  
  private boolean isSeatAlreadyReserved(String reservedSeats, int seatNumber) {
    String expression = "\"seatNumber\":" + seatNumber + ",";
    return reservedSeats.contains(expression);
  }

  private ReservedSeats addSeat(String seats, int seatNumber, String clientName, int clientId) throws DatabaseConnectionException, DatabaseResultException {
    ReservedSeats reservedSeats = new Gson().fromJson(seats, ReservedSeats.class);

    if (reservedSeats == null) {
      reservedSeats = new ReservedSeats();
      reservedSeats.setSeats(new ArrayList<SeatData>());
    }
    
    SeatData seatData = reservedSeats.createSeatData(seatNumber, clientName, clientId);
    reservedSeats.addSeatData(seatData);
    return reservedSeats;
  }
  
}
