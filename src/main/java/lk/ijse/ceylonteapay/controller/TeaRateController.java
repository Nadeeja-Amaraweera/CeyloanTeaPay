package lk.ijse.ceylonteapay.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;


public class TeaRateController implements Initializable {

    @FXML
    private ComboBox<String> monthCombo;

    @FXML
    private ComboBox<Integer> yearCombo;

    @FXML
    private TextField txtTeaRate;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setMonthAndYears();
    }

    @FXML
    private void addRate(){
        String month = monthCombo.getValue();
        Integer year = yearCombo.getValue();
        double teaRate = Double.parseDouble(txtTeaRate.getText());

        System.out.println(month+" - "+year+" - "+teaRate);
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
