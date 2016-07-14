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


    static Random r = new Random();

    static char[] choices = (ALPHA +
            ALPHA_CAPS +
            NUM +
            SPL_CHARS).toCharArray();

    public static char[] generatePswd() {
        int len = MIN_LENGTH_OF_PASSWORD + (int) (Math.random() * MAX_LENGTH_OF_PASSWORD);
        StringBuilder salt = new StringBuilder(len);
        for (int i = 0; i<len; ++i)
            salt.append(choices[r.nextInt(choices.length)]);
        return salt.toString().toCharArray();
    }
}