package com.radoslav.microclimate.service.providers;

import javax.persistence.EntityManager;
import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.ext.ContextProvider;
import org.apache.cxf.message.Message;

import com.radoslav.microclimate.service.pojos.EntityManagerContext;

@Provider
public class EntityManagerContextProvider implements ContextProvider<EntityManager> {

  public EntityManager createContext(Message message) {
    EntityManagerContext managerContext = new EntityManagerContext();
    return managerContext.getEntityManager();
  }

}
