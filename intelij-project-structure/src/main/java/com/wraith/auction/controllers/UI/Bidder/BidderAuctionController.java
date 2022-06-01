package com.wraith.auction.controllers.UI.Bidder;

import com.wraith.auction.Main;
import com.wraith.auction.classes.Adapters.UserAdapter;
import com.wraith.auction.classes.Auction.Item;
import com.wraith.auction.classes.InfoBox.MessageBox;
import com.wraith.auction.classes.User.User;
import com.wraith.auction.controllers.UtilsController;
import com.wraith.auction.exceptions.DataBaseException;
import com.wraith.auction.exceptions.UserExceptions;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Auction controller
 */
public class BidderAuctionController extends BidderHomeController
{
    /**
     * JavaFX UI element
     */
    @FXML
    private Label timerLabel;
    /**
     * JavaFX UI element
     */
    @FXML
    private Label startingPriceLabel;
    /**
     * JavaFX UI element
     */
    @FXML
    private Label itemLabel;
    /**
     * JavaFX UI element
     */
    @FXML
    private TextField userBidInput;
    /**
     * JavaFX UI element
     */
    @FXML
    private Label lastBidAmount;
    /**
     * JavaFX UI element
     */
    @FXML
    private Label lastBidderLabel;
    /**
     * JavaFX UI element
     */
    @FXML
    private Label balanceLabel;
    /**
     * JavaFX UI element
     */
    @FXML
    private TableView<UserAdapter> participantsTable;
    /**
     * JavaFX UI element
     */
    @FXML
    private TableColumn<UserAdapter, Long> colID;
    /**
     * JavaFX UI element
     */
    @FXML
    private TableColumn<UserAdapter, String> colName;
    /**
     * JavaFX UI element
     */
    @FXML
    protected Button bidButton;

    /**
     * Item on auction
     */
    private Item item;
    /**
     * Auction timer
     */
    private Timer timer;
    /**
     * Last bidder object
     */
    private User lastBidder;
    /**
     * Last bid
     */
    private long lastBidSum;
    /**
     * Timer counter
     */
    private int timerCounter;

    /**
     * Bot's in use
     */
    private User[] botsTable;

    /**
     * Scene initialization
     */
    @Override
    protected void initialize()
    {


        try { item = (Item) UtilsController.deserializeObject("itemToParticipate"); }
        catch (IOException | ClassNotFoundException e) { e.printStackTrace(); }
        botsTable = new User[getBotsAmount()];

        for(int i = 0; i < botsTable.length; i++)
        {
            ResultSet resultSet;
            String SQL = String.format("SELECT `ID`, `FirstName`, `LastName` FROM `accounts` WHERE `Login` = 'Bot_%s' LIMIT 1", Main.botNames[i]);

            try {
                resultSet = dataBase.select(SQL);
                resultSet.last();

                if(resultSet.getRow() == 0)
                    continue;

                User bot = new User();
                bot.setID(resultSet.getInt("ID"));
                bot.setFirstName(resultSet.getString("FirstName"));
                bot.setLastName(resultSet.getString("LastName"));

                botsTable[i] = bot;

            } catch (SQLException | DataBaseException | UserExceptions e) {
                e.printStackTrace();
            }

        }

        colID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));

        ObservableList<UserAdapter> observableList = FXCollections.observableArrayList();

        observableList.add(new UserAdapter(user));

        for (User bot: botsTable)
            observableList.add(new UserAdapter(bot));

        lastBidSum = item.getStartingPrice();

        item.setCurrentPrice(lastBidSum);

        itemLabel.setText(item.getName());
        balanceLabel.setText("$" + user.getBalance());
        userBidInput.setText(String.valueOf(item.getStartingPrice() + 2500));
        startingPriceLabel.setText("$" + item.getStartingPrice());

        participantsTable.setItems(observableList);

        randomBotMakeABid();

    }

    /**
     * User make a bid
     */
    @FXML
    private void makeBid()
    {

        try
        {
            long bidSum = Long.parseLong(userBidInput.getText());

            if(bidSum < (item.getCurrentPrice() + 2500))
            {
                new MessageBox("Error", "The bid must be more than the starting price by $2,500.");
                return;
            }

            if(bidSum > user.getBalance())
            {
                new MessageBox("Error", "You don't have enough money to make a bid.");
                return;
            }
            lastBidder = user;
            lastBidSum = bidSum;

            updateAuctionUI();

        }
        catch (NumberFormatException exception) { new MessageBox("Error", "Enter correct amount!"); }
    }

    /**
     * Used to set active bots in a bid
     * @return Bot's amount
     */
    private int getBotsAmount()
    {
        Random random = new Random();
        return random.nextInt(5 - 1) + 1;
    }

    /**
     * Get IDX of a random bot
     * @return Random bot IDX
     */
    private int getRandomBot()
    {
        Random random = new Random();
        return random.nextInt(botsTable.length);
    }

    /**
     * Generate random bid sum for user
     * @return Bid amount
     */
    private long getRandomBitSum()
    {
        Random random = new Random();
        return random.nextLong( (lastBidSum + 10000) - (lastBidSum + 2500)) + (lastBidSum + 2500);
    }

    /**
     * Get time when bot will place a bid
     * @return Bot to make a bid
     */
    private int botWillMakeABid()
    {
        Random random = new Random();
        return random.nextInt(4);
    }

    /**
     * Controll, will bot place a bid when actual user made a bid before him
     * @return 0/1
     */
    private int botWillBid()
    {
        Random random = new Random();
        return random.nextInt(2);
    }

    /**
     * Update UI
     */
    private void updateAuctionUI()
    {
        item.setCurrentPrice(lastBidSum);
        lastBidAmount.setText(lastBidSum + "$");
        timerLabel.setText(5 + " SEC");
        userBidInput.setText(String.valueOf(lastBidSum + 2500));
        lastBidderLabel.setText(lastBidder.getFirstName() + " " + lastBidder.getLastName());

        startBidTimer();

    }

    /**
     * To place a bid by bot
     */
    private void randomBotMakeABid()
    {
        lastBidder = botsTable[getRandomBot()];
        lastBidSum = getRandomBitSum();

        updateAuctionUI();
    }

    /**
     * Bid timer
     */
    private void startBidTimer()
    {
        int botHandler = botWillMakeABid();
        timerCounter = 5;

        if(timer != null)
            timer.cancel();

        timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run()
            {
                Platform.runLater(() -> {

                    timerLabel.setText((--timerCounter) + " SEC");

                    if (timerCounter == 0)
                    {
                        timer.cancel();
                        timer = null;
                        getAuctionResult();

                        return;
                    }

                    if(botHandler == timerCounter)
                    {
                        if(lastBidder == user && botWillBid() == 0)
                            return;

                        timer.cancel();
                        timer = null;
                        randomBotMakeABid();
                    }
                });

            }
        }, 1000, 1000);
    }

    /**
     * Get result of an auction
     */
    private void getAuctionResult()
    {
        if(lastBidder == user)
        {
            user.setBalance(user.getBalance() - lastBidSum);
            user.save("UserClass");
        }

        timerLabel.setVisible(false);

        String SQL;

        try
        {
            SQL = String.format("UPDATE `items` SET `currentPrice` = %d, `newOwnerID` = %d WHERE `ID` = %d LIMIT 1", lastBidSum, lastBidder.getID(), item.getID());
            dataBase.execute(SQL);

            SQL = String.format("UPDATE `accounts` SET `Balance` = `Balance` + %d WHERE `ID` = %d LIMIT 1", lastBidSum, item.getOwnerID());
            dataBase.execute(SQL);

            SQL = String.format("UPDATE `accounts` SET `Balance` = %d WHERE `ID` = %d LIMIT 1", user.getBalance(), user.getID());
            dataBase.execute(SQL);
        }
        catch (SQLException | DataBaseException e) { e.printStackTrace(); }

        lastBidder.sendNotification(item.getOwnerID(), String.format("Your item %s(#%d) have been sold at auction for $%d.", item.getName(), item.getID(), lastBidSum));
        new MessageBox("THE END", "Auction ended.\nWinner: " + lastBidder.getFirstName() + " " + lastBidder.getLastName());
        try { sceneController.activate("bidder-home"); }
        catch (IOException e) { e.printStackTrace(); }
    }
}
