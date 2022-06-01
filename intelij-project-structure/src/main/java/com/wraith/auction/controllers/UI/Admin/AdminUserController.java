package com.wraith.auction.controllers.UI.Admin;

import com.wraith.auction.classes.InfoBox.Dialog;
import com.wraith.auction.classes.InfoBox.MessageBox;
import com.wraith.auction.classes.User.Admin;
import com.wraith.auction.classes.User.Bidder;
import com.wraith.auction.classes.User.User;
import com.wraith.auction.controllers.UtilsController;
import com.wraith.auction.exceptions.DataBaseException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Admin find user controller
 */
public class AdminUserController extends AdminHomeController
{
    /**
     * JavaFX UI element
     */
    @FXML
    private Button deleteButton;
    /**
     * JavaFX UI element
     */
    @FXML
    private Label balanceLabel;
    /**
     * JavaFX UI element
     */
    @FXML
    private Label accountLabel;
    /**
     * JavaFX UI element
     */
    @FXML
    private ImageView userImage;
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
     * Founded user object
     */
    private User foundedUser;

    /**
     * Initialization of scene
     */
    @Override
    protected void initialize()
    {
        try {
            foundedUser = (User) UtilsController.deserializeObject("FoundedUserClass");
        }
        catch (Exception exception) { System.out.println(exception.getMessage()); }

        updateHomePage();
    }
    /**
     * Update home page UI
     */
    private void updateHomePage()
    {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        nameLabel.setText(User.getFullName(foundedUser.getFirstName(), foundedUser.getLastName()));
        sexLabel.setText(User.getSex(foundedUser.getSex()));
        dateLabel.setText(dateTimeFormatter.format(LocalDateTime.now()));

        Color color;
        String account;
        ResultSet resultSet;
        String SQL = String.format("SELECT `ID` FROM `items` WHERE `ownerID` = %d OR `newOwnerID` = %d", foundedUser.getID(), foundedUser.getID());

        if(foundedUser instanceof Admin)
        {
            account = "Admin";
            deleteButton.setVisible(false);

            color = Color.rgb(47, 140, 117);
        }
        else if(foundedUser instanceof Bidder)
        {
            account = "Bidder";
            color = Color.rgb(140, 89, 47);
        }
        else
        {
            account = "Seller";
            color = Color.rgb(47, 126, 140);
        }

        accountLabel.setText(account);
        balanceLabel.setText(String.format("$%d", foundedUser.getBalance()));

        sexLabel.setTextFill(color);
        dateLabel.setTextFill(color);
        nameLabel.setTextFill(color);
        balanceLabel.setTextFill(color);
        accountLabel.setTextFill(color);
        itemsCounterLabel.setTextFill(color);

        try
        {
            resultSet = dataBase.select(SQL);
            resultSet.last();
            itemsCounterLabel.setText(String.valueOf(resultSet.getRow()));
        }
        catch (SQLException | DataBaseException e) { itemsCounterLabel.setText("0"); }
    }
    /**
     * Delete button clicked
     */
    @FXML
    private void deleteClicked()
    {
        try
        {
            String SQL = String.format("DELETE FROM `accounts` WHERE `ID` = %d LIMIT 1", foundedUser.getID());
            dataBase.execute(SQL);

            SQL = String.format("DELETE FROM `items` WHERE `ownerID` = %d", foundedUser.getID());
            dataBase.execute(SQL);

            SQL = String.format("DELETE FROM `notifications` WHERE `ownerID` = %d", foundedUser.getID());
            dataBase.execute(SQL);

            new Dialog(AdminUserController.this, "DELETED", "Account was successfully deleted", "OK", "GO BACK");
        }
        catch (Exception exception) { System.out.println(exception.getMessage()); }
    }

    /**
     * Response from Dialog
     * @param response true/false
     */
    public void response(boolean response)
    {
        try { sceneController.activate("admin-users"); }
        catch (Exception exception) { new MessageBox("error", "Currently unavailable!\n" + exception.getMessage()); }
    }
}
