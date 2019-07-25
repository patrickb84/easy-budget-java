package easybudget;

import java.util.Scanner;
import java.sql.Date;

public class BudgetApp {

	/**
	 * Test client for BudgetData
	 * @param args
	 */
	public static void main(String[] args)
	{
		BudgetData bd = new BudgetData();
//		Input input = new Input();
//		input.readFile();
		
//		bd.selectCategory("Entertainment");
//		bd.updateCategory(101, "Entertainment");
//		bd.selectCategory("Credit");
		bd.selectDateRange("01/02/2018", "01/10/2018");
		
//		TODO: 1) Convert string date into date 2) Add tran_type column
		
		
		
		
// ========================================================================================================//
		
//		Scanner input = new Scanner(System.in);
//		
//		System.out.println("Select from the following options:");
//		System.out.println("[1] DateRange\n[2] Credit/Debit\n[3] View Category\n[4] Set Category");
//		
//		int n = input.nextInt();
//		
//		
//		switch(n)
//		{
//			case 1:
//				System.out.println("Figure this out first.");
//				break;
//			case 2:
//				System.out.println(">Credit\n>Debit");
//				String t = input.nextLine();
//				bd.selectTranType(t);
//				break;
//			case 3:
//				System.out.println(">Food/Groceries\n>Gas\n>Entertainment\n>Bills");
//				String c = input.nextLine();
//				bd.selectCategory(c);
//				break;
//			case 4:
//				System.out.println("\">Food/Groceries\\n>Gas\\n>Entertainment\\n>Bills");
//				String d = input.nextLine();
//				input.next();
//				System.out.println("Tran_id: ");
//				int i = input.nextInt();
//				bd.updateCategory(i, d);
//		}
	}
}
