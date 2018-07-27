package app;
import services.TableService;
public class App{
	
	public static void main(String[] args) {
		TableService tableService = new TableService();
		
		tableService.start();
	
	 }

 } 
