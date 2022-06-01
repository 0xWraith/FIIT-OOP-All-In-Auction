package com.wraith.auction.classes.Auction;
import java.io.Serializable;

/**
 * Item class
 */
public class Item implements Serializable
{

    /**
     * Item ID
     */
    private long ID;
    /**
     * Item name
     */
    private String name;
    /**
     * Item nescription
     */
    private String description;
    /**
     * Item image URL(Currently not in use)
     */
    private String imgURL;
    /**
     * Item starting price
     */
    private long startingPrice;
    /**
     * Item price after bids
     */
    private long currentPrice;
    /**
     * Item owner ID
     */
    private long ownerID;
    /**
     * Item new owner ID
     */
    private long newOwnerID;
    /**
     * Item category
     */
    private int category;
    /**
     * Item new owner name
     */
    private String newOnwerName;

    /**
     * Getter
     * @return ID
     */
    public long getID() { return ID; }

    /**
     * Getter
     * @return Item name
     */
    public String getName() { return name; }

    /**
     * Getter
     * @return Item description
     */
    public String getDescription() { return description; }

    /**
     * Getter
     * @return Item image URL
     */
    public String getImgURL() { return imgURL; }

    /**
     * Getter
     * @return Item starting price
     */
    public long getStartingPrice() { return startingPrice; }

    /**
     * Getter
     * @return Item price after bids
     */
    public long getCurrentPrice() { return currentPrice; }

    /**
     * Getter
     * @return Item owner ID
     */
    public long getOwnerID() { return ownerID; }

    /**
     * Getter
     * @return Item new owner ID
     */
    public long getNewOwnerID() { return newOwnerID; }

    /**
     * Getter
     * @return Item new owner name
     */
    public String getNewOnwerName() { return newOnwerName; }

    /**
     * Getter
     * @return Item category
     */
    public int getCategory() { return category; }

    /**
     * Setter
     * @param ID Item ID
     */
    public void setID(long ID) { this.ID = ID; }
    /**
     * Setter
     * @param name Item name
     */
    public void setName(String name) { this.name = name; }
    /**
     * Setter
     * @param description Item description
     */
    public void setDescription(String description) { this.description = description; }
    /**
     * Setter
     * @param imgURL Item image URL
     */
    public void setImgURL(String imgURL) { this.imgURL = imgURL; }
    /**
     * Setter
     * @param startingPrice Item starting price
     */
    public void setStartingPrice(long startingPrice) { this.startingPrice = startingPrice; }
    /**
     * Setter
     * @param currentPrice Item price after bids
     */
    public void setCurrentPrice(long currentPrice) { this.currentPrice = currentPrice; }
    /**
     * Setter
     * @param ownerID Item owner ID
     */
    public void setOwnerID(long ownerID) { this.ownerID = ownerID; }
    /**
     * Setter
     * @param newOwnerID Item new owner ID
     */
    public void setNewOwnerID(long newOwnerID) { this.newOwnerID = newOwnerID; }
    /**
     * Setter
     * @param category Item category
     */
    public void setCategory(int category) { this.category = category; }
    /**
     * Setter
     * @param newOwnerName Item new owner name
     */
    public void setNewOwnerName(String newOwnerName) { this.newOnwerName = newOwnerName; }
}
