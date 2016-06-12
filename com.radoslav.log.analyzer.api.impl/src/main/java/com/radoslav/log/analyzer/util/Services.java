package com.radoslav.log.analyzer.util;

import com.radoslav.log.analyzer.constants.CfgConstants;

public class Services {

  public static boolean isRegisteredWithOsgi() {
    return Boolean.parseBoolean(Configuration.getCfgValueByKey(CfgConstants.OSGI_LOOKUP, String.valueOf(false)));
  }

  public static boolean isRegisteredWithSpi() {
    return Boolean.parseBoolean(Configuration.getCfgValueByKey(CfgConstants.SPI_LOOKUP, String.valueOf(false)));
  }

  public static boolean isRegisteredWithJndi() {
    return Boolean.parseBoolean(Configuration.getCfgValueByKey(CfgConstants.JNDI_LOOKUP, String.valueOf(false)));
  }
  
}
