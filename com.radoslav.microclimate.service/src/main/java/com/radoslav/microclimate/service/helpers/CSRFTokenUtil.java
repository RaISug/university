package com.radoslav.microclimate.service.helpers;

import java.math.BigInteger;
import java.security.SecureRandom;


public class CSRFTokenUtil {

  private static final SecureRandom secureRandomGenerator = new SecureRandom();
  
  public static String generateCsrfToken() {
    return new BigInteger(130, secureRandomGenerator).toString(32);
  }
}
