package com.intercity.application.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.intercity.database.accessor.StationAccessor;
import com.intercity.database.entity.Station;
import com.intercity.exception.DatabaseConnectionException;
import com.intercity.exception.DatabaseResultException;

@Path("transport/api/v1/stations")
public class StationsAPI {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Station> getCities() throws DatabaseConnectionException, DatabaseResultException {
    StationAccessor accessor = new StationAccessor();
    return accessor.getCities();
  }
  
  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Station getStation(@PathParam("id") int stationId) throws DatabaseConnectionException, DatabaseResultException {
    StationAccessor accessor = new StationAccessor();
    return accessor.getStationById(stationId);
  }
  
}
