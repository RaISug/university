package com.radoslav.services.entity.access;

import java.util.List;

import com.radoslav.services.entity.PasswordStorage;

public class PasswordStorageDAO extends AbstractDAO<PasswordStorage> {

	private static PasswordStorageDAO instance = new PasswordStorageDAO();
	
	private PasswordStorageDAO() {}

	public static PasswordStorageDAO getInstance() {
		return instance;
	}
	
	@Override
	public PasswordStorage getSingalData(String key) {
		return null;
	}

	@Override
	public List<PasswordStorage> getData() {
		return null;
	}
}
