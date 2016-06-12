package com.radoslav.log.entry.builders;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.radoslav.log.analyzer.constants.CfgConstants;
import com.radoslav.log.analyzer.util.Configuration;
import com.radoslav.log.data.LogSeverity;
import com.radoslav.log.entries.LogEntry;
import com.radoslav.log.entries.LogEntryImpl;

public class LogEntryBuilderImpl implements LogEntryBuilder {

  private String logKeysDelimiter = null;
 
  public LogEntryBuilderImpl() {
    this.logKeysDelimiter = Configuration.getCfgValueByKey(CfgConstants.LOG_KEYS_DELIMITER, CfgConstants.DEFAULT_LOG_KEYS_DELIMITER);
  }

  @Override
  public LogEntry buildLogEntry(String logRow) {
    if (isLogTemplateSpecified()) {
      return buildLogEntryUsingCustomTemplate(logRow);
    }
    return buildLogEntryUsingDefaulLogTemplate(logRow);
  }
  
  private boolean isLogTemplateSpecified() {
    return Configuration.getCfgValueByKey(CfgConstants.LOG_ENTRY_TEMPLATES) != null;
  }
  
  private LogEntry buildLogEntryUsingCustomTemplate(String logRow) {
    String logTemplate = getLogEntryTemplate();

    String keysDelimiter = getLogKeysDelimiter();
    String[] logKeys = logTemplate.split(keysDelimiter);
    String[] logKeyValues = logRow.split(keysDelimiter);

    if (logKeys.length != logKeyValues.length) {
      throw new RuntimeException("The specified template doesn't match the format of the log entries");
    }
    
    LogEntry logEntry = new LogEntryImpl();
    
    for (int i = 0 ; i < logKeyValues.length ; i++) {
      if (logKeys[i].equalsIgnoreCase("datetime")) {
        try {
          logEntry.setDate(new SimpleDateFormat("yyyy:mm:dd").parse(logKeyValues[i]));
        } catch (ParseException e) {
          throw new RuntimeException("Unable to parse datetime value in the log entry");
        }
      } else if (logKeys[i].equalsIgnoreCase("severity")) {
        logEntry.setSeverity(LogSeverity.valueOf(logKeyValues[i].toUpperCase()));
      } else if (logKeys[i].equalsIgnoreCase("message")) {
        logEntry.setMessage(logKeyValues[i]);
      } else {
        logEntry.putLogProperty(logKeys[i], logKeyValues[i]);
      }
    }
    
    return logEntry;
  }
  
  private String getLogEntryTemplate() {
    return Configuration.getCfgValueByKey(CfgConstants.LOG_ENTRY_TEMPLATES);
  }
  
  private LogEntry buildLogEntryUsingDefaulLogTemplate(String logRow) {
    String[] logKeyValues = logRow.split(getLogKeysDelimiter());
    if (logKeyValues.length < 3) {
      throw new RuntimeException("Invalid log entry.");
    }
    
    String date = logKeyValues[0];
    String severity = logKeyValues[1];
    String message = logKeyValues[2];
    
    LogEntry logEntry = new LogEntryImpl();

    try {
      logEntry.setDate(new SimpleDateFormat("yyyy:mm:dd").parse(date));
    } catch (ParseException e) {
      throw new RuntimeException("Not supported date format.");
    }
    
    LogSeverity logSeverity = LogSeverity.valueOf(severity.toUpperCase());
    
    logEntry.setSeverity(logSeverity);
    logEntry.setMessage(message);
    
    for (int i = 3 ; i < logKeyValues.length ; i++) {
      logEntry.putLogProperty("additional-property-" + i, logKeyValues[i]);
    }
    
    return logEntry;
  }

  @Override
  public String getLogKeysDelimiter() {
    return this.logKeysDelimiter;
  }
  
  @Override
  public void setLogKeysDelimiter(String delimiter) {
    this.logKeysDelimiter = delimiter;
  }

}
