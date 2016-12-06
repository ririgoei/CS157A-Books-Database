import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.util.Scanner;


public class BookDatabase {

	public static Connection conn = null;
	public static  Statement stmt = null;
	
	 // JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://127.0.0.1";

	   //  Database credentials
	   static final String USER = "root";
	   static final String PASS = "123456";
	
	
	/**
	 * Creates a connection instance for connecting to DB   
	 */
    public BookDatabase()
    {
    	try {
            Class.forName(JDBC_DRIVER).newInstance();
        } catch (Exception ex) {
        	System.out.println("JDBC driver not found");
            }
		try {
            conn =
               DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", USER, PASS);
    	    stmt = conn.createStatement();
    	    
	     } catch (SQLException ex) {
	           System.out.println("SQL exeption thrown");
	            }		
	    System.out.println("Connected to database!");  
	}
	
    
    /**
     * Initializes books schema
     */
    public void createSchema()
    {
    	String dropSchema = "DROP SCHEMA IF EXISTS books";
    	String schema = "CREATE SCHEMA books";
    	String select = "USE books";
    	try {
    		stmt.executeUpdate(dropSchema);

			int result = stmt.executeUpdate(schema);
    		stmt.executeUpdate(select);

			if(result == 1)
			{
				System.out.println("books Schema created successfully");
			}
			else{
				System.out.println("Failed to initilize schema!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    /**
     * Initialize all the tables in books database
     */
    public void createTables()
    {
    	 String authors = "CREATE TABLE IF NOT EXISTS authors(" + 
					"authorID INTEGER NOT NULL AUTO_INCREMENT," +
					"firstName CHAR(20) NOT NULL," +
					"lastName CHAR(20) NOT NULL," +
					"PRIMARY KEY(authorID)" +
					")";
    	
    	 String publishers = "CREATE TABLE publishers " +
    	 			" (publisherID INTEGER NOT NULL auto_increment, " +
    	 			" publisherName CHAR(100) NOT NULL, " +
    	 			" PRIMARY KEY ( publisherID ))";
    	 		
    	String titles = "CREATE TABLE titles " +
    	 			" (isbn CHAR(10) NOT NULL, " +
    	 			" title VARCHAR(2500) NOT NULL, " +
    	 			" editionNumber INTEGER NOT NULL, " +
    	 			" year CHAR(4) NOT NULL, " +
    	 			" publisherID INTEGER NOT NULL, " +
    	 			" price FLOAT NOT NULL, " +
    	 			" PRIMARY KEY ( isbn ), " +
    	 			" FOREIGN KEY ( publisherID ) REFERENCES publishers( publisherID ))";

    	 	
 		String authorISBN = "CREATE TABLE IF NOT EXISTS authorISBN(" +
				"authorID INTEGER NOT NULL," +
				"FOREIGN KEY (authorID) REFERENCES authors(authorID) " +
				"ON DELETE CASCADE " +
				"ON UPDATE CASCADE," +
				"isbn CHAR(10) NOT NULL," + 
				"FOREIGN KEY (isbn) REFERENCES titles(isbn) " +
				"ON DELETE CASCADE " +
				"ON UPDATE CASCADE"+
				")";

 		
 		try {
			int result1 = stmt.executeUpdate(authors);
			int result2 = stmt.executeUpdate(publishers);
			int result3 = stmt.executeUpdate(titles);
			int result4 = stmt.executeUpdate(authorISBN);

			if(result1 == 0 && result2 == 0 && result3 == 0 && result4 == 0 )
			{
				System.out.println("Authors table created!");
			}
			else{
				System.out.println("Failed to initilize table authors!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    
    }
    
    public void populateData()
    {
    	FileReader file;
		try {
			file = new FileReader("authors.csv");
			Scanner in = new Scanner(file);
			
			while(in.hasNextLine())
			{
			String[] temp = in.nextLine().split(",");
			System.out.println(temp[1]);
			}
			
		} catch (FileNotFoundException e) {
			System.err.println("Caught FileNotFoundException: " + e.getMessage());
		}
    }
    
    
    public static void main(String[] args) throws SQLException
    {
    	BookDatabase bd = new BookDatabase();
    	bd.populateData();
    	conn.close();
    }
	
}
