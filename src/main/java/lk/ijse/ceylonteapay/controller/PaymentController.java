
package lk.ijse.ceylonteapay.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;


public class PaymentController implements Initializable {

   @FXML
   private void openTeaRateWindow() {
       try {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/lk/ijse/ceylonteapay/TeaRate.fxml"));
           Parent root = loader.load();

           Stage stage = new Stage();
           stage.setResizable(false);
           stage.setScene(new Scene(root));
           stage.setTitle("Tea Rate");
           stage.show();
       }catch (IOException e){
           JOptionPane.showMessageDialog(null,e);
       }
   }

//   Change with plucking date


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
}
