package lk.ijse.ceylonteapay.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.ceylonteapay.db.DBConnection;
import lk.ijse.ceylonteapay.dto.EmployeeDTO;
import lk.ijse.ceylonteapay.dto.LandDTO;
import lk.ijse.ceylonteapay.dto.DailyTeaDTO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class DailyTeaModel {

    public boolean addTeaField(DailyTeaDTO teaDTO) throws Exception{
        DBConnection dbc = DBConnection.getInstance();
        Connection conn = dbc.getConnection();

        String sql = "INSERT INTO Tea (Emp_ID, Lnd_ID, Date_Collected, Full_Weight, Bag_Weight, Water_Weight,Total_Weight, Quality) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1,teaDTO.getEmpID());
        pstm.setInt(2,teaDTO.getLndID());
        pstm.setDate(3, Date.valueOf(teaDTO.getDateCollected()));
        pstm.setDouble(4,teaDTO.getFullWeight());
        pstm.setDouble(5,teaDTO.getBagWeight());
        pstm.setDouble(6,teaDTO.getWaterWeight());
        pstm.setDouble(7,teaDTO.getTotalWeight());
        pstm.setString(8,teaDTO.getQuality());

        int result = pstm.executeUpdate();

        return result>0;
    }

        public boolean updateTeaField(DailyTeaDTO teaDTO)throws Exception{
        DBConnection dbc = DBConnection.getInstance();
        Connection conn = dbc.getConnection();

        String sql = "UPDATE Tea SET Emp_ID = ?, Lnd_ID = ?, Date_Collected = ?, Full_Weight = ?, Bag_Weight = ?, Water_Weight = ?,Total_Weight = ?,Quality = ? WHERE Tea_ID = ?";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1,teaDTO.getEmpID());
        pstm.setInt(2,teaDTO.getLndID());
        pstm.setDate(3,Date.valueOf(teaDTO.getDateCollected()));
        pstm.setDouble(4,teaDTO.getFullWeight());
        pstm.setDouble(5,teaDTO.getBagWeight());
        pstm.setDouble(6,teaDTO.getWaterWeight());
        pstm.setDouble(7,teaDTO.getTotalWeight());
        pstm.setString(8,teaDTO.getQuality());
        pstm.setInt(9,teaDTO.getTeaID());

        int result = pstm.executeUpdate();

        return result>0;
    }

        public boolean deleteTeaField(int teaID)throws Exception{
        DBConnection dbc = DBConnection.getInstance();
        Connection conn = dbc.getConnection();

        String sql ="DELETE FROM Tea WHERE Tea_ID = ?";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1,teaID);
        int result = pstm.executeUpdate();
        return result>0;
    }

        public ResultSet checkTeaId(int teaId)throws Exception{
        DBConnection dbc = DBConnection.getInstance();
        Connection conn = dbc.getConnection();

        String sql = "SELECT * FROM Tea WHERE Tea_ID = ?";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1,teaId);

        ResultSet result = pstm.executeQuery();
        return result;
    }

        public ObservableList<DailyTeaDTO> getAllTeaFields() throws Exception {
        DBConnection dbc = DBConnection.getInstance();
        Connection conn = dbc.getConnection();

        String sql = "SELECT Tea.Tea_ID, Tea.Emp_ID, Employee.Name AS EmployeeName, " +
                "Tea.Lnd_ID, Land.LandName AS LandName, Tea.Date_Collected, " +
                "Tea.Full_Weight, Tea.Bag_Weight, Tea.Water_Weight, Tea.Total_Weight, Tea.Quality " +
                "FROM Tea " +
                "JOIN Employee ON Tea.Emp_ID = Employee.EmpID " +
                "JOIN Land ON Tea.Lnd_ID = Land.LndID " +
                "ORDER BY Tea.Tea_ID DESC";

        PreparedStatement pstm = conn.prepareStatement(sql);

        ObservableList<DailyTeaDTO> list = FXCollections.observableArrayList();

        ResultSet rs = pstm.executeQuery();
        while (rs.next()) {

            int teaID = rs.getInt("Tea_ID");
            int empID = rs.getInt("Emp_ID");
            String empName = rs.getString("EmployeeName");   // FIXED
            int lndID = rs.getInt("Lnd_ID");
            String landName = rs.getString("LandName");      // FIXED
            LocalDate date = rs.getDate("Date_Collected").toLocalDate();
            double fullWeight = rs.getDouble("Full_Weight");
            double bagWeight = rs.getDouble("Bag_Weight");
            double waterWeight = rs.getDouble("Water_Weight");
            double totalWeight = rs.getDouble("Total_Weight");
            String quality = rs.getString("Quality");

            DailyTeaDTO teaDTO = new DailyTeaDTO(
                    teaID, empID, empName,  // include empName
                    lndID, landName,        // include landName
                    date, fullWeight, bagWeight, waterWeight, totalWeight, quality
            );

            list.add(teaDTO);
        }
        return list;
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
    }
