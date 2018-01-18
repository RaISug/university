package com.radoslav.log.analyzer.util;

import java.util.List;

import com.radoslav.log.providers.ConfigurationProvider;
import com.radoslav.log.providers.ConfigurationProviderImpl;

public class Configuration {

  public static String getCfgValueByKey(String key) {
    List<ConfigurationProvider> cfgProviders = Lookup.allInstancesWithSpiByClass(ConfigurationProvider.class);
    
    for (ConfigurationProvider cfgProvider : cfgProviders) {
      String propertyValue = cfgProvider.getProperty(key);
      if (propertyValue != null) {
        return propertyValue;
      }
    }
    
    return new ConfigurationProviderImpl().getProperty(key);
  }

  public static String getCfgValueByKey(String key, String defaultValue) {
    String propertyValue = Configuration.getCfgValueByKey(key);
    return propertyValue == null ? defaultValue : propertyValue;
  }
  
}
