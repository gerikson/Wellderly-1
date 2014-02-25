package edu.sdsc.dao;

import java.sql.DriverManager;
import java.sql.Connection;

public class WellConn {

	private static java.sql.Connection conn = null;
	private static final String DRIVER = "com.vertica.jdbc.Driver";
	private static final String URL = "jdbc:vertica://bigonc.sdsc.edu:5433/wellderly";
	private static final String USERNAME = "dbadmin";
	private static final String PASSWORD = "3Nathan$";

	public static Connection getConn() throws Exception {

		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

		} catch (Exception e) {
			System.out.println(e.toString());

		} finally {

		}
		return conn;
	}

}