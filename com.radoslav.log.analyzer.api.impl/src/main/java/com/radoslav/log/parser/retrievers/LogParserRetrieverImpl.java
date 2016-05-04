package com.radoslav.log.parser.retrievers;

import com.radoslav.log.analyzer.util.Services;
import com.radoslav.log.parsers.LogParser;
import com.radoslav.log.parsers.LogParserImpl;
import com.radoslav.log.retrievers.JndiRetriever;
import com.radoslav.log.retrievers.OsgiRetriever;
import com.radoslav.log.retrievers.SpiRetriever;

public class LogParserRetrieverImpl implements LogParserRetriever {

  @Override
  public LogParser retrieveLogParser() {
    LogParser logParser = lookupService();
    if (logParser != null) {
      return logParser;
    }
    
    return new LogParserImpl();
  }

  private LogParser lookupService() {
    if (Services.isRegisteredWithOsgi()) {
      return new OsgiRetriever().retrieveLogParser();
    } else if (Services.isRegisteredWithSpi()) {
      return new SpiRetriever().retrieveLogParser();
    } else if (Services.isRegisteredWithJndi()) {
      return new JndiRetriever().retrieveLogParser();
    }
    
    return null;
  }
}
