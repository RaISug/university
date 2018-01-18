package com.radoslav.microclimate.service.beans;

import javax.persistence.EntityManager;

public class EntityManagerContext {

  private static final InheritableThreadLocal<EntityManager> threadLocal = new InheritableThreadLocal<EntityManager>();

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
