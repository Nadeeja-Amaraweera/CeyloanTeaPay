/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package lk.ijse.ceylonteapay.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.ceylonteapay.App;
import lk.ijse.ceylonteapay.db.DBConnection;
import lk.ijse.ceylonteapay.util.EmailUtil;

/**
 * FXML Controller class
 *
 * @author nadeeja
 */
public class MainLoginController implements Initializable {

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtPassword;
    @FXML
    private void login()throws IOException {
        String userName = txtName.getText();
        String password = txtPassword.getText();

        try {


            DBConnection dbc = DBConnection.getInstance();
            Connection connection = dbc.getConnection();

            String sql = "SELECT * FROM Login";
            PreparedStatement pstm = connection.prepareStatement(sql);

            ResultSet rs = pstm.executeQuery();

            String dbUserName = "";
            String dbPassword = "";
            while (rs.next()){
                dbUserName = rs.getString("UserName");
                dbPassword = rs.getString("Password");
            }
            System.out.println(dbUserName+dbPassword);

            if (userName.equals(dbUserName) && password.equals(dbPassword)) {
                Parent layoutFXML = App.loadFXML("Layout");

                // Get current stage
                Stage stage = (Stage) txtName.getScene().getWindow();

                // Set the new scene
                Scene scene = new Scene(layoutFXML);
                stage.setScene(scene);
                stage.setTitle("Ceylon Tea Pay");
                stage.show();

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText(null);
                alert.setContentText("Invalid username or password!");
                alert.showAndWait();

                // Send email in background thread
                Task<Void> emailTask = new Task<>() {
                    @Override
                    protected Void call() {
                        EmailUtil.sendLoginFailEmail("nadeejaamaraweera@gmail.com", userName,password); // your admin email
                        return null;
                    }
                };
                new Thread(emailTask).start();
            }

        }catch (SQLException e){

        }



    }

    @FXML
    private void forgorPassword(){

        new Alert(Alert.AlertType.INFORMATION,"Email send successfully !").show();

        try {

            DBConnection dbc = DBConnection.getInstance();
            Connection connection = dbc.getConnection();

            String sql = "SELECT * FROM Login";
            PreparedStatement pstm = connection.prepareStatement(sql);

            ResultSet rs = pstm.executeQuery();

            String dbUserName = "";
            String dbPassword = "";
            while (rs.next()) {
                dbUserName = rs.getString("UserName");
                dbPassword = rs.getString("Password");
            }
            // Send email in background thread
            String finalDbUserName = dbUserName;
            String finalDbPassword = dbPassword;

            Task<Void> emailTask = new Task<>() {
                @Override
                protected Void call() {
                    EmailUtil.sendLoginForgetEmail("nadeejaamaraweera@gmail.com", finalDbUserName, finalDbPassword); // your admin email
                    return null;
                }
            };
            new Thread(emailTask).start();
        }catch (Exception e){

        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
