package com.radoslav.javaee.courses.entities;

public class User implements InMemoryEntity {

  public static final String SESSION_ATTRIBUTE = "user";

  private String name;

  public User(String name) {
    this.name = name;
  }

  @Override
  public EntityType getType() {
    return EntityType.USER;
  }

  @Override
  public String getIdentifier() {
    return name;
  }

}
