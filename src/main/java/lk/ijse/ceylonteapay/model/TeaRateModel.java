package lk.ijse.ceylonteapay.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.ceylonteapay.db.DBConnection;
import lk.ijse.ceylonteapay.dto.TeaRateDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TeaRateModel {

    public boolean addTeaRate(TeaRateDTO teaRateDTO) throws Exception{
        DBConnection dbc = DBConnection.getInstance();
        Connection conn = dbc.getConnection();

        String sql = "INSERT INTO TeaRate (Month,Year,ratePerKg) VALUES (?,?,?)";

//        int rateId, String month, int year, double rate
        PreparedStatement pstm = conn.prepareStatement(sql);
//        pstm.setInt(1,teaRateDTO.getRateId());
        pstm.setString(1,teaRateDTO.getMonth());
        pstm.setInt(2,teaRateDTO.getYear());
        pstm.setDouble(3,teaRateDTO.getRate());

        int rs = pstm.executeUpdate();

        return rs>0;
    }

    public ObservableList<TeaRateDTO> loadTeaRate()throws Exception{
        DBConnection dbc = DBConnection.getInstance();
        Connection conn = dbc.getConnection();

        String sql = "SELECT * FROM TeaRate ORDER BY Year DESC";

        PreparedStatement pstm = conn.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();

        ObservableList<TeaRateDTO> list = FXCollections.observableArrayList();

        while (rs.next()){
            int rateId = rs.getInt("rateId");
            String month = rs.getString("Month");
            int year = rs.getInt("Year");
            double ratePerKg = rs.getDouble("ratePerKg");

            TeaRateDTO teaRateDTO = new TeaRateDTO(rateId,month,year,ratePerKg);
            list.add(teaRateDTO);
        }
        return list;
    }

    public boolean deleteRate(int id) throws Exception{
        DBConnection dbc = DBConnection.getInstance();
        Connection conn = dbc.getConnection();

        String sql = "DELETE FROM TeaRate WHERE rateId = ?";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1,id);
        int result  = pstm.executeUpdate();

        return result>0;
    }
}
