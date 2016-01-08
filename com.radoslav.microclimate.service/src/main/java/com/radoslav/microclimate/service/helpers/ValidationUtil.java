package com.radoslav.microclimate.service.helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.radoslav.microclimate.service.beans.StatisticBean;
import com.radoslav.microclimate.service.exceptions.BadRequestException;
import com.radoslav.microclimate.service.exceptions.MicroclimateException;

public class ValidationUtil {

  private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
  
  public static void validatePassword(String password) throws MicroclimateException {
    if (password == null) {
      throw new BadRequestException("Password field can not be empty.");
    }
    
    if (password.trim().length() <= 5) {
      throw new BadRequestException("Password length must be larger or equal to 6 symbols.");
    }
  }
  
  public static void validateEmail(String email) throws MicroclimateException {
    if (email == null) {
      throw new BadRequestException("Email field can not be empty.");
    }

    Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    Matcher matcher = pattern.matcher(email);
    
    if (matcher.matches() == false) {
      throw new BadRequestException("Email field should contain valid email.");
    }
  }

  public static void validateThatParamIsNotEmpty(String param, String paramName) throws BadRequestException {
    if (param == null) {
      throw new BadRequestException("\"" + paramName + "\" field can not be empty.");
    }
  }
  
  public static void validateStatisticInputData(StatisticBean statistic) throws BadRequestException {
    if (statistic.getRainfall() < 0) {
      throw new BadRequestException("\"rainfall\" field can't contain negative value.");
    }
    
    if (statistic.getHumidity() < 0) {
      throw new BadRequestException("\"humidity\" field can't contain negative value.");
    } else if (statistic.getHumidity() == 0) {
      throw new BadRequestException("\"humidity\" field can't be empty.");
    }
    
    if (statistic.getSnowCover() < 0) {
      throw new BadRequestException("\"snowCover\" field can't contain negative value.");
    }
    
    if (statistic.getWindSpeed() < 0) {
      throw new BadRequestException("\"windSpeed\" field can't contain negative value.");
    }
    
    int type = statistic.getWeather();
    if (type < 0 && type > 4) {
      throw new BadRequestException("\"type\" field can not contains the provided value.");
    }
    
    if (statistic.getLatitude() == 0L) {
      throw new BadRequestException("\"latitude\" field can't be empty.");
    }
    
    if (statistic.getLongitude() == 0L) {
      throw new BadRequestException("\"longitude\" field can't be empty.");
    }
    
    if (statistic.getDate() == null) {
      throw new BadRequestException("\"date\" field can't be empty.");
    }
  }

}
