package com.framework.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import config.Constants;

public class DatabaseConnectionManager {
	protected final static Logger logger = LogManager.getLogger(DatabaseConnectionManager.class.getName());

	private DatabaseConnectionManager() {
		logger.info("I am here to guarantee singleton! ");
	}

	private static class DatabaseConnectionMaster {
		private static Connection databaseConnection = null;
	}

	public static Connection getConnection(Constants.DB_CONN_ENUM dbConn) throws Exception {
		if (DatabaseConnectionMaster.databaseConnection == null) {
			switch (dbConn) {
			case LOCALHOST_POSTGRE:
				Class.forName("org.postgresql.Driver");
				DatabaseConnectionMaster.databaseConnection = DriverManager.getConnection(
						Constants.LOCALHOST_POSTGRE_JDBC_URL, 
						Constants.LOCALHOST_POSTGRE_JDBC_USERNAME,
						Constants.LOCALHOST_POSTGRE_JDBC_PASSWORD);
				break;
			case LOCALHOST_SYBASE:
				Class.forName("sybase.jdbc.sqlanywhere.IDriver");
				DatabaseConnectionMaster.databaseConnection = DriverManager.getConnection(
						Constants.LOCALHOST_SYBASE_JDBC_URL, 
						Constants.LOCALHOST_SYBASE_JDBC_USERNAME,
						Constants.LOCALHOST_SYBASE_JDBC_PASSWORD);
				break;
			default:
				Class.forName("org.postgresql.Driver");
				DatabaseConnectionMaster.databaseConnection = DriverManager.getConnection(
						Constants.LOCALHOST_POSTGRE_JDBC_URL, 
						Constants.LOCALHOST_POSTGRE_JDBC_USERNAME,
						Constants.LOCALHOST_POSTGRE_JDBC_PASSWORD);
				break;
			}
		}
		logger.info("Connected to database");
		return DatabaseConnectionMaster.databaseConnection;
	}

	public static void closeConnection() throws SQLException {
		if (DatabaseConnectionMaster.databaseConnection != null)
			DatabaseConnectionMaster.databaseConnection.close();
		logger.info("Disconnected to database");
	}
}