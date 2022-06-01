package com.wraith.auction.classes.User;

import com.wraith.auction.Main;
import com.wraith.auction.controllers.UtilsController;
import com.wraith.auction.database.DataBase;
import com.wraith.auction.exceptions.DataBaseException;
import com.wraith.auction.exceptions.UserExceptions;
import com.wraith.auction.interfaces.UserInterface;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.BinaryOperator;

/**
 * User class
 */
public class User implements UserInterface, Serializable
{

    /**
     * Inner class for notifications
     */
    public class Notification
    {
        /**
         * ID of Notification
         */
        private long ID;
        /**
         * Message of Notification
         */
        private String message;
        /**
         * Creation date
         */
        private Date date;

        /**
         * Getter
         * @return Notification ID
         */
        public long getID() { return ID; }

        /**
         * Setter
         * @param ID Notification ID
         */
        public void setID(long ID) { this.ID = ID; }

        /**
         * Getter
         * @return Notification message
         */
        public String getMessage() { return message; }

        /**
         * Setter
         * @param message Notification message
         */
        public void setMessage(String message) { this.message = message; }

        /**
         * Getter
         * @return Notification date
         */
        public Date getDate() { return date; }
        /**
         * Setter
         * @param date Notification date
         */
        public void setDate(Date date) { this.date = date; }

        /**
         * Create notification and send it to user
         * @param toID - ID of user
         * @param message - Message
         */
        public void create(long toID, String message)
        {
            String SQL;
            DataBase dataBase = DataBase.getInstance();

            SQL = String.format("INSERT INTO `notifications`(`OwnerID`, `Message`) VALUES (%d, '%s')", toID, message);

            try { dataBase.execute(SQL); }
            catch (SQLException | DataBaseException e) { e.printStackTrace(); }
        }
    }

    /**
     * User ID
     */
    private long ID;
    /**
     * User login
     */
    private String login;
    /**
     * User first name
     */
    private String firstName;
    /**
     * User last name
     */
    private String lastName;
    /**
     * User email
     */
    private String email;
    /**
     * User password
     */
    private String hash;
    /**
     * User salt for password
     */
    private String salt;
    /**
     * User balance
     */
    private long balance;
    /**
     * User sex
     */
    private boolean sex;
    /**
     * User logged state
     */
    private boolean logged;


    /**
     * Get user full name
     * @param firstName - User first name
     * @param lastName - User last name
     * @return full name
     */
    public static String getFullName(String firstName, String lastName)
    {
        BinaryOperator<String> getFullName = (firstNameInterface, lastNameInterface) -> firstNameInterface + " " + lastNameInterface;
        return getFullName.apply(firstName, lastName);
    }

    /**
     * Get user sex
     * @param sex 0/1
     * @return sex string
     */
    public static String getSex(int sex) { return (sex == 0 ? "Male" : "Female"); }

    /**
     * Get user ID
     * @return ID
     */
    public long getID() { return this.ID; }

    /**
     * Set ID
     * @param ID ID
     * @throws UserExceptions Invalid ID
     */
    public void setID(long ID) throws UserExceptions
    {
        if(ID < 1)
            throw new UserExceptions("Error: Invalid ID(" + ID + ") to set");

        this.ID = ID;
    }

    /**
     * Get login
     * @return User login
     */
    public String getLogin() { return login; }

    /**
     * Set login
     * @param login User login
     * @throws UserExceptions Login text error
     */
    public void setLogin(String login) throws UserExceptions
    {
        if(login.length() < 1 || login.length() > 32)
            throw new UserExceptions("Error: Invalid login to set");

        this.login = login;
    }

    /**
     * Get user first name
     * @return User first name
     */
    public String getFirstName() { return firstName; }

    /**
     * Set user first name
     * @param firstName New first name
     * @throws UserExceptions Error in first name
     */
    public void setFirstName(String firstName) throws UserExceptions
    {
        if(firstName.length() < 1 || firstName.length() > 32)
            throw new UserExceptions("Error: Invalid name to set");

        this.firstName = firstName;
    }

    /**
     * Get user last name
     * @return User last name
     */
    public String getLastName() { return lastName; }
    /**
     * Set user last name
     * @param lastName New last name
     * @throws UserExceptions Error in last name
     */
    public void setLastName(String lastName) throws UserExceptions
    {
        if(lastName.length() < 1 || lastName.length() > 32)
            throw new UserExceptions("Error: Invalid last name to set");

        this.lastName = lastName;
    }

    /**
     * Get user mail
     * @return User mail
     */
    public String getEmail() { return email; }

    /**
     * @param email New user mail
     * @throws UserExceptions Error in mail
     */
    public void setEmail(String email) throws UserExceptions
    {
        if(email.length() < 5  || email.length() > 64 || !email.contains("@") || !email.contains("."))
            throw new UserExceptions("Error: Invalid mail to set");

        this.email = email;
    }

    /**
     * Set user hash
     * @param hash Hash of password
     */
    public void setHash(String hash) { this.hash = hash; }
    /**
     * Set user salt
     * @param salt Salt for hash
     */
    public void setSalt(String salt) { this.salt = salt; }

    /**
     * Get user balance
     * @return User balance
     */
    public long getBalance() { return balance; }
    /**
     * Change user balance
     * @param balance New balance
     */
    public void setBalance(long balance) {
        if(balance < 0)
            balance = 0;

        this.balance = balance;
    }

    /**
     * Check if user logged in
     * @return Login state
     */
    public boolean isLogged() { return logged; }

    /**
     * Set log in state
     * @param logged false/true
     */
    public void setLogged(boolean logged) { this.logged = logged; }

    /**
     * Get sex
     * @return sex
     */
    public int getSex() { return sex ? 1 : 0; }

    /**
     * Set sex
     * @param sex sex 0/1
     */
    public void setSex(boolean sex) { this.sex = sex; }

    /**
     * @param login User login
     * @param password User password
     * @return null - user not found or resultSet - user data
     * @throws SQLException sql error
     * @throws DataBaseException db error
     */
    public final static ResultSet loginUser(String login, String password) throws SQLException, DataBaseException {
        DataBase dataBase;
        String sql;

        dataBase = DataBase.getInstance();

        sql = String.format("SELECT * FROM `accounts` WHERE `Login` = '%s' AND `Hash` = '%s' LIMIT 1", login, password);
        ResultSet resultSet = dataBase.select(sql);
        resultSet.last();

        if(resultSet.getRow() == 0)
            return null;

        return resultSet;
    }

    /**
     * Set user values
     * @param resultSet Data set from database
     * @throws SQLException SQL error
     * @throws UserExceptions DB Error
     */
    public void setValues(ResultSet resultSet) throws SQLException, UserExceptions {

        setID(resultSet.getInt("ID"));
        setLogin(resultSet.getString("Login"));
        setFirstName(resultSet.getString("FirstName"));
        setLastName(resultSet.getString("LastName"));
        setEmail(resultSet.getString("Mail"));
        setHash(resultSet.getString("Hash"));
        setSalt(resultSet.getString("Salt"));
        setBalance(resultSet.getInt("Balance"));
        setSex(resultSet.getBoolean("Sex"));
        setLogged(true);
    }

    /**
     * Log out user
     * @throws IOException Error in scene controller
     */
    public void logOut() throws IOException { Main.getSceneController().activate("start"); }

    /**
     * Serialize object
     * @param name Class name
     */
    public void save(String name)
    {
        try { UtilsController.serializeObject(this, name); }
        catch (Exception ex) { System.out.println(ex.getMessage()); }
    }

    /**
     * Send notification
     * @param toID user ID
     * @param message message
     */
    public void sendNotification(Long toID, String message) { this.new Notification().create(toID, message); }
}

