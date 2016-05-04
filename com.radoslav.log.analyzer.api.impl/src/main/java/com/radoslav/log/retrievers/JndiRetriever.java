package com.radoslav.log.retrievers;

import com.radoslav.log.analyzer.util.Configuration;
import com.radoslav.log.analyzer.util.Lookup;
import com.radoslav.log.entry.builders.LogEntryBuilder;
import com.radoslav.log.entry.builder.retrievers.LogEntryBuilderRetriever;
import com.radoslav.log.parser.retrievers.LogParserRetriever;
import com.radoslav.log.parsers.LogParser;
import com.radoslav.log.provider.retrievers.DatabaseProviderRetriever;
import com.radoslav.log.provider.retrievers.StatisticProviderRetriever;
import com.radoslav.log.providers.DatabaseProvider;
import com.radoslav.log.providers.StatisticProvider;

public class JndiRetriever  implements LogEntryBuilderRetriever,
  DatabaseProviderRetriever, StatisticProviderRetriever, LogParserRetriever {

  @Override
  public LogEntryBuilder retrieveLogEntryBuilder() {
    return (LogEntryBuilder) Lookup.withJndiByName(Configuration.getCfgValueByKey("com.radoslav.log.entry.builder.name"));
  }

  @Override
  public LogParser retrieveLogParser() {
    return (LogParser) Lookup.withJndiByName(Configuration.getCfgValueByKey("com.radoslav.log.entry.parser.name"));
  }

  @Override
  public StatisticProvider retrieveStatisticProvider() {
    return (StatisticProvider) Lookup.withJndiByName(Configuration.getCfgValueByKey("com.radoslav.statistic.provider.name"));
  }

  @Override
  public DatabaseProvider retrieveDatabaseProvider() {
    return (DatabaseProvider) Lookup.withJndiByName(Configuration.getCfgValueByKey("com.radoslav.database.provider.name"));
  }
  
}
