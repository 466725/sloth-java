package com.framework.helpers;

import config.Constants;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Manager class to handle all Database resultset
 *
 * @author Weipeng Zheng
 *
 */
public class DatabaseResultsetManager {
    protected final static Logger logger = LogManager.getLogger(DatabaseResultsetManager.class.getName());

    /**
     * Create a database resultSet by executing sql.
     *
     * @param sql        The sql to execute
     * @param Connection type of targeted database
     * @return ResultSet sql execution result set
     * @throws Exception
     */
    public static ResultSet createResultset(String sql, Constants.DB_CONN_ENUM dbConn) throws Exception {
        logger.info("Database resultset will be created");
        return DatabaseStatementManager.createStatement(dbConn).executeQuery(sql);
    }

    /**
     * Close database resultSet, if it's open.
     *
     * @throws SQLException
     */
    public static void closeResultset(ResultSet rs) throws SQLException {
        rs.close();
        logger.info("Database resultset has been closed. ");
    }
}