package lk.ijse.ceylonteapay.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import lk.ijse.ceylonteapay.dto.TeaRateDTO;
import lk.ijse.ceylonteapay.model.TeaRateModel;


public class TeaRateController implements Initializable {

    @FXML
    private ComboBox<String> monthCombo;

    @FXML
    private ComboBox<Integer> yearCombo;

    @FXML
    private TextField txtTeaRate;

    @FXML
    private TableView<TeaRateDTO> teaRateTable;

    @FXML
    private TableColumn<TeaRateDTO,Integer> colId;
    @FXML
    private TableColumn<TeaRateDTO,String> colMonth;
    @FXML
    private TableColumn<TeaRateDTO,Integer> colYear;
    @FXML
    private TableColumn<TeaRateDTO,Double> colRate;

    private static TeaRateModel teaRateModel = new TeaRateModel();
    ObservableList<TeaRateDTO> teaRateDTOS = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setMonthAndYears();
        setTableColumns();
    }

    private void setTableColumns() {
        colId.setCellValueFactory(new PropertyValueFactory<TeaRateDTO,Integer>("rateId"));
        colMonth.setCellValueFactory(new PropertyValueFactory<TeaRateDTO,String>("month"));
        colYear.setCellValueFactory(new PropertyValueFactory<TeaRateDTO,Integer>("year"));
        colRate.setCellValueFactory(new PropertyValueFactory<TeaRateDTO,Double>("rate"));

        teaRateTable.setItems(loadTeaRate());


    }

    @FXML
    private void addRate(){
        try {
            String month = monthCombo.getValue();
            Integer year = yearCombo.getValue();
            double teaRate = Double.parseDouble(txtTeaRate.getText());

//        int rateId, String month, int year, double rate
            TeaRateDTO teaRateDTO = new TeaRateDTO(month, year, teaRate);
            boolean result = teaRateModel.addTeaRate(teaRateDTO);
            refreshTable();

            if (result){
                new Alert(Alert.AlertType.INFORMATION,"Tea Rate Added Successfully").show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteRate(){
        try {
            TeaRateDTO teaRateDTO = teaRateTable.getSelectionModel().getSelectedItem();
            if (teaRateDTO == null) {
                new Alert(Alert.AlertType.INFORMATION, "Please Select Table Column").show();
            } else {

                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setTitle("Confirm Delete");
                confirmAlert.setHeaderText("Delete Income Record");
                confirmAlert.setContentText(
                        "Are you sure you want to delete income ID: "
                                + teaRateDTO.getRateId() + " ?");

                Optional<ButtonType> result = confirmAlert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    int id = teaRateDTO.getRateId();
                    boolean success = teaRateModel.deleteRate(id);
                    refreshTable();
                    if (success){
                        new Alert(Alert.AlertType.ERROR,"Tea Rate Deleted Successfully").show();
                    }
                }



            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void refreshTable() {
        teaRateDTOS.clear();
        teaRateDTOS.addAll(loadTeaRate());
        teaRateTable.setItems(teaRateDTOS);
    }

    @FXML
    private void clearFields(){

    }

    private void setMonthAndYears() {
        // Month names
        monthCombo.setItems(FXCollections.observableArrayList(
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        ));

        // Year range
        ObservableList<Integer> years = FXCollections.observableArrayList();
        int currentYear = LocalDate.now().getYear();
        for (int i = currentYear - 1; i <= currentYear + 1; i++) {
            years.add(i);
        }
        yearCombo.setItems(years);
    }

    private ObservableList<TeaRateDTO> loadTeaRate(){
        try {
            ObservableList<TeaRateDTO> list = teaRateModel.loadTeaRate();
            return list;
        }catch (Exception e){
            return FXCollections.observableArrayList();
        }
    }



}
