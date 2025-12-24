package lk.ijse.ceylonteapay.model;

import lk.ijse.ceylonteapay.db.DBConnection;
import lk.ijse.ceylonteapay.dto.PaymentDTO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PaymentModel {

    public boolean savePayment(PaymentDTO paymentDTO)throws Exception{
        DBConnection dbc = DBConnection.getInstance();
        Connection conn = dbc.getConnection();

        String sql = "INSERT INTO Payment (rateId, empId, empName, teaSalary, expenseSalary, finalSalary, Payment_Date) VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement pstm = conn.prepareStatement(sql);

//        int rateId, int employeeId, String employeeName, double teaSalary, double expenseSalary, double finalSalary, LocalDate date
        pstm.setInt(1,paymentDTO.getRateId());
        pstm.setInt(2,paymentDTO.getEmployeeId());
        pstm.setString(3,paymentDTO.getEmployeeName());
        pstm.setDouble(4,paymentDTO.getTeaSalary());
        pstm.setDouble(5,paymentDTO.getExpenseSalary());
        pstm.setDouble(6,paymentDTO.getFinalSalary());
        pstm.setDate(7, Date.valueOf(paymentDTO.getDate()));

        int result = pstm.executeUpdate();

        return result>0;
    }


}
