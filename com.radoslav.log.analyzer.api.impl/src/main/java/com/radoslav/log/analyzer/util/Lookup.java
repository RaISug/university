package com.radoslav.log.analyzer.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import javax.naming.InitialContext;
import javax.naming.NamingException;

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
    
    return null;
  }
}
