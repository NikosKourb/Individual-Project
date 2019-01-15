package dontshootthemessenger;

import java.io.BufferedWriter;
import java.io.FileWriter;


public class Log {

	//static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss");
	
	public Log() {}
	
	public static void appendStrToFile(String fileName,String str) { 
		try { 
			// Open given file in append mode. 
			
			BufferedWriter out = new BufferedWriter(new FileWriter(fileName, true)); 
			
			out.write(str); 
			out.newLine();
			out.newLine();
			out.close(); 
			} 
		
		catch (Exception e) {
			e.printStackTrace(); 
		}
		
	} 
	

}