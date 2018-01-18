package com.radoslav.services.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER_TABLE")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	
	@Column(name = "USERNAME", nullable = false, length = 35, unique = true)
	private String userName;
	
	@Column(name = "REALNAME", nullable = false)
	private String realName;
	
	@Column(nullable = false)
	private String faculty;
	
	@Column(nullable = false)
	private String speciality;
	
	@Column(nullable = false)
	private int course;
	
	@Column(name = "GROUPNUMBER",nullable = false)
	private int groupNumber;

	@Column(name = "FACULTY_NUMBER", nullable = false)
	private long facultyNumber;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getFaculty() {
		return faculty;
	}

	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public int getCourse() {
		return course;
	}

	public void setCourse(int course) {
		this.course = course;
	}

	public int getGroup() {
		return groupNumber;
	}

	public void setGroup(int group) {
		this.groupNumber = group;
	}

	public long getFacultyNumber() {
		return facultyNumber;
	}

	public void setFacultyNumber(long facultyNumber) {
		this.facultyNumber = facultyNumber;
	}
}
