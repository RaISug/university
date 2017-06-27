package com.radoslav.javaee.courses.entities;

public class Image implements InMemoryEntity {

  private String name;
  private String owner;
  private byte[] content;

  public Image(String name, String owner, byte[] content) {
    this.name = name;
    this.owner = owner;
    this.content = content;
  }

  public EntityType getType() {
    return EntityType.IMAGE;
  }

  @Override
  public String getIdentifier() {
    return name;
  }

  public String getOwner() {
    return owner;
  }

  public byte[] getContent() {
    return content;
  }
}
