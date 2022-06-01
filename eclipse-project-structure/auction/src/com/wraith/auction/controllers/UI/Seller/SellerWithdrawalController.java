package com.wraith.auction.controllers.UI.Seller;

import com.wraith.auction.classes.Auction.Category;
import com.wraith.auction.classes.InfoBox.MessageBox;
import com.wraith.auction.database.DataBase;
import com.wraith.auction.exceptions.DataBaseException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.math.BigInteger;
import java.sql.SQLException;

/**
 * Seller withdraw money controller
 */
public class SellerWithdrawalController extends SellerHomeController
{
    /**
     * JavaFX UI element
     */
    @FXML
    private TextField cardNumberInput;
    /**
     * JavaFX UI element
     */
    @FXML
    private TextField amountInput;

    /**
     * Scene initialization
     */
    @Override
    protected void initialize() { }

    /**
     * Withdraw money button listener
     */
    @FXML
    public void withdrawalClicked()
    {
        try {

            long cardNumber = Long.parseLong(cardNumberInput.getText());
            long amount = Long.parseLong(amountInput.getText());

            if(amount <= 0 || amount > user.getBalance())
            {
                new MessageBox("Error", "Enter correct amount to withdrawal!");
                return;
            }

            String SQL;
            DataBase dataBase = DataBase.getInstance();

            user.setBalance(user.getBalance() - amount);
            user.save("UserClass");

            SQL = String.format("UPDATE `accounts` SET `Balance` = %d WHERE `ID` = %d LIMIT 1", user.getBalance(), user.getID());
            dataBase.execute(SQL);

            new MessageBox("SUCCESSFUL", "You successfully received money to your bank account!");
        }
        catch (NumberFormatException exception) { new MessageBox("Error", "Enter correct credit card or amount!"); }
        catch (SQLException | DataBaseException exception) { new MessageBox("Error", "DataBase error!\n" + exception.getMessage()); }

    }
}
