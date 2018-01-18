package com.intercity.application.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.intercity.application.pojo.SearchBean;
import com.intercity.database.accessor.RouteAccessor;
import com.intercity.database.entity.Route;
import com.intercity.exception.DatabaseConnectionException;
import com.intercity.exception.DatabaseResultException;
import com.intercity.exception.InputParamException;

@Path("transport/api/v1/routes")
public class RoutesAPI {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Route> getRoutes() throws DatabaseConnectionException {
    RouteAccessor accessor = new RouteAccessor();
    return accessor.getRoutes();
  }
  
  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Route getRouteById(@PathParam("id") int routeId) throws DatabaseConnectionException, DatabaseResultException {
    RouteAccessor accessor = new RouteAccessor();
    return accessor.getRouteById(routeId);
  }
  
  @GET
  @Path("search")
  @Produces(MediaType.APPLICATION_JSON)
  public List<Route> searchRoutes(@QueryParam("") SearchBean searchData) throws DatabaseConnectionException, DatabaseResultException,
    InputParamException {
    RouteAccessor accessor = new RouteAccessor();
    return accessor.searchRoutes(searchData);
  }
}
