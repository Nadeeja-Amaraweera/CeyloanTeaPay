/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package lk.ijse.ceylonteapay.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import lk.ijse.ceylonteapay.App;

/**
 * FXML Controller class
 *
 * @author nadeeja
 */
public class MainLoginController implements Initializable {

    @FXML
    private javafx.scene.control.TextField txtName;
    @FXML
    private javafx.scene.control.TextField txtPassword;
    @FXML
    private void login() throws IOException {
        String userName = txtName.getText();
        String password = txtPassword.getText();
        if (userName.equals("nadeeja") && password.equals("123")){
            Parent layoutFXML = App.loadFXML("Layout");

            // Get current stage
            Stage stage = (Stage) txtName.getScene().getWindow();

            // Set the new scene
            Scene scene = new Scene(layoutFXML);
            stage.setScene(scene);
            stage.setTitle("Ceylon Tea Pay");
            stage.show();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Invalid username or password!");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
