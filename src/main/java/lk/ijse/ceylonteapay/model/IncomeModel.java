package lk.ijse.ceylonteapay.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.ceylonteapay.db.DBConnection;
import lk.ijse.ceylonteapay.dto.IncomeDTO;
import lk.ijse.ceylonteapay.dto.StockDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IncomeModel {

    public IncomeDTO getAllTeaSalary(int month, int year)throws Exception{
        DBConnection dbc = DBConnection.getInstance();
        Connection conn = dbc.getConnection();

        String sql = "SELECT SUM(teaSalary) AS total_tea, SUM(expenseSalary) AS total_expense FROM Payment WHERE MONTH(Payment_Date) = ? AND YEAR(Payment_Date) = ?";

        PreparedStatement pstm = conn.prepareStatement(sql);

        pstm.setInt(1,month);
        pstm.setInt(2,year);
        ResultSet rs = pstm.executeQuery();

        ObservableList<IncomeDTO> list = FXCollections.observableArrayList();

        while (rs.next()){
            double allTeaSalary = rs.getDouble("total_tea");
            double allOtherWorkSalary = rs.getDouble("total_expense");

            return new IncomeDTO(allTeaSalary,allOtherWorkSalary);
        }
        return new IncomeDTO(0,0);
    }

}
