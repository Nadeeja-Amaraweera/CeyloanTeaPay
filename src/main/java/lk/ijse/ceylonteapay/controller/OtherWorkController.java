
package lk.ijse.ceylonteapay.controller;

import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

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
import lk.ijse.ceylonteapay.dto.LandDTO;
import lk.ijse.ceylonteapay.dto.OtherWorkDTO;
import lk.ijse.ceylonteapay.model.OtherWorkModel;

import javax.swing.*;


public class OtherWorkController implements Initializable {
    @FXML
    private AnchorPane rootWork;
    @FXML
    private Label txtWorkID;
    @FXML
    private ComboBox<EmployeeDTO>  cmbEmployeeIds;
    @FXML
    private ComboBox<LandDTO> cmbLandIds;
    @FXML
    private DatePicker txtDate;
    @FXML
    private TextArea txtDetails;
    @FXML
    private TextField txtSalary;
    @FXML
    private TableView<OtherWorkDTO> tableView;
    @FXML
    private TableColumn<OtherWorkDTO,Integer> col_WorkID;
    @FXML
    private TableColumn<OtherWorkDTO,Integer> col_EmployeeID;
    @FXML
    private TableColumn<OtherWorkDTO,String> col_EmployeeName;
    @FXML
    private TableColumn<OtherWorkDTO,Integer> col_AreaID;
    @FXML
    private TableColumn<OtherWorkDTO,String> col_AreaName;
    @FXML
    private TableColumn<OtherWorkDTO,LocalDate> col_Date;
    @FXML
    private TableColumn<OtherWorkDTO,String> col_Details;
    @FXML
    private TableColumn<OtherWorkDTO,Double> col_Salary;

    private final OtherWorkModel otherWorkModel = new OtherWorkModel();

    ObservableList<OtherWorkDTO> otherWorkObservableList = FXCollections.observableArrayList();

    private int selectedEmpId = -1;
    private String selectedEmpName = "";
    private int selectedLandId = -1;
    private String selectedAreaName = "";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadEmployeeIds();
        loadLands();
        selectionLandCombo();
        selectionEmpCombo();
        cmbEmployeeIds.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal!=null){
                selectedEmpId = newVal.getId();
                selectedEmpName = newVal.getName()+" - "+newVal.getId();
                System.out.println(newVal.getName()+" - "+newVal.getId());
            }
        });

        cmbLandIds.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal!=null){
                selectedLandId = newVal.getLndID();
                selectedAreaName = newVal.getLndName()+" - "+newVal.getLndNo();
                System.out.println(newVal.getLndName()+" - "+newVal.getLndNo());
            }
        });
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        setOtherWorkDetails(newVal);
                    }
                }
        );

        setTableColumns();
    }

    private void setOtherWorkDetails(OtherWorkDTO newVal) {
        // Set Employee Combo by ID
        System.out.println(newVal.getEmp_ID());
        for (EmployeeDTO emp : cmbEmployeeIds.getItems()) {
            if (emp.getId() == newVal.getEmp_ID()) {
                cmbEmployeeIds.setValue(emp);
                break;
            }
        }

        // Set Land Combo by ID
        System.out.println(newVal.getLnd_Id());
        for (LandDTO land : cmbLandIds.getItems()) {
            if (land.getLndID() == newVal.getLnd_Id()) {
                cmbLandIds.setValue(land);
                break;
            }
        }

        txtWorkID.setText(String.valueOf(newVal.getWorkID()));
        txtDate.setValue(newVal.getDate());
        txtDetails.setText(newVal.getDetails());
        txtSalary.setText(String.valueOf(newVal.getSalary()));

    }

    @FXML
    private void addFields(){

        try {
            if (validation()) {
                LocalDate date = txtDate.getValue();
                String details = txtDetails.getText();
                double salary = Double.parseDouble(txtSalary.getText());

                OtherWorkDTO otherWorkDTO = new OtherWorkDTO(selectedEmpId, selectedLandId, date, details,salary);
                boolean result = otherWorkModel.addWorkField(otherWorkDTO);

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
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    private void updateField(){
        try {
            if (!txtWorkID.getText().isEmpty()) {
                if (validation()) {
                    int workId = Integer.parseInt(txtWorkID.getText());
                    LocalDate date = txtDate.getValue();
                    String details = txtDetails.getText();
                    double salary = Double.parseDouble(txtSalary.getText());


                    OtherWorkDTO otherWorkDTO = new OtherWorkDTO(workId, selectedEmpId, selectedLandId, date, details,salary);
                    boolean result = otherWorkModel.updateWorkField(otherWorkDTO);

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
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Empty");
                alert.setHeaderText("Work id is empty");
                alert.show();
                txtWorkID.requestFocus();
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }

    private boolean validation() {
        LocalDate date = txtDate.getValue();
        String details = txtDetails.getText();
        String salary = txtSalary.getText();


        if (cmbEmployeeIds.getValue()==null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty");
            alert.setHeaderText("Employee is empty");
            alert.show();
            cmbEmployeeIds.requestFocus();
            return false;
        }
        if (cmbLandIds.getValue()==null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty");
            alert.setHeaderText("Area is empty");
            alert.show();
            cmbLandIds.requestFocus();
            return false;
        }
        if (date==null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty");
            alert.setHeaderText("Date is empty");
            alert.show();
            txtDate.requestFocus();
            return false;
        }
        if (details.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty");
            alert.setHeaderText("Details is empty");
            alert.show();
            txtDetails.requestFocus();
            return false;
        }
        if (salary.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty");
            alert.setHeaderText("Salary is empty");
            alert.show();
            txtSalary.requestFocus();
            return false;
        }
        return true;
    }

    @FXML
    private void deleteField(){
        try {
            if (!txtWorkID.getText().isEmpty()) {
                OtherWorkDTO selected = tableView.getSelectionModel().getSelectedItem();

                int id = selected.getWorkID();
                boolean result = otherWorkModel.deleteWorkField(id);

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
            }else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Empty");
                alert.setHeaderText("Work id is empty please select in table");
                alert.show();
                txtWorkID.requestFocus();
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }

    private void clearFields() {
        txtWorkID.setText("No id is here");
        txtDetails.clear();
        txtDate.setValue(null);
        txtSalary.clear();
        cmbEmployeeIds.getSelectionModel().clearSelection();
        cmbLandIds.getSelectionModel().clearSelection();
    }

    @FXML
    private void reset(){
        clearFields();
    }

    private void refreshTable(){
        otherWorkObservableList.clear();
        otherWorkObservableList.addAll(loadOtherWorkFields());
        tableView.setItems(otherWorkObservableList);
    }
    private void setTableColumns(){
        col_WorkID.setCellValueFactory(new PropertyValueFactory<OtherWorkDTO,Integer>("workID"));
        col_EmployeeID.setCellValueFactory(new PropertyValueFactory<OtherWorkDTO,Integer>("emp_ID"));
        col_EmployeeName.setCellValueFactory(new PropertyValueFactory<OtherWorkDTO,String>("empName"));
        col_AreaID.setCellValueFactory(new PropertyValueFactory<OtherWorkDTO,Integer>("lnd_Id"));
        col_AreaName.setCellValueFactory(new PropertyValueFactory<OtherWorkDTO,String>("lndName"));
        col_Date.setCellValueFactory(new PropertyValueFactory<OtherWorkDTO,LocalDate>("date"));
        col_Details.setCellValueFactory(new PropertyValueFactory<OtherWorkDTO,String>("details"));
        col_Salary.setCellValueFactory(new PropertyValueFactory<OtherWorkDTO,Double>("salary"));

        tableView.setItems(loadOtherWorkFields());
    }

    private void selectionEmpCombo() {
        cmbEmployeeIds.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            try {
                if (newVal != null) {
                    int id = newVal.getId();
                    System.out.println("Selected Name: " + newVal.getName());

                    try {
                        ResultSet result = otherWorkModel.getEmployeeNameCombo(id);

                        if (result.next()){
                            System.out.println("Selection Successfully"+id);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }catch (Exception e){

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
                        ResultSet result = otherWorkModel.getLandNameCombo(id);

                        if (result.next()){
                            System.out.println("Selection Successfully"+id);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }catch (Exception e){

            }
        });
    }

    private void loadEmployeeIds() {
        try {
            ObservableList<EmployeeDTO> idList = otherWorkModel.getEmployeeId();
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

        }catch (Exception e){

        }
    }

    private void loadLands(){
        try {
            ObservableList<LandDTO> landDTOObservableList = otherWorkModel.getLandId();
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
        }catch (Exception e){

        }

    }

    public ObservableList<OtherWorkDTO> loadOtherWorkFields() {
        try {
            ObservableList<OtherWorkDTO> teaDTOS = otherWorkModel.getAllOtherWorkFields();
            return teaDTOS;
        } catch (Exception e) {
            e.printStackTrace();
            return FXCollections.observableArrayList();
        }
    }

    @FXML
    public void goBackHome()throws Exception{
        Stage stage = (Stage) rootWork.getScene().getWindow();
        stage.setScene(new Scene(
                FXMLLoader.load(getClass().getResource("/lk/ijse/ceylonteapay/Home.fxml"))
        ));
    }
    
}
