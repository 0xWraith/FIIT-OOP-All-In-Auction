package com.wraith.auction.controllers.UI.Auth;

import com.wraith.auction.Main;
import com.wraith.auction.classes.User.RegUser;
import com.wraith.auction.controllers.SceneController;
import com.wraith.auction.database.DataBase;
import com.wraith.auction.exceptions.DataBaseException;
import com.wraith.auction.exceptions.UserExceptions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Registration controller
 */
public class RegistrationController implements Initializable
{
    /**
     * JavaFX UI element
     */
    @FXML
    private TextField firstNameInput;
    /**
     * JavaFX UI element
     */
    @FXML
    private TextField lastNameInput;
    /**
     * JavaFX UI element
     */
    @FXML
    private TextField mailInput;

    /**
     * JavaFX UI element
     */
    @FXML
    private RadioButton maleButton;
    /**
     * JavaFX UI element
     */
    @FXML
    private RadioButton femaleButton;
    /**
     * JavaFX UI element
     */
    @FXML
    private RadioButton sellerButton;
    /**
     * JavaFX UI element
     */
    @FXML
    private RadioButton bidderButton;
    /**
     * JavaFX UI element
     */
    @FXML
    private Label errorLabel;
    /**
     * JavaFX UI element
     */
    @FXML
    private TextField loginInput;
    /**
     * JavaFX UI element
     */
    @FXML
    private TextField passwordInput;


    /**
     * New user object
     */
    private RegUser regUser;
    /**
     * SceneController object
     */
    private SceneController sceneController;

    /**
     * Scene initialization
     */
    @FXML
    public void initialize()
    {
        regUser = new RegUser();
        sceneController = Main.getSceneController();

        maleButton.setOnAction(event -> {
            boolean sex = !maleButton.isSelected();

            regUser.setSex(!sex);
            femaleButton.setSelected(sex);
        });

        femaleButton.setOnAction(event -> {
            boolean sex = femaleButton.isSelected();

            regUser.setSex(sex);
            maleButton.setSelected(!sex);
        });

        sellerButton.setOnAction(event -> {
            boolean accountType = !sellerButton.isSelected();

            regUser.setAccountType(!accountType ? 0 : 1);
            bidderButton.setSelected(accountType);
        });

        bidderButton.setOnAction(event -> {
            boolean accountType = bidderButton.isSelected();

            regUser.setAccountType(!accountType ? 0 : 1);
            sellerButton.setSelected(!accountType);
        });
    }

    /**
     * Create account
     * @param event JavaFX argument
     * @throws DataBaseException DB issues
     */
    @FXML
    private void createAccount(ActionEvent event) throws DataBaseException
    {
        if(regUser != null && regUser.isLogged())
            return;

        DataBase dataBase = null;

        try {

            String sql;
            dataBase = DataBase.getInstance();

            regUser.setLogin(loginInput.getText());
            sql = String.format("SELECT `ID` FROM `accounts` WHERE `Login` = '%s' LIMIT 1", regUser.getLogin());
            ResultSet resultSet = dataBase.select(sql);
            resultSet.last();

            if(resultSet.getRow() == 1)
            {
                errorLabel.setText("User with login " + regUser.getLogin() + "\nalready registered");
                return;
            }

            regUser.setFirstName(firstNameInput.getText());
            regUser.setLastName(lastNameInput.getText());
            regUser.setEmail(mailInput.getText());
            regUser.setLogged(true);

            sql = String.format("INSERT INTO `accounts`(`Login`, `FirstName`, `LastName`, `Mail`, `Hash`, `Salt`, `Sex`, `AccountType`) " +
                    "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', %d, %d)", regUser.getLogin(), regUser.getFirstName(), regUser.getLastName(),
                    regUser.getEmail(), passwordInput.getText(), "fjhus!#$@eh@", regUser.getSex(), regUser.getAccountType());

            dataBase.execute(sql);
        }
        catch (UserExceptions exception) { errorLabel.setText(exception.getMessage()); return;}
        catch (SQLException exception)
        {
            System.out.println(exception.getMessage());
            return;
        }
        catch (DataBaseException exception)
        {
            errorLabel.setText(exception.getMessage());

            if(dataBase.getConnection() == null)
                dataBase.setConnection();

            return;
        }
        errorLabel.setText("You've registered your account!");

        try { sceneController.activate("start"); }
        catch (Exception ex) { System.out.println(ex.getMessage()); }
    }


    /**
     * Menu buttons handler
     * @param mouseEvent JavaFX listener
     */
    public void regGoBack(MouseEvent mouseEvent)
    {
        try { sceneController.activate("start"); }
        catch (Exception exception) { System.out.println(exception.getMessage()); }
    }

    /**
     * Scene initialization
     * @param location JavaFX argument
     * @param resources JavaFX argument
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) { initialize(); }
}
