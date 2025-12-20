package lk.ijse.ceylonteapay.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.ceylonteapay.db.DBConnection;
import lk.ijse.ceylonteapay.dto.EmployeeDTO;
import lk.ijse.ceylonteapay.dto.LandDTO;
import lk.ijse.ceylonteapay.dto.OtherWorkDTO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class OtherWorkModel {

    public ObservableList<OtherWorkDTO> getAllOtherWorkFields() throws Exception {
        DBConnection dbc = DBConnection.getInstance();
        Connection conn = dbc.getConnection();

        String sql = "SELECT OtherWork.Work_ID, OtherWork.Emp_ID, Employee.Name AS EmployeeName, " +
                "OtherWork.Lnd_ID, Land.LandName AS LandName, OtherWork.Date, OtherWork.Details ,OtherWork.Salary " +
                "FROM OtherWork " +
                "JOIN Employee ON OtherWork.Emp_ID = Employee.EmpID " +
                "JOIN Land ON OtherWork.Lnd_ID = Land.LndID " +
                "ORDER BY OtherWork.Work_ID DESC";

        PreparedStatement pstm = conn.prepareStatement(sql);

        ObservableList<OtherWorkDTO> list = FXCollections.observableArrayList();

        ResultSet rs = pstm.executeQuery();
        while (rs.next()) {

            int workID = rs.getInt("Work_ID");
            int empID = rs.getInt("Emp_ID");
            String empName = rs.getString("EmployeeName");      // NEW

            int lndID = rs.getInt("Lnd_ID");
            String landName = rs.getString("LandName");         // NEW

            LocalDate date = rs.getDate("Date").toLocalDate();
            String details = rs.getString("Details");
            double salary = rs.getDouble("Salary");

            OtherWorkDTO dto = new OtherWorkDTO(
                    workID, empID, empName,
                    lndID, landName,
                    date, details , salary
            );

            list.add(dto); //FIXED (use add, not addAll)
        }

        return list;
    }

    public boolean addWorkField(OtherWorkDTO otherWorkDTO)throws Exception{
        DBConnection dbc = DBConnection.getInstance();
        Connection conn = dbc.getConnection();

        String sql = "INSERT INTO OtherWork (Emp_ID,Lnd_ID,Date,Details,Salary) VALUES (?,?,?,?,?)";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1,otherWorkDTO.getEmp_ID());
        pstm.setInt(2,otherWorkDTO.getLnd_Id());
        pstm.setDate(3, Date.valueOf(otherWorkDTO.getDate()));
        pstm.setString(4,otherWorkDTO.getDetails());
        pstm.setDouble(5,otherWorkDTO.getSalary());
        int result = pstm.executeUpdate();

        return result>0;
    }

    public boolean updateWorkField(OtherWorkDTO otherWorkDTO)throws Exception{
        DBConnection dbc = DBConnection.getInstance();
        Connection conn = dbc.getConnection();

        String sql = "UPDATE OtherWork SET Emp_ID = ?, Lnd_ID = ?, Date = ?, Details = ?,Salary = ? WHERE Work_ID = ?";
        PreparedStatement pstm = conn.prepareCall(sql);
        pstm.setInt(1,otherWorkDTO.getEmp_ID());
        pstm.setInt(2,otherWorkDTO.getLnd_Id());
        pstm.setDate(3,Date.valueOf(otherWorkDTO.getDate()));
        pstm.setString(4, otherWorkDTO.getDetails());
        pstm.setDouble(5,otherWorkDTO.getSalary());
        pstm.setInt(6,otherWorkDTO.getWorkID());

        int result = pstm.executeUpdate();

        return result>0;
    }

    public boolean deleteWorkField(int WorkID)throws Exception{
        DBConnection dbc = DBConnection.getInstance();
        Connection conn = dbc.getConnection();

        String sql ="DELETE FROM OtherWork WHERE Work_ID = ?";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1,WorkID);

        int result = pstm.executeUpdate();

        return result>0;
    }

    public ResultSet getEmployeeNameCombo(int id)throws Exception{
        DBConnection dbc = DBConnection.getInstance();
        Connection conn = dbc.getConnection();

        String sql = "SELECT * FROM Employee WHERE EmpID = ?";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1,id);

        ResultSet rs = pstm.executeQuery();
        if (rs.next()){
            String name = rs.getString("Name");
            String address = rs.getString("Address");
            String nic = rs.getString("NIC");
            String dob = rs.getString("DOB");
            String gender = rs.getString("Gender");
            String telNo = rs.getString("TelNo");

            System.out.println("DB Name: " + name + ", Address: " + address + ", NIC: " + nic + ", DOB: " + dob + ", Gender: " + gender + ", TelNo: " + telNo);
        }
        return rs;
    }

    public ResultSet getLandNameCombo(int id)throws Exception{
        DBConnection dbc = DBConnection.getInstance();
        Connection conn = dbc.getConnection();

        String sql = "SELECT * FROM Land WHERE LndID = ?";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1,id);

        ResultSet rs = pstm.executeQuery();
        if (rs.next()){
            String landId = rs.getString("LndID");
            String landName = rs.getString("LandName");
            String landNo = rs.getString("LandNo");

            System.out.println("Land ID: "+landId+" Land Name: "+landName+" Land No: "+landNo);

        }
        return rs;
    }

    public ObservableList<EmployeeDTO> getEmployeeId() throws Exception {
        ObservableList<EmployeeDTO> idList = FXCollections.observableArrayList();

        DBConnection dbc = DBConnection.getInstance();
        Connection conn = dbc.getConnection();

        String sql = "SELECT EmpID, Name FROM Employee";

        PreparedStatement pstm = conn.prepareStatement(sql);

        ResultSet rs = pstm.executeQuery();

        while (rs.next()) {
            idList.add(new EmployeeDTO(
                    rs.getInt("EmpID"),
                    rs.getString("Name")
            ));
        }
        return idList;
    }

    public ObservableList<LandDTO> getLandId()throws Exception{
        ObservableList<LandDTO> idList = FXCollections.observableArrayList();

        DBConnection dbc = DBConnection.getInstance();
        Connection conn = dbc.getConnection();

        String sql = "SELECT * FROM Land";

        PreparedStatement pstm = conn.prepareStatement(sql);

        ResultSet rs = pstm.executeQuery();

        while (rs.next()){
            idList.add(new LandDTO(
                    rs.getInt("LndID"),
                    rs.getString("LandName"),
                    rs.getString("LandNo")
            ));
        }
        return idList;
    }
}
