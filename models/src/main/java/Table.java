/**
**Author: James Paolo W. Menguito
**Description: Table class that holds a list of keyvals that are essentially an object
**				containing a String key and String value field. Contains various getters and setters
**/
package models;
import java.util.List;
import java.util.ArrayList;

public class Table {

	private List<Keyval> keyvalTable = new ArrayList<Keyval>();
	private int row;
	private int col;
	
	//constructor of Table object
	public Table (int row, int col, List<Keyval> keyvalTable) {
		this.keyvalTable.addAll(keyvalTable);
		this.row = row;
		this.col = col;
	 }	     
    public void exit() {
    	System.exit(0);
     }

    public int getRow() {
    	return this.row;
     }

    public int getCol() {
    	return this.col;
     }
    
    public void setRow(int row) {
    	this.row = row;	
   	 }
   	public void setCol(int col) {
   		this.col = col;
   	 } 
    //returns the list
	public List<Keyval> getTable() {
		return this.keyvalTable;
	 }
	// returns list size
	public int getTableSize() {
		return this.keyvalTable.size();
	 }
	public void setTable(List<Keyval> keyvalTable) {
		this.keyvalTable = keyvalTable;
	 }
	public void setFields(int row, int col, List<Keyval> keyvalTable) {
		setRow(row);
		setCol(col);
		setTable(keyvalTable);
	 }

}
