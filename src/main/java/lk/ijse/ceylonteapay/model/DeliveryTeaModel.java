package lk.ijse.ceylonteapay.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.ceylonteapay.db.DBConnection;
import lk.ijse.ceylonteapay.dto.DeliveryCartTM;
import lk.ijse.ceylonteapay.dto.FactoryDTO;
import lk.ijse.ceylonteapay.dto.StockDTO;

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

    public ObservableList<StockDTO> getComboStock()throws Exception{
        ObservableList<StockDTO> list = FXCollections.observableArrayList();

        DBConnection dbc = DBConnection.getInstance();
        Connection conn = dbc.getConnection();

        String sql = "SELECT id,date,quality FROM Stock";
        PreparedStatement pstm = conn.prepareStatement(sql);

        ResultSet rs = pstm.executeQuery();

        while (rs.next()){
            list.add(new StockDTO(
               rs.getInt("id"),
                    rs.getDate("date").toLocalDate(),
                    rs.getString("quality")
            ));
        }
        return list;
    }

    public boolean placeOrder(ObservableList<DeliveryCartTM> cartList)throws Exception {

            Connection con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false); // 🚨 START TRANSACTION

            try {
                for (DeliveryCartTM item : cartList) {

                    // Lock stock row
                    PreparedStatement check = con.prepareStatement(
                            "SELECT availableQuantity FROM Stock WHERE id=? FOR UPDATE"
                    );
                    check.setInt(1, item.getStockId());

                    ResultSet rs = check.executeQuery();
                    rs.next();

                    if (rs.getInt(1) < item.getQty()) {
                        throw new RuntimeException(
                                "Not enough stock for Stock ID: " + item.getStockId()
                        );
                    }

                    // 2️⃣ Insert delivery
                    PreparedStatement insert = con.prepareStatement(
                            "INSERT INTO DeliveryTea " +
                                    "(deliveryFactoryId, deliveryFactoryName, StockId, deliveryQty, deliveryDate) " +
                                    "VALUES (?,?,?,?,?)"
                    );
                    insert.setInt(1, item.getFactoryId());
                    insert.setString(2, item.getFactoryName());
                    insert.setInt(3, item.getStockId());
                    insert.setInt(4, item.getQty());
                    insert.setDate(5, java.sql.Date.valueOf(item.getDate()));

                    insert.executeUpdate();

                    // 3️⃣ Update stock
                    PreparedStatement update = con.prepareStatement(
                            "UPDATE Stock SET availableQuantity = availableQuantity - ? WHERE id = ?"
                    );
                    update.setInt(1, item.getQty());
                    update.setInt(2, item.getStockId());

                    update.executeUpdate();
                }

                con.commit(); // ✅ SUCCESS
                return true;

            } catch (Exception e) {
                con.rollback(); // ❌ FAIL
                throw e;

            } finally {
                con.setAutoCommit(true);
            }
    }
}
