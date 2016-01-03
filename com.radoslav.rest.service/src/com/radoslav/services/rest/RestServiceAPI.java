package com.radoslav.services.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.GsonBuilder;
import com.radoslav.services.entity.User;
import com.radoslav.services.entity.access.UserDAO;


@Path("/api/v1")
public class RestServiceAPI {
	
	private UserDAO userAccessObject = null;
	
	public RestServiceAPI() {
		userAccessObject = UserDAO.getInstance();
	}

	@GET
	@Path("/users")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsers() {
		List<User> users = userAccessObject.getData();
		
		return Response.ok(this.toJson(users), MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Path("/user/{userName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsers(@PathParam("userName") String name) {
		User user = userAccessObject.getSingalData(name);
		
		return Response.ok(this.toJson(user), MediaType.APPLICATION_JSON).build();
	}
	
	private String toJson(Object data) {
		return new GsonBuilder().setPrettyPrinting().create().toJson(data);
	}
}
