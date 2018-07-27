/***
***Author: James Paolo W. Menguito
***Description: Util Class for TableService. Provides reusable methods such as printMenu and checkInput
***				Also provides a static final Scanner for input, Randomizer from apache commons
***				and the FILE_NAME constant.
***/
package utils;
import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.simple.RandomSource;
import java.util.Random;
import java.util.Scanner;
 
public class Util {
	public static final Scanner INPUT = (new Scanner(System.in)).useDelimiter("\\n");
	public static final UniformRandomProvider RANDOM = RandomSource.create(RandomSource.MT);
	public static final String FILE_NAME = "Table.txt"; 	
    		
	public static void printMenu() {
		System.out.println("");
    	System.out.println("===Menu==");
    	System.out.println("[1]Print");	
		System.out.println("[2]Search");
		System.out.println("[3]Edit");	
		System.out.println("[4]Add");	
		System.out.println("[5]Sort");	
		System.out.println("[6]Reset");	
		System.out.println("[7]Exit");
	 }
	public static int randomInt(int min, int max) {
		return (RANDOM.nextInt(max) + min);
		
	 }
	
	public static char asciiToChar(int num) {
		return (char) num;
	
	 }
	
	//Editting or adding a row needs correct input
    public static boolean checkInput(String input) {
    	
    	if(input.length() != 3) {
    		System.out.println("New string must be 3 characters-long. Changes unsaved.");
    		return false;
    	}else if((input.trim()).isEmpty()) {
    		System.out.println("New string is empty. Changes unsaved.");
    		return false;
    	}else if(input.contains(" ") || input.contains(":")) {
    		System.out.println("New string contains illegal characters (spaces or \":\"). Changes unsaved.");
    		return false;
    	}
    	
    	return true;
    }
    
   

 }
