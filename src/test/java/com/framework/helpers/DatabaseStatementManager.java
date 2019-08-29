package com.framework.helpers;

import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import config.Constants;

public class DatabaseStatementManager {
	protected final static Logger logger = LogManager.getLogger(DatabaseStatementManager.class.getName());

	/**
	 * Private Constructor, to make this class a singleton one.
	 * 
	 */
	private DatabaseStatementManager() {
		logger.info("I am here to guarantee singleton! ");
	}

	/**
	 * Inner class, to encapsulate database statement securely
	 * 
	 */
	private static class DatabaseStatementMaster {
		private static Statement databaseStatement = null;
	}

	/**
	 * Create a database statement. If created already return it, otherwise create
	 * and return it.
	 * 
	 * @param Connection type of targeted database
	 * @return Statement database statement
	 */
	public static Statement createStatement(Constants.DB_CONN_ENUM dbConn) {
		if (DatabaseStatementMaster.databaseStatement == null) {
			try {
				DatabaseStatementMaster.databaseStatement = DatabaseConnectionManager.getConnection(dbConn).createStatement();
				logger.info("Database statement has been created");
			} catch (Throwable e) {
				//logger.fatal("Exception is: ", e);
				logger.fatal("Failed to create database statement!");
			}
		}
		return DatabaseStatementMaster.databaseStatement;
	}

	/**
	 * Close database statement, if it's open.
	 * 
	 * @throws SQLException
	 */
	public static void closeStatement() throws SQLException {
		if (DatabaseStatementMaster.databaseStatement != null)
			DatabaseStatementMaster.databaseStatement.close();
		logger.info("Database statement has been closed");
	}
}