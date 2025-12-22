package lk.ijse.ceylonteapay.model;

import lk.ijse.ceylonteapay.db.DBConnection;
import lk.ijse.ceylonteapay.dto.PaymentDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PaymentModel {
    public PaymentDTO getMonthlySalary(int empId, int year, int month) {

        String monthYear = year + "-" + String.format("%02d", month);

        String sql = """
        SELECT 
            e.EmpID,
            e.Name,

            IFNULL(SUM(t.Total_Weight) * r.ratePerKg, 0) AS teaSalary,
            IFNULL(SUM(ow.Salary), 0) AS otherWorkSalary,

            IFNULL(SUM(t.Total_Weight) * r.ratePerKg, 0)
            + IFNULL(SUM(ow.Salary), 0) AS finalSalary

        FROM Employee e

        LEFT JOIN Tea t 
            ON e.EmpID = t.Emp_ID
            AND MONTH(t.Date_Collected) = ?
            AND YEAR(t.Date_Collected) = ?

        LEFT JOIN TeaRate r 
            ON r.monthYear = ?

        LEFT JOIN OtherWork ow 
            ON e.EmpID = ow.Emp_ID
            AND MONTH(ow.Date) = ?
            AND YEAR(ow.Date) = ?

        WHERE e.EmpID = ?

        GROUP BY e.EmpID
        """;

        try (PreparedStatement ps = DBConnection.getInstance()
                .getConnection().prepareStatement(sql)) {

            ps.setInt(1, month);
            ps.setInt(2, year);
            ps.setString(3, monthYear);
            ps.setInt(4, month);
            ps.setInt(5, year);
            ps.setInt(6, empId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new PaymentDTO(
                        rs.getInt("EmpID"),
                        rs.getString("Name"),
                        rs.getDouble("teaSalary"),
                        rs.getDouble("otherWorkSalary"),
                        rs.getDouble("finalSalary")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new PaymentDTO(empId, "", 0, 0, 0);
    }

}
