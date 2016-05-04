package com.radoslav.log.analyzer;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.radoslav.log.analyzer.util.FileGenerator;
import com.radoslav.log.data.LogSeverity;
import com.radoslav.log.entries.LogEntry;
import com.radoslav.log.parser.retrievers.LogParserRetriever;
import com.radoslav.log.parser.retrievers.LogParserRetrieverImpl;
import com.radoslav.log.parsers.LogParser;

public class LogAnalyzer {

  private LogParser logParser;
  private InputStream inputStream;

  public LogAnalyzer(InputStream inputStream) {
    this(inputStream, new LogParserRetrieverImpl());
  }
  
  public LogAnalyzer(InputStream inputStream, LogParserRetriever retriever) {
    this(inputStream, retriever.retrieveLogParser());
  }
  
  public LogAnalyzer(InputStream inputStream, LogParser logParser) {
    this.logParser = logParser;
    this.inputStream = inputStream;
  }

  public List<LogEntry> retrieveLogEntriesInSpecifiedHour(Date date) {
    Calendar entryDateTime = Calendar.getInstance();
    
    Calendar anHourEarlier = Calendar.getInstance();
    anHourEarlier.setTime(date);
    anHourEarlier.add(Calendar.HOUR, -1);
    
    Calendar dateTime = Calendar.getInstance();
    dateTime.setTime(date);
    
    List<LogEntry> logEntries = logParser.parse(inputStream);
    
    Iterator<LogEntry> iterator = logEntries.iterator();
    while (iterator.hasNext()){
      LogEntry logEntry = iterator.next();
      
      entryDateTime.setTime(logEntry.getDate());
      if (entryDateTime.before(anHourEarlier) || entryDateTime.after(dateTime)) {
        iterator.remove();
      }
    }
    
    return logEntries;
  }

  public List<LogEntry> retrieveLogEntriesByDateTime(Date date) {
    Calendar entryDate = Calendar.getInstance();
    
    Calendar anFewMinutesEarlier = Calendar.getInstance();
    anFewMinutesEarlier.setTime(date);
    anFewMinutesEarlier.add(Calendar.MINUTE, -2);
    
    Calendar anFewMinutesLater = Calendar.getInstance();
    anFewMinutesLater.setTime(date);
    anFewMinutesLater.add(Calendar.MINUTE, 2);

    List<LogEntry> logEntries = logParser.parse(inputStream);
    
    Iterator<LogEntry> iterator = logEntries.iterator();
    while (iterator.hasNext()){
      LogEntry logEntry = iterator.next();
      
      entryDate.setTime(logEntry.getDate());
      if (entryDate.before(anFewMinutesEarlier) || entryDate.after(anFewMinutesLater)) {
        iterator.remove();
      }
    }
    
    return logEntries;
  }

  public List<LogEntry> retrieveLogEntriesBetweenTimePeriod(Date beginDate, Date endDate) {
    List<LogEntry> logEntries = logParser.parse(inputStream);
    
    Iterator<LogEntry> iterator = logEntries.iterator();
    while (iterator.hasNext()){
      LogEntry logEntry = iterator.next();
      
      Date entryDate = logEntry.getDate();
      if (entryDate.before(beginDate) || entryDate.after(endDate)) {
        iterator.remove();
      }
    }
    
    return logEntries;
  }

  public List<LogEntry> retrieveLogEntriesBySeverity(LogSeverity severity) {
    List<LogEntry> logEntries = logParser.parse(inputStream);
    
    Iterator<LogEntry> iterator = logEntries.iterator();
    while (iterator.hasNext()){
      LogEntry logEntry = iterator.next();
      if (logEntry.getSeverity() != severity) {
        iterator.remove();
      }
    }
    
    return logEntries;
  }
  
  public List<LogEntry> retrieveLogEntriesByMessage(String logMessage) {
    List<LogEntry> logEntries = logParser.parse(inputStream);
    
    Iterator<LogEntry> iterator = logEntries.iterator();
    while (iterator.hasNext()){
      LogEntry logEntry = iterator.next();
      if (logEntry.getMessage().contains(logMessage)) {
        iterator.remove();
      }
    }
    
    return logEntries;
  }
  
  public OutputStream generateFileWithAnalysis(OutputStream outputStream) {
    List<LogEntry> logEntries = logParser.parse(inputStream);
    
    return FileGenerator.generateFileFromEntries(logEntries, outputStream);
  }
  
}
