package com.radoslav.services.connection;

import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

import org.eclipse.persistence.config.PersistenceUnitProperties;

public class DatabaseConnection {

	private static DatabaseConnection connection = null;
	private static EntityManagerFactory entityFactory = null;
	
	private DatabaseConnection() {
		try {
			Context context = new InitialContext();
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/DefaultDB");
			Map<String, DataSource> properties = new HashMap<String, DataSource>();  
			properties.put(PersistenceUnitProperties.NON_JTA_DATASOURCE, dataSource);
			entityFactory = Persistence.createEntityManagerFactory("jpa");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public static DatabaseConnection getInstance() {
		if (connection == null) {
			connection = new DatabaseConnection();
		}
		return connection;
	}
	
	
	public EntityManager getEntityManager() {
		return entityFactory.createEntityManager();
	}
}
