package com.wraith.auction.controllers.UI.Admin;

import com.wraith.auction.classes.Auction.Category;
import com.wraith.auction.classes.InfoBox.MessageBox;
import com.wraith.auction.database.DataBase;
import com.wraith.auction.exceptions.DataBaseException;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Admin category controller
 */
public class AdminCategoryController extends AdminHomeController
{
    /**
     * JavaFX UI element
     */
    @FXML
    private TextField categoryInput;
    /**
     * JavaFX UI element
     */
    @FXML
    protected TableView<String> categoryTable;
    /**
     * JavaFX UI element
     */
    @FXML
    protected TableColumn<String, String> colName;

    /**
     * Selected item
     */
    private String selectedItem;

    /**
     * Initialization of scene
     */
    @Override
    protected void initialize()
    {
        colName.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue()));

        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(Category.getInstance().getCategories().values());
        categoryTable.setItems(observableList);

        TableView.TableViewSelectionModel<String> selectionModel = categoryTable.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
        selectionModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if(newValue == null)
                return;

            selectedItem = newValue;
        });
    }

    /**
     * Button click action
     */
    @FXML
    private void addClicked()
    {
        try {
            String category = categoryInput.getText();

            if (category.length() == 0 || category.length() > 32) {
                new MessageBox("Error", "Enter correct category name!");
                return;
            }
            String SQL;
            ResultSet resultSet;

            SQL = String.format("SELECT `ID` FROM `categories` WHERE `Name` = '%s' LIMIT 1", category);
            resultSet = dataBase.select(SQL);
            resultSet.next();

            if(resultSet.getRow() != 0)
            {
                new MessageBox("Error", "Category already exists!");
                return;
            }

            Category categoryInstance = Category.getInstance();

            dataBase = DataBase.getInstance();

            SQL = String.format("INSERT INTO `categories`(`Name`) VALUES ('%s')", category);
            dataBase.execute(SQL);

            SQL = String.format("SELECT `ID` FROM `categories` WHERE `Name` = '%s' LIMIT 1", category);
            resultSet = dataBase.select(SQL);

            resultSet.next();

            categoryInstance.setCategories(resultSet.getInt("ID"), category);
            categoryTable.getItems().add(category);

            new MessageBox("SUCCESSFUL", "Category created successfully!");
        }
        catch (SQLException | DataBaseException exception) { new MessageBox("Error", "DataBase error!\n" + exception.getMessage()); }

    }

    /**
     * Delete button clicked
     */
    @FXML
    private void deleteClicked()
    {
        if(selectedItem == null)
            return;

        try
        {
            String SQL;
            Category.getInstance().removeCategory(selectedItem);
            SQL = String.format("DELETE FROM `categories` WHERE `Name` = '%s'", selectedItem);
            dataBase.execute(SQL);

            Platform.runLater(() -> { categoryTable.getItems().remove(selectedItem); selectedItem = null; } );
        }
        catch (Exception e) { System.out.println(e.getMessage()); }
    }
}
