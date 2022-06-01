package com.wraith.auction.controllers.UI.Seller;

import com.wraith.auction.Main;
import com.wraith.auction.classes.InfoBox.Dialog;
import com.wraith.auction.classes.User.User;
import com.wraith.auction.controllers.SceneController;
import com.wraith.auction.controllers.UtilsController;
import com.wraith.auction.database.DataBase;
import com.wraith.auction.exceptions.DataBaseException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.spi.DateFormatProvider;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Seller home controller
 */
public class SellerHomeController implements Initializable
{
    /**
     * JavaFX UI element
     */
    @FXML
    private Label notificationsLabel;
    /**
     * JavaFX UI element
     */
    @FXML
    private Label nameLabel;
    /**
     * JavaFX UI element
     */
    @FXML
    private Label sexLabel;
    /**
     * JavaFX UI element
     */
    @FXML
    private Label itemsCounterLabel;
    /**
     * JavaFX UI element
     */
    @FXML
    private Label dateLabel;
    /**
     * JavaFX UI element
     */
    @FXML
    private Label balanceLabel;
    /**
     * JavaFX UI element
     */
    @FXML
    protected Label welcome;

    /**
     * User object
     */
    protected User user;
    /**
     * Database object
     */
    protected DataBase dataBase;
    /**
     * SceneController object
     */
    protected SceneController sceneController;

    /**
     * Scene initialization
     */
    @FXML
    protected void initialize() { updateHomePage(); }

    /**
     * Scene initialization
     * @param location JavaFX argument
     * @param resources JavaFX argument
     */
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        try {
            dataBase = DataBase.getInstance();
            sceneController = Main.getSceneController();
            user = (User) UtilsController.deserializeObject("UserClass");
            welcome.setText(user.getFirstName());
        }
        catch (Exception exception) { System.out.println(exception.getMessage()); }
        initialize();
    }

    /**
     * Update home page UI
     */
    private void updateHomePage()
    {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        nameLabel.setText(User.getFullName(user.getFirstName(), user.getLastName()));
        sexLabel.setText(User.getSex(user.getSex()));
        balanceLabel.setText(String.format("$%d", user.getBalance()));
        dateLabel.setText(dateTimeFormatter.format(LocalDateTime.now()));

        ResultSet resultSet;
        String SQL = String.format("SELECT `ID` FROM `items` WHERE `newOwnerID` = -1 AND `ownerID` = %d", user.getID());

        try
        {
            resultSet = dataBase.select(SQL);
            resultSet.last();
            itemsCounterLabel.setText(String.valueOf(resultSet.getRow()));

            SQL = String.format("SELECT `ID` FROM `notifications` WHERE `OwnerID` = %d AND `Read` = 0", user.getID());
            resultSet = dataBase.select(SQL);
            resultSet.last();

            if(resultSet.getRow() != 0)
                notificationsLabel.setText(String.format("*NEW %d NOTIFICATIONS", resultSet.getRow()));

        }
        catch (SQLException | DataBaseException e) { System.out.println(e.getMessage());}
    }

    /**
     * Menu buttons handler
     */
    @FXML
    protected void logOut()
    {
        try { user.logOut(); }
        catch (Exception exception) { System.out.println(exception.getMessage()); }
    }
    /**
     * Menu buttons handler
     */
    @FXML
    protected void sellItem()
    {
        try { sceneController.activate("seller-sell-item"); }
        catch (Exception exception) { System.out.println(exception.getMessage()); }
    }
    /**
     * Menu buttons handler
     */
    @FXML
    protected void sellerHistory()
    {
        try { sceneController.activate("seller-history"); }
        catch (Exception exception) { System.out.println(exception.getMessage()); }
    }
    /**
     * Menu buttons handler
     */
    @FXML
    protected void sellerHome()
    {
        try { sceneController.activate("seller-home"); }
        catch (Exception exception) { System.out.println(exception.getMessage()); }
    }
    /**
     * Menu buttons handler
     */
    @FXML
    private void moreClicked()
    {
        try {
            new Dialog(SellerHomeController.this, "MORE", "Select action", "NOTIFICATIONS", "WITHDRAW");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Menu buttons handler
     * @param response true/false
     */
    public void response(boolean response) {
        try {
            if (!response)
                sceneController.activate("seller-withdrawal");
            else
                sceneController.activate("seller-notifications");
        }
        catch (Exception exception) { System.out.println(exception.getMessage()); }
    }
}
