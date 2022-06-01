package com.wraith.auction.controllers.UI.Bidder;

import com.wraith.auction.Main;
import com.wraith.auction.classes.InfoBox.Dialog;
import com.wraith.auction.classes.User.User;
import com.wraith.auction.controllers.SceneController;
import com.wraith.auction.controllers.UI.Seller.SellerHomeController;
import com.wraith.auction.controllers.UtilsController;
import com.wraith.auction.database.DataBase;
import com.wraith.auction.exceptions.DataBaseException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Bidder home controller
 */
public class BidderHomeController implements Initializable
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
        catch (Exception exception)
        {
            System.out.println(exception.getMessage());
        }
        initialize();
    }

    /**
     * Update home text UI
     */
    private void updateHomePage()
    {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        nameLabel.setText(User.getFullName(user.getFirstName(), user.getLastName()));
        sexLabel.setText(User.getSex(user.getSex()));
        balanceLabel.setText(String.format("$%d", user.getBalance()));
        dateLabel.setText(dateTimeFormatter.format(LocalDateTime.now()));

        ResultSet resultSet;
        String SQL = String.format("SELECT `ID` FROM `items` WHERE `newOwnerID` = %d", user.getID());

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
        catch (SQLException | DataBaseException e) { itemsCounterLabel.setText("0"); }
    }

    /**
     * Menu buttons handler
     * @param mouseEvent JavaFX argument
     */
    @FXML
    protected void logOut(MouseEvent mouseEvent)
    {
        try { user.logOut(); }
        catch (Exception exception) { System.out.println(exception.getMessage()); }
    }
    /**
     * Menu buttons handler
     * @param mouseEvent JavaFX argument
     */
    @FXML
    protected void bidsList(MouseEvent mouseEvent)
    {
        try { sceneController.activate("bidder-bids"); }
        catch (Exception exception) { System.out.println(exception.getMessage()); }
    }
    /**
     * Menu buttons handler
     * @param mouseEvent JavaFX argument
     */
    @FXML
    protected void bidderHistory(MouseEvent mouseEvent)
    {
        try { sceneController.activate("bidder-history"); }
        catch (Exception exception) { System.out.println(exception.getMessage()); }
    }
    /**
     * Menu buttons handler
     * @param mouseEvent JavaFX argument
     */
    @FXML
    protected void bidderHome(MouseEvent mouseEvent)
    {
        try { sceneController.activate("bidder-home"); }
        catch (Exception exception) { System.out.println(exception.getMessage()); }
    }
    /**
     * Menu buttons handler
     */
    @FXML
    private void moreClicked()
    {
        try {
            new Dialog(BidderHomeController.this, "MORE", "Select action", "NOTIFICATIONS", "UPDATE BALANCE");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Response from Dialog
     * @param response true/false
     */
    public void response(boolean response) {
        try {
            if (!response)
                sceneController.activate("bidder-update-balance");
            else
                sceneController.activate("bidder-notifications");
        }
        catch (Exception exception) { System.out.println(exception.getMessage()); }
    }
}
