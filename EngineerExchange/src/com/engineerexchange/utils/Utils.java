package com.engineerexchange.utils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utils {
	private Utils(){
		
	}
	
	public static void close(Connection c, CallableStatement cs, ResultSet rs)
	{
		if (cs != null)
		{
			try
			{
				cs.close();
			}
			catch (SQLException e)
			{
				Logger.getGlobal().log(Level.SEVERE, "Error closing callable statement", e);
			}
		}
		if (rs != null)
		{
			try
			{
				rs.close();
			}
			catch (SQLException e)
			{
				Logger.getGlobal().log(Level.SEVERE, "Error closing result set", e);
			}
		}
		if (c != null)
		{
			try
			{
				c.close();
			}
			catch (SQLException e)
			{
				Logger.getGlobal().log(Level.SEVERE, "Error closing connection", e);
			}
		}
	}
}
