package com.moutain.microclimate.service.filters;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import com.moutain.microclimate.service.annotations.EntityManagerBinding;
import com.moutain.microclimate.service.dbaccessors.DatabaseConnector;
import com.moutain.microclimate.service.exceptions.MicroclimateException;
import com.moutain.microclimate.service.pojos.EntityManagerContext;

@Provider
@EntityManagerBinding
public class EntityManagerFilter implements ContainerRequestFilter, ContainerResponseFilter {

  public void filter(ContainerRequestContext requestContext) throws IOException {
    EntityManager entityManager;
    try {
      entityManager = DatabaseConnector.getEntityManager();
      EntityManagerContext managerContext = new EntityManagerContext();
      managerContext.setEntityManager(entityManager);
    } catch (MicroclimateException e) {
      throw new RuntimeException("Failed to create Entity Manager.");
    }
  }
  
  public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
    EntityManagerContext managerContext = new EntityManagerContext();
    EntityManager entityManager = managerContext.getEntityManager();
    managerContext.removeEntityManager();
    closeEntityManager(entityManager);
  }
  
  private void closeEntityManager(EntityManager entityManager) {
    if (entityManager != null) {
      if (entityManager.getTransaction().isActive()) {
        entityManager.getTransaction().rollback();
        throw new RuntimeException("Active transaction was found.");
      }
      entityManager.close();
    }
  }

}
