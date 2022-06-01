package com.wraith.auction.controllers;

import com.wraith.auction.classes.Auction.Item;
import com.wraith.auction.classes.Adapters.ItemAdapter;
import com.wraith.auction.classes.User.User;
import com.wraith.auction.database.DataBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;

/**
 * History of bids table controller
 */
public class HistoryController
{
    /**
     * @param sql Query to database
     * @param history Admin or Seller/Bidder table
     * @param historyTable JavaFX table ID column
     * @param colID JavaFX table ID column
     * @param colName JavaFX table Name column
     * @param colDescription JavaFX table Description column
     * @param colStartPrice JavaFX table Starting Price column
     * @param colEndPrice JavaFX table Final Price column
     * @param colOwner JavaFX table Owner column
     */
    public static void updateTable(String sql, boolean history, TableView<ItemAdapter> historyTable, TableColumn<ItemAdapter, Long> colID, TableColumn<ItemAdapter, String> colName, TableColumn<ItemAdapter, String> colDescription, TableColumn<ItemAdapter, Long> colStartPrice, TableColumn<ItemAdapter, Long> colEndPrice, TableColumn<ItemAdapter, String> colOwner)
    {
        colID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colStartPrice.setCellValueFactory(new PropertyValueFactory<>("startingPrice"));

        if(history)
            colEndPrice.setCellValueFactory(new PropertyValueFactory<>("currentPrice"));
        else
            colEndPrice.setCellValueFactory(new PropertyValueFactory<>("category"));

        colOwner.setCellValueFactory(new PropertyValueFactory<>("newOwnerName"));

        ObservableList<ItemAdapter> observableList = FXCollections.observableArrayList();

        try
        {
            Item item;
            ResultSet resultSet;
            DataBase dataBase = DataBase.getInstance();

            resultSet = dataBase.select(sql);

            while(resultSet.next())
            {
                item = new Item();
                item.setID(resultSet.getInt("ID"));
                item.setName(resultSet.getString("Name"));
                item.setDescription(resultSet.getString("Description"));
                item.setStartingPrice(resultSet.getInt("startingPrice"));
                item.setCurrentPrice(resultSet.getInt("currentPrice"));
                item.setOwnerID(resultSet.getInt("ownerID"));
                item.setCategory(resultSet.getInt("Category"));
                item.setNewOwnerName(User.getFullName(resultSet.getString("FirstName"), resultSet.getString("LastName")));

                observableList.add(new ItemAdapter(item));
            }
        }
        catch (Exception exception) { System.out.println(exception.getMessage()); }
        historyTable.setItems(observableList);
    }
}
