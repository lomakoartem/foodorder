package ua.rd.foodorder.infrastructure.passwordGenerateAndHash;

/**
 * Created by Artem_Lomako on 7/11/2016.
 */
public interface PasswordGenerator {

    char[] generate();

    char[] generate(int length);

}
