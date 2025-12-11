package lk.ijse.ceylonteapay.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.ceylonteapay.db.DBConnection;
import lk.ijse.ceylonteapay.dto.EmployeeDTO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class EmployeeModel {

    public boolean saveEmployee(EmployeeDTO employeeDTO)throws Exception{
        DBConnection dbc = DBConnection.getInstance();
        Connection conn = dbc.getConnection();

        String sql = "INSERT INTO Employee (Name,NIC,Dob,Address,Gender,TelNo) VALUES (?,?,?,?,?,?)";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1,employeeDTO.getName());
        pstm.setString(2,employeeDTO.getNic());
        pstm.setDate(3, Date.valueOf(employeeDTO.getDob()));
        pstm.setString(4,employeeDTO.getAddress());
        pstm.setString(5,employeeDTO.getGender());
        pstm.setString(6,employeeDTO.getTelNo());

        int result = pstm.executeUpdate();

        return result>0;
    }

    public boolean deleteEmployee(int id)throws Exception{
        DBConnection dbc = DBConnection.getInstance();
        Connection conn = dbc.getConnection();

        String sql = "DELETE FROM Employee WHERE EmpID = ?";
        PreparedStatement pstm = conn.prepareStatement(sql);

        pstm.setInt(1,id);

        int result = pstm.executeUpdate();

        return result>0;
    }

    public boolean updateEmployee(EmployeeDTO employeeDTO)throws Exception{
        DBConnection dbc = DBConnection.getInstance();
        Connection conn = dbc.getConnection();

        String sql = "UPDATE Employee SET Name = ?, NIC = ?, Dob = ?, Address = ?, Gender = ?, TelNo = ? WHERE EmpID = ?";

        PreparedStatement pstm = conn.prepareStatement(sql);

        pstm.setString(1,employeeDTO.getName());
        pstm.setString(2,employeeDTO.getNic());
        pstm.setDate(3, Date.valueOf(employeeDTO.getDob()));
        pstm.setString(4,employeeDTO.getAddress());
        pstm.setString(5,employeeDTO.getGender());
        pstm.setString(6,employeeDTO.getTelNo());
        pstm.setInt(7,employeeDTO.getId());

        int result = pstm.executeUpdate();

        return result>0;
    }

    public ObservableList<EmployeeDTO> getAllEmployees()throws Exception{
        DBConnection dbc = DBConnection.getInstance();
        Connection conn = dbc.getConnection();

        PreparedStatement ps = conn.prepareStatement("SELECT * FROM Employee ORDER BY EmpID DESC");
        ResultSet rs = ps.executeQuery();

        ObservableList<EmployeeDTO> list = FXCollections.observableArrayList();

        while (rs.next()){
                    int empId = rs.getInt("EmpID");
                    String empName = rs.getString("Name");
                    LocalDate empdob = rs.getDate("Dob").toLocalDate();
                    String empNic = rs.getString("NIC");
                    String empAddress = rs.getString("Address");
                    String empGender = rs.getString("Gender");
                    String empTelno = rs.getString("TelNo");

                    EmployeeDTO employeeDTO = new EmployeeDTO(empId,empName,empdob,empNic,empAddress,empGender,empTelno);
                    list.add(employeeDTO);
        }
        return list;
    }
}
