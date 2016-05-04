package com.radoslav.log.entries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.radoslav.log.data.LogSeverity;

public class LogEntryImpl implements LogEntry {

  private Date logDate;
  private String logMessage;
  private LogSeverity logSeverity;
  private List<LogEntry> previousLogEntries;
  private Map<String, Object> additionalLogProperties;
  
  public LogEntryImpl() {
    previousLogEntries = new ArrayList<>();
    additionalLogProperties = new HashMap<>();
  }
  
  public Date getDate() {
    return this.logDate;
  }

  public void setDate(Date logDateTime) {
    this.logDate = logDateTime;
  }

  public LogSeverity getSeverity() {
    return this.logSeverity;
  }
  
  public void setSeverity(LogSeverity logSeverity) {
    this.logSeverity = logSeverity;
  }
  
  public String getMessage() {
    return this.logMessage;
  }
  
  public void setMessage(String logMessage) {
    this.logMessage = logMessage;
  }
 
  public List<LogEntry> getPreviousLogEntries() {
    return previousLogEntries;
  }

  public void setPreviousLogEntries(List<LogEntry> previousLogEntries) {
    this.previousLogEntries = previousLogEntries;
  }
  
  public void addPreviousLogEntry(LogEntry logEntry) {
    previousLogEntries.add(logEntry);
  }
  
  public void putLogProperty(String propertyName, Object propertyValue) {
    additionalLogProperties.put(propertyName, propertyValue);
  }
  
  public Object getLogProperty(String propertyName) {
    return additionalLogProperties.get(propertyName);
  }
  
  public void setLogProperties(Map<String, Object> logProperties) {
    this.additionalLogProperties = logProperties;
  }
  
  public Map<String, Object> getLogProperties() {
    return additionalLogProperties;
  }
  
  @Override
  public Object clone() throws CloneNotSupportedException {
    LogEntry clonedEntry = (LogEntry) super.clone();
    
    clonedEntry.setDate(new Date(logDate.getTime()));
    clonedEntry.setLogProperties(new HashMap<String, Object>(additionalLogProperties));
    
    List<LogEntry> clonedList = new ArrayList<>(previousLogEntries.size());
    Collections.copy(clonedList, previousLogEntries);
    
    clonedEntry.setPreviousLogEntries(clonedList);
    
    return clonedEntry;
  }
  
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    
    builder.append("LogEntry: [");
    builder.append("date").append("=").append(logDate);
    builder.append("message").append("=").append(logMessage);
    builder.append("severity").append("=").append(logSeverity);
    builder.append("additionalProperties").append("=").append(additionalLogProperties);
    builder.append("previousLogs").append("=").append(previousLogEntries);
    builder.append("]");
    
    return builder.toString();
  }
}
