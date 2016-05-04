package com.radoslav.log.entry.builders;

import com.radoslav.log.entries.LogEntry;

//TODO: make API independent from the LogEntry
public interface LogEntryBuilder {
  
  public LogEntry buildLogEntry(String logRow);
  
  public String getLogPropertiesDelimiter();

  public void setLogPropertiesDelimiter(String delimiter);

}
