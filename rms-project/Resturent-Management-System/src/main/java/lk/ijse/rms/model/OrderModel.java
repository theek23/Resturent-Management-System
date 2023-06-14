package lk.ijse.rms.model;

import com.jfoenix.controls.JFXButton;
import lk.ijse.rms.db.DBConnection;
import lk.ijse.rms.dto.Orders;
import lk.ijse.rms.dto.OrdersDisplay1;
import lk.ijse.rms.dto.OrdersSummry;
import lk.ijse.rms.dto.OrdersSummry2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class OrderModel {
    public static boolean save(Orders orders) throws SQLException {
        Connection con =null;
        try {
            con = DBConnection.getInstance().getConnection();
            String sql = "INSERT INTO orders(order_id, cust_name, cust_phone, order_status, order_price, qty, emp_id, bench_id, takeaway_id, del_id, time) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, orders.getOrderId());
            pstm.setString(2, orders.getCustName());
            pstm.setString(3, orders.getCustPhone());
            pstm.setString(4, orders.getOrderStatus());
            pstm.setDouble(5, orders.getOrderPrice());
            pstm.setInt(6,orders.getQty());
            pstm.setString(7, orders.getEmpId());
            pstm.setString(8, orders.getBenchId());
            pstm.setString(9, orders.getTakeawayId());
            pstm.setString(10, orders.getDelId());
            pstm.setString(11, orders.getTime());

            return pstm.executeUpdate() > 0;
        }catch (SQLException e){
            con.rollback();
            return false;
        }
    }
    public static List<Orders> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM orders";

        List<Orders> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new Orders(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getDouble(5),
                    resultSet.getInt(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9),
                    resultSet.getString(10),
                    resultSet.getString(11)
            ));
        }
        con.close();
        return data;
    }
    public static List<Orders> getSummery() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT order_id,qty,order_price,order_status,cust_name FROM orders";

        List<Orders> data = new ArrayList<>();

        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()) {
            data.add(new OrdersSummry(
                    resultSet.getString(1),
                    resultSet.getInt(2),
                    resultSet.getDouble(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }

        return data;
    }
    public static boolean update(String custName, String custPhone, String orderStatus, Integer qty, Double orderPrice, String benchId, String takeawayId,String delId, String orderId) throws SQLException {
        Connection con =null;
        try {
            con = DBConnection.getInstance().getConnection();
            String sql = "UPDATE orders SET cust_name = ?, cust_phone = ?, order_status = ?, qty = ?, order_price = ?, bench_id = ?, takeaway_id = ?, del_id = ? WHERE order_id = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, custName);
            pstm.setString(2, custPhone);
            pstm.setString(3, orderStatus);
            pstm.setInt(4,qty);
            pstm.setDouble(5, orderPrice);
            pstm.setString(6, benchId);
            pstm.setString(7, takeawayId);
            pstm.setString(8, delId);
            pstm.setString(9, orderId);

            return pstm.executeUpdate() > 0;
        }catch (SQLException e){
            con.rollback();
            return false;
        }
    }
    public static boolean updateStatus(String ordeerId, String status) throws SQLException {
        Connection con =null;
        try {
            con = DBConnection.getInstance().getConnection();
            String sql = "UPDATE orders SET order_status = ? WHERE order_id = ?";
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setString(1, status);
            pstm.setString(2, ordeerId);

            return pstm.executeUpdate() > 0;
        }catch (SQLException e){
            con.rollback();
            return false;
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
            con.rollback();
            return false;
        }
    }
    public static boolean deleteAll(String orderId) throws SQLException{
        boolean isOrderDetailDeleted =deleteOrderDetail(orderId);
        boolean isOrderdeleted = deleteOrder(orderId);
        if (isOrderdeleted && isOrderDetailDeleted){
            return true;
        }else {
            return false;
        }
    }
    private static boolean deleteOrderDetail(String orderId) throws SQLException{
        Connection con =null;
        try {
            con = DBConnection.getInstance().getConnection();
            String sql = "DELETE FROM orderdetail WHERE order_id =?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, orderId);

            return pstm.executeUpdate() > 0;
        }catch (SQLException e){
            con.rollback();
            return false;
        }
    }
    private static boolean deleteOrder(String orderId)throws SQLException{
        Connection con =null;
        try {
            con = DBConnection.getInstance().getConnection();
            String sql = "DELETE FROM orders WHERE order_id =?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, orderId);

            return pstm.executeUpdate() > 0;
        }catch (SQLException e){
            con.rollback();
            return false;
        }
    }
    public static List<OrdersDisplay1> loadData() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT order_id,qty,time,order_status FROM orders ORDER BY time DESC LIMIT 6";

        List<OrdersDisplay1> data = new ArrayList<>();

        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()) {
            data.add(new OrdersDisplay1(
                    resultSet.getString(1),
                    OrderDetailModel.loadDataForDisplay(resultSet.getString(1)),
                    resultSet.getInt(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            ));
        }
        return data;
    }
    public static List<OrdersSummry2> loadDatatoOrders() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT order_id,order_price,time,bench_id,takeaway_id,order_status FROM orders";

        List<OrdersSummry2> data = new ArrayList<>();

        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()) {
            data.add(new OrdersSummry2(
                    resultSet.getString(1),
                    resultSet.getDouble(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            ));
        }
        return data;
    }
    public static Double todaySales() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT SUM(order_price) AS sales FROM orders WHERE DATE(time)=current_date";
        Double todaySales = 0.0;
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet resultSet = stmt.executeQuery();
        if (resultSet.next()) {
            Double result = resultSet.getDouble("sales");
            todaySales = result;
        }
        return todaySales;
    }
    public static Double yesterdaySales() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT SUM(order_price) AS sales FROM orders WHERE DATE(time)=current_date-1";
        Double yesterdaySales = 0.0;
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet resultSet = stmt.executeQuery();
        if (resultSet.next()) {
            Double result = resultSet.getDouble("sales");
            yesterdaySales = result;
        }
        return yesterdaySales;
    }
    public static Double last7DaysSales() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT SUM(order_price) AS sales FROM orders WHERE time >= DATE_SUB(CURDATE(), INTERVAL 7 DAY)";
        Double last7days = 0.0;
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet resultSet = stmt.executeQuery();
        if (resultSet.next()) {
            Double result = resultSet.getDouble("sales");
            last7days = result;
        }
        return last7days;
    }
    public static Double thisMothSales() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT SUM(order_price) AS sales FROM orders WHERE YEAR(time) = YEAR(CURDATE()) AND MONTH(time) = MONTH(CURDATE());";
        Double thisMoth = 0.0;
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet resultSet = stmt.executeQuery();
        if (resultSet.next()) {
            Double result = resultSet.getDouble("sales");
            thisMoth = result;
        }
        return thisMoth;
    }
    public static Double totalSales() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT SUM(order_price) AS sales FROM orders;";
        Double total = 0.0;
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet resultSet = stmt.executeQuery();
        if (resultSet.next()) {
            Double result = resultSet.getDouble("sales");
            total = result;
        }
        return total;
    }
    public static String getOrderIdForNext() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        ResultSet resultSet = con.createStatement().executeQuery("SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1");
        String lastSalaryId="";
        if (resultSet.next()) {
            lastSalaryId = resultSet.getString("order_id");

        }
        return lastSalaryId;
    }
}
