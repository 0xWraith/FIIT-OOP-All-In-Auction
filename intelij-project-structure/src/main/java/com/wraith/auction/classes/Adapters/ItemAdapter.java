package com.wraith.auction.classes.Adapters;

import com.wraith.auction.classes.Auction.Category;
import com.wraith.auction.classes.Auction.Item;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Item adapter class
 */
public class ItemAdapter
{
    /**
     * Item ID
     */
    private final SimpleLongProperty ID;
    /**
     * Item name
     */
    private final SimpleStringProperty name;
    /**
     * Item description
     */
    private final SimpleStringProperty description;
    /**
     * Item starting price
     */
    private final SimpleLongProperty startingPrice;
    /**
     * Item price after bid
     */
    private final SimpleLongProperty currentPrice;
    /**
     * Item owner ID
     */
    private final SimpleLongProperty ownerID;
    /**
     * Item new owner ID
     */
    private final SimpleStringProperty newOwnerName;
    /**
     * Item category
     */
    private final SimpleStringProperty category;

    /**
     * Constructor
     * @param item Intance of Item class
     */
    public ItemAdapter(Item item)
    {
        Category category = Category.getInstance();
        String categoryStr = category.getCategories().get(item.getCategory());

        ID = new SimpleLongProperty(item.getID());
        name = new SimpleStringProperty(item.getName());
        description = new SimpleStringProperty(item.getDescription());
        startingPrice = new SimpleLongProperty(item.getStartingPrice());
        currentPrice = new SimpleLongProperty(item.getCurrentPrice());
        ownerID = new SimpleLongProperty(item.getOwnerID());
        newOwnerName = new SimpleStringProperty(item.getNewOnwerName());
        this.category = new SimpleStringProperty(categoryStr == null ? "INVALID" : categoryStr);
    }

    /**
     * ID getter
     * @return ID of item
     */
    public SimpleLongProperty IDProperty() { return ID; }
    /**
     * Name getter
     * @return Name of item
     */
    public SimpleStringProperty nameProperty() { return name; }
    /**
     * Description getter
     * @return Description of item
     */
    public SimpleStringProperty descriptionProperty() { return description; }
    /**
     * Starting price getter
     * @return Startin price of item
     */
    public SimpleLongProperty startingPriceProperty() { return startingPrice; }
    /**
     * Current price getter
     * @return Current price of item
     */
    public SimpleLongProperty currentPriceProperty() { return currentPrice; }
    /**
     * Owner name getter
     * @return Owner name of item
     */
    public SimpleStringProperty newOwnerNameProperty() { return newOwnerName; }
    /**
     * Category getter
     * @return Category of item
     */
    public SimpleStringProperty categoryProperty() { return category; }
    /**
     * Owner ID getter
     * @return Owner ID of item
     */
    public SimpleLongProperty ownerIDProperty() { return ownerID; }
}
