package lk.ijse.ceylonteapay.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.ceylonteapay.dto.StockDTO;
import lk.ijse.ceylonteapay.model.StockModel;

import javax.swing.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;


public class StockController implements Initializable {

    @FXML
    private TableView<StockDTO> tableView;
    @FXML
    private TableColumn<StockDTO, Integer> col_id;
    @FXML
    private TableColumn<StockDTO, LocalDate> col_date;
    @FXML
    private TableColumn<StockDTO, String> col_quality;
    @FXML
    private TableColumn<StockDTO, Integer> col_qty;
    @FXML
    private TableColumn<StockDTO, Integer> col_ava_qty;


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

    private final String QUALITY_REGEX = "^[A-Za-z0-9 ]+$";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        col_id.setCellValueFactory(new PropertyValueFactory<StockDTO, Integer>("id"));
        col_date.setCellValueFactory(new PropertyValueFactory<StockDTO, LocalDate>("date"));
        col_quality.setCellValueFactory(new PropertyValueFactory<StockDTO, String>("quality"));
        col_qty.setCellValueFactory(new PropertyValueFactory<StockDTO, Integer>("quantity"));
        col_ava_qty.setCellValueFactory(new PropertyValueFactory<StockDTO, Integer>("availableQuantity"));


        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldvalue, newvalue) -> {
            if (newvalue != null) {
                setStockDetails(newvalue);
            }
        });
        tableView.setItems(loadStock());
    }

    private void setStockDetails(StockDTO stockDTO) {
        txtStockId.setText(String.valueOf(stockDTO.getId()));
        txtDate.setValue(stockDTO.getDate());
        txtQuality.setText(stockDTO.getQuality());
        txtQuantity.setText(String.valueOf(stockDTO.getQuantity()));
        txtAvailableQuantity.setText(String.valueOf(stockDTO.getAvailableQuantity()));
    }

    @FXML
    private void save() {

        LocalDate date = txtDate.getValue();
        String quality = txtQuality.getText();
        String qtyText = txtQuantity.getText();
        String avaQtyText = txtAvailableQuantity.getText();

        // First, check for empty TextFields and invalid inputs
        if (date == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a date.").show();
        } else if (quality.isEmpty() || !quality.matches(QUALITY_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Quality. Must contain letters, numbers, or spaces and cannot be empty.").show();
        } else if (qtyText.isEmpty() || avaQtyText.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Quantity and Available Quantity cannot be empty.").show();
        } else {
            // Safe to parse integers now
            int qty, avaQty;
            try {
                qty = Integer.parseInt(qtyText);
                avaQty = Integer.parseInt(avaQtyText);
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR, "Quantity and Available Quantity must be valid integers.").show();
                return;
            }

            // Further validation
            if (qty <= 0) {
                new Alert(Alert.AlertType.ERROR, "Quantity must be a positive number.").show();
            } else if (avaQty < 0) {
                new Alert(Alert.AlertType.ERROR, "Available Quantity cannot be negative.").show();
            } else {
                try {
                    StockDTO stockDTO = new StockDTO(date, quality, qty, avaQty);
                    boolean result = stockModel.saveStock(stockDTO);

                    if (result) {
                        new Alert(Alert.AlertType.INFORMATION, "Stock Added Successfully.").show();
                        refreshTable();
                        clearFields();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Stock Added Not Successfully.").show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
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
    private void update() {

        LocalDate date = txtDate.getValue();
        String quality = txtQuality.getText();
        String qtyText = txtQuantity.getText();
        String avaQtyText = txtAvailableQuantity.getText();

        StockDTO selected = tableView.getSelectionModel().getSelectedItem();

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Update");
        confirmAlert.setHeaderText("Update Stock Record");
        confirmAlert.setContentText("Are you sure you want to Update Stock Quality: " + selected.getQuality() + " ?");

        Optional<ButtonType> confirm = confirmAlert.showAndWait();

        if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
            final String QUALITY_REGEX = "^[A-Za-z0-9 ]+$"; // letters, numbers, spaces

            if (selected == null) {
                new Alert(Alert.AlertType.ERROR, "Please select a stock item from the table!").show();
            } else if (date == null) {
                new Alert(Alert.AlertType.ERROR, "Please select a date.").show();
            } else if (quality.isEmpty() || !quality.matches(QUALITY_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid Quality. Must contain letters, numbers, or spaces and cannot be empty.").show();
            } else if (qtyText.isEmpty() || avaQtyText.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Quantity and Available Quantity cannot be empty.").show();
            } else {
                int qty, avaQty;
                try {
                    qty = Integer.parseInt(qtyText);
                    avaQty = Integer.parseInt(avaQtyText);
                } catch (NumberFormatException e) {
                    new Alert(Alert.AlertType.ERROR, "Quantity and Available Quantity must be valid integers.").show();
                    return;
                }

                if (qty <= 0) {
                    new Alert(Alert.AlertType.ERROR, "Quantity must be a positive number.").show();
                } else if (avaQty < 0) {
                    new Alert(Alert.AlertType.ERROR, "Available Quantity cannot be negative.").show();
                } else {
                    try {
                        int id = selected.getId();
                        StockDTO stockDTO = new StockDTO(id, date, quality, qty, avaQty);
                        boolean result = stockModel.updateStock(stockDTO);

                        if (result) {
                            new Alert(Alert.AlertType.INFORMATION, "Stock Updated Successfully.").show();
                            refreshTable();
                            clearFields();
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Stock Updated Not Successfully.").show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }


        }
    }


    @FXML
    private void deleteRecord() {
        StockDTO selected = tableView.getSelectionModel().getSelectedItem();

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Delete");
        confirmAlert.setHeaderText("Delete Stock Record");
        confirmAlert.setContentText(
                "Are you sure you want to delete Stock Quality Name: "
                        + selected.getQuality() + " ?");

        Optional<ButtonType> confirm = confirmAlert.showAndWait();

        if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
            if (selected == null) {
                new Alert(Alert.AlertType.ERROR, "Please select a stock item from the table!").show();
            } else {
                try {
                    int id = selected.getId();
                    boolean result = stockModel.deleteStock(id);
                    if (result) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success !");
                        alert.setHeaderText("Stock Deleted Successfully.");
                        alert.show();
                        refreshTable();
                        clearFields();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error !");
                        alert.setHeaderText("Stock Deleted Not Successfully.");
                        alert.show();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }


    }

    @FXML
    private void reset() {
        clearFields();
    }

    private ObservableList<StockDTO> loadStock() {
        try {
            ObservableList<StockDTO> list = stockModel.getStock();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return FXCollections.observableArrayList();
        }
    }
}
