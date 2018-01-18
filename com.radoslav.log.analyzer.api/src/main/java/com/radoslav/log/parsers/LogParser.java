package com.radoslav.log.parsers;

import java.io.InputStream;
import java.util.List;

import com.radoslav.log.entries.LogEntry;

//TODO: make the APIs independent from the LogEntry
public interface LogParser {

  public String getLogEntryDelimiter();
  
  public void setLogEntryDelimiter(String delimiter);
  
  public List<LogEntry> parse(InputStream inputStream);
  
}
