package com.radoslav.log.analyzer.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

public class Lookup {

  public static Object withJndiByName(String name) {
    try {
      InitialContext context = new InitialContext();
      return context.lookup(name);
    } catch (NamingException e) {
      return null;
    }
  }
  
  public static <T> T theFirstInstanceWithSpiByClass(Class<T> clazz) {
    ServiceLoader<T> loader = ServiceLoader.load(clazz);
    
    Iterator<T> iterator = loader.iterator();
    if (iterator != null && iterator.hasNext()) {
      return iterator.next();
    }
    
    return null;
  }
  
  public static <T> List<T> allInstancesWithSpiByClass(Class<T> clazz) {
    ServiceLoader<T> loader = ServiceLoader.load(clazz);
    
    Iterator<T> iterator = loader.iterator();
    if (iterator != null && iterator.hasNext()) {
      List<T> instances = new ArrayList<>();
      while (iterator.hasNext()) {
        instances.add(iterator.next());
      }
      return instances;
    }
    
    return new ArrayList<T>();
  }
  

  @SuppressWarnings("unchecked")
  public static <T> T theFirstServiceWithOsgiByClassName(Class<T> clazz) {
    BundleContext bundleContext = FrameworkUtil.getBundle(Lookup.class).getBundleContext();
    ServiceReference<?> reference = bundleContext.getServiceReference(clazz.getName());
    if (reference == null) {
      return null;
    }
    
    return (T) bundleContext.getService(reference);
  }
  
  @SuppressWarnings("unchecked")
  public static <T> List<T> allServicesWithOsgiByClassName(Class<T> clazz) {
    List<T> services = new ArrayList<>();
    
    try {
      BundleContext bundleContext = FrameworkUtil.getBundle(Lookup.class).getBundleContext();
      ServiceReference<?>[] references = bundleContext.getAllServiceReferences(clazz.getName(), null);
      if (references == null || references.length == 0) {
        return services;
      }
      
      for (ServiceReference<?> reference : references) {
        services.add((T) bundleContext.getService(reference));
      }
    } catch (InvalidSyntaxException e) {
      return services;
    }
    
    return services;
  }
}
