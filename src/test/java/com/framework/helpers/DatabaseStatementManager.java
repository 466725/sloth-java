package com.framework.helpers;

import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import config.Constants;

public class DatabaseStatementManager {
	protected final static Logger logger = LogManager.getLogger(DatabaseStatementManager.class.getName());

	private DatabaseStatementManager() {
		logger.info("I am here to guarantee singleton! ");
	}

	private static class DatabaseStatementMaster {
		private static Statement databaseStatement = null;
	}

	public static Statement createStatement(Constants.DB_CONN_ENUM dbConn) {
		if (DatabaseStatementMaster.databaseStatement == null) {
			try {
				DatabaseStatementMaster.databaseStatement = DatabaseConnectionManager.getConnection(dbConn).createStatement();
			} catch (Throwable e) {
				logger.error("Exception is: ", e);
			}
		}
		logger.info("Database statement has been created");
		return DatabaseStatementMaster.databaseStatement;
	}

	public static void closeStatement() throws SQLException {
		if (DatabaseStatementMaster.databaseStatement != null)
			DatabaseStatementMaster.databaseStatement.close();
		logger.info("Database statement has been closed");
	}
}