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
import lk.ijse.ceylonteapay.dto.LandDTO;
import lk.ijse.ceylonteapay.model.LandModel;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class LandController implements Initializable {
    @FXML
    private AnchorPane rootLand;
    @FXML
    private TextField txtLandNameField;
    @FXML
    private TextField txtLandNoField;
    @FXML
    private TableColumn<LandDTO, Integer> col_id;
    @FXML
    private TableColumn<LandDTO, String> col_Name;
    @FXML
    private TableColumn<LandDTO, String> col_No;
    @FXML
    private TableView<LandDTO> tableView;

    private final LandModel landModel = new LandModel();

    ObservableList<LandDTO> landDTOObservableList = FXCollections.observableArrayList();

    // Land Name – letters + spaces, minimum 3 characters
    private final String LAND_NAME_REGEX = "^[A-Za-z0-9 ]{3,}$";

    // Land Number – letters, numbers, dash, slash, minimum 1 character
    private final String LAND_NO_REGEX = "^[A-Za-z0-9/-]{1,}$";


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        col_id.setCellValueFactory(new PropertyValueFactory<LandDTO, Integer>("lndID"));
        col_Name.setCellValueFactory(new PropertyValueFactory<LandDTO, String>("lndName"));
        col_No.setCellValueFactory(new PropertyValueFactory<LandDTO, String>("lndNo"));

        tableView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldvalue, newvalue) -> {
                    if (newvalue != null) {
                        setLandDetails(newvalue);
                    }
                }
        );
        tableView.setItems(loadLands());
    }

    private void setLandDetails(LandDTO landDTO) {
        txtLandNameField.setText(landDTO.getLndName());
        txtLandNoField.setText(landDTO.getLndNo());
    }

    @FXML
    public void addLand() {
        String landName = txtLandNameField.getText();
        String landNo = txtLandNoField.getText();
        System.out.println("Name: " + landName + " No: " + landNo);

        if (!landName.matches(LAND_NAME_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Land Name. Must be at least 3 letters and only contain letters and spaces.").show();

        } else if (!landNo.matches(LAND_NO_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Land Number. Can contain letters, numbers, dash, or slash.").show();

        } else {
            try {
                LandDTO landDTO = new LandDTO(landName, landNo);
                boolean result = landModel.saveLand(landDTO);

                if (result) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Land Save");
                    alert.setContentText("Land save successfully !");
                    alert.show();
                    clearFields();
                }
                refreshTable();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    @FXML
    public void updateLand() {
        LandDTO selected = tableView.getSelectionModel().getSelectedItem();
        String landName = txtLandNameField.getText();
        String landNo = txtLandNoField.getText();

        if (selected == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a land from the table!").show();
        } else if (!landName.matches(LAND_NAME_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Land Name. Must be at least 3 letters and only contain letters and spaces.").show();
        } else if (!landNo.matches(LAND_NO_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Land Number. Can contain letters, numbers, dash, or slash.").show();
        } else {

            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Update");
            confirmAlert.setHeaderText("Update Area Record");
            confirmAlert.setContentText(
                    "Are you sure you want to Update Area Name: "
                            + selected.getLndName() + " ?");

            Optional<ButtonType> confirm = confirmAlert.showAndWait();

            if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
                try {

                    int id = selected.getLndID();
                    System.out.println(id);

                    LandDTO landDTO = new LandDTO(id, landName, landNo);
                    boolean result = landModel.updateLand(landDTO);

                    if (result) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success !");
                        alert.setHeaderText("Land Updated Successfully.");
                        alert.show();
                        refreshTable();
                        clearFields();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error !");
                        alert.setHeaderText("Land Updated Not Successfully.");
                        alert.show();
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }


        }


    }

    @FXML
    public void deleteLand() {
        LandDTO selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a land from the table!").show();
        } else {

            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Delete");
            confirmAlert.setHeaderText("Delete Area Record");
            confirmAlert.setContentText(
                    "Are you sure you want to delete Area Name: "
                            + selected.getLndName() + " ?");

            Optional<ButtonType> confirm = confirmAlert.showAndWait();

            if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
                try {
                    int id = selected.getLndID();
                    boolean result = landModel.deleteLand(id);
                    if (result) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success !");
                        alert.setHeaderText("Land Deleted Successfully.");
                        alert.show();
                        refreshTable();
                        clearFields();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error !");
                        alert.setHeaderText("Land Deleted Not Successfully.");
                        alert.show();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }


        }
    }

    @FXML
    public void reset() {
        clearFields();
    }

    public void exit() {
        System.exit(0);
    }

    private void clearFields() {
        txtLandNameField.setText("");
        txtLandNoField.setText("");
    }

    private void refreshTable() {
        landDTOObservableList.clear();
        landDTOObservableList.addAll(loadLands());
        tableView.setItems(landDTOObservableList);
    }

    @FXML
    public void goBackHome() throws IOException {
        Stage stage = (Stage) rootLand.getScene().getWindow();
        stage.setScene(new Scene(
                FXMLLoader.load(getClass().getResource("/lk/ijse/ceylonteapay/Home.fxml"))
        ));
    }

    public ObservableList<LandDTO> loadLands() {
        try {
            ObservableList<LandDTO> landDTOObservableList = landModel.getAllLands();
            // No need to copy into another list, can return directly
            return landDTOObservableList;
        } catch (Exception e) {
            e.printStackTrace(); // or use JavaFX Alert
            return FXCollections.observableArrayList(); // empty list on error
        }
    }

}
