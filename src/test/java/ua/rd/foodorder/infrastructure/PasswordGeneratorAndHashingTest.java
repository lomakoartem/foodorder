package ua.rd.foodorder.infrastructure;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.rd.foodorder.infrastructure.parsers.EmployeeExcelFileParser;
import ua.rd.foodorder.infrastructure.passwordGenerateAndHash.PasswordGeneratorAndHashing;

/**
 * Created by Artem_Lomako on 7/12/2016.
 */
public class PasswordGeneratorAndHashingTest {

    private PasswordGeneratorAndHashing generator;

    @BeforeClass
    public void setUp()
    {
     generator = new PasswordGeneratorAndHashing();
    }
    @Test
    public void testPasswordIsGenerated()
    {
        char [] password = generator.generate();
        System.out.println(password);
    }
}
