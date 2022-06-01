package com.wraith.auction.controllers.UI.Bidder;

import com.wraith.auction.classes.Adapters.ItemAdapter;
import com.wraith.auction.classes.Auction.Item;
import com.wraith.auction.classes.InfoBox.MessageBox;
import com.wraith.auction.classes.User.Bidder;
import com.wraith.auction.controllers.HistoryController;
import com.wraith.auction.controllers.UtilsController;
import com.wraith.auction.exceptions.DataBaseException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Bids list controller
 */
public class BidderBidsListController extends BidderHomeController
{
    /**
     * JavaFX UI element
     */
    @FXML
    protected TableView<ItemAdapter> bidsTable;
    /**
     * JavaFX UI element
     */
    @FXML
    protected TableColumn<ItemAdapter, Long> colID;
    /**
     * JavaFX UI element
     */
    @FXML
    protected TableColumn<ItemAdapter, String> colName;
    /**
     * JavaFX UI element
     */
    @FXML
    protected TableColumn<ItemAdapter, String> colDescription;
    /**
     * JavaFX UI element
     */
    @FXML
    protected TableColumn<ItemAdapter, Long> colStartPrice;
    /**
     * JavaFX UI element
     */
    @FXML
    protected TableColumn<ItemAdapter, Long> colEndPrice;
    /**
     * JavaFX UI element
     */
    @FXML
    protected TableColumn<ItemAdapter, String> colOwner;
    /**
     * JavaFX UI element
     */
    @FXML
    protected Button selectButton;

    /**
     * Selected item object
     */
    private ItemAdapter selectedItem;

    /**
     * Scene initialization
     */
    @Override
    protected void initialize()
    {

        TableView.TableViewSelectionModel<ItemAdapter> selectionModel = bidsTable.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
        selectionModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if(newValue == null)
                return;

            selectedItem = newValue;
        });

        HistoryController.updateTable("SELECT items.*, accounts.FirstName, accounts.LastName FROM items INNER JOIN accounts ON items.ownerID = accounts.ID WHERE `Accepted` = 1 AND Started = 0",
                false, bidsTable, colID, colName, colDescription, colStartPrice, colEndPrice, colOwner);

    }

    /**
     * Selected item listener
     */
    @FXML
    private void selectItem()
    {
        if(((Bidder)user).participateIn(selectedItem))
            selectButton.setVisible(false);
    }
}
