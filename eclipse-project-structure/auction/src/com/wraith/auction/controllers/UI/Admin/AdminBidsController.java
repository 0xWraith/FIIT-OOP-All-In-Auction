package com.wraith.auction.controllers.UI.Admin;

import com.wraith.auction.classes.Adapters.ItemAdapter;
import com.wraith.auction.classes.InfoBox.Dialog;
import com.wraith.auction.controllers.HistoryController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;

/**
 * Admin bids controller
 */
public class AdminBidsController  extends AdminHomeController
{
    /**
     * JavaFX UI element
     */
    @FXML
    private TableView<ItemAdapter> bidsTable;
    /**
     * JavaFX UI element
     */
    @FXML
    private TableColumn<ItemAdapter, Long> colID;
    /**
     * JavaFX UI element
     */
    @FXML
    private TableColumn<ItemAdapter, String> colName;
    /**
     * JavaFX UI element
     */
    @FXML
    private TableColumn<ItemAdapter, String> colDescription;
    /**
     * JavaFX UI element
     */
    @FXML
    private TableColumn<ItemAdapter, Long> colStartPrice;
    /**
     * JavaFX UI element
     */
    @FXML
    private TableColumn<ItemAdapter, Long> colEndPrice;
    /**
     * JavaFX UI element
     */
    @FXML
    private TableColumn<ItemAdapter, String> colOwner;
    /**
     * JavaFX UI element
     */
    @FXML
    private Button selectButton;

    /**
     * Selected item
     */
    private ItemAdapter selectedItem;

    /**
     * Initialization of UI
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

        HistoryController.updateTable("SELECT items.*, accounts.FirstName, accounts.LastName FROM items INNER JOIN accounts ON items.ownerID = accounts.ID WHERE `Accepted` = 0",
                false, bidsTable, colID, colName, colDescription, colStartPrice, colEndPrice, colOwner);

    }

    /**
     * Response handler from Dialog
     * @param response True/False
     */
    public void response(boolean response)
    {
        try
        {
            String SQL;
            String message;
            Long ID = selectedItem.IDProperty().getValue();

            if(!response)
            {
                message = String.format("Administrator %s deleted your item %s(%s) from sell", user.getFirstName(), selectedItem.nameProperty().getValue(), selectedItem.categoryProperty().getValue());
                SQL = String.format("DELETE FROM `items` WHERE `ID` = %d LIMIT 1", ID);
            }
            else
            {
                message = String.format("Administrator %s accepted your item %s(%s) to sell", user.getFirstName(), selectedItem.nameProperty().getValue(), selectedItem.categoryProperty().getValue());
                SQL = String.format("UPDATE `items` SET `Accepted` = 1 WHERE `ID` = %d LIMIT 1", ID);
            }

            user.sendNotification(selectedItem.ownerIDProperty().getValue(), message);
            dataBase.execute(SQL);
            Platform.runLater(() -> { bidsTable.getItems().remove(selectedItem); selectedItem = null; } );
        }
        catch (Exception e) { System.out.println(e.getMessage()); }
    }

    /**
     * Handler for choosen item
     */
    @FXML
    private void selectItem()
    {

        if(selectedItem == null)
            return;

        try {
            new Dialog(AdminBidsController.this, "Action", "Select action which\nyou want to do:", "ACCEPT", "DELETE");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
