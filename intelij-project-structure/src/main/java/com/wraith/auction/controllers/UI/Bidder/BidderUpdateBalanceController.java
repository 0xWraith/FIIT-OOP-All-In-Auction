package com.wraith.auction.controllers.UI.Bidder;

import com.wraith.auction.classes.InfoBox.MessageBox;
import com.wraith.auction.database.DataBase;
import com.wraith.auction.exceptions.DataBaseException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.SQLException;

/**
 * Bidder update balance controller
 */
public class BidderUpdateBalanceController extends BidderHomeController
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
     * Update balance button clicked
     */
    @FXML
    public void withdrawalClicked()
    {
        try {

            Long cardNumber = Long.parseLong(cardNumberInput.getText());
            Long amount = Long.parseLong(amountInput.getText());

            if(amount <= 0)
            {
                new MessageBox("Error", "Enter correct amount to withdrawal!");
                return;
            }

            String SQL;
            DataBase dataBase = DataBase.getInstance();

            user.setBalance(user.getBalance() + amount);
            user.save("UserClass");

            SQL = String.format("UPDATE `accounts` SET `Balance` = %d WHERE `ID` = %d LIMIT 1", user.getBalance(), user.getID());
            dataBase.execute(SQL);

            new MessageBox("SUCCESSFUL", "You successfully received money to your account!");
        }
        catch (NumberFormatException exception) { new MessageBox("Error", "Enter correct credit card or amount!"); }
        catch (SQLException | DataBaseException exception) { new MessageBox("Error", "DataBase error!\n" + exception.getMessage()); }
    }
}
