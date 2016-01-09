package com.radoslav.microclimate.service.beans;

public enum Weather {
  
  SUNNY("sunny"), CLOUDY("cloudy"), RAINY("rainy"), SNOWY("cloudy");
  
  private String weather;
  
  private Weather(String weather) {
    this.weather = weather;
  }
  
  public String getWeather() {
    return weather;
  }
  
  public static Weather fromString(String param) {
    for (Weather weather : values()) {
      if (weather.getWeather().equals(param)) {
        return weather;
      }
    }
    return null;
  }
  
  public String absolutePath() {
    return this.getClass().getName() + "." + this.name();
  }
}
