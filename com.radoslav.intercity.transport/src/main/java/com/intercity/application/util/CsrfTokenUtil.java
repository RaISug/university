package com.intercity.application.util;

import java.math.BigInteger;
import java.security.SecureRandom;


public class CsrfTokenUtil {

  private static SecureRandom secureRandomGenerator = new SecureRandom();
  
  public static String generateCsrfToken() {
    return new BigInteger(130, secureRandomGenerator).toString(32);
  }
}
