package com.engineerexchange.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	
	private DatabaseConnection()
	{
		super();
	}
	
	public static Connection openDBConnection()
	{
		try
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:sqlserver://aahc6z1w53zp9m.cihqdcghmf7i.us-east-2.rds.amazonaws.com:1433;databaseName=EEDB", "sa", "S3cur!ty");
			return conn;
		}
		catch(SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e)
		{
			return null;
		}
	}
	
}
