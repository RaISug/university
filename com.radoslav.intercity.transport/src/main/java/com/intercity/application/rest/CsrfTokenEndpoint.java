package com.intercity.application.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.intercity.application.pojo.CsrfBean;
import com.intercity.application.util.CsrfTokenUtil;

@Path("/api/v1/csrf")
public class CsrfTokenEndpoint {

  @Context HttpServletRequest request;
  @Context HttpServletResponse response;
  
  @GET
  @Path("token")
  @Produces(MediaType.APPLICATION_JSON)
  public CsrfBean getXSRFToken() {
    String token = CsrfTokenUtil.generateCsrfToken();
    
    HttpSession session = request.getSession(false);
    session.setAttribute("X-CSRF-TOKEN", token);
    
    CsrfBean xsrfResponse = new CsrfBean();
    xsrfResponse.setHeaderName("X-CSRF-TOKEN");
    xsrfResponse.setXsrfToken(token);
    
    return xsrfResponse;
  }
}
