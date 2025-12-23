
package lk.ijse.ceylonteapay.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.ceylonteapay.db.DBConnection;
import lk.ijse.ceylonteapay.dto.DailyTeaDTO;
import lk.ijse.ceylonteapay.dto.EmployeeDTO;
import lk.ijse.ceylonteapay.dto.OtherWorkDTO;
import lk.ijse.ceylonteapay.model.EmployeeModel;
import lk.ijse.ceylonteapay.model.PaymentModel;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class PaymentController implements Initializable {

    @FXML
    private ComboBox<EmployeeDTO> cmbEmployee;

    @FXML
    private ComboBox<String> monthCombo;

    @FXML
    private TextField txtTeaSalary;

    @FXML
    private TextField txtOtherSalary;

    @FXML
    private TextField txtFinalSalary;

    private static EmployeeModel employeeModel = new EmployeeModel();
    private static PaymentModel paymentModel = new PaymentModel();

    private int selectEmpid;
    private Month selectedMonth;
    private int selectedMonthNumber;
    private int selectedYear;

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

//   Change with plucking date


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadEmployees();
        loadMonths();


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
    private void calculateSalary() {

//        must need to get this year
        loadTeaDataByMonth(selectedMonthNumber,selectEmpid);
        loadOtherWorkByMonth(selectedMonthNumber,selectEmpid);


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


    @FXML
    private void savePayment() {
        // save to Payment table
    }

}
