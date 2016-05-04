package com.radoslav.log.retrievers;

import com.radoslav.log.analyzer.util.Lookup;
import com.radoslav.log.entry.builder.retrievers.LogEntryBuilderRetriever;
import com.radoslav.log.entry.builders.LogEntryBuilder;
import com.radoslav.log.parser.retrievers.LogParserRetriever;
import com.radoslav.log.parsers.LogParser;
import com.radoslav.log.provider.retrievers.DatabaseProviderRetriever;
import com.radoslav.log.provider.retrievers.StatisticProviderRetriever;
import com.radoslav.log.providers.DatabaseProvider;
import com.radoslav.log.providers.StatisticProvider;

public class SpiRetriever implements LogEntryBuilderRetriever,
  DatabaseProviderRetriever, StatisticProviderRetriever, LogParserRetriever {

  @Override
  public LogEntryBuilder retrieveLogEntryBuilder() {
    return Lookup.theFirstInstanceWithSpiByClass(LogEntryBuilder.class);
  }

  @Override
  public DatabaseProvider retrieveDatabaseProvider() {
    return Lookup.theFirstInstanceWithSpiByClass(DatabaseProvider.class);
  }

  @Override
  public StatisticProvider retrieveStatisticProvider() {
    return Lookup.theFirstInstanceWithSpiByClass(StatisticProvider.class);
  }

  @Override
  public LogParser retrieveLogParser() {
    return Lookup.theFirstInstanceWithSpiByClass(LogParser.class);
  }

}
