package com.wraith.auction.classes.User;

import com.wraith.auction.classes.Auction.Item;
import com.wraith.auction.classes.InfoBox.MessageBox;
import com.wraith.auction.database.DataBase;
import com.wraith.auction.exceptions.DataBaseException;

import java.sql.SQLException;

/**
 * Seller class
 */
public class Seller extends User
{
    public void sellItem(Item item)
    {
        try
        {
            String SQL;
            DataBase dataBase = DataBase.getInstance();

            SQL = String.format("INSERT INTO `items`(`Name`, `Description`, `startingPrice`, `ownerID`, `Category`) " +
                    "VALUES ('%s', '%s', %d, %d, %d)", item.getName(), item.getDescription(), item.getStartingPrice(), getID(), item.getCategory());

            dataBase.execute(SQL);

            new MessageBox("SUCCESSFUL", "You successfully started selling your item\nNow wait until Administrator accept it!");
        }
        catch (SQLException | DataBaseException exception) { new MessageBox("Error", "DataBase error!\n" + exception.getMessage()); }
    }
    /**
     * @return Text of greeting
     */
    public String getUser() { return String.format("Seller, %s", getFirstName()); }
}
