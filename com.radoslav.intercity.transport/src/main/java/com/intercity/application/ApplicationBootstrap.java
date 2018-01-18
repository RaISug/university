package com.intercity.application;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.intercity.application.provider.JsonProvider;
import com.intercity.application.rest.CompaniesAPI;
import com.intercity.application.rest.CsrfTokenEndpoint;
import com.intercity.application.rest.ManagementAPI;
import com.intercity.application.rest.RoutesAPI;
import com.intercity.application.rest.SeatsAPI;
import com.intercity.application.rest.StationsAPI;
import com.intercity.application.rest.UserRegistrationAPI;
import com.intercity.application.rest.UsersAPI;
import com.intercity.application.rest.VehiclesAPI;
import com.intercity.exception.RestApiExceptionMapper;

public class ApplicationBootstrap extends Application {

  private Set<Object> singleton = new HashSet<Object>();
  
  public ApplicationBootstrap() {
    singleton.add(new SeatsAPI());
    singleton.add(new UsersAPI());
    singleton.add(new RoutesAPI());
    singleton.add(new VehiclesAPI());
    singleton.add(new StationsAPI());
    singleton.add(new JsonProvider());
    singleton.add(new CompaniesAPI());
    singleton.add(new ManagementAPI());
    singleton.add(new CsrfTokenEndpoint());
    singleton.add(new UserRegistrationAPI());
    singleton.add(new RestApiExceptionMapper());
  }
  
  @Override
  public Set<Object> getSingletons() {
    return singleton;
  }
}
