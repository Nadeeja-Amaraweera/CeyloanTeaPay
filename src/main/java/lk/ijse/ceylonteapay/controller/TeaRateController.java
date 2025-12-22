package lk.ijse.ceylonteapay.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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

    private static TeaRateModel teaRateModel = new TeaRateModel();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setMonthAndYears();
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

            if (result){
                new Alert(Alert.AlertType.INFORMATION,"Tea Rate Added Successfully").show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
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



}
