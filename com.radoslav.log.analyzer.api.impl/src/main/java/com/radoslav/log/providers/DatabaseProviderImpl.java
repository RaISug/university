package com.radoslav.log.providers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.radoslav.log.analyzer.constants.CfgConstants;
import com.radoslav.log.analyzer.util.Configuration;
import com.radoslav.log.connection.HttpURLConnectionFacade;
import com.radoslav.log.entries.LogEntry;

public class DatabaseProviderImpl implements DatabaseProvider  {

  private List<LogEntry> logEntries = null;
  
  public DatabaseProviderImpl() {
    logEntries = new ArrayList<LogEntry>();
  }
  
  @Override
  public void processLogEntry(LogEntry logEntry) {
    if (logEntry != null) {
      logEntries.add(logEntry);
      if (logEntries.size() >= 15) {
        sendEntriesToExternalSystem();
      }
    } else if (logEntry == null && logEntries.size() != 0) {
      sendEntriesToExternalSystem();
    } 
  }

  private void sendEntriesToExternalSystem() {
    String endpointURL = getEndpointURL();
    if (endpointURL == null) {
      return;
    }
    
    HttpURLConnectionFacade connection = null;
    try {
      connection = new HttpURLConnectionFacade(endpointURL);

      connection.preparePOSTRequest();
      connection.configureDefaultAuthorizationHeader();
      
      String requestBody = new Gson().toJson(logEntries);
      connection.addRequestBody(requestBody);
      
      connection.executeRequest();
      
      connection.readAndConsumeInputStream();
    } catch (Exception exception) {
      String exceptionMessage = exception.getMessage();
      try {
        exceptionMessage += connection.readAndConsumeInputStream();
      } catch (IOException e) {
        exceptionMessage += "Failed to consume the error response body. Reason: [" + e.getMessage() + "]";
      }
      throw new RuntimeException(exceptionMessage, exception);
    } finally {
      if (connection != null) {
        connection.closeConnection();
      }
    }
  }

  private String getEndpointURL() {
    return Configuration.getCfgValueByKey(CfgConstants.DATABASE_SERVICE_ENDPOINT_URL);
  }
  
}
