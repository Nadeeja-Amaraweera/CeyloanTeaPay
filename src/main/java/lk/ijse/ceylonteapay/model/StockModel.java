package lk.ijse.ceylonteapay.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.ceylonteapay.db.DBConnection;
import lk.ijse.ceylonteapay.dto.StockDTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StockModel {
    public boolean saveStock(StockDTO stockDTO) throws Exception {
        DBConnection dbc = DBConnection.getInstance();
        Connection conn = dbc.getConnection();

        String sql = "INSERT INTO Stock (date,quality,quantity,availableQuantity) VALUES (?,?,?,?)";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setDate(1, Date.valueOf(stockDTO.getDate()));
        pstm.setString(2, stockDTO.getQuality());
        pstm.setInt(3, stockDTO.getQuantity());
        pstm.setInt(4, stockDTO.getAvailableQuantity());

        int result = pstm.executeUpdate();

        return result > 0;

    }

    public ObservableList<StockDTO> getStock() throws Exception {
        DBConnection dbc = DBConnection.getInstance();
        Connection conn = dbc.getConnection();

        String sql = "SELECT * FROM Stock ORDER BY id DESC";
        PreparedStatement statement = conn.prepareStatement(sql);

        ResultSet rs = statement.executeQuery();

        ObservableList<StockDTO> list = FXCollections.observableArrayList();

        while (rs.next()) {
            int stockId = rs.getInt("id");
            LocalDate date = rs.getDate("date").toLocalDate();
            String quality = rs.getString("quality");
            int qty = rs.getInt("quantity");
            int avaQty = rs.getInt("availableQuantity");

            StockDTO stockDTO = new StockDTO(stockId, date, quality, qty, avaQty);
            list.add(stockDTO);
        }
        return list;
    }

    public boolean updateStock(StockDTO stockDTO) throws Exception {
        DBConnection dbc = DBConnection.getInstance();
        Connection conn = dbc.getConnection();

        String sql = "UPDATE Stock SET date = ?, quality = ? ,quantity = ?, availableQuantity = ? WHERE id = ?";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setDate(1, Date.valueOf(stockDTO.getDate()));
        pstm.setString(2, stockDTO.getQuality());
        pstm.setInt(3, stockDTO.getQuantity());
        pstm.setInt(4, stockDTO.getAvailableQuantity());
        pstm.setInt(5, stockDTO.getId());

        int result = pstm.executeUpdate();

        return result > 0;
    }

    public boolean deleteStock(int id) throws Exception {
        DBConnection dbc = DBConnection.getInstance();
        Connection conn = dbc.getConnection();

        String sql = "DELETE FROM Stock WHERE id = ?";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, id);

        int result = pstm.executeUpdate();

        return result > 0;
    }

    public List<StockDTO> getStockSummary() throws SQLException {

        List<StockDTO> list = new ArrayList<>();

        String sql = """
                    SELECT quality,
                           quantity,
                           availableQuantity
                    FROM Stock
                """;
        ResultSet rs = DBConnection.getInstance()
                .getConnection()
                .prepareStatement(sql)
                .executeQuery();

        while (rs.next()) {
            list.add(new StockDTO(
                    null, // date not needed
                    rs.getString("quality"),
                    rs.getInt("quantity"),
                    rs.getInt("availableQuantity")
            ));
        }
        return list;
    }
}
