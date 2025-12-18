
package lk.ijse.ceylonteapay.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import lk.ijse.ceylonteapay.dto.StockDTO;
import lk.ijse.ceylonteapay.model.StockModel;


public class IcomeController implements Initializable {

    private static StockModel stockModel = new StockModel();


    @FXML
    private BarChart<String, Number> stockBarChart;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadStockChart();
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


}
