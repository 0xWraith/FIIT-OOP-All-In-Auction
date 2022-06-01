package com.wraith.auction.controllers.UI.Seller;

import com.wraith.auction.classes.Auction.Category;
import com.wraith.auction.classes.Auction.Item;
import com.wraith.auction.classes.InfoBox.MessageBox;
import com.wraith.auction.classes.User.Seller;
import com.wraith.auction.database.DataBase;
import com.wraith.auction.exceptions.DataBaseException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.sql.SQLException;

/**
 * Seller sell item controller
 */

public class SellerSellItemController extends SellerHomeController
{
    /**
     * JavaFX UI element
     */
    @FXML
    private TextField nameInput;
    /**
     * JavaFX UI element
     */
    @FXML
    private TextField descriptionInput;
    /**
     * JavaFX UI element
     */
    @FXML
    private TextField startingPriceInput;
    /**
     * JavaFX UI element
     */
    @FXML
    private ChoiceBox<String> categorySelect;

    /**
     * Category objects
     */
    private Category category;

    /**
     * Scene initialization
     */
    @Override
    protected void initialize()
    {
        category = Category.getInstance();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(category.getCategories().values());
        categorySelect.setItems(observableList);
    }

    /**
     * Sell item button clicked
     */
    @FXML
    public void sellItemClicked()
    {
        try
        {

            String name = nameInput.getText();
            String description = descriptionInput.getText();
            int startingPrice = Integer.parseInt(startingPriceInput.getText());
            int categorySelected = categorySelect.getSelectionModel().getSelectedIndex();

            if(name.length() == 0 || name.length() > 32)
            {
                new MessageBox("Error", "Enter correct name!");
                return;
            }
            if(description.length() == 0 || description.length() > 128)
            {
                new MessageBox("Error", "Enter correct name!");
                return;
            }
            if(startingPrice < 0)
            {
                new MessageBox("Error", "Enter correct price!");
                return;
            }
            if(categorySelected == -1)
            {
                new MessageBox("Error", "Select item category!");
                return;
            }

            Item item = new Item();

            item.setName(name);
            item.setDescription(description);
            item.setStartingPrice(startingPrice);
            item.setOwnerID(user.getID());
            item.setCategory(category.getCategoryID(categorySelect.getSelectionModel().getSelectedItem()));

            ((Seller)user).sellItem(item);
        }
        catch (NumberFormatException exception) { new MessageBox("Error", "Enter correct starting price!"); }
    }
}
