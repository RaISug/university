package com.radoslav.log.analyzer.constants;

public class CfgConstants {

  
  public static final String SPI_LOOKUP = "com.radoslav.spi.services.lookup";
  public static final String OSGI_LOOKUP = "com.radoslav.osgi.services.lookup";
  public static final String JNDI_LOOKUP = "com.radoslav.jndi.services.lookup";

  public static final String OAUTH_TOKEN = "com.radoslav.oauth.token";
  public static final String CLIENT_CREDENTIALS = "com.radoslav.client.credentials";
  public static final String AUTHORIZATION_TYPE = "com.radoslav.authorization.type";

  public static final String LOG_ENTRY_TEMPLATES = "com.radoslav.log.entry.templates";

  public static final String DEFAULT_LOG_ENTRIES_DELIMITER = "\n";
  public static final String LOG_ENTRIES_DELIMITER = "com.radoslav.log.entries.delimiter";
  
  public static final String DEFAULT_LOG_KEYS_DELIMITER = "#";
  public static final String LOG_KEYS_DELIMITER = "com.radoslav.log.keys.delimiter";
  
  public static final String JNDI_NAME_OF_LOG_ENTRY_PARSER = "com.radoslav.log.entry.parser.classname";
  public static final String JNDI_NAME_OF_LOG_ENTRY_BUILDER = "com.radoslav.log.entry.builder.classname";
  public static final String JNDI_NAME_OF_DATABASE_PROVIDER = "com.radoslav.database.provider.classname";
  public static final String JNDI_NAME_OF_STATISTICS_PROVIDER = "com.radoslav.statistic.provider.classname";
  
  public static final String DATABASE_SERVICE_ENDPOINT_URL = "com.radoslav.database.service.endpoint.url";
  public static final String STATISTICS_SERVICE_ENDPOINT_URL = "com.radoslav.statistics.service.endpoint.url";

}
