package com.wraith.auction.classes.InfoBox;

import com.wraith.auction.Main;
import com.wraith.auction.controllers.SceneController;
import com.wraith.auction.controllers.UI.InfoBox.DialogController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

/**
 * Custom dialog class
 */
public class Dialog
{
    /**
     * Constructor
     * @param classForResponse Class to send 0/1 response
     * @param header Header of dialog
     * @param info Information text of dialog
     * @param leftButton Text of left button
     * @param rightButton Text of right button
     * @throws IOException When there is an error while creating dialog
     */
    public Dialog(Object classForResponse, String header, String info, String leftButton, String rightButton) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(SceneController.ECLIPSE_RESOURCE_PATH + "InfoBox/dialog.fxml"));
        Parent parent = fxmlLoader.load();
        DialogController dialogController = fxmlLoader.getController();
        dialogController.updateUI(classForResponse, header, info, leftButton, rightButton);

        Scene scene = new Scene(parent, 500, 250);
        Stage stage = new Stage();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(Objects.requireNonNull(Main.class.getResource(SceneController.ECLIPSE_RESOURCE_PATH + "Styles/buttons.css")).toExternalForm());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.showAndWait();
    }
}
