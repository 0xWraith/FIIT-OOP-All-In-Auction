package com.wraith.auction.controllers.UI.Admin;

import com.wraith.auction.classes.InfoBox.MessageBox;
import com.wraith.auction.classes.User.Admin;
import com.wraith.auction.classes.User.Bidder;
import com.wraith.auction.classes.User.Seller;
import com.wraith.auction.classes.User.User;
import com.wraith.auction.database.DataBase;
import com.wraith.auction.exceptions.DataBaseException;
import com.wraith.auction.exceptions.UserExceptions;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Admin find user controller
 */
public class AdminUsersController extends AdminHomeController
{
    /**
     * JavaFX UI element
     */
    @FXML
    private TextField nameInput;
    /**
     * JavaFX UI element
     */
    @FXML
    private TextField IDInput;
    /**
     * Initialization of scene
     */
    @Override
    protected void initialize() { }
    /**
     * Button click listener
     */
    @FXML
    private void onSearchClicked()
    {
        String fullName = nameInput.getText();
        long ID = tryParseID(IDInput.getText());

        if(ID == -1 && !fullName.contains(" "))
        {
            new MessageBox("error", "Please, enter correct values!");
            return;
        }
        String SQL;
        ResultSet resultSet;
        DataBase dataBase = DataBase.getInstance();

        if(fullName.contains(" "))
        {
            String[] nameParts = fullName.split(" ");
            SQL = String.format("SELECT * FROM `accounts` WHERE `FirstName` = '%s' AND `LastName` = '%s' LIMIT 1", nameParts[0], nameParts[1]);
        }
        else
            SQL = String.format("SELECT * FROM `accounts` WHERE `ID` = %d LIMIT 1", ID);

        try {
            resultSet = dataBase.select(SQL);
            resultSet.last();

            if(resultSet.getRow() == 0)
            {
                new MessageBox("ERROR", "There is no such user!");
                return;
            }

            System.out.println(resultSet);
            User foundedUser = null;

            int accountType = resultSet.getInt("AccountType");

            if(accountType == 0)
                foundedUser = new Seller();
            else if(accountType == 1)
                foundedUser = new Bidder();
            else
                foundedUser = new Admin();

            foundedUser.setValues(resultSet);
            foundedUser.save("FoundedUserClass");

            sceneController.activate("admin-user");
        }
        catch (SQLException | DataBaseException | UserExceptions | IOException e) { System.out.println(e.getMessage()); }
    }
    /**
     * @param input String with number
     * @return number or -1
     */
    private long tryParseID(String input)
    {
        try { return Long.parseLong(input); }
        catch (Exception exception) { return -1; }
    }
}
