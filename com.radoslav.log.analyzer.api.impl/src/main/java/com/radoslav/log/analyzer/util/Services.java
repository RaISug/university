package com.radoslav.log.analyzer.util;

public class Services {

  public static boolean isRegisteredWithOsgi() {
    return Boolean.parseBoolean(Configuration.getCfgValueByKey("com.radoslav.osgi.services.lookup"));
  }

  public static boolean isRegisteredWithSpi() {
    return Boolean.parseBoolean(Configuration.getCfgValueByKey("com.radoslav.spi.services.lookup"));
  }

  public static boolean isRegisteredWithJndi() {
    return Boolean.parseBoolean(Configuration.getCfgValueByKey("com.radoslav.jndi.services.lookup"));
  }
  
}
