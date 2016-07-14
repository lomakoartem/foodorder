package ua.rd.foodorder.infrastructure;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.rd.foodorder.infrastructure.parsers.EmployeeExcelFileParser;
import ua.rd.foodorder.infrastructure.passwordGenerateAndHash.PasswordGeneratorAndHashing;
import ua.rd.foodorder.infrastructure.passwordGenerateAndHash.PasswordHash;

/**
 * Created by Artem_Lomako on 7/12/2016.
 */
public class PasswordGeneratorAndHashingTest {

    private static PasswordGeneratorAndHashing generator;

    private static PasswordHash hash;


    @BeforeClass
    public static void setUp() {
        generator = new PasswordGeneratorAndHashing();
        hash = new PasswordHash();
    }

    /*@Test
    public void testPasswordIsGenerated() {
        char[] password = generator.generatePswd();
    //    System.out.println(password);
    }*/

    @Test
    public void testPasswordIsHashed() {
       char[]  password = generator.generatePswd();
     /*   String hashedPasswored = hash.hash(password);
        System.out.println(password);
        System.out.println(hashedPasswored);*/
        char [] passwordTest = {'f','&','7','#','*','/','w','R','v','$','V','u'};
        System.out.println(hash.hash(passwordTest));
        System.out.println(hash.hash(passwordTest));

   //     System.out.println(hash.hash(password));
        //System.out.println(hash.hash(hashedPasswored.toCharArray()));
    }
}
