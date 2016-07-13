package ua.rd.foodorder.infrastructure.passwordGenerateAndHash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by Artem_Lomako on 7/11/2016.
 */
import java.util.Random;

public class PasswordGeneratorAndHashing {

    private static final String ALPHA_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUM = "0123456789";
    private static final String SPL_CHARS = "!@#$%^&*_=+-/";
    private static final int MIN_LENGTH_OF_PASSWORD = 9;
    private static final int MAX_LENGTH_OF_PASSWORD = 21;
    private static final int MIN_QUANTITY_OF_SYMBOLS = 1;

    public static char[] generatePswd() {

        Random rnd = new Random();

        if (MIN_LENGTH_OF_PASSWORD > MAX_LENGTH_OF_PASSWORD)
            throw new IllegalArgumentException("Min. Length > Max. Length!");


        int len = rnd.nextInt(MAX_LENGTH_OF_PASSWORD - MIN_LENGTH_OF_PASSWORD + 1)
                + MIN_LENGTH_OF_PASSWORD;

        int noOfCAPSAlpha = MIN_QUANTITY_OF_SYMBOLS + (int)(Math.random()*len);

        int noOfDigits = MIN_QUANTITY_OF_SYMBOLS
                + (int)(Math.random() * (len - noOfCAPSAlpha));

        int noOfSplChars = MIN_QUANTITY_OF_SYMBOLS
                + (int)(Math.random() * (len - noOfCAPSAlpha - noOfDigits));

        char[] pswd = new char[len];
        int index = 0;
        for (int i = 0; i < noOfCAPSAlpha; i++) {
            index = getNextIndex(rnd, len, pswd);
            pswd[index] = ALPHA_CAPS.charAt(rnd.nextInt(ALPHA_CAPS.length()));
        }
        for (int i = 0; i < noOfDigits; i++) {
            index = getNextIndex(rnd, len, pswd);
            pswd[index] = NUM.charAt(rnd.nextInt(NUM.length()));
        }
        for (int i = 0; i < noOfSplChars; i++) {
            index = getNextIndex(rnd, len, pswd);
            pswd[index] = SPL_CHARS.charAt(rnd.nextInt(SPL_CHARS.length()));
        }
        for (int i = 0; i < len; i++) {
            if (pswd[i] == 0) {
                pswd[i] = ALPHA.charAt(rnd.nextInt(ALPHA.length()));
            }
        }
        return pswd;
    }

    private static int getNextIndex(Random rnd, int len, char[] pswd) {
        int index = rnd.nextInt(len);
        while (pswd[index = rnd.nextInt(len)] != 0) ;
        return index;
    }


}