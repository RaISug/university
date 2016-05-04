package com.radoslav.log.parsers;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.radoslav.log.data.LogSeverity;
import com.radoslav.log.entries.LogEntry;
import com.radoslav.log.entry.builder.retrievers.LogBuilderRetrieverImpl;
import com.radoslav.log.entry.builder.retrievers.LogEntryBuilderRetriever;
import com.radoslav.log.entry.builders.LogEntryBuilder;
import com.radoslav.log.provider.retrievers.DatabaseProviderRetriever;
import com.radoslav.log.provider.retrievers.DatabaseProviderRetrieverImpl;
import com.radoslav.log.provider.retrievers.StatisticProviderRetriever;
import com.radoslav.log.provider.retrievers.StatisticProviderRetrieverImpl;
import com.radoslav.log.providers.DatabaseProvider;
import com.radoslav.log.providers.StatisticProvider;

public class LogParserImpl implements LogParser {

  private String logEntryDelimiter = null;
  private LogEntryBuilder logEntryBuilder = null;
  private DatabaseProvider databaseProvider = null;
  private StatisticProvider statisticProvider = null;

  public LogParserImpl() {
    this(new LogBuilderRetrieverImpl(), new DatabaseProviderRetrieverImpl(), new StatisticProviderRetrieverImpl());
  }
  
  public LogParserImpl(LogEntryBuilderRetriever builderRetriever, DatabaseProviderRetriever databaseRetriever, StatisticProviderRetriever statisticRetriever) {
    this(builderRetriever.retrieveLogEntryBuilder(), databaseRetriever.retrieveDatabaseProvider(), statisticRetriever.retrieveStatisticProvider());
  }
  
  public LogParserImpl(LogEntryBuilder logEntryBuilder, DatabaseProvider databaseProvider, StatisticProvider statisticProvider) {
    this.logEntryBuilder = logEntryBuilder;
    this.databaseProvider = databaseProvider;
    this.statisticProvider = statisticProvider;
    this.logEntryDelimiter  = System.getProperty("line.separator", "\n");
  }
  
  @Override
  public List<LogEntry> parse(InputStream inputStream) {
    List<LogEntry> logEntries = new ArrayList<>();
    if (inputStream == null) {
      return logEntries;
    }
    
    try (Scanner scanner = new Scanner(inputStream)) {
      scanner.useDelimiter(getLogEntryDelimiter());
      
      while (scanner.hasNext()) {
        LogEntry logEntry = logEntryBuilder.buildLogEntry(scanner.next());
        
        passEntryToDatabaseProvider(logEntry);
        passEntryToStatisticProvider(logEntry);
        
        if (logEntry.getSeverity() == LogSeverity.ERROR) {
          addPreviousEntriesToEntry(logEntries, logEntry);
        }
        
        logEntries.add(logEntry);
      }
    } catch (Exception exception) {
      return new ArrayList<>();
    }
    
    return logEntries;
  }

  @Override
  public String getLogEntryDelimiter() {
    return logEntryDelimiter;
  }

  @Override
  public void setLogEntryDelimiter(String delimiter) {
    this.logEntryDelimiter = delimiter;
  }
  
  private void addPreviousEntriesToEntry(List<LogEntry> logEntries, LogEntry logEntry) {
    int entriesLength = logEntries.size();
    int loopStartIndex = entriesLength <= 15 ? 0 : entriesLength;
    
    for (int i = loopStartIndex ; i < entriesLength ; i--) {
      LogEntry entry = logEntries.get(i);
      if (entry.getSeverity() == LogSeverity.ERROR) {
        logEntry.setPreviousLogEntries(new ArrayList<LogEntry>());
      }
      
      logEntry.addPreviousLogEntry(entry);
    }
  }

  private void passEntryToDatabaseProvider(LogEntry logEntry) {
    try {
      databaseProvider.processLogEntry((LogEntry) logEntry.clone());
    } catch (CloneNotSupportedException e) {
      //skip
    }
  }
  
  private void passEntryToStatisticProvider(LogEntry logEntry) {
    try {
      statisticProvider.processLogEntry((LogEntry) logEntry.clone());
    } catch (CloneNotSupportedException e) {
      //skip
    }
  }

}
