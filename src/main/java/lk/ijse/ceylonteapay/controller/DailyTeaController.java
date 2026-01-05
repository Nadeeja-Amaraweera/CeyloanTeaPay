
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
import lk.ijse.ceylonteapay.db.DBConnection;
import lk.ijse.ceylonteapay.dto.DailyTeaDTO;
import lk.ijse.ceylonteapay.dto.EmployeeDTO;
import lk.ijse.ceylonteapay.dto.LandDTO;
import lk.ijse.ceylonteapay.model.DailyTeaModel;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

// Using Inheritance
public class DailyTeaController implements Initializable {

    @FXML
    private AnchorPane rootTea;

    @FXML
    private ComboBox<EmployeeDTO> cmbEmployeeIds;
    @FXML
    private ComboBox<LandDTO> cmbLandIds;
    @FXML
    private TextField txtTeaID;
    @FXML
    private TextField txtFullWeight;
    @FXML
    private TextField txtBagWeight;
    @FXML
    private TextField txtWaterWeight;
    @FXML
    private TextField txtTotalWeight;
    @FXML
    private DatePicker txtDate;
    @FXML
    private TextField txtQuality;
    @FXML
    private TableView<DailyTeaDTO> tableView;
    @FXML
    private TableColumn<DailyTeaDTO, Integer> col_TeaID;
    @FXML
    private TableColumn<DailyTeaDTO, Integer> col_EmployeeID;
    @FXML
    private TableColumn<DailyTeaDTO, String> col_EmployeeName;
    @FXML
    private TableColumn<DailyTeaDTO, Integer> col_AreaID;
    @FXML
    private TableColumn<DailyTeaDTO, String> col_AreaName;
    @FXML
    private TableColumn<DailyTeaDTO, LocalDate> col_Date;
    @FXML
    private TableColumn<DailyTeaDTO, Double> col_FullWeight;
    @FXML
    private TableColumn<DailyTeaDTO, Double> col_BagWeight;
    @FXML
    private TableColumn<DailyTeaDTO, Double> col_WaterWeight;
    @FXML
    private TableColumn<DailyTeaDTO, Double> col_TotalWeight;
    @FXML
    private TableColumn<DailyTeaDTO, String> col_Quality;

//   Using Abstraction
    private final DailyTeaModel dailyTeaModel = new DailyTeaModel();

    ObservableList<DailyTeaDTO> teaDTOObservableList = FXCollections.observableArrayList();

    private int selectedEmpId = -1;
    private String selectedEmpName = "";
    private int selectedLandId = -1;
    private String selectedAreaName = "";

//    Runtime Polymorphism
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadEmployeeIds();
        loadLands();
        selectionLandCombo();
        selectionEmpCombo();
        cmbEmployeeIds.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                selectedEmpId = newVal.getId();
                selectedEmpName = newVal.getName();
                System.out.println(newVal.getName() + " - " + newVal.getId());
            }
        });

        cmbLandIds.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                selectedLandId = newVal.getLndID();
                selectedAreaName = newVal.getLndName();
                System.out.println(newVal.getLndName() + " - " + newVal.getLndNo());
            }
        });

        tableView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        setTeaDetails(newVal);
                    }
                }
        );

        setTableColumns();

    }

    private void setTeaDetails(DailyTeaDTO newVal) {
        System.out.println(newVal.getEmpID());
        // Set Employee Combo by ID
        for (EmployeeDTO emp : cmbEmployeeIds.getItems()) {
            if (emp.getId() == newVal.getEmpID()) {
                cmbEmployeeIds.setValue(emp);
                break;
            }
        }

        // Set Land Combo by ID
        System.out.println(newVal.getLndID());
        for (LandDTO land : cmbLandIds.getItems()) {
            if (land.getLndID() == newVal.getLndID()) {
                cmbLandIds.setValue(land);
                break;
            }
        }

        txtTeaID.setText(String.valueOf(newVal.getTeaID()));
        txtDate.setValue(newVal.getDateCollected());
        txtQuality.setText(newVal.getQuality());
        txtFullWeight.setText(String.valueOf(newVal.getFullWeight()));
        txtWaterWeight.setText(String.valueOf(newVal.getWaterWeight()));
        txtTotalWeight.setText(String.valueOf(newVal.getTotalWeight()));
        txtBagWeight.setText(String.valueOf(newVal.getBagWeight()));
    }

    private void setTableColumns() {
        col_TeaID.setCellValueFactory(new PropertyValueFactory<DailyTeaDTO, Integer>("teaID"));
        col_EmployeeID.setCellValueFactory(new PropertyValueFactory<DailyTeaDTO, Integer>("empID"));
        col_EmployeeName.setCellValueFactory(new PropertyValueFactory<DailyTeaDTO, String>("empName"));
        col_AreaID.setCellValueFactory(new PropertyValueFactory<DailyTeaDTO, Integer>("lndID"));
        col_AreaName.setCellValueFactory(new PropertyValueFactory<DailyTeaDTO, String>("lndName"));
        col_Date.setCellValueFactory(new PropertyValueFactory<DailyTeaDTO, LocalDate>("dateCollected"));
        col_FullWeight.setCellValueFactory(new PropertyValueFactory<DailyTeaDTO, Double>("fullWeight"));
        col_BagWeight.setCellValueFactory(new PropertyValueFactory<DailyTeaDTO, Double>("bagWeight"));
        col_WaterWeight.setCellValueFactory(new PropertyValueFactory<DailyTeaDTO, Double>("waterWeight"));
        col_TotalWeight.setCellValueFactory(new PropertyValueFactory<DailyTeaDTO, Double>("totalWeight"));
        col_Quality.setCellValueFactory(new PropertyValueFactory<DailyTeaDTO, String>("Quality"));

        tableView.setItems(loadTeaFields());
    }

    @FXML
    private void goBackHome() throws IOException {
        Stage stage = (Stage) rootTea.getScene().getWindow();
        stage.setScene(new Scene(
                FXMLLoader.load(getClass().getResource("/lk/ijse/ceylonteapay/Home.fxml"))
        ));
    }

    @FXML
    private void addFields() {
        try {
            if (validation()) {
                String quality = txtQuality.getText();
                double fullWeight = Double.parseDouble(txtFullWeight.getText());
                double bagWeight = Double.parseDouble(txtBagWeight.getText());
                double waterWeight = Double.parseDouble(txtWaterWeight.getText());
                double totalWeight = fullWeight - (bagWeight + waterWeight);
                LocalDate date = txtDate.getValue();

                DailyTeaDTO teaDTO = new DailyTeaDTO(selectedEmpId, selectedLandId, selectedEmpName, selectedAreaName, date, fullWeight, bagWeight, waterWeight, totalWeight, quality);
//                Using Abstraction
                boolean result = dailyTeaModel.addTeaField(teaDTO);

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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void getName() {
        try {
            DBConnection dbc = DBConnection.getInstance();
            Connection conn = dbc.getConnection();

            String sql = "SELECT Tea.Tea_ID, Employee.Name AS EmployeeName, Land.LandName AS LandName, " +
                    "Tea.Date_Collected, Tea.Total_Weight, Tea.Quality " +
                    "FROM Tea " +
                    "JOIN Employee ON Tea.Emp_ID = Employee.EmpID " +
                    "JOIN Land ON Tea.Lnd_ID = Land.LndID";

            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int teaId = rs.getInt("Tea_ID");
                String empName = rs.getString("EmployeeName");
                String landName = rs.getString("LandName");

                System.out.println(teaId + " - " + empName + " - " + landName);
            }


        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void clearFields() {
        txtTeaID.clear();
        txtQuality.clear();
        txtFullWeight.clear();
        txtBagWeight.clear();
        txtWaterWeight.clear();
        tableView.getSelectionModel().clearSelection();

        txtDate.setValue(null);       // DatePicker clear

        cmbEmployeeIds.getSelectionModel().clearSelection();  // ComboBox clear
        cmbLandIds.getSelectionModel().clearSelection();      // ComboBox clear
    }

    @FXML
    private void reset() {
        clearFields();
    }

    @FXML
    private void updateFields() {
        if (validation()) {
            int teaId = Integer.parseInt(txtTeaID.getText());
            String quality = txtQuality.getText();
            double fullWeight = Double.parseDouble(txtFullWeight.getText());
            double bagWeight = Double.parseDouble(txtBagWeight.getText());
            double waterWeight = Double.parseDouble(txtWaterWeight.getText());
            double totalWeight = fullWeight - (bagWeight + waterWeight);
            LocalDate date = txtDate.getValue();
            try {

                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setTitle("Confirm Update");
                confirmAlert.setHeaderText("Update Tea Plucking Record");
                confirmAlert.setContentText(
                        "Are you sure you want to Update income ID: "
                                + teaId + " ?");

                Optional<ButtonType> confirm = confirmAlert.showAndWait();

                if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
                    if (checkTeaID(teaId).next()) {

                        DailyTeaDTO teaDTO = new DailyTeaDTO(teaId, selectedEmpId, selectedLandId, date, fullWeight, bagWeight, waterWeight, totalWeight, quality);
                        boolean result = dailyTeaModel.updateTeaField(teaDTO);

                        if (result) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Success !");
                            alert.setHeaderText(selectedEmpId + " : Employee Updated Successfully.");
                            alert.show();
                            refreshTable();
                            clearFields();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error !");
                            alert.setHeaderText(selectedEmpId + " : Employee Updated Not Successfully.");
                            alert.show();
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error !");
                        alert.setHeaderText(teaId + " : TeaID Is Not Found.");
                        alert.show();
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void delete() {
        DailyTeaDTO selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            new Alert(Alert.AlertType.ERROR, "Please select an employee from the table!").show();
        } else {
            try {

                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setTitle("Confirm Delete");
                confirmAlert.setHeaderText("Delete Tea Plucking Record");
                confirmAlert.setContentText(
                        "Are you sure you want to delete Tea ID: "
                                + selectedItem.getTeaID() + " ?");

                Optional<ButtonType> confirm = confirmAlert.showAndWait();

                if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
                    int teaID = selectedItem.getTeaID();
                    if (checkTeaID(teaID).next()) {
                        boolean result = dailyTeaModel.deleteTeaField(teaID);

                        if (result) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Success !");
                            alert.setHeaderText(teaID + ": Tea Field Deleted Successfully.");
                            alert.show();
                            refreshTable();
                            clearFields();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error !");
                            alert.setHeaderText(teaID + ": Tea Field Deleted Not Successfully.");
                            alert.show();
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error !");
                        alert.setHeaderText(teaID + " : TeaID Is Not Found.");
                        alert.show();
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    private ResultSet checkTeaID(int teaId) throws Exception {

        ResultSet result = dailyTeaModel.checkTeaId(teaId);

        return result;
    }

    private void refreshTable() {
        teaDTOObservableList.clear();
        teaDTOObservableList.addAll(loadTeaFields());
        tableView.setItems(teaDTOObservableList);
    }

    private boolean validation() {
        if (cmbEmployeeIds.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty");
            alert.setHeaderText("Employee is empty");
            alert.show();
            cmbEmployeeIds.requestFocus();
            return false;
        }
        if (cmbLandIds.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty");
            alert.setHeaderText("Area is empty");
            alert.show();
            cmbLandIds.requestFocus();
            return false;
        }
        if (txtDate.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty");
            alert.setHeaderText("Date is empty");
            alert.show();
            txtDate.requestFocus();
            return false;
        }
        LocalDate today = LocalDate.now();

        if (txtDate.getValue().isAfter(today)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Date");
            alert.setHeaderText("Future dates are not allowed");
            alert.show();
            txtDate.requestFocus();
            return false;
        }
        if (txtFullWeight.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty");
            alert.setHeaderText("Full Weight is empty");
            alert.show();
            txtFullWeight.requestFocus();
            return false;
        }
        if (!txtFullWeight.getText().matches("\\d+(\\.\\d+)?")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Full Weight must be a number!");
            alert.show();
            txtFullWeight.requestFocus();
            return false;
        }
        if (txtBagWeight.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty");
            alert.setHeaderText("Bag Weight is empty");
            alert.show();
            txtBagWeight.requestFocus();
            return false;
        }
        if (!txtBagWeight.getText().matches("\\d+(\\.\\d+)?")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Bag Weight must be a number!");
            alert.show();
            txtBagWeight.requestFocus();
            return false;
        }
        if (txtWaterWeight.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty");
            alert.setHeaderText("Water Weight is empty");
            alert.show();
            txtWaterWeight.requestFocus();
            return false;
        }
        if (!txtWaterWeight.getText().matches("\\d+(\\.\\d+)?")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Water Weight must be a number!");
            alert.show();
            txtWaterWeight.requestFocus();
            return false;
        }
        if (txtQuality.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty");
            alert.setHeaderText("Quality is empty");
            alert.show();
            txtQuality.requestFocus();
            return false;
        }
        return true;
    }

    private ObservableList<DailyTeaDTO> loadTeaFields() {
        try {
            ObservableList<DailyTeaDTO> teaDTOS = dailyTeaModel.getAllTeaFields();
            return teaDTOS;
        } catch (Exception e) {
            e.printStackTrace();
            return FXCollections.observableArrayList();
        }
    }

    private void selectionEmpCombo() {
        cmbEmployeeIds.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            try {
                if (newVal != null) {
                    int id = newVal.getId();
                    System.out.println("Selected Name: " + newVal.getName());

                    try {
                        ResultSet result = dailyTeaModel.getEmployeeNameCombo(id);

                        if (result.next()) {
                            System.out.println("Selection Successfully" + id);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            } catch (Exception e) {

            }

        });
    }

    private void selectionLandCombo() {
        cmbLandIds.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            try {
                if (newVal != null) {
                    int id = newVal.getLndID();
                    System.out.println("Selected Name: " + newVal.getLndName());

                    try {
                        ResultSet result = dailyTeaModel.getLandNameCombo(id);

                        if (result.next()) {
                            System.out.println("Selection Successfully" + id);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            } catch (Exception e) {

            }
        });
    }

    private void loadEmployeeIds() {
        try {
            ObservableList<EmployeeDTO> idList = dailyTeaModel.getEmployeeId();
            cmbEmployeeIds.setItems(idList);

            // Show only ID + Name in ComboBox
            cmbEmployeeIds.setCellFactory(cb -> new ListCell<>() {
                @Override
                protected void updateItem(EmployeeDTO item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getId() + " - " + item.getName());
                    }
                }
            });

            // Also show selected value correctly
            cmbEmployeeIds.setButtonCell(new ListCell<>() {
                @Override
                protected void updateItem(EmployeeDTO item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getId() + " - " + item.getName());
                    }
                }
            });

        } catch (Exception e) {

        }
    }

    private void loadLands() {
        try {
            ObservableList<LandDTO> landDTOObservableList = dailyTeaModel.getLandId();
            cmbLandIds.setItems(landDTOObservableList);

            // Show only land Name + No in ComboBox
            cmbLandIds.setCellFactory(cb -> new ListCell<>() {
                @Override
                protected void updateItem(LandDTO item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getLndName() + " - " + item.getLndNo());
                    }
                }
            });

            // Also show selected value correctly
            cmbLandIds.setButtonCell(new ListCell<>() {
                @Override
                protected void updateItem(LandDTO item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getLndName() + " - " + item.getLndNo());
                    }
                }
            });
        } catch (Exception e) {

        }

    }

}
