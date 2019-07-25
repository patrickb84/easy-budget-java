package easybudget;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Imports data from .csv file
 * @author Jeff Ostler and Patrick Bradshaw
 *
 */
public class Input {
	
	private static String fileIn = new File("src\\data.csv").getAbsolutePath();
	
	/**
	 * Reads file contents and parses data into designated columns
	 */
	public void readFile()
	{
		String line = null;
		String[] str = null;
		int n = 100;
		
		try (Scanner reader = new Scanner(new File(fileIn)))
		{
			reader.nextLine();
			while (reader.hasNextLine())
			{
				line = reader.nextLine();
				str = line.split(",");
				
				System.out.printf("1)%s 2)%s 3)%s 4)%s%n", str[0], str[1], str[2], str[3], str[4]);
				BudgetData bd = new BudgetData(n, str[0], str[1], str[2], str[3], str[4]);
				System.out.println();	
				
				n++;
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("Error 404: file not found.");
			e.printStackTrace();
		}
	}
}
