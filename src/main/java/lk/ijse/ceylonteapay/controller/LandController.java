package lk.ijse.ceylonteapay.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.ceylonteapay.db.DBConnection;
import lk.ijse.ceylonteapay.dto.LandDTO;
import lk.ijse.ceylonteapay.model.LandModel;

import javax.swing.*;

public class LandController implements Initializable {
    @FXML
    private AnchorPane rootLand;
    @FXML
    private TextField txtLandNameField;
    @FXML
    private TextField txtLandNoField;
    @FXML
    private TableColumn<LandDTO,Integer> col_id;
    @FXML
    private TableColumn<LandDTO, String> col_Name;
    @FXML
    private TableColumn<LandDTO, String> col_No;
    @FXML
    private TableView<LandDTO> tableView;

    private final LandModel landModel = new LandModel();

    ObservableList<LandDTO> landDTOObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        col_id.setCellValueFactory(new PropertyValueFactory<LandDTO,Integer>("lndID"));
        col_Name.setCellValueFactory(new PropertyValueFactory<LandDTO,String>("lndName"));
        col_No.setCellValueFactory(new PropertyValueFactory<LandDTO,String>("lndNo"));

        tableView.getSelectionModel().selectedItemProperty().addListener(
                (obs,oldvalue,newvalue)->{
                    if (newvalue!=null){
                        setLandDetails(newvalue);
                    }
                }
        );
        tableView.setItems(loadLands());
    }

    private void setLandDetails(LandDTO landDTO) {
        txtLandNameField.setText(landDTO.getLndName());
        txtLandNoField.setText(landDTO.getLndNo());
    }

    @FXML
    public void addLand(){
        String landName = txtLandNameField.getText();
        String landNo = txtLandNoField.getText();
        System.out.println("Name: "+landName+" No: "+landNo);

        try {
            LandDTO landDTO = new LandDTO(landName,landNo);
            boolean result = landModel.saveLand(landDTO);

            if (result){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Land Save");
                alert.setContentText("Land save successfully !");
                alert.show();
                clearFields();
            }
            refreshTable();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    @FXML
    public void updateLand(){
        String landName = txtLandNameField.getText();
        String landNo = txtLandNoField.getText();

        try {
            LandDTO selected = tableView.getSelectionModel().getSelectedItem();
            int id = selected.getLndID();
            System.out.println(id);

            LandDTO landDTO = new LandDTO(id,landName,landNo);
            boolean result = landModel.updateLand(landDTO);

            if (result){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success !");
                alert.setHeaderText("Land Updated Successfully.");
                alert.show();
                refreshTable();
                clearFields();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error !");
                alert.setHeaderText("Land Updated Not Successfully.");
                alert.show();
            }

        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    @FXML
    public void deleteLand(){
        LandDTO selected = tableView.getSelectionModel().getSelectedItem();
        int id = selected.getLndID();

        try {
            boolean result = landModel.deleteLand(id);
            if (result){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success !");
                alert.setHeaderText("Land Deleted Successfully.");
                alert.show();
                refreshTable();
                clearFields();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error !");
                alert.setHeaderText("Land Deleted Not Successfully.");
                alert.show();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
        }
    }
    @FXML
    public void reset(){
        clearFields();
    }
    public void exit(){
        System.exit(0);
    }

    private void clearFields() {
        txtLandNameField.setText("");
        txtLandNoField.setText("");
    }

    private void refreshTable() {
        landDTOObservableList.clear();
        landDTOObservableList.addAll(loadLands());
        tableView.setItems(landDTOObservableList);
    }
    @FXML
    public void goBackHome() throws IOException {
        Stage stage = (Stage) rootLand.getScene().getWindow();
        stage.setScene(new Scene(
                FXMLLoader.load(getClass().getResource("/lk/ijse/ceylonteapay/Home.fxml"))
        ));
    }

    public ObservableList<LandDTO> loadLands(){
        try {
            ObservableList<LandDTO> landDTOObservableList = landModel.getAllLands();
            // No need to copy into another list, can return directly
            return landDTOObservableList;
        } catch (Exception e) {
            e.printStackTrace(); // or use JavaFX Alert
            return FXCollections.observableArrayList(); // empty list on error
        }
    }
    
}
