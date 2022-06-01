package com.wraith.auction.controllers.UI.InfoBox;

import com.wraith.auction.controllers.UI.Admin.AdminBidsController;
import com.wraith.auction.controllers.UI.Admin.AdminUserController;
import com.wraith.auction.controllers.UI.Bidder.BidderHomeController;
import com.wraith.auction.controllers.UI.Seller.SellerHomeController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Controller of DialogBox
 */
public class DialogController
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
     * JavaFX UI element
     */
    @FXML
    private Button leftButton;
    /**
     * JavaFX UI element
     */
    @FXML
    private Button rightButton;

    /**
     * Object of class to send response
     */
    private Object classForResponse;

    /**
     * Send True response
     * @param event JavaFX argument
     */
    @FXML
    private void responseTrue(ActionEvent event) { closeDialog(event, true); }

    /**
     * Send False response
     * @param event JavaFX argument
     */
    @FXML
    private void responseFalse(ActionEvent event) { closeDialog(event, false); }

    /**
     * Close dialog and send response
     * @param event JavaFX argument
     * @param response Response
     */
    private void closeDialog(ActionEvent event, boolean response)
    {
        if(classForResponse instanceof AdminBidsController)
            ((AdminBidsController) classForResponse).response(response);

        else if(classForResponse instanceof SellerHomeController)
            ((SellerHomeController) classForResponse).response(response);

        else if(classForResponse instanceof AdminUserController)
            ((AdminUserController) classForResponse).response(response);

        else if(classForResponse instanceof BidderHomeController)
            ((BidderHomeController) classForResponse).response(response);

        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    /**
     * Update dialog UI
     * @param classForResponse Class to send response
     * @param header Header of dialog
     * @param info Information text of dialog
     * @param leftButton Left button text
     * @param rightButton Right button text
     */
    public void updateUI(Object classForResponse, String header, String info, String leftButton, String rightButton)
    {
        this.classForResponse = classForResponse;
        headerLabel.setText(header);
        smallInfoLabel.setText(info);
        this.leftButton.setText(leftButton);
        this.rightButton.setText(rightButton);
    }

    /**
     * Close dialog without response
     * @param event JavaFX argument
     */
    @FXML
    private void close(ActionEvent event)
    {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
