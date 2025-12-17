package lk.ijse.ceylonteapay.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.ceylonteapay.dto.DeliveryCartTM;
import lk.ijse.ceylonteapay.dto.FactoryDTO;
import lk.ijse.ceylonteapay.dto.StockDTO;
import lk.ijse.ceylonteapay.model.DeliveryTeaModel;
import lk.ijse.ceylonteapay.model.StockModel;

import javax.swing.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

//import static lk.ijse.ceylonteapay.controller.StockController.stockModel;


public class DeliveryTeaController implements Initializable {

    @FXML
    private TableView<StockDTO> tblStock;

    @FXML
    private TableColumn<StockDTO,Integer>col_stockTableId;
    @FXML
    private TableColumn<StockDTO,Integer> col_stockTableStock;
    @FXML
    private TableColumn<StockDTO,String> col_stockTableQuality;

    @FXML
    private TableView<DeliveryCartTM> tblDelivery;

    @FXML
    private ComboBox<FactoryDTO> cmdFactoryName;
    @FXML
    private ComboBox<StockDTO> cmdStockNo;
    @FXML
    private DatePicker txtDate;
    @FXML
    private TextField txtQuantity;

    @FXML
    private TableColumn<DeliveryCartTM,Integer> colStockId;
    @FXML
    private TableColumn<DeliveryCartTM,Integer> colFactoryId;
    @FXML
    private TableColumn<DeliveryCartTM,String> colFactoryName;
    @FXML
    private TableColumn<DeliveryCartTM,Integer> colQty;
    @FXML
    private TableColumn<DeliveryCartTM,LocalDate> colDate;


    ObservableList<DeliveryCartTM> cartList = FXCollections.observableArrayList();

    private static DeliveryTeaModel deliveryTeaModel = new DeliveryTeaModel();

    ObservableList<StockDTO> stockDTOObservableList = FXCollections.observableArrayList();



    private static StockModel stockModel = new StockModel();


    private int selectedFactoryId = -1;
    private String selectedFactoryName = "";
    private int selectedStockId = -1;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadFactoryCombo();
        loadStockCombo();
        initializeCartTable();

        cmdFactoryName.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                selectedFactoryId = newVal.getFactoryId();
                selectedFactoryName = newVal.getFactoryName();
                System.out.println(selectedFactoryName);
            }
        });

        cmdStockNo.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                selectedStockId = newVal.getId();
                System.out.println(newVal.getDate());
            }
        });

        col_stockTableId.setCellValueFactory(new PropertyValueFactory<StockDTO,Integer>("id"));
        col_stockTableQuality.setCellValueFactory(new PropertyValueFactory<StockDTO,String>("quality"));
        col_stockTableStock.setCellValueFactory(new PropertyValueFactory<StockDTO,Integer>("availableQuantity"));

        tblStock.setItems(loadStockTable());
    }

    private void initializeCartTable() {
        colStockId.setCellValueFactory(new PropertyValueFactory<>("stockId"));
        colFactoryId.setCellValueFactory(new PropertyValueFactory<>("factoryId"));
        colFactoryName.setCellValueFactory(new PropertyValueFactory<>("factoryName"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
    }

    @FXML
    private void addToCart() {
//        int stockId, int factoryId, String factoryName, int qty, LocalDate date

        FactoryDTO selectedFactory = cmdFactoryName.getSelectionModel().getSelectedItem();
        StockDTO selectedStock = cmdStockNo.getSelectionModel().getSelectedItem();

        int stockId = selectedStockId;
        int factoryId = selectedFactoryId;
        String factoryName = selectedFactoryName;
        String qtyText = txtQuantity.getText();
        LocalDate date = txtDate.getValue();

        if (selectedStock==null){
            new Alert(Alert.AlertType.ERROR, "Please select a Stock.").show();
        } else if(selectedFactory==null){
            new Alert(Alert.AlertType.ERROR, "Please select a Factory.").show();
        } else if (qtyText.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Quantity cannot be empty.").show();
        } else if (date == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a date.").show();
        } else {
            int qty;
            try {
                qty = Integer.parseInt(qtyText);
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR, "Quantity must be a valid number.").show();
                return;
            }

            if (qty <= 0) {
                new Alert(Alert.AlertType.ERROR, "Quantity must be a positive number.").show();
            } else {
                DeliveryCartTM cartTM = new DeliveryCartTM(
                        stockId,
                        factoryId,
                        factoryName,
                        qty,
                        date
                );

                cartList.add(cartTM);
                tblDelivery.setItems(cartList);
            }
        }
    }

    @FXML
    private void placeOrder(){
        if (cartList.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Cart is empty!").show();
            return;
        }
        try {
            boolean isSuccess = deliveryTeaModel.placeOrder(cartList);

            if (isSuccess) {
                new Alert(Alert.AlertType.INFORMATION, "Delivery Order Placed Successfully!").show();
                cartList.clear();
                tblDelivery.refresh();
                refreshStockTable();
            } else {
                new Alert(Alert.AlertType.ERROR, "Order Failed!").show();
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void refreshStockTable() {
        stockDTOObservableList.clear();
        stockDTOObservableList.addAll(loadStockTable());
        tblStock.setItems(stockDTOObservableList);
    }

    private ObservableList<StockDTO> loadStockTable(){
        try {
            ObservableList<StockDTO> list = stockModel.getStock();
            return list;
        }catch (Exception e){
            e.printStackTrace();
            return FXCollections.observableArrayList();
        }
    }

    private void loadFactoryCombo() {
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
        } catch (Exception e) {

        }
    }

    private void loadStockCombo() {
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
                        setText(item.getDate() + " - " + item.getQuality());
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
                        setText(item.getDate() + " - " + item.getQuality());

                    }
                }
            });

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

}
