package lk.ijse.ceylonteapay.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.ceylonteapay.db.DBConnection;
import lk.ijse.ceylonteapay.dto.EmployeeDTO;
import lk.ijse.ceylonteapay.dto.LandDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class LandModel {
    public boolean saveLand(LandDTO landDTO)throws Exception{
        DBConnection dbc = DBConnection.getInstance();
        Connection conn = dbc.getConnection();

        String sql = "INSERT INTO Land (LandName,LandNo) VALUES (?,?)";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1,landDTO.getLndName());
        pstm.setString(2,landDTO.getLndNo());

        int result = pstm.executeUpdate();

        return result>0;
    }

    public ObservableList<LandDTO> getAllLands()throws Exception{

        DBConnection dbc = DBConnection.getInstance();
        Connection conn = dbc.getConnection();

        PreparedStatement ps = conn.prepareStatement("SELECT * FROM Land ORDER BY LndID DESC");
        ResultSet rs = ps.executeQuery();

        ObservableList<LandDTO> list = FXCollections.observableArrayList();

        while (rs.next()){
            int lndID = rs.getInt("LndID");
            String landName = rs.getString("LandName");
            String landNo = rs.getString("LandNo");


            LandDTO landDTO = new LandDTO(lndID,landName,landNo);
            list.add(landDTO);
        }
        return  list;
    }

    public boolean updateLand(LandDTO landDTO)throws Exception{
        DBConnection dbc = DBConnection.getInstance();
        Connection conn = dbc.getConnection();

        String sql = "UPDATE Land SET LandName = ?, LandNo = ? WHERE LndID = ?";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1,landDTO.getLndName());
        pstm.setString(2,landDTO.getLndNo());
        pstm.setInt(3,landDTO.getLndID());
        int result = pstm.executeUpdate();

        return result>0;
    }

    public boolean deleteLand(int id)throws Exception{
        DBConnection dbc = DBConnection.getInstance();
        Connection conn = dbc.getConnection();

        String sql = "DELETE FROM Land WHERE LndID = ?";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1,id);

        int result = pstm.executeUpdate();

        return result>0;
    }
}
