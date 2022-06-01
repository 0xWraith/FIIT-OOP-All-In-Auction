package com.wraith.auction.controllers.UI.Admin;

import com.wraith.auction.classes.Adapters.ItemAdapter;
import com.wraith.auction.controllers.HistoryController;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Admin bids history controller
 */
public class AdminHistoryController extends AdminHomeController
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
     * Initialization of scene
     */
    @Override
    protected void initialize()
    {
        HistoryController.updateTable("SELECT items.*, accounts.FirstName, accounts.LastName FROM items INNER JOIN accounts ON items.newOwnerID = accounts.ID WHERE Accepted = 1 AND newOwnerID != -1",
                true, historyTable, colID, colName, colDescription, colStartPrice, colEndPrice, colOwner);
    }
}
