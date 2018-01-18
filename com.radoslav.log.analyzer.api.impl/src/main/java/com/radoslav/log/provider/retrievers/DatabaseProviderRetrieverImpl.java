package com.radoslav.log.provider.retrievers;

import com.radoslav.log.analyzer.util.Services;
import com.radoslav.log.providers.DatabaseProvider;
import com.radoslav.log.providers.DatabaseProviderImpl;
import com.radoslav.log.retrievers.JndiRetriever;
import com.radoslav.log.retrievers.OsgiRetriever;
import com.radoslav.log.retrievers.SpiRetriever;

public class DatabaseProviderRetrieverImpl implements DatabaseProviderRetriever {

  @Override
  public DatabaseProvider retrieveDatabaseProvider() {
    DatabaseProvider databaseProvider = lookupService();
    if (databaseProvider != null) {
      return databaseProvider;
    }
    
    return new DatabaseProviderImpl();
  }

  private DatabaseProvider lookupService() {
    if (Services.isRegisteredWithOsgi()) {
      return new OsgiRetriever().retrieveDatabaseProvider();
    } else if (Services.isRegisteredWithSpi()) {
      return new SpiRetriever().retrieveDatabaseProvider();
    } else if (Services.isRegisteredWithJndi()) {
      return new JndiRetriever().retrieveDatabaseProvider();
    }
    
    return null;
  }
  
}
