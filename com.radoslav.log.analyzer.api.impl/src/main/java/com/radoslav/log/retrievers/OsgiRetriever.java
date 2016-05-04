package com.radoslav.log.retrievers;

import com.radoslav.log.entry.builders.LogEntryBuilder;
import com.radoslav.log.entry.builder.retrievers.LogEntryBuilderRetriever;
import com.radoslav.log.parser.retrievers.LogParserRetriever;
import com.radoslav.log.parsers.LogParser;
import com.radoslav.log.provider.retrievers.DatabaseProviderRetriever;
import com.radoslav.log.provider.retrievers.StatisticProviderRetriever;
import com.radoslav.log.providers.DatabaseProvider;
import com.radoslav.log.providers.StatisticProvider;

public class OsgiRetriever  implements LogEntryBuilderRetriever,
  DatabaseProviderRetriever, StatisticProviderRetriever, LogParserRetriever {

  @Override
  public LogEntryBuilder retrieveLogEntryBuilder() {
    return null;
  }

  @Override
  public LogParser retrieveLogParser() {
    return null;
  }

  @Override
  public StatisticProvider retrieveStatisticProvider() {
    return null;
  }

  @Override
  public DatabaseProvider retrieveDatabaseProvider() {
    return null;
  }

}
