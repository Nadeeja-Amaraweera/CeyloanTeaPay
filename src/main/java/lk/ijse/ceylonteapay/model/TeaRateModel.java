package lk.ijse.ceylonteapay.model;

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
}
