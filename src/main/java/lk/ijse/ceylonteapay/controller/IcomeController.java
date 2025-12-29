
package lk.ijse.ceylonteapay.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import lk.ijse.ceylonteapay.dto.IncomeDTO;
import lk.ijse.ceylonteapay.dto.StockDTO;
import lk.ijse.ceylonteapay.model.IncomeModel;
import lk.ijse.ceylonteapay.model.StockModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ResourceBundle;


public class IcomeController implements Initializable {

    private static StockModel stockModel = new StockModel();
    private static IncomeModel incomeModel = new IncomeModel();

    private String selectedMonth;
    private int selectedYear;

    @FXML
    private ComboBox<Integer> cmdYears;

    @FXML
    private ComboBox<String> cmdMonths;

    @FXML
    private BarChart<String, Number> stockBarChart;

    @FXML
    private TextField txtTeaSalaryField;

    @FXML
    private TextField txtOtherWorkSalary;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadMonthAndYears();
        loadStockChart();

    }

    @FXML
    private void getAllSalary() {
        try {


            int year = cmdYears.getValue();
            String month = cmdMonths.getValue();
            int monthNumber = Month.valueOf(month.toUpperCase()).getValue();

            IncomeDTO incomeDTO = incomeModel.getAllTeaSalary(monthNumber,year);

            txtTeaSalaryField.setText(String.valueOf(incomeDTO.getTeaSalary()));
            txtOtherWorkSalary.setText(String.valueOf(incomeDTO.getOtherWorkSalary()));

        } catch (Exception e) {

        }
    }

    @FXML
    private void savePayment() {

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
