package ua.rd.foodorder.infrastructure.passwordGenerateAndHash;

import java.security.MessageDigest;

public class PasswordHash {

    public static String hash(char [] base) {
        try {
            String passwordString = new String(base);
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(passwordString.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return new String(hexString);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }
}