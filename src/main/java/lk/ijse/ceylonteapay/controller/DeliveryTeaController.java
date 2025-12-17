package lk.ijse.ceylonteapay.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lk.ijse.ceylonteapay.dto.DeliveryTeaDTO;
import lk.ijse.ceylonteapay.dto.EmployeeDTO;
import lk.ijse.ceylonteapay.dto.FactoryDTO;
import lk.ijse.ceylonteapay.dto.StockDTO;
import lk.ijse.ceylonteapay.model.DeliveryTeaModel;

import javax.swing.*;


public class DeliveryTeaController implements Initializable {

    @FXML
    private TableView<StockDTO> tblStock;
    @FXML
    private TableView<DeliveryTeaDTO> tblDelivery;

    @FXML
    private TextField txtDeliveryId;
    @FXML
    private ComboBox<FactoryDTO> cmdFactoryName;
    @FXML
    private ComboBox<StockDTO> cmdStockNo;
    @FXML
    private DatePicker txtDate;
    @FXML
    private TextField txtQuantity;

    private static DeliveryTeaModel deliveryTeaModel = new DeliveryTeaModel();

    private int selectedFactoryId = -1;
    private int selectedStockId = -1;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadFactory();
        loadStock();

        cmdFactoryName.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal!=null){
                selectedFactoryId = newVal.getFactoryId();
                System.out.println(newVal.getFactoryName());
            }
        });

        cmdStockNo.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal!=null){
                selectedStockId = newVal.getId();
                System.out.println(newVal.getDate());
            }
        });
    }

    @FXML
    private void addToCart(){
//        int stockId, int factoryId, String factoryName, int qty, LocalDate date

//        StockDTO stockid = cmdStockNo.getSelectionModel().getSelectedItem();
//        FactoryDTO factoryid = cmdFactoryName.getSelectionModel().getSelectedItem();

        System.out.println(selectedStockId+" - "+selectedFactoryId);
    }

    private void loadFactory(){
        try {
            ObservableList<FactoryDTO> list = deliveryTeaModel.getComboFactory();
            cmdFactoryName.setItems(list);

            // Show only ID + Name in ComboBox
            cmdFactoryName.setCellFactory(cb -> new ListCell<>() {
                @Override
                protected void updateItem(FactoryDTO item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
//                        setText(item.getFactoryId() + " - " + item.getFactoryName());
                        setText(item.getFactoryName());

                    }
                }
            });

            // Also show selected value correctly
            cmdFactoryName.setButtonCell(new ListCell<>() {
                @Override
                protected void updateItem(FactoryDTO item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
//                        setText(item.getFactoryId() + " - " + item.getFactoryName());
                        setText(item.getFactoryName());
                    }
                }
            });
        }catch (Exception e){

        }
    }

    private void loadStock(){
        try {

            ObservableList<StockDTO> list = deliveryTeaModel.getComboStock();
            cmdStockNo.setItems(list);

            cmdStockNo.setCellFactory(cb -> new ListCell<>() {
                @Override
                protected void updateItem(StockDTO item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
//                        setText(item.getId() + " - " + item.getDate()+ " - "+item.getQuality());
                        setText(item.getDate()+ " - "+item.getQuality());
                    }
                }
            });

            // Also show selected value correctly
            cmdStockNo.setButtonCell(new ListCell<>() {
                @Override
                protected void updateItem(StockDTO item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
//                        setText(item.getId() + " - " + item.getDate()+ " - "+item.getQuality());
                        setText(item.getDate()+ " - "+item.getQuality());

                    }
                }
            });

        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
}
