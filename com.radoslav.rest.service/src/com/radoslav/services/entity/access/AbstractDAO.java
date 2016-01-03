package com.radoslav.services.entity.access;

import java.util.List;

import javax.persistence.EntityManager;

import com.radoslav.services.connection.DatabaseConnection;

public abstract class AbstractDAO<T> {

private DatabaseConnection connection = null;
	
	public AbstractDAO() {
		connection = DatabaseConnection.getInstance();
	}
	
	public void persistEntity(T entity) {
		EntityManager manager = this.getEntityManager();
		try {
			manager.getTransaction().begin();
			manager.persist(entity);
			manager.getTransaction().commit();
		} finally {
			if (manager.getTransaction().isActive()) {
				manager.getTransaction().rollback();
			}
		}
		manager.close();
	}

	public EntityManager getEntityManager() {
		return connection.getEntityManager();
	}
	
	public abstract T getSingalData(String key);
	
	public abstract List<T> getData();
}
