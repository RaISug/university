package com.intercity.database.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the stations database table.
 * 
 */
@Entity
@Table(name="stations")
@NamedQueries({
  @NamedQuery(name="Station.findAll", query="SELECT s FROM Station s"),
  @NamedQuery(name="Station.getCities", query="SELECT DISTINCT s.city FROM Station s"),
  @NamedQuery(name="Station.getById", query="SELECT s FROM Station s WHERE s.id = :id")
})
public class Station implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int id;

	@Lob
	private String description;

	private String name;
	
	private String city;

  public Station() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

}