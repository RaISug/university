package com.intercity.application.filter;

import java.io.IOException;
import java.net.HttpURLConnection;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.codec.binary.Base64;

import com.intercity.database.accessor.UserAccessor;
import com.intercity.database.entity.User;
import com.intercity.exception.ClientCredentialException;
import com.intercity.exception.DatabaseConnectionException;

public class BasicAuthenticationFilter implements Filter{

  public void init(FilterConfig filterConfig) throws ServletException {
    
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest servletRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;
    
    HttpSession session = servletRequest.getSession(false);
    if (session == null) {
      String authorizationHeader = servletRequest.getHeader("Authorization");
      
      if (authorizationHeader == null) {
        sendErrorMessage(httpResponse, "Missing authorization header", HttpURLConnection.HTTP_BAD_REQUEST);
        return;
      }
      
      String[] parsedHeader = authorizationHeader.split(" ");
      
      if (parsedHeader == null || parsedHeader.length != 2 || parsedHeader[0].equals("Basic") == false) {
        sendErrorMessage(httpResponse, "Wrong authorization header", HttpURLConnection.HTTP_BAD_REQUEST);
        return;
      }
  
      String decodedHeader = new String(Base64.decodeBase64(parsedHeader[1]));
      if (decodedHeader.trim().equals("")) {
        sendErrorMessage(httpResponse, "Missing credentials", HttpURLConnection.HTTP_FORBIDDEN);
        return;
      }
      
      String[] credentials = decodedHeader.split(":");
      
      UserAccessor accessor = new UserAccessor(credentials[0], credentials[1]);
      try {
        User user = accessor.validateCredentials();
        
        servletRequest.getSession(true);
        String clientName = user.getFirstname() + " " + user.getLastname();
        servletRequest.getSession().setAttribute("clientName", clientName);
        servletRequest.getSession().setAttribute("userId", user.getId());
      } catch (ClientCredentialException e) {
        sendErrorMessage(httpResponse, e.getMessage(), e.getStatusCode());
        return;
      } catch (DatabaseConnectionException e) {
        sendErrorMessage(httpResponse, e.getMessage(), e.getStatusCode());
        return;
      }
    }
    chain.doFilter(request, response);
  }
  
  private void sendErrorMessage(HttpServletResponse httpResponse, String errorMessage, int responseCode) throws IOException {
    httpResponse.addHeader("ErrorDescription", errorMessage);
    httpResponse.sendError(responseCode, errorMessage);
    httpResponse.getWriter().println(errorMessage);
  }

  public void destroy() {
    
  }

}
