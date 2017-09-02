import java.sql.*;
public class Books {
	  
	 
	   public static void main(String[] args) {
	   
	   try{
	      

	      //STEP 1: Open a connection
	      System.out.println("Connecting to database...");
	      Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/books","root","root");
	      Statement mystmt = myConn.createStatement();

	      //Create the Books database
	      String sql = "CREATE DATABASE books";
	      mystmt.executeUpdate(sql);
	      System.out.println("Database created successfully...");

	      System.out.println("Create table in given database...");

	      //Handles the creation of tables
	      String authors = "CREATE TABLE authors " +
				"(authorID INTEGER not NULL auto_increment, " +
				" firstName VARCHAR(20) NOT NULL, " +
				" lastName VARCHAR(20) NOT NULL, " +
				" PRIMARY KEY ( authorID ))";

		String authorISBN = "CREATE TABLE authorISBN " +
			" (authorID INT NOT NULL, " +
			" isbn CHAR(10) NOT NULL, " +
			" FOREIGN KEY ( authorID) REFERENCES authors( authorID ), " +
			" FOREIGN KEY ( isbn ) REFERENCES titles ( isbn ))";

		String titles = "CREATE TABLE titles " +
			" (isbn CHAR(10) NOT NULL, " +
			" title VARCHAR(2500) NOT NULL, " +
			" editionNumber INTEGER NOT NULL, " +
			" year CHAR(4) NOT NULL, " +
			" publisherID INTEGER NOT NULL, " +
			" price FLOAT NOT NULL, " +
			" PRIMARY KEY ( isbn ), " +
			" FOREIGN KEY ( publisherID ) REFERENCES publishers( publisherID ))";

		String publishers = "CREATE TABLE publishers " +
			" (publisherID INTEGER NOT NULL auto_increment, " +
			" publisherName CHAR(100) NOT NULL, " +
			" PRIMARY KEY ( publisherID ))";
		
		try{
			mystmt.executeUpdate(authors);
			mystmt.executeUpdate(authorISBN);
			mystmt.executeUpdate(titles);
			mystmt.executeUpdate(publishers);
		}
		catch(SQLException se){
			se.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	 //////////////////////////////////////////////////////////////////////////////////////////////////   
	      /*
	       * q1, get author last name and first name by alphabetical order 
	       * @param first name
	       * @param last name 
	       */
	      String q1= "SELECT * from author ORDER BY lastName, firstName ";
	     
	
	      ResultSet authorName = mystmt.executeQuery(q1);
	      
	      System.out.println("Select all author in table  alphabetically by last name, first name: ");
	      while(authorName.next()){
	         //Retrieve by column name
	        
	         String first = authorName.getString("firstName");
	         String last = authorName.getString("lastName");

	         //Display values
	         
	         System.out.print("FirstName: " + first+" ");
	         System.out.println("lastName: " + last);
	      }
	      
	      System.out.println("");
	      
	 ///////////////////////////////////////////////////////////////////////////////////////////     
	      /*
	       * q2, get all publisher 
	       * @param publisherID
	       * @param publisherName
	       */
	    
	      String q2 = "SELECT * FROM publisher";
	      ResultSet publisher = mystmt.executeQuery(q2);
	      
	      System.out.println("Select all publishers from the publishers table");
	      while(publisher.next()){
		         //Retrieve by column name
		        
		         int publisherID = publisher.getInt("publisherID");
		         String publisherName = publisher.getString("publisherName");

		         //Display values
		         
		         System.out.print("Publisher ID: " + publisherID+"\n ");
		         System.out.println("Publisher Name: " + publisherName);
		      }
	      
	      
	      System.out.println("");
	      
	    ////////////////////////////////////////////////////////////////////////////////////////////////
	      /*
	       * q3, select from specific publisher and list all books published by that publisher, 
	       * order the books by title.
	       * @param title
	       * @param copyright
	       * @param isbn
	       */
	      String q3 = "SELECT title, copyright,isbn FROM title, publisher  "
	      		+ "WHERE title.publisherID = publisher.publisherID "
	      		+ "AND publisher.publisherName = 'sarah tech' ORDER BY title;" ;
	      
	      ResultSet publisherLookUp = mystmt.executeQuery(q3);
	      System.out.println("Please Select a Publisher ");
	      System.out.println("Sarah Tech");
	      System.out.println("The following books are from publisher's Sarah Tech: \n");
	      while(publisherLookUp.next()){
		         //Retrieve by column name
		      
		        String bookTitle = publisherLookUp.getString("title");
		        int isbn = publisherLookUp.getInt("isbn");
		        int copyRight = publisherLookUp.getInt("copyright");
		         //Display values
		         
		       
		         System.out.println("Book Title: " + bookTitle+"\n "+ "ISBN: "+isbn+","
		         		+ " " +"Copy Right: "+ copyRight+ "\n ");
		         
		      }
	      System.out.println("");
	      ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	      
	      
	      /*
	       * q4, INSERT NEW AUTHOR
	       * @param first name
	       * @param last name
	       * @param authorID, value set as default , auto incremented in data base
	       */
	      System.out.println("Insert New Author: Roy Ck ");
	      String q4 = "insert into author (authorID, firstName, lastName) values (default, 'Roy', 'Ck')";
	      
	       mystmt.executeUpdate(q4);
	     
		   System.out.println("Insert completed\n");
		   
		  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		   
		   
		      
	      /*
	       * q5, EDIT/UPDATE THE EXISITNG INFORMATION ABOUT AN AUTHOR
	       * @param last name
	       * @param first name
	       *Update 
	       * 
	       */
		   System.out.println("Updating author Emma Nathan to Emma Smith");
		   String q5 = "UPDATE author SET firstName = 'Emma' , "
		   		+ "lastName = 'Smith' WHERE authorID= '1'";
		   mystmt.executeUpdate(q5);
		   System.out.println("Updated ");
	    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		   
		   /*
		    * q6, ADD A NEW TITLE FOR AN AUTHOR
		    * @PARAM title
		    * @PARAM isbn
		    * @PARAM publisher
		    * @PARAM publisherID
		    * @PARAM price
		    * @param eidtionNumber
		    * @param copyright
		    * 
		    */
		  
	      
		   String q6 = "INSERT INTO title(isbn, title,editionNumber,copyright,publisherID,price)"
				   		+"VALUES('17','Advance Programming In C', 2, '2011','1',22.3)";
		   String q7 = "INSERT INTO authorISBN(author,isbn)"
				   + "VALUES(4,'16')";
				   	
				   	;
		   mystmt.executeUpdate(q6);
		   
		   System.out.println("Insert Completed\n");
		   
		   //////////////////////////////////////////////////////////////////////////////////////////////////////
		   
		   /*
		    * q6, ADD A NEW PUBLISHER
		    * @PARAM publisherID
		    * @PARAM publisherName
		    */
		   
		   System.out.println("Insert publisher : Gary Newton  ");
		  String q8 = "insert into publisher (publisherID, publisherName) values (default, 'Gary Newton')";
		      
		       mystmt.executeUpdate(q8);
		     
			   System.out.println("Insert completed\n");
		   
			   
		   //////////////////////////////////////////////////////////////////////////////////////////////////////////
			  
			   
			  /*
			   * q7, EDIT/UPDATE NEW PUBLISHER
			   * @PARAM publisherID
			   * @PARAM publisherName
			   */
			   
			   System.out.println("Updating publisher Sarah Tech  to Sarah & Newton Tech");
			   String q9 = "UPDATE publisher SET publisherName= 'Sarah & Newton Tech' WHERE publisherID = '1'";
			   		
			   mystmt.executeUpdate(q9);
			   System.out.println("Updated ");
			   
			   
			   
			   
			   
		     /////////////////////////////////////////////////////////////////////////////////////////////////////////
			   
		   
	      
	      //STEP 6: Clean-up environment
	      authorName.close();
	      publisher.close();
	      publisherLookUp.close();
	      mystmt.close();
	      myConn.close();
	   }catch(SQLException se){
	      //Handle errors for JDBC
	      se.printStackTrace();
	   }catch(Exception e){
	      //Handle errors for Class.forName
	      e.printStackTrace();
	   }
	   System.out.println("");
	   System.out.println("Goodbye!");
	}//end main
	}