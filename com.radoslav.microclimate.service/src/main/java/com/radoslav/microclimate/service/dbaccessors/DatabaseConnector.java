package com.radoslav.microclimate.service.dbaccessors;

import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

import com.radoslav.microclimate.service.exceptions.InternalServerErrorException;
import com.radoslav.microclimate.service.exceptions.MicroclimateException;

public class DatabaseConnector {

  private static EntityManagerFactory entityManagerFactory = null;
  
  public static EntityManager getEntityManager() throws MicroclimateException {
    if (entityManagerFactory == null) {
      synchronized(DatabaseConnector.class) {
        if (entityManagerFactory == null) {
          lookupEntityManagerFactory();
        }
      }
    }
    return entityManagerFactory.createEntityManager();
  }
  
  private static synchronized void lookupEntityManagerFactory() throws MicroclimateException {
    Context context = null;
    try {
      context =  new InitialContext();
      DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/Intercity");
      Map<String, DataSource> properties = new HashMap<String, DataSource>();
      properties.put("javax.persistence.nonJtaDataSource", dataSource);
      entityManagerFactory = Persistence.createEntityManagerFactory("IntercityTransport", properties);
    } catch(NamingException namingException) {
      throw new InternalServerErrorException("Error while connection to database", namingException);
    } finally {
      if (context != null) {
        try {
          context.close();
        } catch (NamingException namingException) {
          throw new InternalServerErrorException("Exception while connection to database", namingException);
        }
      }
    }
  }
}
