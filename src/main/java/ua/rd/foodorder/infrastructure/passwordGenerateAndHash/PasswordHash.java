package ua.rd.foodorder.infrastructure.passwordGenerateAndHash;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.regex.Pattern;

/**
 * Created by Artem_Lomako on 7/11/2016.
 */
public class PasswordHash {
    /**
     * Each token produced by this class uses this identifier as a prefix.
     */
    public static final String ID = "$31$";

    /**
     * The minimum recommended cost, used by default
     */
    public static final int DEFAULT_COST = 16;

    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";

    private static final int SIZE = 128;

    private static final Pattern layout = Pattern.compile("\\$31\\$(\\d\\d?)\\$(.{43})");

    private final SecureRandom random;

    private final int cost;

    public PasswordHash() {
        this(DEFAULT_COST);
    }
    public PasswordHash(int cost)
    {
        iterations(cost); /* Validate cost */
        this.cost = cost;
        this.random = new SecureRandom();
    }

    private static int iterations(int cost)
    {
        if ((cost & ~0x1F) != 0)
            throw new IllegalArgumentException("cost: " + cost);
        return 1 << cost;
    }
    public String hash(char[] password)
    {
        byte[] salt = new byte[SIZE / 8];
        random.nextBytes(salt);
        byte[] dk = pbkdf2(password, salt, 1 << cost);
        byte[] hash = new byte[salt.length + dk.length];
        System.arraycopy(salt, 0, hash, 0, salt.length);
        System.arraycopy(dk, 0, hash, salt.length, dk.length);
        Base64.Encoder enc = Base64.getUrlEncoder().withoutPadding();
        return ID + cost + '$' + enc.encodeToString(hash);
    }
    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations)
    {
        KeySpec spec = new PBEKeySpec(password, salt, iterations, SIZE);
        try {
            SecretKeyFactory f = SecretKeyFactory.getInstance(ALGORITHM);
            return f.generateSecret(spec).getEncoded();
        }
        catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("Missing algorithm: " + ALGORITHM, ex);
        }
        catch (InvalidKeySpecException ex) {
            throw new IllegalStateException("Invalid SecretKeyFactory", ex);
        }
    }
}
