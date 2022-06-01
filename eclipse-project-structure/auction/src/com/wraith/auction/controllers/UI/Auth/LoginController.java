package com.wraith.auction.controllers.UI.Auth;

import com.wraith.auction.Main;
import com.wraith.auction.classes.User.Admin;
import com.wraith.auction.classes.User.Bidder;
import com.wraith.auction.classes.User.Seller;
import com.wraith.auction.classes.User.User;
import com.wraith.auction.controllers.SceneController;
import com.wraith.auction.controllers.UtilsController;
import com.wraith.auction.database.DataBase;
import com.wraith.auction.exceptions.DataBaseException;
import com.wraith.auction.exceptions.UserExceptions;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Login Controller
 */
public class LoginController implements Initializable
{
    /**
     * JavaFX UI element
     */
    @FXML
    private TextField loginInput;
    /**
     * JavaFX UI element
     */
    @FXML
    private Label errorLabel;
    /**
     * JavaFX UI element
     */
    @FXML
    private TextField passwordInput;
    /**
     * User object
     */
    private User user;
    /**
     * SceneController object
     */
    private SceneController sceneController;
    /**
     * Login controller
     * @throws DataBaseException Error with DB
     */
    @FXML
    private void onPressedLogin() throws DataBaseException
    {
        if(user != null && user.isLogged())
            return;

        try {
            ResultSet resultSet = User.loginUser(loginInput.getText(), passwordInput.getText());

            if(resultSet == null)
            {
                errorLabel.setText("Invalid login or password!");
                return;
            }

            int accountType = resultSet.getInt("AccountType");

            if(accountType == 0)
                user = new Seller();
            else if(accountType == 1)
                user = new Bidder();
            else
                user = new Admin();

            user.setValues(resultSet);
        }
        catch (UserExceptions | SQLException exception) { System.out.println(exception.getMessage()); return; } catch (DataBaseException exception)
        {
            errorLabel.setText(exception.getMessage());

            if(DataBase.getInstance().getConnection() == null)
                DataBase.getInstance().setConnection();

            return;
        }

        errorLabel.setText("You've logged into account!\n" + user.getUser());


        user.save("UserClass");

        try {
        //RTTI
        if (user instanceof Seller)
            sceneController.activate("seller-home");
        else if (user instanceof Bidder)
            sceneController.activate("bidder-home");
        else if (user instanceof Admin)
                sceneController.activate("admin-home");
        }
        catch (IOException e) { e.printStackTrace(); }
    }
    /**
     * Menu buttons listener
     * @param mouseEvent JavaFX argument
     */
    @FXML
    private void authGoBack(MouseEvent mouseEvent)
    {
        try { sceneController.activate("start"); }
        catch (Exception exception) { System.out.println(exception.getMessage()); }
    }
    /**
     * @param location JavaFX argument
     * @param resources JavaFX argument
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) { sceneController = Main.getSceneController(); }
}