package lk.ijse.ceylonteapay.controller;

import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.ceylonteapay.db.DBConnection;
import lk.ijse.ceylonteapay.dto.FactoryDTO;
import lk.ijse.ceylonteapay.dto.StockDTO;
import lk.ijse.ceylonteapay.model.StockModel;


public class StockController implements Initializable {

    @FXML
    private TableView<StockDTO> tableView;
    @FXML
    private TableColumn<StockDTO,Integer> col_id;
    @FXML
    private TableColumn<StockDTO, LocalDate> col_date;
    @FXML
    private TableColumn<StockDTO,String> col_quality;
    @FXML
    private TableColumn<StockDTO,Integer> col_qty;
    @FXML
    private TableColumn<StockDTO,Integer> col_ava_qty;



    @FXML
    private TextField txtStockId;
    @FXML
    private TextField txtQuality;
    @FXML
    private DatePicker txtDate;
    @FXML
    private TextField txtQuantity;
    @FXML
    private TextField txtAvailableQuantity;

    private static StockModel stockModel = new StockModel();

    ObservableList<StockDTO> stockDTOObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        col_id.setCellValueFactory(new PropertyValueFactory<StockDTO,Integer>("id"));
        col_date.setCellValueFactory(new PropertyValueFactory<StockDTO, LocalDate>("date"));
        col_quality.setCellValueFactory(new PropertyValueFactory<StockDTO,String>("quality"));
        col_qty.setCellValueFactory(new PropertyValueFactory<StockDTO,Integer>("quantity"));
        col_ava_qty.setCellValueFactory(new PropertyValueFactory<StockDTO,Integer>("availableQuantity"));


        tableView.getSelectionModel().selectedItemProperty().addListener(
                (obs,oldvalue,newvalue)->{
                    if (newvalue!=null){
                        setStockDetails(newvalue);
                    }
                }
        );
        tableView.setItems(loadStock());
    }

    private void setStockDetails(StockDTO stockDTO){
        txtStockId.setText(String.valueOf(stockDTO.getId()));
        txtDate.setValue(stockDTO.getDate());
        txtQuality.setText(stockDTO.getQuality());
        txtQuantity.setText(String.valueOf(stockDTO.getQuantity()));
        txtAvailableQuantity.setText(String.valueOf(stockDTO.getAvailableQuantity()));
    }

    @FXML
    private void save(){
        try {

            LocalDate date = txtDate.getValue();
            String quality = txtQuality.getText();
            int qty = Integer.parseInt(txtQuantity.getText());
            int avaQty = Integer.parseInt(txtAvailableQuantity.getText());

            StockDTO stockDTO = new StockDTO(date,quality,qty,avaQty);
            boolean result = stockModel.saveStock(stockDTO);

            if (result){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success !");
                alert.setHeaderText("Stock Added Successfully.");
                alert.show();
                refreshTable();
                clearFields();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error !");
                alert.setHeaderText("Stock Added Not Successfully.");
                alert.show();
            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void clearFields() {
        txtStockId.setText("No id is here");
        txtDate.setValue(null);
        txtQuality.setText("");
        txtQuantity.setText("");
        txtAvailableQuantity.setText("");
    }

    private void refreshTable() {
        stockDTOObservableList.clear();
        stockDTOObservableList.addAll(loadStock());
        tableView.setItems(stockDTOObservableList);
    }

    @FXML
    private void update(){

    }

    @FXML
    private void delete(){

    }

    @FXML
    private void reset(){

    }

    private ObservableList<StockDTO> loadStock(){
        try {
            ObservableList<StockDTO> list = stockModel.getStock();
            return list;
        }catch (Exception e){
            e.printStackTrace();
            return FXCollections.observableArrayList();
        }
    }
}
