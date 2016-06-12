package com.radoslav.log.retrievers;

import com.radoslav.log.analyzer.constants.CfgConstants;
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
    return (LogEntryBuilder) Lookup.withJndiByName(Configuration.getCfgValueByKey(CfgConstants.JNDI_NAME_OF_LOG_ENTRY_BUILDER));
  }

  @Override
  public LogParser retrieveLogParser() {
    return (LogParser) Lookup.withJndiByName(Configuration.getCfgValueByKey(CfgConstants.JNDI_NAME_OF_LOG_ENTRY_PARSER));
  }

  @Override
  public StatisticProvider retrieveStatisticProvider() {
    return (StatisticProvider) Lookup.withJndiByName(Configuration.getCfgValueByKey(CfgConstants.JNDI_NAME_OF_STATISTICS_PROVIDER));
  }

  @Override
  public DatabaseProvider retrieveDatabaseProvider() {
    return (DatabaseProvider) Lookup.withJndiByName(Configuration.getCfgValueByKey(CfgConstants.JNDI_NAME_OF_DATABASE_PROVIDER));
  }
  
}
