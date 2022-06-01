package com.wraith.auction.controllers.UI.Seller;

import com.wraith.auction.classes.Adapters.NotificationsAdapter;
import com.wraith.auction.controllers.NotificationsController;
import com.wraith.auction.database.DataBase;
import com.wraith.auction.exceptions.DataBaseException;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.SQLException;

/**
 * Seller notifications controller
 */
public class SellerNotificationsController extends SellerHomeController
{
    /**
     * JavaFX UI element
     */
    @FXML
    protected TableView<NotificationsAdapter> notificationsTable;
    /**
     * JavaFX UI element
     */
    @FXML
    protected TableColumn<NotificationsAdapter, Long> colID;
    /**
     * JavaFX UI element
     */
    @FXML
    protected TableColumn<NotificationsAdapter, String> colMessage;
    /**
     * JavaFX UI element
     */
    @FXML
    protected TableColumn<NotificationsAdapter, String> colDate;

    /**
     * Scene initialization
     */
    @Override
    protected void initialize()
    {
        String SQL = String.format("SELECT * FROM `notifications` WHERE `ownerID` = %d AND `Read` = 0", user.getID());
        NotificationsController.updateTable(SQL, notificationsTable, colID, colMessage, colDate);
        SQL = String.format("UPDATE `notifications` SET `Read` = 1 WHERE `ownerID` = %d", user.getID());

        try { DataBase.getInstance().execute(SQL); }
        catch (SQLException | DataBaseException e) { e.printStackTrace(); }
    }
}
