package com.intercity.application.rest;

import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.intercity.database.accessor.RouteAccessor;
import com.intercity.database.accessor.VehicleAccessor;
import com.intercity.database.entity.Vehicle;
import com.intercity.exception.DatabaseConnectionException;
import com.intercity.exception.DatabaseResultException;

@Path("transport/api/v1/vehicles")
public class VehiclesAPI {
  
  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Vehicle getVehicleById(@PathParam("id") int vehicleId) throws DatabaseConnectionException, DatabaseResultException {
    VehicleAccessor accessor = new VehicleAccessor();
    return accessor.getVehicleById(vehicleId);
  }
  
  @GET
  @Path("departure")
  @Produces(MediaType.APPLICATION_JSON)
  public List<Date> getTransportDepartureTime() throws DatabaseConnectionException {
    RouteAccessor accessor = new RouteAccessor();
    return accessor.getDepartureTimeList();
  }

}
