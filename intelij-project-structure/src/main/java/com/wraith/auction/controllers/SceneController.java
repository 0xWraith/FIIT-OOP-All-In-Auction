package com.wraith.auction.controllers;

import com.wraith.auction.Main;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Objects;

/**
 * Scene controller
 */
public class SceneController
{
    /**
     * Main stage
     */
    private final Stage main;
    /**
     * Using resource path of Inteliji or Eclipse
     */
    private final static boolean PATH_STATE = false;
    /**
     * Resource path
     */
    public static final String ECLIPSE_RESOURCE_PATH = (PATH_STATE ? "resources/" : "");
    /**
     * All added stages
     */
    Hashtable<String, String> hashTable = new Hashtable<>();

    /**
     * Constructor
     * @param main Main stage
     */
    public SceneController(Stage main) { this.main = main; }

    /**
     * Add scene to SceneController
     * @param name Name of scene
     * @param source Path to scene
     */
    public void addScreen(String name, String source) { hashTable.put(name, source); }

    /**
     * Activate scene
     * @param name Scene name
     * @throws IOException Error of opeping scene
     */
    public void activate(String name) throws IOException
    {
        if(hashTable.containsKey(name))
            main.getScene().setRoot(FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(String.format("%s%s", ECLIPSE_RESOURCE_PATH, hashTable.get(name))))));
    }

}
