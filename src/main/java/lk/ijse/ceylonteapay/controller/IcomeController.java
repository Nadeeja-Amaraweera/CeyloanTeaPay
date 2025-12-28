
package lk.ijse.ceylonteapay.controller;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import lk.ijse.ceylonteapay.dto.StockDTO;
import lk.ijse.ceylonteapay.model.StockModel;


public class IcomeController implements Initializable {

    private static StockModel stockModel = new StockModel();

    private String selectedMonth;
    private int selectedYear;

    @FXML
    private ComboBox<Integer> cmdYears;

    @FXML
    private ComboBox<String> cmdMonths;

    @FXML
    private BarChart<String, Number> stockBarChart;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadMonthAndYears();
        loadStockChart();
        comboSelections();
    }

    private void comboSelections() {
        cmdYears.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null){
//                selectedYear=newValue.
            }
        });
    }

    @FXML
    private void getAllSalary(){
    }

    private void loadMonthAndYears() {
        // Month names
        cmdMonths.setItems(FXCollections.observableArrayList(
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        ));

        // Year range
        ObservableList<Integer> years = FXCollections.observableArrayList();
        int currentYear = LocalDate.now().getYear();
        for (int i = currentYear - 1; i <= currentYear + 1; i++) {
            years.add(i);
        }
        cmdYears.setItems(years);
    }

    private void loadStockChart() {

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Stock");

        try {
            for (StockDTO stock : stockModel.getStockSummary()) {
                series.getData().add(
                        new XYChart.Data<>(
                                stock.getQuality(),
                                stock.getQuantity()
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        stockBarChart.getData().clear();
        stockBarChart.getData().add(series);
    }


}
