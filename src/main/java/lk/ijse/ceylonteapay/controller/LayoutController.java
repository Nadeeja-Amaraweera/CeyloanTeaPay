
package lk.ijse.ceylonteapay.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
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
    }

    @FXML
    private void clickOtherWork()throws IOException{
        Parent teaFXML = App.loadFXML("OtherWork");
        mainContent.getChildren().setAll(teaFXML);
    }

    @FXML
    private void clickEmployee()throws IOException{
        Parent teaFXML = App.loadFXML("Employee");
        mainContent.getChildren().setAll(teaFXML);
    }

    @FXML
    private void clickAreas()throws IOException{
        Parent teaFXML = App.loadFXML("Land");
        mainContent.getChildren().setAll(teaFXML);
    }

    @FXML
    private void clickFactories()throws IOException{
        Parent teaFXML = App.loadFXML("Factory");
        mainContent.getChildren().setAll(teaFXML);
    }

    @FXML
    private void clickStock()throws IOException{
        Parent teaFXML = App.loadFXML("Stock");
        mainContent.getChildren().setAll(teaFXML);
    }

    @FXML
    private void clickDelivery()throws IOException{
        Parent teaFXML = App.loadFXML("DeliveryTea");
        mainContent.getChildren().setAll(teaFXML);
    }

    
}
