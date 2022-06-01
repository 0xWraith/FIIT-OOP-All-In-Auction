package com.wraith.auction;

import com.wraith.auction.controllers.SceneController;
import com.wraith.auction.database.DataBase;
import com.wraith.auction.exceptions.DataBaseException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.Objects;

/**
 * Main class
 */
public class Main extends Application
{
    /**
     * Bots name
     */
    public static String[] botNames = new String[] {
            "Matthew Shmidt",
            "Thomas Mraz",
            "Alona Svec",
            "Marek Sawa",
            "Beata Spitalova"
    };
    /**
     * SceneController object
     */
    private static SceneController sceneController;
    /**
     * Entry point to JavaFX
     * @param stage JavaFX argument
     */
    @Override
    public void start(Stage stage)
    {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(SceneController.ECLIPSE_RESOURCE_PATH + "start.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 500);

            sceneController = new SceneController(stage);

            sceneController.addScreen("start", "start.fxml");
            sceneController.addScreen("registration", "Login/registration.fxml");
            sceneController.addScreen("authorization", "Login/authorization.fxml");


            sceneController.addScreen("admin-home", "User/Admin/Home.fxml");
            sceneController.addScreen("admin-bids", "User/Admin/Bids.fxml");
            sceneController.addScreen("admin-users", "User/Admin/Users.fxml");
            sceneController.addScreen("admin-user", "User/Admin/User.fxml");
            sceneController.addScreen("admin-history", "User/Admin/History.fxml");
            sceneController.addScreen("admin-categories", "User/Admin/Category.fxml");

            sceneController.addScreen("bidder-home", "User/Bidder/Home.fxml");
            sceneController.addScreen("bidder-bids", "User/Bidder/Bids.fxml");
            sceneController.addScreen("bidder-history", "User/Bidder/History.fxml");
            sceneController.addScreen("bidder-notifications", "User/Bidder/Notifications.fxml");
            sceneController.addScreen("bidder-update-balance", "User/Bidder/Balance.fxml");
            sceneController.addScreen("bidder-active-auction", "User/Bidder/Auction.fxml");

            sceneController.addScreen("seller-home", "User/Seller/Home.fxml");
            sceneController.addScreen("seller-sell-item", "User/Seller/SellItem.fxml");
            sceneController.addScreen("seller-history", "User/Seller/History.fxml");
            sceneController.addScreen("seller-notifications", "User/Seller/Notifications.fxml");
            sceneController.addScreen("seller-withdrawal", "User/Seller/Withdrawal.fxml");

            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.setTitle("All In Auction");

            scene.getStylesheets().clear();
            scene.getStylesheets().add(Objects.requireNonNull(Main.class.getResource(SceneController.ECLIPSE_RESOURCE_PATH + "Styles/buttons.css")).toExternalForm());
            scene.getStylesheets().add(Objects.requireNonNull(Main.class.getResource(SceneController.ECLIPSE_RESOURCE_PATH + "Styles/choicebox.css")).toExternalForm());
            scene.getStylesheets().add(Objects.requireNonNull(Main.class.getResource(SceneController.ECLIPSE_RESOURCE_PATH + "Styles/input.css")).toExternalForm());
            scene.getStylesheets().add(Objects.requireNonNull(Main.class.getResource(SceneController.ECLIPSE_RESOURCE_PATH + "Styles/menu-text.css")).toExternalForm());
            scene.getStylesheets().add(Objects.requireNonNull(Main.class.getResource(SceneController.ECLIPSE_RESOURCE_PATH + "Styles/table-admin.css")).toExternalForm());
            scene.getStylesheets().add(Objects.requireNonNull(Main.class.getResource(SceneController.ECLIPSE_RESOURCE_PATH + "Styles/table-bidder.css")).toExternalForm());
            scene.getStylesheets().add(Objects.requireNonNull(Main.class.getResource(SceneController.ECLIPSE_RESOURCE_PATH + "Styles/table-seller.css")).toExternalForm());

            stage.show();

            Runnable runnable = () -> { checkAuctionBots(); };
            Thread thread = new Thread(runnable, "botsSync");
            thread.start();
        }
        catch(Exception ex){ System.out.println(ex.getMessage());}
    }

    /**
     * Return SceneController object
     * @return SceneController object
     */
    public static SceneController getSceneController() { return sceneController; }
    /**
     * Entry point to program
     * @param args Program arguments
     */
    public static void main(String[] args)
    {
        DataBase dataBase = DataBase.getInstance();

        try { dataBase.setConnection(); }
        catch (DataBaseException exception) { System.out.println(exception.getMessage()); }

        launch();
    }
    /**
     * Check if auction bot created
     */
    private void checkAuctionBots()
    {
        String SQL;
        DataBase dataBase;
        ResultSet resultSet;

        dataBase = DataBase.getInstance();
        SQL = String.format("SELECT `ID` FROM `accounts` WHERE `Login` = 'Bot_%s' LIMIT 1", botNames[0]);

        try
        {
            resultSet = dataBase.select(SQL);

            if (resultSet.last())
                return;

            for (String name : botNames)
            {
                SQL = String.format("INSERT INTO `accounts`(`Login`, `FirstName`, `LastName`, `Mail`, `Hash`, `Salt`, `Balance`, `Admin`, `Sex`, `AccountType`) VALUES ('Bot_%s', '%s', '%s', 'bot@gmail.com', 'botHadaDWDwadsh', 'fawfawad', 11101, 0, 0, 1)", name, name.split(" ")[0], name.split(" ")[1]);
                dataBase.execute(SQL);
            }
        }
        catch (Exception exception) { System.out.println(exception.getMessage()); }
    }
}