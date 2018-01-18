package com.intercity.application.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.intercity.database.accessor.UserAccessor;
import com.intercity.database.entity.User;
import com.intercity.exception.DatabaseConnectionException;

@Path("api/v1/user")
public class UserRegistrationAPI {

  @GET
  @Path("/registration")
  public Response registerUser(@QueryParam("") User user) throws DatabaseConnectionException {
    UserAccessor accessor = new UserAccessor(user.getEmail(), user.getPassword());
    accessor.registerUser(user.getFirstname(), user.getLastname(), user.getType());
    return Response.status(Response.Status.CREATED).build();
  }
}
