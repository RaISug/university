package com.radoslav.microclimate.service.converters;

import org.eclipse.persistence.mappings.DatabaseMapping;
import org.eclipse.persistence.mappings.converters.Converter;
import org.eclipse.persistence.sessions.Session;

import com.radoslav.microclimate.service.beans.Weather;

public class WeatherConverter implements Converter {

  private static final long serialVersionUID = 1L;

  public Object convertObjectValueToDataValue(Object objectValue, Session session) {
    return null;
  }

  public Object convertDataValueToObjectValue(Object dataValue, Session session) {
    return Weather.fromString((String) dataValue);
  }

  public boolean isMutable() {
    return false;
  }

  public void initialize(DatabaseMapping mapping, Session session) {
    
  }

}
