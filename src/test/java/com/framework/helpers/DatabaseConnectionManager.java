package com.framework.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import config.Constants;

/**
 * Manager class to handle all Database connection
 * 
 * @author Weipeng Zheng
 *
 */
public class DatabaseConnectionManager {
	protected final static Logger logger = LogManager.getLogger(DatabaseConnectionManager.class.getName());

	/**
	 * Private Constructor, to make this class a singleton one.
	 * 
	 */
	private DatabaseConnectionManager() {
		logger.info("I am here to guarantee singleton! ");
	}

	/**
	 * Inner class, to encapsulate database connection securely
	 * 
	 */
	private static class DatabaseConnectionMaster {
		private static Connection databaseConnection = null;
	}

	/**
	 * Create a database connection. If created already return it, otherwise create
	 * and return it.
	 * 
	 * @param Connection type of targeted database
	 * @return Connection database connection
	 */
	public static Connection getConnection(Constants.DB_CONN_ENUM dbConn) {
		if (DatabaseConnectionMaster.databaseConnection == null) {
			switch (dbConn) {
			case LOCALHOST_POSTGRE:
				try {
					Class.forName("org.postgresql.Driver");
					DatabaseConnectionMaster.databaseConnection = DriverManager.getConnection(
							Constants.LOCALHOST_POSTGRE_JDBC_URL, Constants.LOCALHOST_POSTGRE_JDBC_USERNAME,
							Constants.LOCALHOST_POSTGRE_JDBC_PASSWORD);
				} catch (Throwable e) {
					logger.fatal("Exception is: ", e);
				}
				break;
			case LOCALHOST_SYBASE:
				try {
					Class.forName("sybase.jdbc.sqlanywhere.IDriver");
					DatabaseConnectionMaster.databaseConnection = DriverManager.getConnection(
							Constants.LOCALHOST_SYBASE_JDBC_URL, Constants.LOCALHOST_SYBASE_JDBC_USERNAME,
							Constants.LOCALHOST_SYBASE_JDBC_PASSWORD);
				} catch (Throwable e) {
					logger.fatal("Exception is: ", e);
				}
				break;
			default:
				try {
					Class.forName("org.postgresql.Driver");
					DatabaseConnectionMaster.databaseConnection = DriverManager.getConnection(
							Constants.LOCALHOST_POSTGRE_JDBC_URL, Constants.LOCALHOST_POSTGRE_JDBC_USERNAME,
							Constants.LOCALHOST_POSTGRE_JDBC_PASSWORD);
				} catch (Throwable e) {
					logger.fatal("Exception is: ", e);
				}
				break;
			}
		}
		logger.info("Connected to database");
		return DatabaseConnectionMaster.databaseConnection;
	}

	/**
	 * Close database connection, if it's open.
	 * 
	 * @throws SQLException
	 */
	public static void closeConnection() throws SQLException {
		if (DatabaseConnectionMaster.databaseConnection != null)
			DatabaseConnectionMaster.databaseConnection.close();
		logger.info("Disconnected to database");
	}
}