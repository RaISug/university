package com.radoslav.log.provider.retrievers;

import com.radoslav.log.analyzer.util.Services;
import com.radoslav.log.providers.StatisticProvider;
import com.radoslav.log.providers.StatisticProviderImpl;
import com.radoslav.log.retrievers.JndiRetriever;
import com.radoslav.log.retrievers.OsgiRetriever;
import com.radoslav.log.retrievers.SpiRetriever;

public class StatisticProviderRetrieverImpl implements StatisticProviderRetriever {

  @Override
  public StatisticProvider retrieveStatisticProvider() {
    StatisticProvider statisticProvider = lookupService();
    if (statisticProvider != null) {
      return statisticProvider;
    }
    
    return new StatisticProviderImpl();
  }

  private StatisticProvider lookupService() {
    if (Services.isRegisteredWithOsgi()) {
      return new OsgiRetriever().retrieveStatisticProvider();
    } else if (Services.isRegisteredWithSpi()) {
      return new SpiRetriever().retrieveStatisticProvider();
    } else if (Services.isRegisteredWithJndi()) {
      return new JndiRetriever().retrieveStatisticProvider();
    }
    
    return null;
  }
  
}
