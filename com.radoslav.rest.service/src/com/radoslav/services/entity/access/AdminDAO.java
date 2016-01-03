package com.radoslav.services.entity.access;

import java.util.List;

import com.radoslav.services.entity.Admin;

public class AdminDAO extends AbstractDAO<Admin>{
	
	private static AdminDAO instance = new AdminDAO();
	
	private AdminDAO() {}

	public static AdminDAO getInstance() {
		return instance;
	}
	
	@Override
	public Admin getSingalData(String key) {
		return null;
	}

	@Override
	public List<Admin> getData() {
		return null;
	}
}
