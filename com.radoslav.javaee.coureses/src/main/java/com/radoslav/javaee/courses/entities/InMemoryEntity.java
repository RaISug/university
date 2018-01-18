package com.radoslav.javaee.courses.entities;

public interface InMemoryEntity extends PersistentEntity {

  public EntityType getType();
  public String getIdentifier();

}
