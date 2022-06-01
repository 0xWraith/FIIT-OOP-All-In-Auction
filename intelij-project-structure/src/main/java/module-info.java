module com.wraith.auction
{
    
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.wraith.auction to javafx.fxml;
    exports com.wraith.auction;
    exports com.wraith.auction.controllers;
    exports com.wraith.auction.classes.Auction;
    exports com.wraith.auction.classes.User;
    exports com.wraith.auction.interfaces;
    exports com.wraith.auction.database;
    exports com.wraith.auction.classes.Adapters;
    opens com.wraith.auction.interfaces to javafx.fxml;
    opens com.wraith.auction.controllers to javafx.fxml;
    exports com.wraith.auction.controllers.UI.Admin;
    opens com.wraith.auction.controllers.UI.Admin to javafx.fxml;
    opens com.wraith.auction.controllers.UI.InfoBox to javafx.fxml;
    exports com.wraith.auction.controllers.UI.Bidder;
    opens com.wraith.auction.controllers.UI.Bidder to javafx.fxml;
    exports com.wraith.auction.controllers.UI.Seller;
    opens com.wraith.auction.controllers.UI.Seller to javafx.fxml;
    exports com.wraith.auction.controllers.UI.Auth;
    opens com.wraith.auction.controllers.UI.Auth to javafx.fxml;
}