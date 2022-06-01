package com.wraith.auction.controllers;

import com.wraith.auction.classes.Adapters.NotificationsAdapter;
import com.wraith.auction.classes.User.User;
import com.wraith.auction.database.DataBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;

/**
 * Notification table controller
 */
public class NotificationsController
{
    /**
     * Set data to table
     * @param sql Query to database
     * @param notificationsTable JavaFX table
     * @param colID JavaFX table ID column
     * @param colMessage JavaFX table Message column
     * @param colDate JavaFX table Date column
     */
    public static void updateTable(String sql, TableView<NotificationsAdapter> notificationsTable, TableColumn<NotificationsAdapter, Long> colID, TableColumn<NotificationsAdapter, String> colMessage, TableColumn<NotificationsAdapter, String> colDate)
    {
        colID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        colMessage.setCellValueFactory(new PropertyValueFactory<>("message"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        ObservableList<NotificationsAdapter> observableList = FXCollections.observableArrayList();

        try
        {
            User user = new User();
            User.Notification notification = user.new Notification();

            ResultSet resultSet;
            DataBase dataBase = DataBase.getInstance();

            resultSet = dataBase.select(sql);

            while(resultSet.next())
            {
                notification.setID(resultSet.getInt("ID"));
                notification.setMessage(resultSet.getString("Message"));
                notification.setDate(resultSet.getDate("Date"));

                observableList.add(new NotificationsAdapter(notification));
            }
        }
        catch (Exception exception) { System.out.println(exception.getMessage()); }
        notificationsTable.setItems(observableList);
    }
}
