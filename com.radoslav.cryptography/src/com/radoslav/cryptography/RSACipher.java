package com.radoslav.cryptography;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;

import javax.crypto.NoSuchPaddingException;

public class RSACipher {

  private static final int BIT_LENGTH_OF_PRIMARY_NUMBERS = 1024;
  private static final SecureRandom RANDOM_GENERATOR = new SecureRandom();
  private static final BigInteger PUBLIC_KEY_EXPONENT = new BigInteger("65537");

  public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException {
    BigInteger firstPrimeNumber = generatePrimaryNumber();
    BigInteger secondPrimeNumber = generatePrimaryNumber(firstPrimeNumber);

    BigInteger module = firstPrimeNumber.multiply(secondPrimeNumber);
    BigInteger eulierTotient = calculateEulierTotient(firstPrimeNumber, secondPrimeNumber);

    BigInteger privateKeyExponent = calculatePrivateKeyExponent(eulierTotient);

    Scanner scanner = new Scanner(System.in);

    try {
      System.out.println("Please enter the message that you want to be encrypted: ");

      String messageToEncrypt = scanner.nextLine();
      System.out.println("Message to encrypt: " + messageToEncrypt);

      BigInteger cipherAsInteger = encryptMessage(PUBLIC_KEY_EXPONENT, module, messageToEncrypt);

      System.out.println("Encrypted message: " + new String(cipherAsInteger.toByteArray()));

      String decryptMessage = decryptMessage(cipherAsInteger, privateKeyExponent, module);

      System.out.println("Decrypted message: " + decryptMessage);
    } finally {
      scanner.close();
    }
  }

  /**
   * 
   * Encyrpts plaintext by a given public key exponent module and message
   * 
   * @param publicKeyExponent
   * @param module
   * @param message
   * 
   * @return encrypted message
   */
  private static BigInteger encryptMessage(BigInteger publicKeyExponent, BigInteger module, String message) {
    BigInteger plaintextAsInteger = convertPlaintextToInteger(message);

    return plaintextAsInteger.modPow(publicKeyExponent, module);
  }

  /**
   * 
   * Decrypt message by a given cipher text private key exponent and module
   * 
   * @param cipherAsInteger
   * @param privateKeyExponent
   * @param module
   * 
   * @return decrypted message
   */
  private static String decryptMessage(BigInteger cipherAsInteger, BigInteger privateKeyExponent, BigInteger module) {
    BigInteger plainTextAsInteger = cipherAsInteger.modPow(privateKeyExponent, module);

    return new String(plainTextAsInteger.toByteArray());
  }

  /**
   * Converts plaintext to integer
   * 
   * @param plaintext
   * @return plaintext as integer
   */
  private static BigInteger convertPlaintextToInteger(String plaintext) {
    return new BigInteger(plaintext.getBytes());
  }

  /**
   * Generate primary number by predefined key length
   * and random generator
   * 
   * Key length - 1024 bits
   * 
   * @return primary number
  */
  private static BigInteger generatePrimaryNumber() {
    return BigInteger.probablePrime(BIT_LENGTH_OF_PRIMARY_NUMBERS, RANDOM_GENERATOR);
  }

  /**
   * Generate the next primary number by the given primary number
   * 
   * @param primaryNumber - primary number which will be considered as starting point for next primary number
   * @return next primary number
  */
  private static BigInteger generatePrimaryNumber(BigInteger primaryNumber) {
    return primaryNumber.nextProbablePrime();
  }

  /**
   * Calculates eulier totient, by given primary keys
   * 
   * @param firstPrime - first primary number
   * @param secondPrime - second primary number
   * @return eulier totient
  */
  private static BigInteger calculateEulierTotient(BigInteger firstPrime, BigInteger secondPrime) {
    return firstPrime.subtract(new BigInteger("1")).multiply(secondPrime.subtract(new BigInteger("1")));
  }

  /**
   * Generates private key exponent by given eulier totient
   * 
   * Public key exponent is predefined wiht value of 65537
   *
   * @param eulierTotient
   * @return private key exponent
  */
  private static BigInteger calculatePrivateKeyExponent(BigInteger eulierTotient) {
    return PUBLIC_KEY_EXPONENT.modInverse(eulierTotient);
  }

}
