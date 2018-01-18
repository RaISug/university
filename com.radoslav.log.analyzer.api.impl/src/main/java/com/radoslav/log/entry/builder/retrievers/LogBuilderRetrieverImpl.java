package com.radoslav.log.entry.builder.retrievers;

import com.radoslav.log.analyzer.util.Services;
import com.radoslav.log.entry.builders.LogEntryBuilder;
import com.radoslav.log.entry.builders.LogEntryBuilderImpl;
import com.radoslav.log.retrievers.JndiRetriever;
import com.radoslav.log.retrievers.OsgiRetriever;
import com.radoslav.log.retrievers.SpiRetriever;

public class LogBuilderRetrieverImpl implements LogEntryBuilderRetriever {

  @Override
  public LogEntryBuilder retrieveLogEntryBuilder() {
    LogEntryBuilder entryBuilder = lookupService();
    if (entryBuilder != null) {
      return entryBuilder;
    }
    
    return new LogEntryBuilderImpl();
  }
  
  private LogEntryBuilder lookupService() {
    if (Services.isRegisteredWithOsgi()) {
      return new OsgiRetriever().retrieveLogEntryBuilder();
    } else if (Services.isRegisteredWithSpi()) {
      return new SpiRetriever().retrieveLogEntryBuilder();
    } else if (Services.isRegisteredWithJndi()) {
      return new JndiRetriever().retrieveLogEntryBuilder();
    }
    
    return null;
  }
  
}
