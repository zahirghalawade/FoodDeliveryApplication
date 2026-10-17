package com.tap1.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public  class dbConnection {
	private static final String URL = "jdbc:mysql://localhost:3306/Tap_Foods";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "1234";
	static Connection connection;

	public static  Connection getConnection()
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection=DriverManager.getConnection(URL,USERNAME,PASSWORD);
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}

}
