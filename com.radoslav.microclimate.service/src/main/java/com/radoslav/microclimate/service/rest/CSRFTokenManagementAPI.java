package com.radoslav.microclimate.service.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.radoslav.microclimate.service.helpers.CSRFTokenUtil;

@Path("csrf")
public class CSRFTokenManagementAPI {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public CSRFToken getCSRFToken(@Context HttpServletRequest request) {
    String token = CSRFTokenUtil.generateCsrfToken();
    
    HttpSession session = request.getSession(false);
    session.setAttribute("X-CSRF-TOKEN", token);
    
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
