package com.intercity.jaas.principal;

import java.security.Principal;

public class RolePrincipal implements Principal{
  
  private String name = null;
  
  public RolePrincipal(String name) {
    this.name = name;
  }

  public void setName(String name) {
    this.name = name;
  }
  
  public String getName() {
    return name;
  }

}
