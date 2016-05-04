package com.radoslav.log.entries;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.radoslav.log.data.LogSeverity;

public interface LogEntry extends Cloneable {

  public Date getDate();
  
  public void setDate(Date date);
  
  public void setMessage(String message);
  
  public String getMessage();
  
  public void setSeverity(LogSeverity severity);
  
  public LogSeverity getSeverity();
  
  public List<LogEntry> getPreviousLogEntries();
  
  public void setPreviousLogEntries(List<LogEntry> logEntries);
  
  public void addPreviousLogEntry(LogEntry logEntry);
  
  public void putLogProperty(String propertyKey, Object propertyValue);
  
  public Object getLogProperty(String propertyKey);
  
  public void setLogProperties(Map<String, Object> logProperties);
  
  public Map<String, Object> getLogProperties();
  
  public Object clone() throws CloneNotSupportedException;
  
}
