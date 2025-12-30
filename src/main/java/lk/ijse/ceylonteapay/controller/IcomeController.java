
package lk.ijse.ceylonteapay.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.ceylonteapay.dto.DailyTeaDTO;
import lk.ijse.ceylonteapay.dto.IncomeDTO;
import lk.ijse.ceylonteapay.dto.StockDTO;
import lk.ijse.ceylonteapay.model.IncomeModel;
import lk.ijse.ceylonteapay.model.StockModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;
import java.util.ResourceBundle;


public class IcomeController implements Initializable {

    private static StockModel stockModel = new StockModel();
    private static IncomeModel incomeModel = new IncomeModel();

    ObservableList<IncomeDTO> incomeDTOObservableList = FXCollections.observableArrayList();

    private final String THIS_MONTH_SALARY = "^-?\\d+$";

    @FXML
    private ComboBox<Integer> cmdYears;

    @FXML
    private ComboBox<String> cmdMonths;

    @FXML
    private BarChart<String, Number> stockBarChart;

    @FXML
    private LineChart<String, Number> incomeLineChart;

    @FXML
    private TextField txtTeaSalaryField;

    @FXML
    private TextField txtOtherWorkSalary;

    @FXML
    private TextField txtThisMonthIncome;

    @FXML
    private TextField txtFinalIncome;

    @FXML
    private TableView<IncomeDTO> tableView;

    @FXML
    private TableColumn<IncomeDTO,Integer> colIncomeId;
    @FXML
    private TableColumn<IncomeDTO,String> colMonth;
    @FXML
    private TableColumn<IncomeDTO,Integer> colYear;
    @FXML
    private TableColumn<IncomeDTO,Double> col_tea_salary;
    @FXML
    private TableColumn<IncomeDTO,Double> col_otherWork_salary;
    @FXML
    private TableColumn<IncomeDTO,Double> col_thisMonthIncome;

    @FXML
    private TableColumn<IncomeDTO,Double> colAmount;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadMonthAndYears();
        loadStockChart();
        loadIncomeChartUsingModel();
        setTableColumns();
    }

    private void setTableColumns() {
//        int incomeId, int paymentId, String month, int year, double finalIncome
        colIncomeId.setCellValueFactory(new PropertyValueFactory<IncomeDTO,Integer>("incomeId"));
        colMonth.setCellValueFactory(new PropertyValueFactory<IncomeDTO,String>("month"));
        colYear.setCellValueFactory(new PropertyValueFactory<IncomeDTO,Integer>("year"));
        col_tea_salary.setCellValueFactory(new PropertyValueFactory<IncomeDTO,Double>("teaSalary"));
        col_otherWork_salary.setCellValueFactory(new PropertyValueFactory<IncomeDTO,Double>("otherWorkSalary"));
        col_thisMonthIncome.setCellValueFactory(new PropertyValueFactory<IncomeDTO,Double>("thisMonthIncome"));

        colAmount.setCellValueFactory(new PropertyValueFactory<IncomeDTO,Double>("finalIncome"));

        tableView.setItems(loadIncomeTable());
    }

    @FXML
    private void getAllSalary() {
        try {
            int year = cmdYears.getValue();
            String month = cmdMonths.getValue();
            if (cmdYears.getValue()== null){
                new Alert(Alert.AlertType.ERROR,"Please Select Year !").show();
            } else if (cmdMonths.getValue()==null) {
                new Alert(Alert.AlertType.ERROR,"Please Select Month !").show();
            }else {
                int monthNumber = Month.valueOf(month.toUpperCase()).getValue();

                IncomeDTO incomeDTO = incomeModel.getAllTeaSalary(monthNumber, year);

                txtTeaSalaryField.setText(String.valueOf(incomeDTO.getTeaSalary()));
                txtOtherWorkSalary.setText(String.valueOf(incomeDTO.getOtherWorkSalary()));
            }
            System.out.println(month+year);

        } catch (Exception e) {

        }
    }

    @FXML
    private void calculateFinalIncome(){

        String thisMonthSalary = txtThisMonthIncome.getText();

        if (!thisMonthSalary.matches(THIS_MONTH_SALARY)){
            new Alert(Alert.AlertType.ERROR,"Monthly Income is Empty !").show();
        } else {

            double totalTeaSalary = Double.parseDouble(txtTeaSalaryField.getText());
            double totalOtherWorkSalary = Double.parseDouble(txtOtherWorkSalary.getText());

            double thisMonthIncome = Double.parseDouble(txtThisMonthIncome.getText());

            double finalSalary = thisMonthIncome - (totalTeaSalary + totalOtherWorkSalary);

            txtFinalIncome.setText(String.valueOf(finalSalary));

        }

    }

    @FXML
    private void savePayment() {
        try {

            String month = cmdMonths.getValue();
            int year = cmdYears.getValue();
            double teaSalary = Double.parseDouble(txtTeaSalaryField.getText());
            double otherWorkSalary = Double.parseDouble(txtOtherWorkSalary.getText());
            double monthlyIncome = Double.parseDouble(txtThisMonthIncome.getText());
            double finalIncome = Double.parseDouble(txtFinalIncome.getText());

            IncomeDTO incomeDTO = new IncomeDTO(month, year, teaSalary, otherWorkSalary, monthlyIncome, finalIncome);
            boolean result = incomeModel.savePayment(incomeDTO);

            if (result){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success !");
                alert.setHeaderText("Field Added Successfully.");
                alert.show();
                refreshTable();

//                clearFields();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error !");
                alert.setHeaderText("Field Added Not Successfully.");
                alert.show();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteIncome(){
        try {
            IncomeDTO selectedIncome = tableView.getSelectionModel().getSelectedItem();

            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Delete");
            confirmAlert.setHeaderText("Delete Income Record");
            confirmAlert.setContentText(
                    "Are you sure you want to delete Income ID: "
                            + selectedIncome.getIncomeId() + " ?");

            Optional<ButtonType> confirm = confirmAlert.showAndWait();

            if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
                if (selectedIncome == null) {
                    new Alert(Alert.AlertType.WARNING,
                            "Please select an income record first!").show();
                } else {
                    int id = selectedIncome.getIncomeId();
                    boolean result = incomeModel.deleteIncome(id);

                    if (result){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success !");
                        alert.setHeaderText("Field Deleted Successfully.");
                        alert.show();
                        refreshTable();

//                clearFields();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error !");
                        alert.setHeaderText("Field Deleted Not Successfully.");
                        alert.show();
                    }
                }
            }


        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error !");
            alert.setHeaderText("Field Deleted Not Successfully.");
            alert.show();
            e.printStackTrace();
        }
    }

    private void refreshTable() {
        incomeDTOObservableList.clear();
        incomeDTOObservableList.addAll(loadIncomeTable());
        tableView.setItems(incomeDTOObservableList);
        loadIncomeChartUsingModel();
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

    private void loadIncomeChartUsingModel() {

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Final Income");

        try {
            ObservableList<IncomeDTO> incomeList =
                    incomeModel.getAllIncomeFields(); // ✅ YOUR METHOD

            for (IncomeDTO income : incomeList) {
                series.getData().add(
                        new XYChart.Data<>(
                                income.getMonth(),
                                income.getFinalIncome()
                        )
                );
            }

            incomeLineChart.getData().clear();
            incomeLineChart.getData().add(series);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ObservableList<IncomeDTO> loadIncomeTable(){
        try {
            ObservableList<IncomeDTO> incomeDTOS = incomeModel.getAllIncomeFields();
            return incomeDTOS;
        } catch (Exception e) {
            e.printStackTrace();
            return FXCollections.observableArrayList();
        }
    }


}
