
package lk.ijse.ceylonteapay.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.ceylonteapay.dto.EmployeeDTO;
import lk.ijse.ceylonteapay.model.EmployeeModel;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable {

    @FXML
    private AnchorPane rootEmployee; // root of Employee.fxml

    @FXML
    private TextField employeeNameField;
    @FXML
    private TextField employeeNICField;
    @FXML
    private DatePicker employeeDateField;
    @FXML
    private TextField employeeAddressField;

    @FXML
    private TextField employeeTelField;

    @FXML
    private TableView<EmployeeDTO> tableView;
    @FXML
    private TableColumn<EmployeeDTO, Integer> col_id;
    @FXML
    private TableColumn<EmployeeDTO, String> col_Name;
    @FXML
    private TableColumn<EmployeeDTO, String> col_Nic;
    @FXML
    private TableColumn<EmployeeDTO, LocalDate> col_dob;
    @FXML
    private TableColumn<EmployeeDTO, String> col_address;
    @FXML
    private TableColumn<EmployeeDTO, String> col_gender;
    @FXML
    private TableColumn<EmployeeDTO, String> col_telNo;

    @FXML
    private RadioButton radioButtonMale;
    @FXML
    private RadioButton radioButtonFemale;

    private final EmployeeModel employeeModel = new EmployeeModel();

    ObservableList<EmployeeDTO> employeeDTOList = FXCollections.observableArrayList();

    // Employee Name – letters + spaces, min 3 chars
    private final String EMPLOYEE_NAME_REGEX = "^[A-Za-z ]{3,}$";

    // Employee NIC – Sri Lanka (old & new)
    private final String EMPLOYEE_NIC_REGEX = "^([0-9]{9}[VvXx]|[0-9]{12})$";

    // Address – letters, numbers, spaces, , . / -
    private final String EMPLOYEE_ADDRESS_REGEX = "^[A-Za-z0-9 ,./-]{5,}$";

    // Gender – Male or Female
    private final String EMPLOYEE_GENDER_REGEX = "^(Male|Female)$";

    // Telephone – Sri Lanka format
    private final String EMPLOYEE_TEL_REGEX = "^(0\\d{9}|\\+94\\d{9})$";


    @Override
    public void initialize(URL url, ResourceBundle rb) {


        // Set up the gender toggle group
        ToggleGroup genderGroup = new ToggleGroup();
        radioButtonMale.setToggleGroup(genderGroup);
        radioButtonFemale.setToggleGroup(genderGroup);

//        Set up the columns in the table
        col_id.setCellValueFactory(new PropertyValueFactory<EmployeeDTO, Integer>("id"));
        col_Name.setCellValueFactory(new PropertyValueFactory<EmployeeDTO, String>("name"));
        col_Nic.setCellValueFactory(new PropertyValueFactory<EmployeeDTO, String>("nic"));
        col_dob.setCellValueFactory(new PropertyValueFactory<EmployeeDTO, LocalDate>("dob"));
        col_address.setCellValueFactory(new PropertyValueFactory<EmployeeDTO, String>("address"));
        col_gender.setCellValueFactory(new PropertyValueFactory<EmployeeDTO, String>("gender"));
        col_telNo.setCellValueFactory(new PropertyValueFactory<EmployeeDTO, String>("telNo"));
//        Load Table data
        tableView.setItems(loadEmployees());

//        Table row Selection
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        setEmployeeDetails(newVal);
                    }
                }
        );
        clearFields();
    }

    //      Gender radio button
    @FXML
    private String getGender() {
        String employeeGender = "";
        if (radioButtonMale.isSelected()) {
            employeeGender = radioButtonMale.getText();
            System.out.println(radioButtonMale.getText());
        } else if (radioButtonFemale.isSelected()) {
            employeeGender = radioButtonFemale.getText();
            System.out.println(radioButtonFemale.getText());
        }
        return employeeGender;
    }

    //      Add Employee
    @FXML
    private void addEmployee() {

        String name = employeeNameField.getText();
        String nic = employeeNICField.getText();
//        Get Date Picker
        LocalDate date = employeeDateField.getValue();
        String address = employeeAddressField.getText();
        String gender = getGender();
        String telNo = employeeTelField.getText();

        if (!name.matches(EMPLOYEE_NAME_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Employee Name. Must be at least 3 letters and only contain letters and spaces.").show();
        } else if (!nic.matches(EMPLOYEE_NIC_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid NIC. Must be 9 digits + V/X or 12 digits.").show();
        } else if (date == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a date.").show();
        } else if (getGender() == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a gender.").show();
        } else if (!telNo.matches(EMPLOYEE_TEL_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Telephone. Must be 10 digits starting with 0 or in the format +94XXXXXXXXX.").show();

        } else {
            try {
                System.out.println("Name: " + name + " Address: " + address + " NIC: " + nic + " Gender: " + gender + " Tel: " + telNo);

                EmployeeDTO employeeDTO = new EmployeeDTO(name, date, nic, address, gender, telNo);
                boolean result = employeeModel.saveEmployee(employeeDTO);

                System.out.println("Add ok");

                refreshTable();
                clearFields();

                if (result) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success !");
                    alert.setHeaderText("Employee Added Successfully.");
                    alert.show();
                    refreshTable();
                    clearFields();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error !");
                    alert.setHeaderText("Employee Added Not Successfully.");
                    alert.show();
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);

            }
        }
    }

    //      Delete Employee
    @FXML
    private void deleteEmployee() {
        EmployeeDTO selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new Alert(Alert.AlertType.ERROR, "Please select an employee from the table!").show();
        } else {
            try {
                int id = selected.getId();

                boolean result = employeeModel.deleteEmployee(id);

                if (result) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success !");
                    alert.setHeaderText("Employee Deleted Successfully.");
                    alert.show();
                    refreshTable();
                    clearFields();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error !");
                    alert.setHeaderText("Employee Deleted Not Successfully.");
                    alert.show();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }


    }

    @FXML
    private void updateEmployee() {

        EmployeeDTO selected = tableView.getSelectionModel().getSelectedItem();
        String name = employeeNameField.getText();
        String nic = employeeNICField.getText();
//        Get Date Picker
        LocalDate date = employeeDateField.getValue();
        String address = employeeAddressField.getText();
        String gender = getGender();
        String telNo = employeeTelField.getText();

        if (selected == null) {
            new Alert(Alert.AlertType.ERROR, "Please select an employee from the table!").show();
        } else if (!name.matches(EMPLOYEE_NAME_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Employee Name. Must be at least 3 letters and only contain letters and spaces.").show();
        } else if (!nic.matches(EMPLOYEE_NIC_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid NIC. Must be 9 digits + V/X or 12 digits.").show();
        } else if (date == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a date.").show();
        } else if (getGender() == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a gender.").show();
        } else if (!telNo.matches(EMPLOYEE_TEL_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Telephone. Must be 10 digits starting with 0 or in the format +94XXXXXXXXX.").show();

        } else {
            try {

                int id = selected.getId();

                EmployeeDTO employeeDTO = new EmployeeDTO(id, name, date, nic, address, gender, telNo);
                boolean result = employeeModel.updateEmployee(employeeDTO);

                if (result) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success !");
                    alert.setHeaderText("Employee Updated Successfully.");
                    alert.show();
                    refreshTable();
                    clearFields();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error !");
                    alert.setHeaderText("Employee Updated Not Successfully.");
                    alert.show();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }


    }

    //      Refresh Table
    private void refreshTable() {
        employeeDTOList.clear();
        employeeDTOList.addAll(loadEmployees());
        tableView.setItems(employeeDTOList);
    }

    @FXML
    public void goBackHome() throws IOException {
        Stage stage = (Stage) rootEmployee.getScene().getWindow();
        stage.setScene(new Scene(
                FXMLLoader.load(getClass().getResource("/lk/ijse/ceylonteapay/Home.fxml"))
        ));
    }

    @FXML
    public void resetEmployee() {
        clearFields();
    }


    private void setEmployeeDetails(EmployeeDTO emp) {
        employeeNameField.setText(emp.getName());
        employeeNICField.setText(emp.getNic());
        employeeDateField.setValue(emp.getDob());
        employeeAddressField.setText(emp.getAddress());

        if (emp.getGender() != null) {
            if (emp.getGender().equalsIgnoreCase("Male")) {
                radioButtonMale.setSelected(true);
            } else if (emp.getGender().equalsIgnoreCase("Female")) {
                radioButtonFemale.setSelected(true);
            }
        }

        employeeTelField.setText(emp.getTelNo());
    }
//    This method will return an Observation of Employee Objects

    //    Get All Employees
    public ObservableList<EmployeeDTO> loadEmployees() {

        try {
            ObservableList<EmployeeDTO> employeeDTOList = employeeModel.getAllEmployees();
            // No need to copy into another list, can return directly
            return employeeDTOList;
        } catch (Exception e) {
            e.printStackTrace(); // or use JavaFX Alert
            return FXCollections.observableArrayList(); // empty list on error
        }

    }

    //    Clear fields
    private void clearFields() {
        employeeNameField.clear();
        employeeNICField.clear();
        employeeDateField.setValue(null);
        employeeAddressField.clear();
        radioButtonMale.setSelected(false);
        radioButtonFemale.setSelected(false);
        employeeTelField.clear();
    }
}
