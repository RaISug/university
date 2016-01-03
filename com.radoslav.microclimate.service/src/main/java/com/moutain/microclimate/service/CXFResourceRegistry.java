package com.moutain.microclimate.service;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.moutain.microclimate.service.exceptions.MicroclimateExceptionMapper;
import com.moutain.microclimate.service.filters.EntityManagerFilter;
import com.moutain.microclimate.service.providers.EntityManagerContextProvider;
import com.moutain.microclimate.service.providers.GsonJsonProvider;

public class CXFResourceRegistry extends Application {

  private Set<Object> singletons = new HashSet<Object>();
  private Set<Class<?>> classes = new HashSet<Class<?>>();

  public CXFResourceRegistry() {
    singletons.add(new GsonJsonProvider());
    singletons.add(new MicroclimateExceptionMapper());

    classes.add(EntityManagerFilter.class);
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
