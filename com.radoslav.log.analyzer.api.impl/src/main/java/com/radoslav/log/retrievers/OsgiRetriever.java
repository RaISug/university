package com.radoslav.log.retrievers;

import com.radoslav.log.analyzer.util.Lookup;
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
    return Lookup.theFirstServiceWithOsgiByClassName(LogEntryBuilder.class);
  }

  @Override
  public LogParser retrieveLogParser() {
    return Lookup.theFirstServiceWithOsgiByClassName(LogParser.class);
  }

  @Override
  public StatisticProvider retrieveStatisticProvider() {
    return Lookup.theFirstServiceWithOsgiByClassName(StatisticProvider.class);
  }

  @Override
  public DatabaseProvider retrieveDatabaseProvider() {
    return Lookup.theFirstServiceWithOsgiByClassName(DatabaseProvider.class);
  }

}
