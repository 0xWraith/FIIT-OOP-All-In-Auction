package com.wraith.auction.classes.Adapters;

import com.wraith.auction.classes.Auction.Category;
import com.wraith.auction.classes.Auction.Item;
import com.wraith.auction.classes.User.User;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import java.text.SimpleDateFormat;

/**
 * Notifications adapter
 */
public class NotificationsAdapter
{
    /**
     * Notification ID
     */
    private final SimpleLongProperty ID;
    /**
     * Notification message
     */
    private final SimpleStringProperty message;
    /**
     * Notification date
     */
    private final SimpleStringProperty date;

    /**
     * Constructor
     * @param notification Instance of User.Notification
     */
    public NotificationsAdapter(User.Notification notification)
    {
        ID = new SimpleLongProperty(notification.getID());
        message = new SimpleStringProperty(notification.getMessage());
        date = new SimpleStringProperty(notification.getDate().toString());
    }

    /**
     * Get notification ID
     * @return Message ID
     */
    public SimpleLongProperty IDProperty() { return ID; }
    /**
     * Get notification message
     * @return Message
     */
    public SimpleStringProperty messageProperty() { return message; }
    /**
     * Get notification creation date
     * @return Message date create
     */
    public SimpleStringProperty dateProperty() { return date; }
}
