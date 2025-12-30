package lk.ijse.ceylonteapay.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.ceylonteapay.db.DBConnection;
import lk.ijse.ceylonteapay.dto.IncomeDTO;
import lk.ijse.ceylonteapay.dto.PaymentDTO;
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

    public boolean savePayment(IncomeDTO incomeDTO)throws Exception{
        DBConnection dbc = DBConnection.getInstance();
        Connection conn = dbc.getConnection();

        String sql = "INSERT INTO Income (month, year, teaSalary, otherWorkSalary, thisMonthIncome, finalIncome) VALUES (?, ?, ?, ?, ?, ?) ";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1,incomeDTO.getMonth());
        pstm.setInt(2,incomeDTO.getYear());
        pstm.setDouble(3,incomeDTO.getTeaSalary());
        pstm.setDouble(4,incomeDTO.getOtherWorkSalary());
        pstm.setDouble(5,incomeDTO.getThisMonthIncome());
        pstm.setDouble(6,incomeDTO.getFinalIncome());

        int result = pstm.executeUpdate();

        return result>0 ;

    }

    public ObservableList<IncomeDTO> getAllIncomeFields() throws Exception {
        DBConnection dbc = DBConnection.getInstance();
        Connection conn = dbc.getConnection();

        String sql = "SELECT * FROM Income";

        PreparedStatement pstm = conn.prepareStatement(sql);

        ResultSet rs = pstm.executeQuery();

        ObservableList<IncomeDTO> list = FXCollections.observableArrayList();

//        int incomeId, String month, int year, double teaSalary, double otherWorkSalary, double thisMonthIncome, double finalIncome
        while (rs.next()){
            int incomeId = rs.getInt("incomeId");
            String month = rs.getString("Month");
            int year = rs.getInt("Year");
            double teaSalary = rs.getDouble("teaSalary");
            double otherWorkSalary = rs.getDouble("otherWorkSalary");
            double thisMonthIncome = rs.getDouble("thisMonthIncome");
            double finalIncome = rs.getDouble("finalIncome");

            IncomeDTO incomeDTO = new IncomeDTO(incomeId,month,year,teaSalary,otherWorkSalary,thisMonthIncome,finalIncome);
            list.add(incomeDTO);
        }
        return list;
    }

    public boolean deleteIncome(int id)throws Exception{
        DBConnection dbc = DBConnection.getInstance();
        Connection conn = dbc.getConnection();

        String sql = "DELETE FROM Income WHERE incomeId = ?";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1,id);

        int result = pstm.executeUpdate();

        return result>0;
    }
}
