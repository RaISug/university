package com.intercity.database.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the routes database table.
 * 
 */
@Entity
@Table(name="routes")
@NamedQueries({
  @NamedQuery(name="Route.findAll", query="SELECT r FROM Route r"),
  @NamedQuery(name="Route.findRouteById", query="SELECT r FROM Route r WHERE r.id = :id"),
  @NamedQuery(name="Route.departureTime", query="SELECT DISTINCT r.departureTime FROM Route r"),
  @NamedQuery(name="Route.searchRoutes", query="SELECT r FROM Route r WHERE r.startDestination = :startDestination AND r.endDestination = :endDestination"
      + " AND FUNC('HOUR', r.departureTime) = FUNC('HOUR', :departureTime) AND FUNC('MINUTE', r.departureTime) = FUNC('MINUTE', :departureTime)"),
  @NamedQuery(name="Route.searchRoutesWithTransference", query="SELECT r FROM Route r WHERE r.startDestination = :startDestination"
      + " AND FUNC('HOUR', r.departureTime) = FUNC('HOUR', :departureTime) AND FUNC('MINUTE', r.departureTime) = FUNC('MINUTE', :departureTime)")
})

public class Route implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int id;

	private String duration;

	private String departureTime;

	@Lob
	private String description;

	private String endDestination;

  private double price;

	private String startDestination;

	@Column(name="vehicle_id")
	private int vehicleId;
	
	@Column(name="driver_id")
	private int userId;
	
	@Column(name="companies_id")
	private int companyId;

	public Route() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDuration() {
    return duration;
  }

  public void setDuration(String duration) {
    this.duration = duration;
  }

  public String getDepartureTime() {
		return this.departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEndDestination() {
		return this.endDestination;
	}

	public void setEndDestination(String endDestination) {
		this.endDestination = endDestination;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getStartDestination() {
		return this.startDestination;
	}

	public void setStartDestination(String startDestination) {
		this.startDestination = startDestination;
	}

  public int getVehicleId() {
    return vehicleId;
  }

  public void setVehicleId(int vehicleId) {
    this.vehicleId = vehicleId;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public int getCompanyId() {
    return companyId;
  }

  public void setCompanyId(int companyId) {
    this.companyId = companyId;
  }

}