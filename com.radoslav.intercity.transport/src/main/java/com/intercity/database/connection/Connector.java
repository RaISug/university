package com.intercity.database.connection;

import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

import com.intercity.exception.DatabaseConnectionException;

public class Connector {

  private static EntityManagerFactory entityManagerFactory = null;
  
  private static synchronized void generateEntityManagerFactory() throws DatabaseConnectionException {
    Context context = null;
    try {
      context =  new InitialContext();
      DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/Intercity");
      Map<String, DataSource> properties = new HashMap<String, DataSource>();
      properties.put("javax.persistence.nonJtaDataSource", dataSource);
      entityManagerFactory = Persistence.createEntityManagerFactory("IntercityTransport", properties);
    } catch(NamingException namingException) {
      throw new DatabaseConnectionException("Error while connection to database", 500, namingException);
    } finally {
      if (context != null) {
        try {
          context.close();
        } catch (NamingException namingException) {
          throw new DatabaseConnectionException("Exception while connection to database", 500, namingException);
        }
      }
    }
  }
  
  public static EntityManager getEntityManager() throws DatabaseConnectionException {
    if (entityManagerFactory == null) {
      synchronized(Connector.class) {
        if (entityManagerFactory == null) {
          generateEntityManagerFactory();
        }
      }
    }
    return entityManagerFactory.createEntityManager();
  }
}
