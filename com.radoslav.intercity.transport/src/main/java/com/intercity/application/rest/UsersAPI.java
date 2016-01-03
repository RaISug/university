package com.intercity.application.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.intercity.database.accessor.UserAccessor;
import com.intercity.database.entity.User;
import com.intercity.exception.DatabaseConnectionException;
import com.intercity.exception.DatabaseResultException;

@Path("users/api/v1/")
public class UsersAPI {

  @GET
  @Path("drivers/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public User getDriver(@PathParam("id") int driverId) throws DatabaseConnectionException, DatabaseResultException {
    UserAccessor accessor = new UserAccessor();
    User user = accessor.getUser(driverId);
    user.setPassword(null);
    if (user.getType().equals("driver") == false) {
      throw new DatabaseResultException("Wrong database record.", 500);
    }
    return user;
  }
}
