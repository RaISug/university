package com.radoslav.log.entry.builders;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.radoslav.log.data.LogSeverity;
import com.radoslav.log.entries.LogEntry;
import com.radoslav.log.entries.LogEntryImpl;

public class LogEntryBuilderImpl implements LogEntryBuilder {

  private String logPropertiesDelimiter = null;
 
  public LogEntryBuilderImpl() {
    this.logPropertiesDelimiter = System.getProperty("com.radoslav.log.properties.delimiter", "#");
  }

  @Override
  public LogEntry buildLogEntry(String logRow) {
    LogEntry logEntry = new LogEntryImpl();
    
    String[] logRowFields = logRow.split(getLogPropertiesDelimiter());
    if (logRowFields.length < 3) {
      throw new RuntimeException("Invalid log entry.");
    }
    
    String date = logRowFields[0];
    String severity = logRowFields[1];
    String message = logRowFields[2];
    
    try {
      logEntry.setDate(new SimpleDateFormat("yyyy:mm:dd").parse(date));
    } catch (ParseException e) {
      throw new RuntimeException("Not supported date format.");
    }
    
    logEntry.setSeverity(LogSeverity.valueOf(severity.toUpperCase()));
    logEntry.setMessage(message);
    
    return logEntry;
  }

  @Override
  public String getLogPropertiesDelimiter() {
    return this.logPropertiesDelimiter;
  }
  
  @Override
  public void setLogPropertiesDelimiter(String delimiter) {
    this.logPropertiesDelimiter = delimiter;
  }

}
