package clueGame;


import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class BadConfigFormatException extends RuntimeException {
	
	public BadConfigFormatException() throws FileNotFoundException{
		super ("Configuration Failed");
	
	}
	public BadConfigFormatException(String format) throws FileNotFoundException {
		super ("Configuration of " + format + "failed");
		System.out.println("BadConfigFormatException was thrown, check logfile.txt for details.");
		PrintWriter out = new PrintWriter("logfile.txt");
		out.println("Configuration of '" + format + "' failed");
		out.close();
	}


}
