package com.intercity.database.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="seats")
@NamedQueries({
  @NamedQuery(name="Seat.findAll", query="SELECT s FROM Seat s"),
  @NamedQuery(name="Seat.getRouteReservedSeats", query="SELECT s FROM Seat s WHERE s.routeId = :routeId"
      + " AND FUNC('YEAR', s.date) = FUNC('YEAR', :date) AND FUNC('MONTH', s.date) = FUNC('MONTH', :date) AND FUNC('DAY', s.date) = FUNC('DAY', :date)"),
  @NamedQuery(name="Seat.updateReservedSeats", query="UPDATE Seat s SET s.reservedSeats = :reservedSeats WHERE s.id = :id")
})
public class Seat {
  @SuppressWarnings("unused")
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE)
  private int id;

  @Column(name="departureDate")
  private String date;

  private String reservedSeats;

  @Column(name="route_id")
  private int routeId;

  @Column(name="vehicle_id")
  private int vehicleId;

  private String notes;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String departureTime) {
    this.date = departureTime;
  }

  public String getReservedSeats() {
    return reservedSeats;
  }

  public void setReservedSeats(String reservedSeats) {
    this.reservedSeats = reservedSeats;
  }

  public int getRouteId() {
    return routeId;
  }

  public void setRouteId(int routeId) {
    this.routeId = routeId;
  }

  public int getVehicleId() {
    return vehicleId;
  }

  public void setVehicleId(int vehicleId) {
    this.vehicleId = vehicleId;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

}
