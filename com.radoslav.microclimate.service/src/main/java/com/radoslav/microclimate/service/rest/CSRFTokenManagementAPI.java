package com.radoslav.microclimate.service.rest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.radoslav.microclimate.service.exceptions.UnauthorizedException;
import com.radoslav.microclimate.service.helpers.CSRFTokenUtil;
import com.radoslav.microclimate.service.helpers.Constants;

@Path("csrf")
public class CSRFTokenManagementAPI {

  private static final Logger logger = LoggerFactory.getLogger(CSRFTokenManagementAPI.class);
  
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public CSRFToken getCSRFToken(@Context HttpServletRequest request, @Context HttpServletResponse response) throws UnauthorizedException {
    HttpSession session = request.getSession(false);
    if (session == null) {
      throw new UnauthorizedException("CSRF tokens are provided only to authorized users. Please log in to procceed.");
    }

    String token = CSRFTokenUtil.generateCsrfToken();
    logger.debug("CSRF token was successfully generated: [{}]", token);
    
    session.setAttribute(Constants.X_XSRF_HEADER, token);
    
    Cookie cookie = new Cookie(Constants.CSRF_COOKIE_NAME, token);
    response.addCookie(cookie);
    
    logger.debug("CSRF cookie is provided: [{}]", cookie);
    
    CSRFToken csrfToken = new CSRFToken();
    csrfToken.setCsrfToken(token);
    
    return csrfToken;
  }
  
  public class CSRFToken {
    
    private String csrfToken;
    
    public void setCsrfToken(String token) {
      this.csrfToken = token;
    }
    
    public String getCsrfToken() {
      return this.csrfToken;
    }
  }
}
