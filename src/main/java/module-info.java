module lk.ijse.ceylonteapay {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    requires java.desktop;
    requires javafx.base;
    requires javafx.graphics;
    requires jakarta.mail;
//    requires lk.ijse.ceylonteapay;
//    requires lk.ijse.ceylonteapay;
//    requires lk.ijse.ceylonteapay;
//    requires lk.ijse.ceylonteapay;
//    requires lk.ijse.ceylonteapay;
//    requires lk.ijse.ceylonteapay;

    opens lk.ijse.ceylonteapay to javafx.fxml;
    exports lk.ijse.ceylonteapay;
    exports lk.ijse.ceylonteapay.db;
    opens lk.ijse.ceylonteapay.db to javafx.fxml;
    exports lk.ijse.ceylonteapay.controller;
    opens lk.ijse.ceylonteapay.controller to javafx.fxml;
    exports lk.ijse.ceylonteapay.model;
    opens lk.ijse.ceylonteapay.model to javafx.fxml;
    exports lk.ijse.ceylonteapay.dto;
    opens lk.ijse.ceylonteapay.dto to javafx.fxml;
}
