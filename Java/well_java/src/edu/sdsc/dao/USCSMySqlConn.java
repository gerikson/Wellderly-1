package edu.sdsc.dao;

import java.sql.DriverManager;
import java.sql.Connection;

public class USCSMySqlConn {
	
	private static java.sql.Connection conn = null;
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://genome-mysql.cse.ucsc.edu:3306/hg19";
	private static final String USERNAME = "genome";
	private static final String PASSWORD = "";
	
	public static Connection getConn() throws Exception{
		
		try{
			Class.forName(DRIVER);
		    conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		    
		}
		catch(Exception e){
		    e.toString();
			
		}
		finally{
			
		}
		return conn;
	}

}