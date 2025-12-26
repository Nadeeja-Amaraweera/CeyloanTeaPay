package lk.ijse.ceylonteapay.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.ceylonteapay.db.DBConnection;
import lk.ijse.ceylonteapay.dto.PaymentDTO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.Month;

public class PaymentModel {

    public boolean savePayment(PaymentDTO paymentDTO)throws Exception{
        DBConnection dbc = DBConnection.getInstance();
        Connection conn = dbc.getConnection();

        String sql = "INSERT INTO Payment (rateId, empId, empName, teaSalary, expenseSalary, finalSalary, SalaryMonth, Payment_Date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement pstm = conn.prepareStatement(sql);

//        int rateId, int employeeId, String employeeName, double teaSalary, double expenseSalary, double finalSalary, LocalDate date
        pstm.setInt(1,paymentDTO.getRateId());
        pstm.setInt(2,paymentDTO.getEmployeeId());
        pstm.setString(3,paymentDTO.getEmployeeName());
        pstm.setDouble(4,paymentDTO.getTeaSalary());
        pstm.setDouble(5,paymentDTO.getExpenseSalary());
        pstm.setDouble(6,paymentDTO.getFinalSalary());
        pstm.setString(7, String.valueOf(paymentDTO.getMonth()));
        pstm.setDate(8, Date.valueOf(paymentDTO.getDate()));

        int result = pstm.executeUpdate();

        return result>0;
    }

    public ObservableList<PaymentDTO> loadPaymentTable()throws Exception{
        DBConnection dbc = DBConnection.getInstance();
        Connection conn = dbc.getConnection();

        String sql = "SELECT * FROM Payment ORDER BY paymentId DESC";

        PreparedStatement pstm = conn.prepareStatement(sql);

        ResultSet rs  = pstm.executeQuery();

        ObservableList<PaymentDTO> list = FXCollections.observableArrayList();

        while (rs.next()){
            int payid = rs.getInt("paymentId");
            int rateid = rs.getInt("rateId");
            int empid = rs.getInt("empId");
            String empName = rs.getString("empName");
            double teaSalary = rs.getDouble("teaSalary");
            double otherSalary = rs.getDouble("expenseSalary");
            double finalSalary = rs.getDouble("finalSalary");
            String month = rs.getString("SalaryMonth");
            LocalDate payementDate = rs.getDate("Payment_Date").toLocalDate();

            PaymentDTO paymentDTO = new PaymentDTO(payid,rateid,empid,empName,teaSalary,otherSalary,finalSalary, Month.valueOf(month),payementDate);
            list.add(paymentDTO);
        }
        return list;
    }


}
