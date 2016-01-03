package com.radoslav.microclimate.service.filters;

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

import com.radoslav.microclimate.service.helpers.Constants;

public class CSRFProtectionFilter implements Filter {

  private static final String POST = "POST";
  private static final String CSRF_HEADER = "X-CSRF-TOKEN";
  private static final String CSRF_FETCH_REQUEST = "Fetch";
  private static final String CSRF_TOKEN_ENDPOINT = "/api/v1/csrf";
  private static final String REGISTRATION_ENDPOINT = "/api/v1/users";

  public void init(FilterConfig filterConfig) throws ServletException {

  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest servletRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;

    String methodType = servletRequest.getMethod();
    String requestUri = servletRequest.getRequestURI().toString();
    if (requestUri.equals(REGISTRATION_ENDPOINT) == false || POST.equals(methodType) == false) {
      String xsrfHeader = servletRequest.getHeader(CSRF_HEADER);

      if (xsrfHeader == null) {
        sendErrorMessage(httpResponse, "Missing xsrf token.", HttpURLConnection.HTTP_UNAUTHORIZED);
        return;
      }

      if (xsrfHeader.equals(CSRF_FETCH_REQUEST) == false) {
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

        if (uri.equals(CSRF_TOKEN_ENDPOINT) == false) {
          sendErrorMessage(httpResponse, "Wrong XSRF token endpoint", HttpURLConnection.HTTP_BAD_REQUEST);
          return;
        }
      }
    }
    filterChain.doFilter(request, response);
  }

  private void sendErrorMessage(HttpServletResponse httpResponse, String errorMessage, int responseCode) throws IOException {
    httpResponse.addHeader(Constants.ERROR_DESCRIPTION_HEADER, errorMessage);
    httpResponse.sendError(responseCode, errorMessage);
    httpResponse.getWriter().println(errorMessage);
  }

  public void destroy() {

  }

}
