package com.moutain.microclimate.service.pojos;

import javax.persistence.EntityManager;

public class EntityManagerContext {

  private ThreadLocal<EntityManager> threadLocal = new ThreadLocal<EntityManager>();

  public EntityManager getEntityManager() {
    return threadLocal.get();
  }
  
  public void setEntityManager(EntityManager entityManager) {
    threadLocal.set(entityManager);
  }

  public void removeEntityManager() {
    threadLocal.set(null);
  }
  
}
