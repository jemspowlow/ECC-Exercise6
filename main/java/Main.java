/**
**Author: James Paolo W. Menguito
**Description: ArrayList of randomly generated key-value pairs
**/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
public class Main{
 
    public static void main (String[] args) {
       
       //declare and initialize a bufferedReader to read a file
       String filename = "Table.txt";
	 
       int choice = 0;
       
       try {
       		
       		Table table = createTable("Table.txt");	
		   
		   do {	
		   		//Take an input
		   		Scanner reader = new Scanner(System.in);
		   		printMenu();
		   		System.out.print("Enter choice: ");	
		   		//switch statement 		
		   		try {
		   			choice = reader.nextInt();	
		   			menu(choice,table,filename);
		   						
		   		 } catch(InputMismatchException e) {
		   			
		   			//Upon entering an invalid input (non-integer), print error
		   			System.out.println("Error: Invalid input.");
		   			
		   		 }
		   		
		   } while(choice != 7);       
       
       } catch(IOException e) {
       		System.out.println("Error has occurred.");
       		return;
       }
    }
    
    public static Table createTable(String filename) throws IOException{
    	BufferedReader br = new BufferedReader(new FileReader(filename));
		List <Keyval> keyvalTable = new ArrayList<Keyval>();
		String newLine;
		int num_row = 0;
		int num_col = 0;
		String[] row;       		
       		
       		//reads the file per line, it will stop when it reaches end of file or when the line is null
			while((newLine = (br.readLine())) != null) {
			
			    //for each readline, we count one row
				num_row++;
			
				//with a line, split it by spaces.
				row = newLine.split("\\s");
				num_col = row.length;				//num_col counts the columns
			   				
				//add each 'word' to the arraylist
				for(String word : row) {			   				
				//each word will be made into a Keyval object
				//key = pair[0] && value = pair[1]
					String[] pair = word.split(":");
					keyvalTable.add(new Keyval(pair[0], pair[1]));
			   	
			     }
			   		
			}
		   //close the BufferedReader
		   br.close();

		   //create a table object
		   return new Table(num_row, num_col, keyvalTable);    
     }
    public static void menu(int choice, Table table, String filename) {
    	
    		switch(choice) {
		   				case 0: 
		   					break;
		   				case 1: 
		   						table.print();
		   					break;
		   				case 2: 
		   						table.search();
		   					break;
		   				case 3: 
		   						table.edit();
		   						table.save(filename);
		   					break;
		   				case 4: 
		   						table.addRow();
		   						table.save(filename);
		   					break;
		   				case 5: 
		   						Collections.sort(table.getTable(),Keyval.WordComparator); //[1]; sort using a custom comparator
		   						table.print();
		   						table.save(filename);
		   					break;
		   				case 6: 
		   						table = table.reset();
		   						table.print();
		   						table.save(filename);
		   					break;
		   				case 7: 
		   						System.exit(0);
		   				default: 
		   					break;
		   			}
    
    } 
    //method to print menu
    public static void printMenu() {
    	System.out.println("");
    	System.out.println("===Menu==");
    	System.out.println("[1]Print");	//done
		System.out.println("[2]Search");//done
		System.out.println("[3]Edit");	//done
		System.out.println("[4]Add");	//done
		System.out.println("[5]Sort");	//done
		System.out.println("[6]Reset");	//done
		System.out.println("[7]Exit");  //done
    }
    
  }
