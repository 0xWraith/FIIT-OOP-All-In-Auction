package com.wraith.auction.controllers.UI.Bidder;

import com.wraith.auction.classes.Adapters.ItemAdapter;
import com.wraith.auction.controllers.HistoryController;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Bidder history controller
 */
public class BidderHistoryController extends BidderHomeController
{
    /**
     * JavaFX UI element
     */
    @FXML
    protected TableView<ItemAdapter> historyTable;
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
     * Scene initialization
     */
    @Override
    protected void initialize()
    {
        String sql = String.format("SELECT items.*, accounts.FirstName, accounts.LastName FROM items INNER JOIN accounts ON items.ownerID = accounts.ID WHERE items.newOwnerID = %d", user.getID());
        HistoryController.updateTable(sql, true, historyTable, colID, colName, colDescription, colStartPrice, colEndPrice, colOwner);
    }
}
