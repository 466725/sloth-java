package com.framework.helpers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import config.Constants;

public class DatabaseResultsetManager {
	protected final static Logger logger = LogManager.getLogger(DatabaseResultsetManager.class.getName());

	public static ResultSet createResultset(String sql, Constants.DB_CONN_ENUM dbConn) throws Exception{
		logger.info("Database resultset will be created");
		return DatabaseStatementManager.createStatement(dbConn).executeQuery(sql);
	}

	public static void closeResultset(ResultSet rs) throws SQLException {
		rs.close();
		logger.info("Database resultset has been closed. ");
	}
}