package com.wraith.auction.classes.InfoBox;

import com.wraith.auction.Main;
import com.wraith.auction.controllers.SceneController;
import com.wraith.auction.controllers.UI.InfoBox.MessageBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

/**
 * Custom MessageBox class
 */
public class MessageBox
{
    /**
     * @param header MessageBox header text
     * @param info MessageBox information text
     */
    public MessageBox(String header, String info)
    {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(SceneController.ECLIPSE_RESOURCE_PATH + "InfoBox/MessageBox.fxml"));
            Parent parent = fxmlLoader.load();

            MessageBoxController messageBoxController = fxmlLoader.getController();
            messageBoxController.updateUI(header.toUpperCase(Locale.ROOT), info);

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
        catch (IOException e) { e.printStackTrace(); }
    }
}
