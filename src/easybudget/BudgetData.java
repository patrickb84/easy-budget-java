package easybudget;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

/**
 * Takes in data from Input class and loads its data into derby database
 * @author Jeff Ostler and Patrick Bradshaw
 *
 */
public class BudgetData {

	private static final String connectionString = 
			"jdbc:derby:budgetDatabase;create=true";
	
	private boolean isCredit;
	private boolean isDebit;
	private String transType;
	private Date date;
	
	/**
	 * A way for other classes to call this class without passing it all the params
	 */
	public BudgetData()
	{
//		createMasterTable();
//		dropTable();
	}

	/**
	 * Takes in data to load into derby database
	 * 
	 * @param transId - Transaction id - primary key
	 * @param strDate - Date of transaction
	 * @param desc - Description of transaction
	 * @param deb - Debit transaction
	 * @param cred - Credit transaction
	 * @param cat - Category of transaction
	 */
	public BudgetData(int transId, String strDate, String desc, String deb, String cred, String cat)
	{
		double debit = 0;
		double credit = 0;
		
		//if credit/debit is not null, then it is that type of transaction
		
		if (!(cred.equals("")))
		{
			credit = Double.parseDouble(cred);
			transType = "Credit";
			isCredit = true;
		}
		if (!(deb.equals("")))
		{
			debit = Double.parseDouble(deb);
			transType = "Debit";
			isDebit = true;
		}		
		
		date = convertStringToDate(strDate);
		initMasterTable(transId, transType, date, desc, credit, debit, cat);
	}

	/**
	 * Creates the Master table. For now, use this to query the Master table as well. Just comment out lines 78 - 85.
	 */
	private void createMasterTable() {
		try (Connection connection = DriverManager.getConnection(connectionString);
				Statement statement = connection.createStatement()){
			
//			statement.execute("CREATE TABLE Master ("
//					+"Tran_Id int Primary Key,"
//					+"Tran_Type varchar(10),"
//					+"Date date,"
//					+"Description varchar(100),"
//					+"Debit decimal(6,2),"
//					+"Credit decimal(6,2),"
//					+"Category varchar(30))");
			ResultSet results = statement.executeQuery("Select * from Master");
			displayResultSet(results);
			
		} catch (SQLException e) {
			System.out.println("Could not create master table.");
			e.printStackTrace();
		}
	}

	/**
	 * Initializes Master table
	 * 
	 * @param id - Transaction id
	 * @param type - Transaction type (credit or debit)
	 * @param date - Date of transaction
	 * @param desc - Description of Transaction
	 * @param cred - Credit transation
	 * @param deb - Debit transaction
	 * @param cat - Category of transaction
	 */
	private void initMasterTable(int id, String type, Date date, String desc, double cred, double deb, String cat)
	{
		try (Connection connection = DriverManager.getConnection(connectionString);
				Statement statement = connection.createStatement()){
			
			if (isCredit)
			{				
				statement.execute("INSERT INTO Master (TRAN_ID, TRAN_TYPE, DATE, DESCRIPTION, CREDIT, CATEGORY) VALUES " 
						+ "(" + id + ", '" + type + "', '" + date + "', '" + desc + "', " + cred + ", '" + cat + "')");						
			}
			
			if (isDebit)
			{				
				statement.execute("INSERT INTO Master (TRAN_ID, TRAN_TYPE, DATE, DESCRIPTION, DEBIT, CATEGORY) VALUES " 
						+ "(" + id + ", '" + type + "', '" + date + "', '" + desc + "', " + deb + ", '" + cat + "')");						
			}
			
		} catch (SQLException e) {
			System.out.println("Could not initialize Master database.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Not used yet. Taken from lab.
	 */
	private static void queryDatabase() {
		try(Connection connection = DriverManager.getConnection(connectionString);
				Statement statement = connection.createStatement()){
			List<String> queries = getQueries();
			
			for (String query : queries)
			{
				ResultSet results = statement.executeQuery(query);
				displayResultSet(results);
				System.out.println();
			}
			
		} catch (SQLException e) {
			System.out.println("Problem querying database");
			e.printStackTrace();
		}
	}
	
	//TODO
	private static List<String> getQueries() {
		List<String> list = new ArrayList<>();

		return list;
	}
	
	/**
	 * Displays results of a query. Called after query is executed. Take from lab.
	 * 
	 * @param results
	 * @throws SQLException
	 */
	private static void displayResultSet(ResultSet results) throws SQLException {
		ResultSetMetaData metaData = results.getMetaData();
		
		// in SQL we start counting at 1
		for(int i = 1; i <= metaData.getColumnCount(); i++)
		{
			System.out.printf("%-7s ", metaData.getColumnLabel(i));
		}
		System.out.println();
		
		while(results.next()) {	
			
			for (int i = 1; i <= metaData.getColumnCount(); i++)
			{
				System.out.printf("%-7s ", results.getString(i));
			}
			System.out.println();
		}
		System.out.println();
	}
	
	/**
	 * Selects a specified data range. 
	 * 
	 * @param start - StartDate
	 * @param end - EndDate
	 */
	public void selectDateRange(String start, String end) 
	{
		try(Connection connection = DriverManager.getConnection(connectionString);
				Statement statement = connection.createStatement()){
			
			Date startDate = convertStringToDate(start);
			Date endDate = convertStringToDate(end);
			
			ResultSet results = statement.executeQuery("SELECT * FROM Master WHERE DATE > '" + startDate + "' AND DATE < '" + endDate + "'");
			displayResultSet(results);
			
		} catch (SQLException e) {
			System.out.println("Could not extract date range.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Selects a specified transaction type (credit or debit).
	 * 
	 * @param tranType - Transaction type - specified by user.
	 */
	public void selectTranType(String tranType)
	{
		try(Connection connection = DriverManager.getConnection(connectionString);
				Statement statement = connection.createStatement()){
			
			statement.execute("SELECT * FROM Master WHERE TRAN_TYPE = " + tranType);
			
		} catch (SQLException e) {
			System.out.println("Could not search " + tranType + "in database");
			e.printStackTrace();
		}
	}
	
	/**
	 * Selects a specified category of transaction.
	 * 
	 * @param cat - Transaction category - specified by user.
	 */
	public void selectCategory(String cat)
	{
		try(Connection connection = DriverManager.getConnection(connectionString);
				Statement statement = connection.createStatement()){
			
			ResultSet results = statement.executeQuery("SELECT * FROM Master WHERE TRAN_TYPE = '" + cat + "'");
			displayResultSet(results);
			
		} catch (SQLException e) {
			System.out.println("Could not select " + cat + " from Master");
			e.printStackTrace();
		}
	}
	
	/**
	 * Updates category to the user's specifications.
	 * 
	 * @param id - Transaction id - Primary Key
	 * @param cat - Name of category to set the category field to.
	 */
	public void updateCategory(int id, String cat)
	{
		try(Connection connection = DriverManager.getConnection(connectionString);
				Statement statement = connection.createStatement()){
			
			statement.execute("UPDATE Master SET CATEGORY = '" + cat + "' WHERE TRAN_ID = " + id);
			
		} catch (SQLException e) {
			System.out.println("Could not set Category to " + cat);
			e.printStackTrace();
		}
	}
	
	/**
	 * Drops table Master.
	 */
	private void dropTable() {
		try (Connection connection = DriverManager.getConnection(connectionString);
				Statement statement = connection.createStatement()){
			
				statement.execute("DROP Table Master");
			
		} catch (SQLException e) {
			System.out.println("Could not drop table.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Converts Date string into a date type SQL can understand.
	 * 
	 * @param d - Date string passed by user.
	 * @return SQL date.
	 */
	private Date convertStringToDate(String d)
	{
		String strArr[] = null;
		strArr = d.split("/");
		String sqlMo = strArr[0] + "-";
		String sqlDa = strArr[1];
		String sqlYr = strArr[2] + "-";
		String sqlDate = sqlYr + sqlMo + sqlDa;

		Date date = Date.valueOf(sqlDate);
		return date;
	}
}
