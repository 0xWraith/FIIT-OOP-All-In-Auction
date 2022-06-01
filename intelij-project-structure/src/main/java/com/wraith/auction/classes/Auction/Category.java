package com.wraith.auction.classes.Auction;

import com.wraith.auction.database.DataBase;
import com.wraith.auction.exceptions.DataBaseException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Category class
 */
public class Category
{
    /**
     * Class instance
     */
    private static Category instance = null;
    /**
     * Categories search by ID
     */
    private Hashtable<Integer, String> categories = null;
    /**
     * Categories search by name
     */
    private Hashtable<String, Integer> IDs = null;

    /**
     * Constructor
     */
    private Category()
    {
        ResultSet resultSet;
        IDs = new Hashtable<>();
        categories = new Hashtable<>();

        try
        {
            DataBase dataBase = DataBase.getInstance();
            resultSet = dataBase.select("SELECT * FROM `categories` ORDER BY `ID` ASC");


            int ID;
            String category;

            while (resultSet.next())
            {
                ID = resultSet.getInt("ID");
                category = resultSet.getString("Name");

                categories.put(ID, category);
                IDs.put(category, ID);
            }
        }
        catch (SQLException | DataBaseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get class instance or create it
     * @return Instance of class
     */
    public static Category getInstance()
    {
        Category control = instance;

        if(control != null)
            return control;

        synchronized (Category.class)
        {
            if(instance == null)
                instance = new Category();

            return instance;
        }
    }


    /**
     * Get all categories
     * @return all created categories
     */
    public Hashtable<Integer, String> getCategories() { return categories; }

    /**
     * Search for category ID by name
     * @param category Name of category
     * @return category ID
     */
    public int getCategoryID(String category) { return IDs.get(category); }

    /**
     * Delete category from HashMaps
     * @param category Name of category
     */
    public void removeCategory(String category)
    {
        categories.remove(getCategoryID(category));
        IDs.remove(category);
    }

    /**
     * Add category to HashMaps
     * @param ID Category ID
     * @param category Name of category
     */
    public void setCategories(int ID, String category) { categories.put(ID, category); IDs.put(category, ID);}
}
