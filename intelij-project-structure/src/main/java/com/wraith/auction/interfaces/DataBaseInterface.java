package com.wraith.auction.interfaces;
import com.wraith.auction.exceptions.DataBaseException;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Database interface
 */
public interface DataBaseInterface
{
    /**
     * Function for executing UPDATE/DELETE queries
     * @param sql Query
     * @return true/false
     * @throws SQLException Error in SQL
     * @throws DataBaseException Error in DataBase class
     */
    boolean execute(String sql) throws SQLException, DataBaseException;

    /**
     * Function for executing SELECT queries
     * @param sql Query
     * @return Data from database
     * @throws SQLException Error in SQL
     * @throws DataBaseException Error in database
     */
    ResultSet select(String sql) throws SQLException, DataBaseException;
}
