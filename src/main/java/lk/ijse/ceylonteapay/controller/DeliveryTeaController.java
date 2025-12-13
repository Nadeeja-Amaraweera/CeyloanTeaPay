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


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadFactory();
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
                        setText(item.getFactoryId() + " - " + item.getFactoryName());
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
                        setText(item.getFactoryId() + " - " + item.getFactoryName());
                    }
                }
            });
        }catch (Exception e){

        }
    }
    
}
