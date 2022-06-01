package com.wraith.auction.classes.Adapters;

import com.wraith.auction.classes.Auction.Category;
import com.wraith.auction.classes.Auction.Item;
import com.wraith.auction.classes.User.User;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * User adapter class
 */
public class UserAdapter
{
    /**
     * User ID
     */
    private final SimpleLongProperty ID;
    /**
     * Username
     */
    private final SimpleStringProperty name;

    /**
     * Constructor
     * @param user Instance of user
     */
    public UserAdapter(User user)
    {
        ID = new SimpleLongProperty(user.getID());
        name = new SimpleStringProperty(user.getFirstName() + " " + user.getLastName());
    }

    /**
     * Get user ID
     * @return User ID
     */
    public SimpleLongProperty IDProperty() { return ID; }
    /**
     * Get user name
     * @return User name
     */
    public SimpleStringProperty nameProperty() { return name; }
}
