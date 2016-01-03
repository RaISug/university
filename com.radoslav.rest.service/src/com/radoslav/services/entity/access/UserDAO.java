package com.radoslav.services.entity.access;

import java.util.List;

import com.radoslav.services.entity.User;

public class UserDAO extends AbstractDAO<User>{

	private static UserDAO instance = new UserDAO();
	
	private UserDAO() {}
	
	public static synchronized UserDAO getInstance() {
		return instance;
	}
	
	@Override
	public List<User> getData() {
		return getEntityManager().createQuery("SELECT u FROM User u", User.class).getResultList();
	}

	@Override
	public User getSingalData(String key) {
		return getEntityManager().createQuery("SELECT u FROM User u WHERE u.userName = ?1", User.class).setParameter(1, key).getSingleResult();
	}

}
