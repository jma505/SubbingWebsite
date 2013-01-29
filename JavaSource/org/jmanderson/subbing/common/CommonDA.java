package org.jmanderson.subbing.common;

/**
 * Base class for database operations
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

public abstract class CommonDA
{

   protected ResultSet rs;
   protected PreparedStatement pStmt;
   protected DataSource ds;
   protected Connection dc;

   public void CloverDA(DataSource ds)
   {
	  this.ds = ds;
	  dc = null;
   }

   public Connection getConnection()
   {
	  return dc;
   }

   public void establishConnection() throws SQLException
   {
	  if (dc == null)
	  {
		 try
		 {
			dc = ds.getConnection();
		 }
		 catch (SQLException e)
		 {
			System.out.println("SQL Error in CommonDA establishConnection(): " + e);
		 }
		 catch (Exception e)
		 {
			System.out.println("Error in CommonDA establishConnection(): " + e);
		 }
	  }
   }

   public void releaseConnection() throws SQLException
   {
	  if (dc != null)
	  {
		 try
		 {
			dc.close();
			dc = null;
		 }
		 catch (SQLException e)
		 {
			System.out.println("SQL Error in CommonDA releaseConnection(): " + e);
		 }
		 catch (Exception e)
		 {
			System.out.println("Error in CommonDA releaseConnection(): " + e);
		 }
	  }
   }
}

