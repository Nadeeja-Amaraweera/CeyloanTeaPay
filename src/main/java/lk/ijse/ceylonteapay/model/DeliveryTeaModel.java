package lk.ijse.ceylonteapay.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.ceylonteapay.db.DBConnection;
import lk.ijse.ceylonteapay.dto.FactoryDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DeliveryTeaModel {

    public ObservableList<FactoryDTO> getComboFactory()throws Exception{

        ObservableList<FactoryDTO> list = FXCollections.observableArrayList();

        DBConnection dbc = DBConnection.getInstance();
        Connection conn = dbc.getConnection();

        String sql = "SELECT FactoryId,FactoryName FROM Factory";

        PreparedStatement pstm = conn.prepareStatement(sql);

        ResultSet rs = pstm.executeQuery();

        while (rs.next()){
            list.add(new FactoryDTO(
                    rs.getInt("FactoryId"),
                    rs.getString("FactoryName")
            ));
        }
        return list;
    }
}
