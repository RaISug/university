package com.radoslav.log.providers;

public class ConfigurationProviderImpl implements ConfigurationProvider {

  @Override
  public String getPropertyValue(String propertyKey) {
    return System.getProperty(propertyKey);
  }

}
