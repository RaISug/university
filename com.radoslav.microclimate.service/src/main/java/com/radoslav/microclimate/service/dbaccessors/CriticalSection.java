package com.radoslav.microclimate.service.dbaccessors;

public interface CriticalSection<T> {

  public T getResult();

  public void executeQuery();
  
}
