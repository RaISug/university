package com.radoslav.log.analyzer.util;

import java.util.List;

import com.radoslav.log.providers.ConfigurationProvider;
import com.radoslav.log.providers.ConfigurationProviderImpl;

public class Configuration {

  public static String getCfgValueByKey(String key) {
    List<ConfigurationProvider> cfgProviders = Lookup.allInstancesWithSpiByClass(ConfigurationProvider.class);
    
    for (ConfigurationProvider cfgProvider : cfgProviders) {
      String value = cfgProvider.getPropertyValue(key);
      if (value != null) {
        return value;
      }
    }
    
    return new ConfigurationProviderImpl().getPropertyValue(key);
  }
}
