package com.wraith.auction.classes.User;

import com.wraith.auction.Main;
import com.wraith.auction.classes.Adapters.ItemAdapter;
import com.wraith.auction.classes.Auction.Item;
import com.wraith.auction.classes.InfoBox.MessageBox;
import com.wraith.auction.controllers.SceneController;
import com.wraith.auction.controllers.UtilsController;
import com.wraith.auction.database.DataBase;
import com.wraith.auction.exceptions.DataBaseException;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Bidder class
 */
public class Bidder extends User
{
    /**
     * Set bidder as the member of auction
     * @param selectedItem Item selected from table
     * @return true/false
     */
    public boolean participateIn(ItemAdapter selectedItem)
    {
        if(selectedItem == null)
        {
            new MessageBox("Error", "You didn't select bid to participate in!");
            return false;
        }

        if(getBalance() < selectedItem.startingPriceProperty().getValue())
        {
            new MessageBox("Error", "You dont have enough money to participate in!\nPlease update your balance at Home page.");
            return false;
        }

        ResultSet resultSet;
        String SQL = String.format("SELECT `ID` FROM `items` WHERE `ID` = %d AND Started = 0 LIMIT 1", selectedItem.IDProperty().getValue());
        try {
            DataBase dataBase = DataBase.getInstance();
            resultSet = dataBase.select(SQL);

            if(!resultSet.last())
            {
                new MessageBox("Error", "Refresh page!\nBid already started.");
                return false;
            }

            SQL = String.format("UPDATE `items` SET `Started` = 1 WHERE `ID` = %d LIMIT 1", selectedItem.IDProperty().getValue());
            dataBase.execute(SQL);

            Item item = new Item();

            item.setID(selectedItem.IDProperty().getValue());
            item.setName(selectedItem.nameProperty().getValue());
            item.setOwnerID(selectedItem.ownerIDProperty().getValue());
            item.setCurrentPrice(selectedItem.currentPriceProperty().getValue());
            item.setStartingPrice(selectedItem.startingPriceProperty().getValue());

            UtilsController.serializeObject(item, "itemToParticipate");
            Main.getSceneController().activate("bidder-active-auction");

            return true;

        }
        catch (SQLException | DataBaseException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @return Text of greeting
     */
    public String getUser() { return String.format("Bidder, %s", getFirstName()); }
}
