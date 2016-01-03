package com.radoslav.microclimate.service;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.radoslav.microclimate.service.exceptions.MicroclimateExceptionMapper;
import com.radoslav.microclimate.service.filters.EntityManagerFilter;
import com.radoslav.microclimate.service.providers.EntityManagerContextProvider;
import com.radoslav.microclimate.service.providers.GsonJsonProvider;
import com.radoslav.microclimate.service.rest.CSRFTokenManagementAPI;
import com.radoslav.microclimate.service.rest.UserManagementAPI;

public class CXFResourceRegistry extends Application {

  private Set<Object> singletons = new HashSet<Object>();
  private Set<Class<?>> classes = new HashSet<Class<?>>();

  public CXFResourceRegistry() {
    singletons.add(new GsonJsonProvider());
    singletons.add(new MicroclimateExceptionMapper());

    classes.add(UserManagementAPI.class);
    classes.add(EntityManagerFilter.class);
    classes.add(CSRFTokenManagementAPI.class);
    classes.add(EntityManagerContextProvider.class);
  }
  
  @Override
  public Set<Object> getSingletons() {
    return singletons;
  }
  
  @Override
  public Set<Class<?>> getClasses() {
    return classes;
  }
  
}
