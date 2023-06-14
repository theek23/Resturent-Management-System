package lk.ijse.rms.model;

import lk.ijse.rms.db.DBConnection;
import lk.ijse.rms.dto.Employee;
import lk.ijse.rms.dto.Items;
import lk.ijse.rms.dto.OrderDetail;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class OrderDetailModel {
    public static boolean save(OrderDetail orderDetail) throws SQLException {
        Connection con =null;
        try {
            con = DBConnection.getInstance().getConnection();
            String sql = "INSERT INTO orderdetail(order_id, item_id, qty, date, unitPrice) " +
                    "VALUES(?, ?, ?, ?, ?)";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, orderDetail.getOrderId());
            pstm.setString(2, orderDetail.getItemId());
            pstm.setInt(3, orderDetail.getQty());
            pstm.setString(4, orderDetail.getDate());
            pstm.setDouble(5, orderDetail.getUnitPrice());

            return pstm.executeUpdate() > 0;
        }catch (SQLException e){
            con.setAutoCommit(false);
            con.rollback();
            return false;
        }
    }
    public static boolean update(Integer qty, String itemId, String orderId) throws SQLException {
        Connection con =null;
        try {
            con = DBConnection.getInstance().getConnection();
            String sql = "UPDATE orderdetail SET qty = ? WHERE order_id LIKE ? ESCAPE '#' AND item_id LIKE ? ESCAPE '#'";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, qty);
            pstm.setString(2, orderId);
            pstm.setString(3, itemId);

            return pstm.executeUpdate() > 0;
        }catch (SQLException e){
            con.rollback();
            return false;
        }finally {
            con.setAutoCommit(false);
        }
    }
    public static boolean delete(String orderId, String itemId) throws SQLException {
        Connection con =null;
        try {
            con = DBConnection.getInstance().getConnection();
            String sql = "DELETE FROM orderdetail WHERE order_id LIKE ? ESCAPE '#' AND item_id LIKE ? ESCAPE '#'";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, orderId);
            pstm.setString(2, itemId);

            return pstm.executeUpdate() > 0;
        }catch (SQLException e){
            con.setAutoCommit(false);
            con.rollback();
            return false;
        }
    }
    public static List<Items> loadDataForDisplay(String orderId) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT i.name,orderdetail.qty FROM orderdetail Inner JOIN item i on orderdetail.item_id = i.item_id WHERE orderdetail.order_id = ?";

        List<Items> data = new ArrayList<>();

        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, orderId);
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()) {
            data.add(new Items(
                    resultSet.getString(1),
                    resultSet.getInt(2)
            ));
        }

        return data;
    }
}
