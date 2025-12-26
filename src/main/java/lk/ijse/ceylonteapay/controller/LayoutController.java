
package lk.ijse.ceylonteapay.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.ceylonteapay.App;

public class LayoutController implements Initializable {

    @FXML
    private AnchorPane mainContent;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void clickTeaPlucking()throws IOException {
        Parent teaFXML = App.loadFXML("DailyTea");
        mainContent.getChildren().setAll(teaFXML);

        // Fade-in transition
        teaFXML.setOpacity(0); // start invisible
        FadeTransition fade = new FadeTransition(Duration.seconds(0.3), teaFXML);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    @FXML
    private void clickOtherWork()throws IOException{
        Parent teaFXML = App.loadFXML("OtherWork");
        mainContent.getChildren().setAll(teaFXML);

        // Fade-in transition
        teaFXML.setOpacity(0); // start invisible
        FadeTransition fade = new FadeTransition(Duration.seconds(0.3), teaFXML);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    @FXML
    private void clickEmployee()throws IOException{
        Parent teaFXML = App.loadFXML("Employee");
        mainContent.getChildren().setAll(teaFXML);

        // Fade-in transition
        teaFXML.setOpacity(0); // start invisible
        FadeTransition fade = new FadeTransition(Duration.seconds(0.3), teaFXML);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    @FXML
    private void clickAreas()throws IOException{
        Parent teaFXML = App.loadFXML("Land");
        mainContent.getChildren().setAll(teaFXML);

        // Fade-in transition
        teaFXML.setOpacity(0); // start invisible
        FadeTransition fade = new FadeTransition(Duration.seconds(0.3), teaFXML);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    @FXML
    private void clickFactories()throws IOException{
        Parent teaFXML = App.loadFXML("Factory");
        mainContent.getChildren().setAll(teaFXML);

        // Fade-in transition
        teaFXML.setOpacity(0); // start invisible
        FadeTransition fade = new FadeTransition(Duration.seconds(0.3), teaFXML);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    @FXML
    private void clickStock()throws IOException{
        Parent teaFXML = App.loadFXML("Stock");
        mainContent.getChildren().setAll(teaFXML);

        // Fade-in transition
        teaFXML.setOpacity(0); // start invisible
        FadeTransition fade = new FadeTransition(Duration.seconds(0.3), teaFXML);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    @FXML
    private void clickDelivery()throws IOException{
        Parent teaFXML = App.loadFXML("DeliveryTea");
        mainContent.getChildren().setAll(teaFXML);

        // Fade-in transition
        teaFXML.setOpacity(0); // start invisible
        FadeTransition fade = new FadeTransition(Duration.seconds(0.3), teaFXML);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    @FXML
    private void clickIncome()throws IOException{
        Parent teaFXML = App.loadFXML("Icome");
        mainContent.getChildren().setAll(teaFXML);

    }

    @FXML
    private void clickPayment()throws IOException{
        Parent teaFXML = App.loadFXML("Payment");
        mainContent.getChildren().setAll(teaFXML);
    }



    
}
