package lk.ijse.ceylonteapay.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.ceylonteapay.db.DBConnection;
import lk.ijse.ceylonteapay.dto.DeliveryCartTM;
import lk.ijse.ceylonteapay.dto.FactoryDTO;
import lk.ijse.ceylonteapay.dto.StockDTO;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

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

    /*public boolean placeOrder(ObservableList<DeliveryCartTM> cartList)throws Exception {

            Connection con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

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

                    //Insert delivery
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


                    //Update stock
                    PreparedStatement update = con.prepareStatement(
                            "UPDATE Stock SET availableQuantity = availableQuantity - ? WHERE id = ?"
                    );
                    update.setInt(1, item.getQty());
                    update.setInt(2, item.getStockId());

                    update.executeUpdate();
                }

                con.commit(); // SUCCESS
                return true;

            } catch (Exception e) {
                con.rollback(); // FAIL
                throw e;

            } finally {
                con.setAutoCommit(true);
            }
    }*/

    public boolean placeOrder(ObservableList<DeliveryCartTM> cartList) throws Exception {

        Connection con = DBConnection.getInstance().getConnection();
        con.setAutoCommit(false);

        try {
            //Insert DELIVERY only ONCE
            DeliveryCartTM firstItem = cartList.get(0);

            PreparedStatement deliveryStmt = con.prepareStatement(
                    "INSERT INTO Delivery (deliveryFactoryId, deliveryFactoryName, deliveryDate) VALUES (?,?,?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            deliveryStmt.setInt(1, firstItem.getFactoryId());
            deliveryStmt.setString(2, firstItem.getFactoryName());
            deliveryStmt.setDate(3, Date.valueOf(firstItem.getDate()));
            deliveryStmt.executeUpdate();

            //Get generated deliveryId
            ResultSet keys = deliveryStmt.getGeneratedKeys();
            if (!keys.next()) {
                throw new SQLException("Failed to generate delivery ID");
            }
            int deliveryId = keys.getInt(1);

            //Process cart items
            for (DeliveryCartTM item : cartList) {

                // 🔒 Lock stock row
                PreparedStatement check = con.prepareStatement(
                        "SELECT availableQuantity FROM Stock WHERE id=? FOR UPDATE"
                );
                check.setInt(1, item.getStockId());

                ResultSet rs = check.executeQuery();
                rs.next();

                if (rs.getInt("availableQuantity") < item.getQty()) {
                    throw new RuntimeException(
                            "Not enough stock for Stock ID: " + item.getStockId()
                    );
                }

                //Insert into DeliveryStock Associate Table
                PreparedStatement stockStmt = con.prepareStatement(
                        "INSERT INTO DeliveryStock (deliveryId, stockId, deliveryQty) VALUES (?,?,?)"
                );
                stockStmt.setInt(1, deliveryId);
                stockStmt.setInt(2, item.getStockId());
                stockStmt.setInt(3, item.getQty());
                stockStmt.executeUpdate();

                //Update Stock
                PreparedStatement update = con.prepareStatement(
                        "UPDATE Stock SET availableQuantity = availableQuantity - ? WHERE id = ?"
                );
                update.setInt(1, item.getQty());
                update.setInt(2, item.getStockId());
                update.executeUpdate();
            }

            con.commit();
            return true;

        } catch (Exception e) {
            con.rollback();
            throw e;

        } finally {
            con.setAutoCommit(true);
        }
    }



    public void printDeliveryTea(int selectedMonthNo, int selectedYear){
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            InputStream reportObject = getClass().getResourceAsStream("/lk/ijse/ceylonteapay/reports/delivery.jrxml");

            if (reportObject == null) {
                throw new RuntimeException("customer.jrxml NOT FOUND");
            }

            JasperReport jr  = JasperCompileManager.compileReport(reportObject); // this is method throws JRException

            Map<String,Object> params = new HashMap<>();
            params.put("DELIVERY_MONTH",selectedMonthNo);
            params.put("DELIVERY_YEAR",selectedYear);

            JasperPrint jp = JasperFillManager.fillReport(jr , params , conn); // fill report (jasperreport, params ,connection)

            JasperViewer.viewReport(jp,false);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
