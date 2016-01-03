package com.intercity.application.rest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;
import com.intercity.application.pojo.ReservedSeats;
import com.intercity.application.pojo.ReservedSeats.SeatData;
import com.intercity.database.accessor.SeatsAccessor;
import com.intercity.database.accessor.VehicleAccessor;
import com.intercity.database.entity.Seat;
import com.intercity.database.entity.Vehicle;
import com.intercity.exception.DatabaseConnectionException;
import com.intercity.exception.DatabaseResultException;
import com.intercity.exception.EntityAlreadyExistException;

@Path("transport/api/v1/seats")
public class SeatsAPI {
  
  @Context HttpServletRequest request;
  
  @GET
  @Path("route/{routeId}/vehicle/{vehicleId}/date/{date}")
  public List<Integer> getSeats(@PathParam("routeId") int routeId, @PathParam("vehicleId") int vehicleId, @PathParam("date") String departureDate)
      throws DatabaseConnectionException, DatabaseResultException, EntityAlreadyExistException {
    SeatsAccessor seatAccessor = new SeatsAccessor();
    Seat seat = seatAccessor.getReservedSeatsByRouteAndDate(departureDate, routeId, vehicleId);
    ReservedSeats reservedSeats = new Gson().fromJson(seat.getReservedSeats(), ReservedSeats.class);
    return getFreeSeats(reservedSeats, vehicleId);
  }
  
  @GET
  @Path("delete/route/{routeId}/vehicle/{vehicleId}/date/{date}")
  public Response removeReservedSeat(@PathParam("seatNumber") int seatNumber, @PathParam("vehicleId") int vehicleId,
      @PathParam("routeId") int routeId, @PathParam("date") String departureDate) 
          throws DatabaseConnectionException, DatabaseResultException {
    SeatsAccessor accessor = new SeatsAccessor();
    accessor.removeSeat(departureDate, routeId, vehicleId, (Integer) request.getSession(false).getAttribute("userId"));
    return Response.status(Status.NO_CONTENT).build();
  }
  
  @GET
  @Path("{seatNumber}/route/{routeId}/vehicle/{vehicleId}/date/{date}")
  public Response reserveSeat(@PathParam("seatNumber") int seatNumber, @PathParam("vehicleId") int vehicleId,
      @PathParam("routeId") int routeId, @PathParam("date") String departureDate) 
          throws DatabaseConnectionException, DatabaseResultException {
    SeatsAccessor accessor = new SeatsAccessor();
    accessor.reserveSeat(departureDate, routeId, vehicleId, seatNumber, (String) request.getSession(false).getAttribute("clientName"), (Integer) request.getSession(false).getAttribute("userId"));
    return Response.status(Status.ACCEPTED).build();
  }
  
  private List<Integer> getFreeSeats(ReservedSeats reservedSeats, int vehicleId) throws DatabaseConnectionException, DatabaseResultException, EntityAlreadyExistException {
    VehicleAccessor vehicleAccessor = new VehicleAccessor();
    Vehicle vehicle = vehicleAccessor.getVehicleById(vehicleId);
    
    List<Integer> freeSeats = new ArrayList<Integer>();
    for (int i = 1 ; i <= vehicle.getCapacity() ; i++) {
      freeSeats.add(i);
    }
    
    if (reservedSeats == null) {
      return freeSeats;
    }
    
    List<SeatData> seatList = reservedSeats.getSeats();
    Integer userId = (Integer) request.getSession().getAttribute("userId");
    Iterator<SeatData> iterator = seatList.iterator();
    while (iterator.hasNext()) {
      SeatData seatData = iterator.next();
      Integer seatNumber = seatData.getSeatNumber();
      Integer registratedClient = seatData.getClientId();
      if (registratedClient.equals(userId)) {
        throw new EntityAlreadyExistException("User already has registrated seat.", 409);
      }
      freeSeats.remove(seatNumber);
    }
    return freeSeats;
  }
  
}
