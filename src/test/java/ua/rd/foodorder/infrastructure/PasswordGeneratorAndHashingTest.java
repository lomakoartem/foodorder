package ua.rd.foodorder.infrastructure;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import ua.rd.foodorder.infrastructure.passwordGenerateAndHash.PasswordGeneratorAndHashing;

public class PasswordGeneratorAndHashingTest {
    
    @Test
    public void testPasswordAlwaysContainsAtLeastOneSpecialCharacter() {
    	String specialChars = "!@#$%^&*_=+-/";
    	int testTimes = 100000;
    	int insuccessfullTimes = 0;
    	for(int i = 0; i < testTimes; ++i) {
    		char[] pswd = PasswordGeneratorAndHashing.generatePswd();
    		boolean found = false;
    		for (char pswdChar : pswd) {
				if (specialChars.indexOf(pswdChar) != -1) {
					found = true;
					break;
				}
			}
    		if (found) {
    			continue;
    		}
//    		System.out.println("Not containes at least 1 spec char: " + Arrays.toString(pswd));
    		++insuccessfullTimes;
    	}
    	assertEquals(0, insuccessfullTimes);
//    	System.out.println("Insuccessfull times (spec chars): " + insuccessfullTimes);
//    	System.out.println("Percentage (spec chars): " + (((double) insuccessfullTimes)/(testTimes)) + "%");
    }
    
    @Test
    public void testPasswordAlwaysContainsAtLeastOneNumber() {
    	String numbers = "0123456789";
    	int testTimes = 100000;
    	int insuccessfullTimes = 0;
    	for(int i = 0; i < testTimes; ++i) {
    		char[] pswd = PasswordGeneratorAndHashing.generatePswd();
    		boolean found = false;
    		for (char pswdChar : pswd) {
				if (numbers.indexOf(pswdChar) != -1) {
					found = true;
					break;
				}
			}
    		if (found) {
    			continue;
    		}
//    		System.out.println("Not containes at least 1 number: " + Arrays.toString(pswd));
    		++insuccessfullTimes;
    	}
    	assertEquals(0, insuccessfullTimes);
//    	System.out.println("Insuccessfull times (number): " + insuccessfullTimes);
//    	System.out.println("Percentage (number): " + (((double) insuccessfullTimes)/(testTimes)) + "%");
    }
    
    @Test
    public void testPasswordAlwaysContainsAtLeastLowercaseLetter() {
    	String lowercaseLetters = "abcdefghijklmnopqrstuvwxyz";
    	int testTimes = 100000;
    	int insuccessfullTimes = 0;
    	for(int i = 0; i < testTimes; ++i) {
    		char[] pswd = PasswordGeneratorAndHashing.generatePswd();
    		boolean found = false;
    		for (char pswdChar : pswd) {
				if (lowercaseLetters.indexOf(pswdChar) != -1) {
					found = true;
					break;
				}
			}
    		if (found) {
    			continue;
    		}
//    		System.out.println("Not containes at least 1 lowercase: " + Arrays.toString(pswd));
    		++insuccessfullTimes;
    	}
    	assertEquals(0, insuccessfullTimes);
//    	System.out.println("Insuccessfull times (lowercase): " + insuccessfullTimes);
//    	System.out.println("Percentage (lowercase): " + (((double) insuccessfullTimes)/(testTimes)) + "%");
    }
    
    @Test
    public void testPasswordAlwaysContainsAtLeastUppercaseLetter() {
    	String lowercaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    	int testTimes = 100000;
    	int insuccessfullTimes = 0;
    	for(int i = 0; i < testTimes; ++i) {
    		char[] pswd = PasswordGeneratorAndHashing.generatePswd();
    		boolean found = false;
    		for (char pswdChar : pswd) {
				if (lowercaseLetters.indexOf(pswdChar) != -1) {
					found = true;
					break;
				}
			}
    		if (found) {
    			continue;
    		}
//    		System.out.println("Not containes at least 1 uppercase: " + Arrays.toString(pswd));
    		++insuccessfullTimes;
    	}
    	assertEquals(0, insuccessfullTimes);
//    	System.out.println("Insuccessfull times (uppercase): " + insuccessfullTimes);
//    	System.out.println("Percentage (uppercase): " + (((double) insuccessfullTimes)/(testTimes)) + "%");
    }
    
    
    
    @Test
    public void testPasswordLengthIsNotGreaterThanMaxLength() {
    	int MAX_LENGTH_OF_PASSWORD = 21;
    	int testTimes = 100000;
    	int insuccessfullTimes = 0;
    	for(int i = 0; i < testTimes; i++) {
    		char[] pswd = PasswordGeneratorAndHashing.generatePswd();
    		if (pswd.length > MAX_LENGTH_OF_PASSWORD) {
//    			System.out.println("Length: " + pswd.length + "; pswd: " + Arrays.toString(pswd));
    			++insuccessfullTimes;
    		}
    	}
    	assertEquals(0, insuccessfullTimes);
//    	System.out.println("Insuccessfull times (>maxlength): " + insuccessfullTimes);
//    	System.out.println("Percentage (>maxlength): " + (((double) insuccessfullTimes)/(testTimes)) + "%");
    }
}
