package com.radoslav.services.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PASSWORD_STORAGE")
public class PasswordStorage {

	@Id
	@GeneratedValue
	private int id;
	
	@Column(name = "USERNAME")
	private String userName;
	
	@Column(nullable = false, length = 32)
	private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
