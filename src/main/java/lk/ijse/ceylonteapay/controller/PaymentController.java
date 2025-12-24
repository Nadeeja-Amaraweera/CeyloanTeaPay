
package lk.ijse.ceylonteapay.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lk.ijse.ceylonteapay.db.DBConnection;
import lk.ijse.ceylonteapay.dto.*;
import lk.ijse.ceylonteapay.model.EmployeeModel;
import lk.ijse.ceylonteapay.model.PaymentModel;
import lk.ijse.ceylonteapay.model.TeaRateModel;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;


public class PaymentController implements Initializable {

    @FXML
    private ComboBox<EmployeeDTO> cmbEmployee;
    @FXML
    private ComboBox<String> monthCombo;
    @FXML
    private ComboBox<TeaRateDTO> cmbTeaRate;

    @FXML
    private TextField txtTeaSalary;
    @FXML
    private TextField txtOtherSalary;
    @FXML
    private TextField txtFinalSalary;


    @FXML
    private TableView<PaymentDTO> tableView;

    @FXML
    private TableColumn<PaymentDTO, Integer> col_empId;
    @FXML
    private TableColumn<PaymentDTO, String> col_empName;
    @FXML
    private TableColumn<PaymentDTO, Double> col_finalSalary;
    @FXML
    private TableColumn<PaymentDTO, Integer> col_id;
    @FXML
    private TableColumn<PaymentDTO, Double> col_otherSalary;
    @FXML
    private TableColumn<PaymentDTO, LocalDate> col_paymentDate;
    @FXML
    private TableColumn<PaymentDTO, Integer> col_rateId;
    @FXML
    private TableColumn<PaymentDTO, Double> col_teaSalary;

    @FXML
    private Label lblTeaRate;

    private static EmployeeModel employeeModel = new EmployeeModel();
    private static PaymentModel paymentModel = new PaymentModel();
    private static TeaRateModel teaRateModel = new TeaRateModel();

    ObservableList<PaymentDTO> paymentDTOS = FXCollections.observableArrayList();

    private int selectEmpid;
    private int selectRateid;
    private String selecetEmpName;
    private Month selectedMonth;
    private int selectedMonthNumber;
    private int selectedYear;
    private double selectedTeaRate;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadEmployees();
        loadMonths();
        loadTeaRateCombo();
        setTableColumn();

        tableView.getSelectionModel().selectedItemProperty().addListener(
                ((observableValue, oldValue, newValue) -> {
                    if (newValue != null){
                        tableSelection(newValue);
                    }
                })
        );

    }

    @FXML
    private void calculateSalary() {

//        must need to get this year
        loadTeaDataByMonth(selectedMonthNumber,selectEmpid);
        loadOtherWorkByMonth(selectedMonthNumber,selectEmpid);

        // Read values from text fields
        double teaSalary = txtTeaSalary.getText().isEmpty()
                ? 0
                : Double.parseDouble(txtTeaSalary.getText());

        double otherWorkSalary = txtOtherSalary.getText().isEmpty()
                ? 0
                : Double.parseDouble(txtOtherSalary.getText());

        // Calculate final salary
        double finalSalary = teaSalary + otherWorkSalary;

        // Set final salary
        txtFinalSalary.setText(String.valueOf(finalSalary));
    }

    @FXML
    private void savePayment() {
        try {
            double teaSalary = Double.parseDouble(txtTeaSalary.getText());
            double expenseSalary = Double.parseDouble(txtOtherSalary.getText());
            double finalSalary = teaSalary+expenseSalary;

//            int rateId, int employeeId, String employeeName, double teaSalary, double expenseSalary, double finalSalary, LocalDate date
            PaymentDTO paymentDTO = new PaymentDTO(selectRateid,selectEmpid,selecetEmpName,teaSalary,expenseSalary,finalSalary,LocalDate.now());
            boolean result = paymentModel.savePayment(paymentDTO);

            if (result) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success !");
                alert.setHeaderText("Payment Successfully.");
                alert.show();
                refreshTable();
//                clearFields();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error !");
                alert.setHeaderText("Payment Successfully.");
                alert.show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void tableSelection(PaymentDTO newValue){
//        Set Employee ComboBox
        for (EmployeeDTO empDTO : cmbEmployee.getItems()){
            if (empDTO.getId() == newValue.getEmployeeId()){
                cmbEmployee.setValue(empDTO);
            }
        }

//        Set Month ComboBox
        LocalDate date = newValue.getDate();
        String monthName = date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        monthCombo.setValue(monthName);

//        Set Tea Rate Date and Rate
        for (TeaRateDTO rateDTO : cmbTeaRate.getItems()){
            if (rateDTO.getRateId() == newValue.getRateId()){
                cmbTeaRate.setValue(rateDTO);
            }
        }

        txtTeaSalary.setText(String.valueOf(newValue.getTeaSalary()));
        txtOtherSalary.setText(String.valueOf(newValue.getExpenseSalary()));
        txtFinalSalary.setText(String.valueOf(newValue.getFinalSalary()));

    }



    @FXML
    private void updatePayment(){

    }

    @FXML
    private void deletePayment(){

    }

    private void refreshTable() {
        paymentDTOS.clear();
        paymentDTOS.addAll(loadPaymentTable());
        tableView.setItems(paymentDTOS);
    }

    private ObservableList<PaymentDTO> loadPaymentTable(){
        try {
            ObservableList<PaymentDTO> list = paymentModel.loadPaymentTable();
            return list;
        }catch (Exception e){
            return FXCollections.observableArrayList();
        }
    }

    private void loadOtherWorkByMonth(int selectedMonthNumber, int selectedEmpId) {
//        List<OtherWorkDTO> list = new ArrayList<>();

        try {

            DBConnection dbc = DBConnection.getInstance();
            Connection conn = dbc.getConnection();

            String sql = " SELECT Salary AS DbotherWorkSalary FROM OtherWork WHERE MONTH(Date) = ? AND Emp_ID = ?";

            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, selectedMonthNumber);
            pstm.setInt(2, selectedEmpId);

            ResultSet rs = pstm.executeQuery();

            boolean hasData = false ;

            double otherWorkSalary = 0;

            if (rs.next()) {
                hasData = true;
                otherWorkSalary = rs.getDouble("DbotherWorkSalary");

                System.out.printf(String.valueOf(otherWorkSalary));
            }
            txtOtherSalary.setText(String.valueOf(otherWorkSalary));

            if (!hasData){
                new Alert(Alert.AlertType.ERROR,"Has not Fields").show();
            }

        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e);
        }

//        return list;
    }

    private void loadTeaDataByMonth(int selectedMonthNumber,int selectedEmpId) {
//        List<DailyTeaDTO> list = new ArrayList<>();
        try {
            DBConnection dbc = DBConnection.getInstance();
            Connection conn = dbc.getConnection();

//           String sql = "SELECT * FROM Tea WHERE MONTH(Date_Collected) = ? AND YEAR(Date_Collected) = ? AND Emp_ID = ?"";

            String sql = "SELECT SUM(Total_Weight) AS totalWeight FROM Tea WHERE MONTH(Date_Collected) = ? AND Emp_ID = ?";


            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, selectedMonthNumber);
            pstm.setInt(2,selectedEmpId);


            ResultSet rs = pstm.executeQuery();

            boolean hasData = false ;

            double totalSalary = 0;

            if (rs.next()) {
                hasData = true;
                totalSalary = rs.getDouble("totalWeight");
            }
            txtTeaSalary.setText(String.valueOf(totalSalary));


            if (!hasData){
                new Alert(Alert.AlertType.ERROR,"Has not Fields").show();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
//        return list;
    }

    private void setTableColumn() {

//        int paymentId, int rateId, int employeeId, String employeeName, double teaSalary, double expenseSalary, double finalSalary, LocalDate date

        col_id.setCellValueFactory(new PropertyValueFactory<PaymentDTO,Integer>("paymentId"));
        col_rateId.setCellValueFactory(new PropertyValueFactory<PaymentDTO,Integer>("rateId"));
        col_empId.setCellValueFactory(new PropertyValueFactory<PaymentDTO,Integer>("employeeId"));
        col_empName.setCellValueFactory(new PropertyValueFactory<PaymentDTO,String>("employeeName"));
        col_teaSalary.setCellValueFactory(new PropertyValueFactory<PaymentDTO,Double>("teaSalary"));
        col_otherSalary.setCellValueFactory(new PropertyValueFactory<PaymentDTO,Double>("expenseSalary"));
        col_finalSalary.setCellValueFactory(new PropertyValueFactory<PaymentDTO,Double>("finalSalary"));
        col_paymentDate.setCellValueFactory(new PropertyValueFactory<PaymentDTO,LocalDate>("date"));

        tableView.setItems(loadPaymentTable());
    }

    private void loadTeaRateCombo() {

        try {
            ObservableList<TeaRateDTO> list = teaRateModel.loadTeaRate();
            cmbTeaRate.setItems(list);

            cmbTeaRate.setCellFactory(cb -> new ListCell<>() {
                @Override
                protected void updateItem(TeaRateDTO item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null : item.getMonth()+" - "+item.getYear());
                }
            });

            cmbTeaRate.setButtonCell(new ListCell<>() {
                @Override
                protected void updateItem(TeaRateDTO item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null : item.getMonth()+" - "+item.getYear());
                }
            });

            cmbTeaRate.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null) {
                    selectedTeaRate = newVal.getRate();
                    selectRateid = newVal.getRateId();
                    lblTeaRate.setText(String.valueOf(selectedTeaRate));
                }
            });



        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void loadMonths() {
        monthCombo.setItems(FXCollections.observableArrayList(
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        ));

        monthCombo.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                selectedMonth = Month.valueOf(newVal.toUpperCase());
                selectedMonthNumber = selectedMonth.getValue();
                selectedYear = LocalDate.now().getYear();
                System.out.println(selectedYear + " - " + selectedMonthNumber + " - " + selectedMonth); // JANUARY
            }
        });


    }

    private void loadEmployees() {
        try {
            ObservableList<EmployeeDTO> list = employeeModel.getAllEmployees();
            cmbEmployee.setItems(list);

            cmbEmployee.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null) {
                    selectEmpid = newVal.getId();
                    selecetEmpName = newVal.getName();
                    System.out.println(newVal.getName() + " - " + newVal.getId());
                }
            });

            cmbEmployee.setCellFactory(cb -> new ListCell<>() {
                @Override
                protected void updateItem(EmployeeDTO item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null : item.getName());
                }
            });

            cmbEmployee.setButtonCell(new ListCell<>() {
                @Override
                protected void updateItem(EmployeeDTO item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null : item.getName());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openTeaRateWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lk/ijse/ceylonteapay/TeaRate.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.setTitle("Tea Rate");
            stage.show();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

}
