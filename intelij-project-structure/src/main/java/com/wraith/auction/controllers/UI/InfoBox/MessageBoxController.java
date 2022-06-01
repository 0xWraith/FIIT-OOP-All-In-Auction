package com.wraith.auction.controllers.UI.InfoBox;

import com.wraith.auction.controllers.UI.Admin.AdminBidsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Controller of MessageBox
 */
public class MessageBoxController
{

    /**
     * JavaFX UI element
     */
    @FXML
    private Label headerLabel;
    /**
     * JavaFX UI element
     */
    @FXML
    private Label smallInfoLabel;

    /**
     * Close MessageBox
     * @param event JavaFX argument
     */
    @FXML
    private void responseTrue(ActionEvent event) { closeDialog(event); }

    /**
     * Close MessageBox
     * @param event JavaFX argument
     */
    private void closeDialog(ActionEvent event)
    {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    /**
     * Update MessageBox UI
     * @param header Header text
     * @param info Information text
     */
    public void updateUI(String header, String info)
    {
        headerLabel.setText(header);
        smallInfoLabel.setText(info);
    }
}
