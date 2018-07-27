/***
***Author: James Paolo W. Menguito
***Description: A Service class that interacts with the Table and Keyval model.
***				It generates a table of key:value objects read and written from a file
**				and allows operations such as search, add row, edit, reset, save
***Data: July 10, 2018
***/
package services;
import models.*;
import utils.Util;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import org.apache.commons.text.TextStringBuilder;
public class TableService {
	/**Imported constants and methods from Util
	**Util.INPUT = Scanner
	**Util.RANDOM = Random
	**READER = BufferedReader
	**WRITER = BufferedWriter
	**Util.FILE_NAME = String
	**/
	public TableService(){ 
		
	}
	
	public void start() { 	 
       int choice = 0;
       
       try {
       		
       		Table table = createTable();	
		   	
		   do {	
		   		//Take an input
		   		Util.printMenu();
		   		System.out.print("Enter choice: ");	
		   		//switch statement 		
		   		try {
		   			choice = Util.INPUT.nextInt();	
		   			menu(choice,table);
		   						
		   		 } catch(InputMismatchException e) {
		   			
		   			//Upon entering an invalid input (non-integer), print error
		   			System.out.println("Error: Invalid input.");
		   		 }
		   		
		   } while(choice != 7);       
       
       } catch(IOException e) {
       		System.out.println("Error has occurred. No File Found.");
       		return;
       }
	
	}
	public void menu(int choice, Table table) {
				switch(choice) {
			   				case 0: 
			   					break;
			   				case 1: 
			   						print(table);	//PRINT
			   					break;
			   				case 2: 
			   						search(table);	//SEARCH
			   					break;
			   				case 3: 
			   						edit(table);	//EDIT
			   						save(table);
			   					break;
			   				case 4: 
			   						addRow(table);	//ADD ROW
			   						save(table);
			   					break;
			   				case 5: 				//SORT
			   						Collections.sort(table.getTable(),Keyval.WordComparator); //[1]; sort using a custom comparator
			   						print(table);
			   						save(table);
			   					break;
			   				case 6: 				//RESET
			   						reset(table);
			   						print(table);
			   						save(table);
			   					break;
			   				case 7: 
			   						System.exit(0);
			   				default: 
			   					break;
			   			}
		
	}
	
	
 	 //RESET - lets the user reset the table, requests a new dimension and generates new strings
    public void reset(Table table) {
 		ArrayList<Keyval> keyvalTable = new ArrayList<Keyval>();

 		System.out.print("Enter desired number of rows: ");
 		int row = Util.INPUT.nextInt();
 		System.out.print("Enter desired number of cols: ");
 		int col = Util.INPUT.nextInt();
 		buildTable(table,row,col);
     }
 	
 	//SEARCH - method that searches the table for a provided term   
    public void search(Table table) {
    	
    	List<Keyval> keyvalTable = table.getTable();
    	List<String> indices = new ArrayList<String>();
		int row = table.getRow();
		int col = table.getCol();
		int currCol = 0;
		int currRow = 0;
		int count=0;
		
		System.out.print("Search for: ");
		String term = Util.INPUT.next();		//term to search for
    	
    	//catch incorrect input
    	if(term.length() > 6) {
    		System.out.println("Search term exceeds character limit.");
    		return;
    	}else if((term.trim()).isEmpty()) {
    		System.out.println("Search term is empty");
    		return;
    	}else if((term.contains(" "))) {
    		System.out.println("Search term cannot contain spaces");
    		return;
    	}
    	for(int i=0;i<table.getTableSize();i++) {
			
			//get the keyval object in the arraylist    		
    		Keyval keyval = keyvalTable.get(i);
    		
    		//since this is a one dimensional arraylist, increment currRow each time it reaches the col^th element in a row.
    		if(i!=0 && i%col==0) {
    			currRow++;	//increase row
    			currCol = 0;//reset the columns upon entering a new row
    		 }
    		
    		if((keyval.getWord()).contains(term.trim())) {
    			count++;
    			indices.add("["+currRow+","+currCol+"]");	
    		 }
    		
    		//each object read, move to the next column
    		currCol++;
    	}
    	
    	//after counting, print count and the indices list
    	System.out.println("Matches: "+count);
    	System.out.println("At indices: " + indices.toString());
    
    }
    
    //EDIT - method that lets the user edit a designated cell in the table by providing the row and column
    public void edit(Table table) {
    	List <Keyval> keyvalTable = table.getTable();

    	System.out.print("Edit row: ");
    	int row = Util.INPUT.nextInt();
    	
    	System.out.print("Edit col: ");
    	int col = Util.INPUT.nextInt();
    	
    	//catch negative values
    	if(col<0 || row<0) {
    		System.out.print("Negative values aren't allowed.");
    		return;
    	}
    	
    	//(given_row * total_col) + given_col = position of object in 1D array list.
    	int index = (row * table.getCol()) + col;
    	
    	//check the provided row and column if it exceeds the table's dimension
    	if(row > table.getRow() || col > table.getCol()) {
    		System.out.println("Array out of bounds");
    		return;
    	}
    	
    	Keyval keyval = keyvalTable.get(index);
    	    	 
		//check if the user wants to edit the key or the value or both
		System.out.println("Editing ["+row+","+col+"]: "+keyval.printMapping());
		System.out.println("[1] Edit Key");
		System.out.println("[2] Edit Value");
		System.out.println("[3] Edit Both");
		System.out.println("[4] Abort");
		System.out.print("Choice: ");
		
		int choice = Util.INPUT.nextInt();
		String word;
		switch(choice) {
			
			case 1: 
					System.out.print("New key: ");
					word = Util.INPUT.next();
					if(Util.checkInput(word)) keyval.setKey(word);
				break;
			case 2: 
					System.out.print("New value: ");
					word = Util.INPUT.next();
					if(Util.checkInput(word)) keyval.setValue(word);
				break;
			case 3: 
					System.out.print("New key: ");
				    word = Util.INPUT.next();
					if(Util.checkInput(word)) keyval.setKey(word);
					System.out.print("New value: ");
					word = Util.INPUT.next();
					if(Util.checkInput(word)) keyval.setValue(word);
				break;
			case 4: 
				break;
			default:
				break;
		}
		
		keyvalTable.set(index,keyval);
    	
    }
 	
 	//PRINT - prints the whole table   
    public void print(Table table) {
    	int count = 0;
    	System.out.println("");
    	System.out.println("");
    	
    	for(Keyval keyval : table.getTable()) {
    		System.out.print(keyval.printMapping());
    		count++;
    		if(count == table.getCol()) {
    			System.out.println("");
    			count = 0;
    		}else{
    			System.out.print(" ");
    		}
    	}
    	System.out.println("");
    	System.out.println("");
    }
    
    //ADD - adds 1 row to the table
 	public void addRow(Table table) {
 		List <Keyval> keyvalTable = table.getTable(); 
 		//ask for a key and then a value * col
 		System.out.println("Adding a new row...");
 		
 		for(int i=0;i<table.getCol();i++) {
 			System.out.print("Enter Key ["+(i+1)+"/"+(table.getCol())+"]: ");
 			String tempKey = Util.INPUT.next();
 			System.out.print("Enter Value ["+(i+1)+"/"+(table.getCol())+"]: ");
 			String tempVal = Util.INPUT.next();
 			
 			//if the inputs are correct, add it to the table
 			if(Util.checkInput(tempKey) && Util.checkInput(tempVal)) {
 				keyvalTable.add(new Keyval(tempKey,tempVal));
 			} else {
 			//if the inputs are incorrect, backtrack loop
 				i--;
 			}
 		}
 		
 		table.setRow(table.getRow()+1);
 	}    
    //SAVE - saves the current keyvalTable to a text file
	public void save(Table table) {
    	
	
    	
    	try {
 	 		BufferedWriter writer = new BufferedWriter(new FileWriter(Util.FILE_NAME));  		
    		for(int i=0;i<table.getTableSize();i++) {
    			writer.write(table.getTable().get(i).printMapping());
    			if((i+1) % table.getCol()==0) {
    				writer.write("\n");
    			}else{
    				writer.write(" ");
    			}
    		}
    		writer.close();
    		
    	} catch(IOException e) {
    		System.out.println("Error");
    		
    	}
    }
    
    //Generate a random table with the given dimensions  
 	public void buildTable(Table table,int row, int col) {
 		
 		List<Keyval> newKeyvalTable = new ArrayList<Keyval>();
 		TextStringBuilder sb2 = new TextStringBuilder();
 		String word;
 		String tempKey="";
 		String tempVal="";
 		int ascii; //ascii holder
 		
 		//randomizer

 		for(int j=0;j<row*col;j++) {
 		 				
 			for(int k=0;k<6;k++) {
 			
 				//generate a random ascii value from 33 to 126;
 				int number = Util.randomInt(93,33);
 				if(number != 58) {
 					char randomChar = Util.asciiToChar(number) ;
 					sb2.append(randomChar);
 					
 		   		 } else {
 					k--;
 				 }
 					
 			 } 
 			System.out.println("Key: "+sb2.leftString(3)+" Value: "+sb2.rightString(3));
 		
 			//make a new Keyval and store it in the table
 			newKeyvalTable.add(new Keyval(sb2.leftString(3),sb2.rightString(3)));
 				
 			//Clear the TextStringBuilder
 			sb2.clear();
 		 }
 		table.setFields(row,col,newKeyvalTable);
 		
 	 }
 	 
 	 public Table createTable() throws IOException {
    
		List <Keyval> keyvalTable = new ArrayList<Keyval>();
		String newLine;
		int num_row = 0;
		int num_col = 0;
		String[] row;       		
        BufferedReader reader = new BufferedReader(new FileReader(Util.FILE_NAME));	
       		//reads the file per line, it will stop when it reaches end of file or when the line is null
			while((newLine = (reader.readLine())) != null) {
			
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
		   reader.close();

		   //create a table object
		   return new Table(num_row, num_col, keyvalTable);    
     }  
		
}
