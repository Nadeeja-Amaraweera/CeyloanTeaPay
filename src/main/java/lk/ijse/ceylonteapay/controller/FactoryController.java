
package lk.ijse.ceylonteapay.controller;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.ceylonteapay.dto.FactoryDTO;
import lk.ijse.ceylonteapay.dto.LandDTO;
import lk.ijse.ceylonteapay.model.FactoryModel;

import javax.swing.*;

public class FactoryController implements Initializable {

    @FXML
    private Label txtFactoryId;
    @FXML
    private TextField txtFactoryName;
    @FXML
    private TextArea txtFactoryAddress;
    @FXML
    private TableView<FactoryDTO> tableView;
    @FXML
    private TableColumn<FactoryDTO,Integer> col_id;
    @FXML
    private TableColumn<FactoryDTO, String> col_Name;
    @FXML
    private TableColumn<FactoryDTO, String> col_Address;

    private static FactoryModel factoryModel = new FactoryModel();

    ObservableList<FactoryDTO> factoryDTOObservableList = FXCollections.observableArrayList();

    // Factory Name – letters + spaces, minimum 3 characters
    private final String FACTORY_NAME_REGEX = "^[A-Za-z ]{3,}$";

    // Factory Address – letters, numbers, spaces, comma, dot, slash, dash, minimum 5 characters
    private final String FACTORY_ADDRESS_REGEX = "^[A-Za-z0-9 ,./-]{5,}$";


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        col_id.setCellValueFactory(new PropertyValueFactory<FactoryDTO,Integer>("factoryId"));
        col_Name.setCellValueFactory(new PropertyValueFactory<FactoryDTO,String>("factoryName"));
        col_Address.setCellValueFactory(new PropertyValueFactory<FactoryDTO,String>("factoryAddress"));

        tableView.getSelectionModel().selectedItemProperty().addListener(
                (obs,oldvalue,newvalue)->{
                    if (newvalue!=null){
                        setFactoryDetails(newvalue);
                    }
                }
        );
        tableView.setItems(loadFactories());
    }

    private void setFactoryDetails(FactoryDTO factoryDTO) {
        txtFactoryId.setText(String.valueOf(factoryDTO.getFactoryId()));
        txtFactoryName.setText(factoryDTO.getFactoryName());
        txtFactoryAddress.setText(factoryDTO.getFactoryAddress());
    }

    @FXML
    private void save(){

        String name = txtFactoryName.getText();
        String address = txtFactoryAddress.getText();

        if (!name.matches(FACTORY_NAME_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Factory Name. Must be at least 3 letters and only contain letters and spaces.").show();
        }else if (!address.matches(FACTORY_ADDRESS_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Factory Address. Must be at least 5 characters and can include letters, numbers, spaces, comma, dot, slash, dash.").show();
        }else {
            try {
                FactoryDTO factoryDTO = new FactoryDTO(name, address);
                boolean result = factoryModel.addFactory(factoryDTO);

                if (result){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success !");
                    alert.setHeaderText("Factory Added Successfully.");
                    alert.show();
                    refreshTable();
                    clearFields();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error !");
                    alert.setHeaderText("Factory Added Not Successfully.");
                    alert.show();
                }
            }catch (Exception e){

            }
        }


    }

    @FXML
    private void update(){
        FactoryDTO selected = tableView.getSelectionModel().getSelectedItem();
        String factoryNameText = txtFactoryName.getText();
        String factoryAddressText = txtFactoryAddress.getText();

        if (selected == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a factory from the table!").show();

        } else if (!factoryNameText.matches(FACTORY_NAME_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Factory Name. Must be at least 3 letters and only contain letters and spaces.").show();
        } else if (!factoryAddressText.matches(FACTORY_ADDRESS_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Factory Address. Must be at least 5 characters and can include letters, numbers, spaces, comma, dot, slash, dash.").show();
        } else {
            try {

                int id = selected.getFactoryId();
                System.out.println(id);

                FactoryDTO factoryDTO = new FactoryDTO(id,factoryNameText,factoryAddressText);
                boolean result = factoryModel.update(factoryDTO);

                if (result){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success !");
                    alert.setHeaderText("Factory Updated Successfully.");
                    alert.show();
                    refreshTable();
                    clearFields();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error !");
                    alert.setHeaderText("Factory Updated Not Successfully.");
                    alert.show();
                }

            }catch (Exception e){
                JOptionPane.showMessageDialog(null,e);
            }
        }


    }

    @FXML
    private void delete(){
        FactoryDTO selected = tableView.getSelectionModel().getSelectedItem();
        if (selected==null){
            new Alert(Alert.AlertType.ERROR, "Please select a factory from the table!").show();
        }else {
            try {
                int id = selected.getFactoryId();
                boolean result = factoryModel.deleteFactory(id);
                if (result){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success !");
                    alert.setHeaderText("Factory Deleted Successfully.");
                    alert.show();
                    refreshTable();
                    clearFields();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error !");
                    alert.setHeaderText("Factory Deleted Not Successfully.");
                    alert.show();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,e);
            }
        }
    }

    @FXML
    private void reset(){
        clearFields();
    }

    private void clearFields() {
        txtFactoryId.setText("No id is here");
        txtFactoryName.setText("");
        txtFactoryAddress.setText("");
    }

    private void refreshTable() {
        factoryDTOObservableList.clear();
        factoryDTOObservableList.addAll(loadFactories());
        tableView.setItems(factoryDTOObservableList);
    }

    public ObservableList<FactoryDTO> loadFactories(){
        try {
            ObservableList<FactoryDTO> landDTOObservableList = factoryModel.getAllFactories();
            // No need to copy into another list, can return directly
            return landDTOObservableList;
        } catch (Exception e) {
            e.printStackTrace(); // or use JavaFX Alert
            return FXCollections.observableArrayList(); // empty list on error
        }
    }

    
}
