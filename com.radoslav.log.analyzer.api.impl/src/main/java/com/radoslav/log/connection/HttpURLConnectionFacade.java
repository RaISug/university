package com.radoslav.log.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

import com.radoslav.log.analyzer.constants.CfgConstants;
import com.radoslav.log.analyzer.util.Configuration;

public class HttpURLConnectionFacade {

  private static final int DEFAULT_TIMEOUT = 5 * 1000;
  private static final String HTTP_GET_REQUEST = "GET";
  private static final String HTTP_POST_REQUEST = "POST";
  
  private String endpoint = null;
  private HttpURLConnection connection = null;
  
  public HttpURLConnectionFacade(String endpoint) throws Exception {
    this.endpoint = endpoint;
    this.connection = createURLConnection();
  }
  
  private HttpURLConnection createURLConnection() throws Exception {
    URL endpointURL = new URL(endpoint);
    if (isConnectionOverTLS(endpointURL)) {
      return createHttpURLConnection(endpointURL);
    }
    return createHttpsURLConnection(endpointURL);
  }
  
  private boolean isConnectionOverTLS(URL endpointURL) throws MalformedURLException {
    return endpointURL.getProtocol().equalsIgnoreCase("HTTPS");
  }
  
  private HttpURLConnection createHttpURLConnection(URL endpointURL) throws IOException {
    return (HttpURLConnection) endpointURL.openConnection();
  }
  
  private HttpsURLConnection createHttpsURLConnection(URL endpointURL) throws IOException {
    return (HttpsURLConnection) endpointURL.openConnection();
  }

  public void prepareGETRequest() throws Exception {
    connection.setDoInput(true);
    
    connection.setRequestMethod(HTTP_GET_REQUEST);
    connection.setReadTimeout(DEFAULT_TIMEOUT);
    connection.setConnectTimeout(DEFAULT_TIMEOUT);
  }
  
  public void preparePOSTRequest() throws Exception {
    connection.setDoInput(true);
    connection.setDoOutput(true);
    
    connection.setReadTimeout(DEFAULT_TIMEOUT);
    connection.setConnectTimeout(DEFAULT_TIMEOUT);
    connection.setRequestMethod(HTTP_POST_REQUEST);

    connection.setRequestProperty("Content-Type", "application/json");
  }
  
  public void configureDefaultAuthorizationHeader() {
    String authorizationType = getAuthorizationType();
    if (authorizationType != null) {
      String authorizationValue = getAuthorizationValue(authorizationType);
      if (authorizationValue != null) {
        String authorizationHeader = String.format("%s %s", authorizationType, authorizationValue);
        connection.setRequestProperty("Authorization", authorizationHeader);
      }
    }
  }

  private String getAuthorizationType() {
    return Configuration.getCfgValueByKey(CfgConstants.AUTHORIZATION_TYPE);
  }

  private String getAuthorizationValue(String authorizationType) {
    if (authorizationType.equalsIgnoreCase("Basic")) {
      return getBase64EncodedClientCredentials();
    }
    return getOAuthToken();
  }

  private String getBase64EncodedClientCredentials() {
    return Configuration.getCfgValueByKey(CfgConstants.CLIENT_CREDENTIALS);
  }

  private String getOAuthToken() {
    return Configuration.getCfgValueByKey(CfgConstants.OAUTH_TOKEN);
  }

  public void addRequestBody(String requestBody) throws IOException {
    if (requestBody != null) {
      OutputStream outputStream = connection.getOutputStream();
      outputStream.write(requestBody.getBytes(StandardCharsets.UTF_8));
    }
  }
  
  public void executeRequest() throws Exception {
    connection.connect();
  }
  
  public String readAndConsumeInputStream() throws IOException {
    InputStream inputStream = null;
    
    int statusCode = connection.getResponseCode();
    if (statusCode >= 400) {
      inputStream = connection.getErrorStream();
    } else {
      inputStream = connection.getInputStream();
    }
    
    if (inputStream == null) {
      return new String();
    }
    
    try {
      CharBuffer charBuffer = CharBuffer.allocate(connection.getContentLength());
      
      BufferedReader bufferReader = new BufferedReader(new InputStreamReader(inputStream));
      while (bufferReader.read(charBuffer) != -1);

      return charBuffer.toString();
    } finally {
      inputStream.close();
    }
  }
  
  public void closeConnection() {
    connection.disconnect();
  }
  
}
