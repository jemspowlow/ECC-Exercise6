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
import org.apache.commons.text.TextStringBuilder;
import org.apache.commons.text.Builder;
public class Table{

	private List<Keyval> keyvalTable = new ArrayList<Keyval>();
	private int row;
	private int col;
	
	//constructor of Table object
	public Table (int row, int col, List<Keyval> keyvalTable) {
		this.keyvalTable.addAll(keyvalTable);
		this.row = row;
		this.col = col;
	}	     

    //Generate a random table with the given dimensions  
 	public List<Keyval> buildTable(int row, int col) {
 		
 		List<Keyval> newKeyvalTable = new ArrayList<Keyval>();
 		
 		TextStringBuilder sb2 = new TextStringBuilder();
 		String word;
 		String tempKey="";
 		String tempVal="";
 		int ascii; //ascii holder
 		
 		//randomizer
 		Random random = new Random();
 			
 			for(int j=0;j<row*col;j++) {
 				 				
 				for(int k=0;k<6;k++) {
 				
 					//generate a random ascii value from 33 to 126;
 					ascii = random.nextInt(93) + 33;
 					if(ascii != 58) {
 						char randomChar = (char) (random.nextInt(93) + 33);
 						sb2.append(randomChar);
 						
 					} else {
 						k--;
 					}
 					
 				}
 				System.out.println("Left String: "+sb2.leftString(3)+" Right String: "+sb2.rightString(3));
 				
 				//make a new Keyval and store it in the table
 				newKeyvalTable.add(new Keyval(sb2.leftString(3),sb2.rightString(3)));
 				
 				//Clear the TextStringBuilder
 				sb2.clear();
 			}
 		
 		return newKeyvalTable;
 	}
 	
 	//SEARCH - method that searches the table for a provided term   
    public void search() {
    	Scanner reader = new Scanner(System.in);
    	List<Keyval> keyvalTable = getTable();
    	List<String> indices = new ArrayList<String>();
		int row = getRow();
		int col = getCol();
		int currCol = 0;
		int currRow = 0;
		int count=0;
		
		System.out.print("Search for: ");
		String term = reader.nextLine();		//term to search for
    	
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

    	for(int i=0;i<keyvalTable.size();i++) {
			
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
    public void edit() {
    	Scanner read = new Scanner(System.in);
    	System.out.print("Edit row: ");
    	int row = read.nextInt();
    	
    	System.out.print("Edit col: ");
    	int col = read.nextInt();
    	
    	//catch negative values
    	if(col<0 || row<0) {
    		System.out.print("Negative values aren't allowed.");
    		return;
    	}
    	
    	//(given_row * total_col) + given_col = position of object in 1D array list.
    	int index = (row * getCol()) + col;
    	
    	//check the provided row and column if it exceeds the table's dimension
    	if(row > getRow() || col > getCol()) {
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
		
		int choice = read.nextInt();
		String word;
		switch(choice) {
			
			case 1: 
					System.out.print("New key: ");
					word = read.next();
					if(checkInput(word)) keyval.setKey(word);
				break;
			case 2: 
					System.out.print("New value: ");
					word = read.next();
					if(checkInput(word)) keyval.setValue(word);
				break;
			case 3: 
					System.out.print("New key: ");
				    word = read.next();
					if(checkInput(word)) keyval.setKey(word);
					System.out.print("New value: ");
					word = read.next();
					if(checkInput(word)) keyval.setValue(word);
				break;
			case 4: 
				break;
			default:
				break;
		}
		
		keyvalTable.set(index,keyval);
    	
    }
 	
 	//PRINT - prints the whole table   
    public void print() {
    	int count = 0;
    	System.out.println("");
    	System.out.println("");
    	
    	for(Keyval keyval : getTable()) {
    		System.out.print(keyval.printMapping());
    		count++;
    		if(count == getCol()) {
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
 	public void addRow() {
 		Scanner reader = new Scanner(System.in);
 		
 		//ask for a key and then a value * col
 		System.out.println("Adding a new row...");
 		for(int i=0;i<col;i++) {
 			System.out.print("Enter Key ["+(i+1)+"/"+(col)+"]: ");
 			String tempKey = reader.nextLine();
 			System.out.print("Enter Value ["+(i+1)+"/"+(col)+"]: ");
 			String tempVal = reader.nextLine();
 			
 			//if the inputs are correct, add it to the table
 			if(checkInput(tempKey) && checkInput(tempVal)) {
 				keyvalTable.add(new Keyval(tempKey,tempVal));
 			} else {
 			//if the inputs are incorrect, backtrack loop
 				i--;
 			}
 		}
 		
 		this.row +=1;
 	}
 	
 	//RESET - lets the user reset the table, requests a new dimension and generates new strings
    public Table reset() {
 		ArrayList<Keyval> keyvalTable = new ArrayList<Keyval>();
 		Scanner reader = new Scanner(System.in);
 		System.out.print("Enter desired number of rows: ");
 		int row = reader.nextInt();
 		System.out.print("Enter desired number of cols: ");
 		int col = reader.nextInt();
 		return new Table(row,col,buildTable(row,col));
    }
    
    //SAVE - saves the current keyvalTable to a text file
    public void save(String filename) {
    	try {
 			BufferedWriter bw = new BufferedWriter(new FileWriter(filename));   	
    		
    		for(int i=0;i<getTable().size();i++) {
    			bw.write(keyvalTable.get(i).printMapping());
    			if((i+1)%getCol()==0) {
    				bw.write("\n");
    			}else{
    				bw.write(" ");
    			}
    		}
    		bw.close();
    		
    	} catch(IOException e) {
    		System.out.println("Error");
    		
    	}
    }
    
    //Editting or adding a row needs correct input
    public boolean checkInput(String input) {
    	
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
    public void exit() {
    	System.exit(0);
    }
    
    //returns the row 
    public int getRow() {
    	return this.row;
    }
    //returns the column
    public int getCol() {
    	return this.col;
    }
    //returns the 2d String array
	public List<Keyval> getTable() {
		return this.keyvalTable;
	}
	
	//References: [1] https://www.mkyong.com/java/how-to-sort-an-arraylist-in-java/
}
