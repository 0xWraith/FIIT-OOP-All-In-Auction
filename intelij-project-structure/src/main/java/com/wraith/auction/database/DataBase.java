package com.wraith.auction.database;

import com.wraith.auction.interfaces.DataBaseInterface;
import com.wraith.auction.exceptions.DataBaseException;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Properties;

/**
 * DataBase controller
 */
public class DataBase implements DataBaseInterface
{
    /**
     * Database statement
     */
    private Statement statement;
    /**
     * Connection to database
     */
    private Connection connection;
    /**
     * Database properties
     */
    private Properties properties;

    /**
     * Database name to connect
     */
    private final String DATABASE_DB = "gs19188";
    /**
     * Database user to connect
     */
    private final String DATABASE_USER = "gs19188";
    /**
     * Database password to connect
     */
    private final String DATABASE_PASSWORD = "X0p06LzKQwHCidGgeevE2KgHThCB3zuo";
    /**
     * Database host to connect
     */
    private final String DATABASE_HOST = "176.31.24.37";
    /**
     * Database driver to connect
     */
    private final String DATABASE_DRIVER = "com.mysql.cj.jdbc.Driver";

    /**
     * Database instance
     */
    private static volatile DataBase instance = null;

    /**
     * Constructor
     */
    private DataBase() { }

    /**
     * Connect to DB
     * @throws DataBaseException Custom exception
     */
    public void setConnection() throws DataBaseException
    {
        if(connection != null)
            throw new DataBaseException("Connection to DataBase already exist!");

        properties = new Properties();
        properties.setProperty("user", DATABASE_USER);
        properties.setProperty("password", DATABASE_PASSWORD);

        try
        {
            Class.forName(DATABASE_DRIVER).getConstructor().newInstance();
            connection = DriverManager.getConnection("jdbc:mysql://"+ DATABASE_HOST + ":3306/" + DATABASE_DB + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", properties);
        }
        catch (SQLException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Get connection object
     * @return connection object
     */
    public Connection getConnection() { return connection; }

    /**
     * Get DB instance
     * @return instance of DB class
     */
    public static DataBase getInstance()
    {
        DataBase control = instance;

        if(control != null)
            return control;

        synchronized (DataBase.class)
        {
            if(instance == null)
                instance = new DataBase();

            return instance;
        }
    }

    /**
     * Perform select query
     * @param sql SQL to perform
     * @return Result from DB
     * @throws SQLException Error's in sql
     * @throws DataBaseException DB error's
     */
    @Override
    public ResultSet select(String sql) throws SQLException, DataBaseException
    {
        if(connection == null)
            throw new DataBaseException("Program doesn't properly connected to DataBase!\nTry again.");

        ResultSet resultSet = null;

        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        resultSet = statement.executeQuery(sql);

        return resultSet;
    }

    /**
     * Perform UPDATE/DELETE
     * @param sql Query
     * @return true if query influence on some row
     * @throws SQLException Error in query
     * @throws DataBaseException Error in database
     */
    @Override
    public boolean execute(String sql) throws SQLException, DataBaseException
    {
        if(connection == null)
            throw new DataBaseException("Program doesn't properly connected to DataBase!\nTry again.");

        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        return statement.execute(sql);
    }
}
