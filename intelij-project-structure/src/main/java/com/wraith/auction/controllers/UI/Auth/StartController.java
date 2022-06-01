package com.wraith.auction.controllers.UI.Auth;

import com.wraith.auction.Main;
import com.wraith.auction.controllers.SceneController;
import javafx.fxml.FXML;

import java.io.IOException;

/**
 * Start menu controller
 */
public class StartController
{
    /**
     * SceneController object
     */
    private SceneController sceneController;

    /**
     * Menu listener
     * @throws IOException SceneController issues
     */
    @FXML
    protected void onLoginButtonClick() throws IOException
    {
        sceneController = Main.getSceneController();
        sceneController.activate("authorization");
    }

    /**
     * Menu listener
     * @throws IOException SceneController issues
     */
    @FXML
    protected void onRegButtonClick() throws IOException
    {
        sceneController = Main.getSceneController();
        sceneController.activate("registration");
    }
}
