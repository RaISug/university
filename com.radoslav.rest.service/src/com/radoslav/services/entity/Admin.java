package com.radoslav.services.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "admins")
public class Admin {

	@Id
	@GeneratedValue
	private int id;
	
	@Column(name = "username", nullable = false, length = 32)
	private String userName;
	
	@Column(nullable = false, length = 32)
	private String password;

	public String getAdminName() {
		return userName;
	}

	public void setAdminName(String adminName) {
		this.userName = adminName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
