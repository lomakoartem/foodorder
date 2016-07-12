package ua.rd.foodorder.infrastructure.passwordGenerateAndHash;

import java.security.SecureRandom;

/**
 * Created by Artem_Lomako on 7/11/2016.
 */


public class PasswordGeneratorAndHashing implements PasswordGenerator {
    String charactersToBeUsed;

    private static final String UPPERCASE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int DEFAULT_PASSWORD_LENGTH = 8;

    public PasswordGeneratorAndHashing() {
        this(UPPERCASE_CHARACTERS);
    }

    public PasswordGeneratorAndHashing(String characters) {
        this.charactersToBeUsed = characters;
    }

    public char[] generate() {
        return generate(DEFAULT_PASSWORD_LENGTH);
    }

    public char[] generate(int length) {
        char[] password = new char[length];

        char[] possibleCharacters = charactersToBeUsed.toCharArray();
        SecureRandom r = new SecureRandom();

        for (int i = 0; i < length; i++) {
            password[i] = possibleCharacters[r.nextInt(possibleCharacters.length)];
        }
        return password;
    }

}

