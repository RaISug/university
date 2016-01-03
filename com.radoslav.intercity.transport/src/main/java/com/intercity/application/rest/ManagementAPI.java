package com.intercity.application.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("management/api/v1")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ManagementAPI {
  
  @Context private HttpServletRequest request;
  @Context private HttpServletResponse response;

  @POST
  @Path("/vehicle")
  public Response vehicle() {
    return Response.ok("{\"message\":\"Успешно регистриране на превозното средство.\"}", MediaType.APPLICATION_JSON).build();
  }
  
  @POST
  @Path("/route")
  public Response route() {
    return Response.ok("{\"message\":\"Успешно регистриране на маршрут.\"}", MediaType.APPLICATION_JSON).build();
  }
  
  @POST
  @Path("/driver")
  public Response driver() {
    return Response.ok("{\"message\":\"Успешно регистриране на шофьор.\"}", MediaType.APPLICATION_JSON).build();
  }
  
}
