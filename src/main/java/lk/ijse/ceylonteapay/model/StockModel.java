package lk.ijse.ceylonteapay.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.ceylonteapay.db.DBConnection;
import lk.ijse.ceylonteapay.dto.FactoryDTO;
import lk.ijse.ceylonteapay.dto.StockDTO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class StockModel {
        public boolean saveStock(StockDTO stockDTO)throws Exception{
            DBConnection dbc = DBConnection.getInstance();
            Connection conn = dbc.getConnection();

            String sql = "INSERT INTO Stock (date,quality,quantity,availableQuantity) VALUES (?,?,?,?)";
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setDate(1, Date.valueOf(stockDTO.getDate()));
            pstm.setString(2,stockDTO.getQuality());
            pstm.setInt(3,stockDTO.getQuantity());
            pstm.setInt(4,stockDTO.getAvailableQuantity());

            int result = pstm.executeUpdate();

            return result>0;

        }

        public  ObservableList<StockDTO> getStock()throws Exception{
            DBConnection dbc = DBConnection.getInstance();
            Connection conn = dbc.getConnection();

            String sql ="SELECT * FROM Stock ORDER BY id DESC";
            PreparedStatement statement = conn.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();

            ObservableList<StockDTO> list = FXCollections.observableArrayList();

            while (rs.next()){
                int stockId = rs.getInt("id");
                LocalDate date = rs.getDate("date").toLocalDate();
                String quality = rs.getString("quality");
                int qty = rs.getInt("quantity");
                int avaQty = rs.getInt("availableQuantity");

                StockDTO stockDTO = new StockDTO(stockId,date,quality,qty,avaQty);
                list.add(stockDTO);
            }
            return list;
        }
}
