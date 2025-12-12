package lk.ijse.ceylonteapay.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.ceylonteapay.db.DBConnection;
import lk.ijse.ceylonteapay.dto.FactoryDTO;
import lk.ijse.ceylonteapay.dto.LandDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FactoryModel {

    public boolean addFactory(FactoryDTO factoryDTO)throws Exception{
        DBConnection dbc = DBConnection.getInstance();
        Connection conn = dbc.getConnection();

        String sql = "INSERT INTO Factory (FactoryName , FactoryAddress) VALUES (?, ?)";
        PreparedStatement pstm = conn.prepareStatement(sql);

        pstm.setString(1,factoryDTO.getFactoryName());
        pstm.setString(2,factoryDTO.getFactoryAddress());

        int result = pstm.executeUpdate();

        return result>0;
    }

    public ObservableList<FactoryDTO> getAllFactories()throws Exception{

        DBConnection dbc = DBConnection.getInstance();
        Connection conn = dbc.getConnection();

        PreparedStatement ps = conn.prepareStatement("SELECT * FROM Factory ORDER BY FactoryId DESC");
        ResultSet rs = ps.executeQuery();

        ObservableList<FactoryDTO> list = FXCollections.observableArrayList();

        while (rs.next()){
            int factoryId = rs.getInt("FactoryId");
            String factoryName = rs.getString("FactoryName");
            String factoryAddress = rs.getString("FactoryAddress");


            FactoryDTO factoryDTO = new FactoryDTO(factoryId,factoryName,factoryAddress);
            list.add(factoryDTO);
        }
        return  list;
    }

    public boolean update(FactoryDTO factoryDTO)throws Exception{

        DBConnection dbc = DBConnection.getInstance();
        Connection conn = dbc.getConnection();

        String sql = "UPDATE Factory SET FactoryName = ?, FactoryAddress = ? WHERE FactoryId = ?";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1,factoryDTO.getFactoryName());
        pstm.setString(2,factoryDTO.getFactoryAddress());
        pstm.setInt(3,factoryDTO.getFactoryId());
        int result = pstm.executeUpdate();

        return result>0;
    }

    public boolean deleteFactory(int id)throws Exception{
        DBConnection dbc = DBConnection.getInstance();
        Connection conn = dbc.getConnection();

        String sql = "DELETE FROM Factory WHERE FactoryId = ?";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1,id);

        int result = pstm.executeUpdate();

        return result>0;
    }

}
