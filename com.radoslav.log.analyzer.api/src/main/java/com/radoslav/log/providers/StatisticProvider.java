package com.radoslav.log.providers;

import com.radoslav.log.entries.LogEntry;

//TODO: make the APIs independent from the LogEntry
public interface StatisticProvider {

  public void processLogEntry(LogEntry logEntry);
  
}
