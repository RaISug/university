package com.radoslav.javaee.courses.db.accessors;

import java.util.Set;

import com.radoslav.javaee.courses.entities.EntityType;
import com.radoslav.javaee.courses.entities.PersistentEntity;

public interface PersistenceAccessor {
  
  public void insert(PersistentEntity entity);

  public void update(PersistentEntity entity);

  public void delete(PersistentEntity entity);

  public Set<? extends PersistentEntity> selectAll(EntityType entityType);

  public PersistentEntity select(EntityType entityType, String identifier);

}
