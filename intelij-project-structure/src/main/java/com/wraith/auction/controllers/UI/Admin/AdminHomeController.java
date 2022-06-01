package com.wraith.auction.controllers.UI.Admin;

import com.wraith.auction.Main;
import com.wraith.auction.classes.InfoBox.MessageBox;
import com.wraith.auction.classes.User.Admin;
import com.wraith.auction.classes.User.Seller;
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

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Admin home controller
 */
public class AdminHomeController implements Initializable
{
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
     * Update home page text UI
     */
    private void updateHomePage()
    {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        nameLabel.setText(User.getFullName(user.getFirstName(), user.getLastName()));
        sexLabel.setText(User.getSex(user.getSex()));
        dateLabel.setText(dateTimeFormatter.format(LocalDateTime.now()));

        ResultSet resultSet;
        String SQL = "SELECT `ID` FROM `items` WHERE `Accepted` = 0";

        try
        {
            resultSet = dataBase.select(SQL);
            resultSet.last();
            itemsCounterLabel.setText(String.valueOf(resultSet.getRow()));
        }
        catch (SQLException | DataBaseException e) { itemsCounterLabel.setText("0"); }
    }
    /**
     * Menu buttons listener
     */
    @FXML
    protected void logOut()
    {
        try { user.logOut(); }
        catch (Exception exception) { System.out.println(exception.getMessage()); }
    }
    /**
     * Menu buttons listener
     */
    @FXML
    protected void acceptBids()
    {
        try { sceneController.activate("admin-bids"); }
        catch (Exception exception) { System.out.println(exception.getMessage()); }
    }
    /**
     * Menu buttons listener
     */
    @FXML
    protected void searchUser()
    {
        try { sceneController.activate("admin-users"); }
        catch (Exception exception) { System.out.println(exception.getMessage()); }
    }
    /**
     * Menu buttons listener
     */
    @FXML
    protected void adminHistory()
    {
        try { sceneController.activate("admin-history"); }
        catch (Exception exception) { System.out.println(exception.getMessage()); }
    }
    /**
     * Menu buttons listener
     */
    @FXML
    protected void adminHome()
    {
        try { sceneController.activate("admin-home"); }
        catch (Exception exception) { System.out.println(exception.getMessage()); }
    }
    /**
     * Menu buttons listener
     */
    @FXML
    private void categoriesClicked()
    {
        try { sceneController.activate("admin-categories"); }
        catch (Exception exception) { new MessageBox("error", "Currently unavailable!\n" + exception.getMessage()); }
    }
}
