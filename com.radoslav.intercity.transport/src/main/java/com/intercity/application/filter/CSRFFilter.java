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

public class CSRFFilter implements Filter{
  
  private final String CSRF_HEADER = "X-CSRF-TOKEN";

  public void init(FilterConfig filterConfig) throws ServletException {
    
  }

  public void doFilter(ServletRequest request, ServletResponse response,
      FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest servletRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;
    String requestUri = servletRequest.getRequestURI().toString();
    
    if (requestUri.equals("/api/v1/user/registration") == false) {
      String xsrfHeader = servletRequest.getHeader(CSRF_HEADER);
      
      if (xsrfHeader == null) {
        sendErrorMessage(httpResponse, "Missing xsrf token.", HttpURLConnection.HTTP_UNAUTHORIZED);
        return;
      }
      
      if (xsrfHeader.equals("Fetch") == false) {
        HttpSession session = servletRequest.getSession(false);
        
        if (session == null) {
          sendErrorMessage(httpResponse, "Please log on to proceed with request.", HttpURLConnection.HTTP_FORBIDDEN);
          return;
        }
        
        String xsrfToken = (String) session.getAttribute(CSRF_HEADER);
        
        if (xsrfToken == null || xsrfToken.equals(xsrfHeader) == false) {
          sendErrorMessage(httpResponse, "Wrong xsrf token.", HttpURLConnection.HTTP_UNAUTHORIZED);
          return;
        }
      } else {
        String uri = servletRequest.getRequestURI().toString();
        
        if (uri.equals("/api/v1/csrf/token") == false) {
          sendErrorMessage(httpResponse, "Wrong XSRF token endpoint", HttpURLConnection.HTTP_BAD_REQUEST);
          return;
        }
      }
    }
    filterChain.doFilter(request, response);
  }

  private void sendErrorMessage(HttpServletResponse httpResponse, String errorMessage, int responseCode) throws IOException {
    httpResponse.addHeader("ErrorDescription", errorMessage);
    httpResponse.sendError(responseCode, errorMessage);
    httpResponse.getWriter().println(errorMessage);
  }

  public void destroy() {
    
  }

}
