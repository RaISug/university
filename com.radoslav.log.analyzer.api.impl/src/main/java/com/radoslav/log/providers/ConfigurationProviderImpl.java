package com.radoslav.log.providers;

import java.util.Map;
import java.util.HashMap;

public class ConfigurationProviderImpl implements ConfigurationProvider {

  private static Map<String, String> cfgProperties = new HashMap<>();
  
  public void putProperty(String key, String value) {
    cfgProperties.put(key, value);
  }
  
  @Override
  public String getProperty(String propertyKey) {
    String propertyValue = System.getProperty(propertyKey);
    if (propertyValue != null) {
      return propertyValue;
    }
    
    return cfgProperties.get(propertyKey);
  }
}
