package lk.ijse.rms.model;

import lk.ijse.rms.db.DBConnection;
import lk.ijse.rms.dto.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ItemModel {
    public static boolean save(Item item) throws SQLException {
        Connection con =null;
        try {
            con = DBConnection.getInstance().getConnection();
            String sql = "INSERT INTO item(item_id, name, unit_price, QTYonHand, cat_id) " +
                    "VALUES(?, ?, ?, ?,?)";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, item.getItemId());
            pstm.setString(2, item.getName());
            pstm.setDouble(3, item.getUnitPrice());
            pstm.setInt(4, item.getQtyOnHand());
            pstm.setString(5, item.getCatId());

            return pstm.executeUpdate() > 0;
        }catch (SQLException e){
            con.rollback();
            return false;
        }
    }

    public static List<String> loadItems() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        ResultSet resultSet = con.createStatement().executeQuery("SELECT item.name, i.cat_type FROM item INNER join itemcategory i on item.cat_id = i.cat_id ORDER BY name");

        List<String> data =new ArrayList<>();

        while (resultSet.next()) {
            data.add(resultSet.getString(1)+" ("+resultSet.getString(2)+")");
        }
        return data;
    }
    public static List<String> loadItemsByName(String word) throws SQLException {
        Connection con =null;
        try {
            con = DBConnection.getInstance().getConnection();
            String sql = "SELECT item.name, i.cat_type FROM item INNER join itemcategory i on item.cat_id = i.cat_id WHERE name LIKE ?";

            List<String> data = new ArrayList<>();

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, word);

            ResultSet resultSet=pstm.executeQuery();
            while (resultSet.next()) {
                data.add(resultSet.getString(1)+" ("+resultSet.getString(2)+")");
            }
            return data;
        }catch (SQLException e){
            con.rollback();
            return null;
        }
    }
    public static List<Item> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM item";

        List<Item> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getInt(4),
                    resultSet.getString(5)
            ));
        }
        return data;
    }
    public static List<OrderDetailSummery> getSummery(String orderId) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT item.name,i.cat_type, o.qty,o.unitPrice*o.qty  FROM item INNER join orderdetail o on item.item_id = o.item_id INNER JOIN itemcategory i on item.cat_id = i.cat_id WHERE o.order_id = ?";

        List<OrderDetailSummery> data = new ArrayList<>();

        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1,orderId);
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()) {
            data.add(new OrderDetailSummery(
                    resultSet.getString(1)+" ("+resultSet.getString(2)+")",
                    resultSet.getInt(3),
                    resultSet.getDouble(4)
            ));
        }
        return data;
    }
    public static String getItemIdForNext() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        ResultSet resultSet = con.createStatement().executeQuery("SELECT item_id FROM item ORDER BY item_id DESC LIMIT 1");
        String lastSalaryId="";
        if (resultSet.next()) {
            lastSalaryId = resultSet.getString("item_id");

        }
        return lastSalaryId;
    }
    public static String getItemId(String description) throws SQLException {
        String[] splitDescription = description.split("\\(");
        description=splitDescription[0].trim();
        String category=splitDescription[1].replace(")", "").trim();
        if (!description.equals("")) {
            Connection con = DBConnection.getInstance().getConnection();
            String sql = "SELECT item.item_id, i.cat_type FROM item INNER join itemcategory i on item.cat_id = i.cat_id WHERE name = ?";
            String itemId = "";
            String cat ="";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, description);

            ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                itemId = resultSet.getString(1);
                cat = resultSet.getString(2);
                if (category.equals(cat)){
                    return itemId;
                }
            }
            return null;
        }
        return null;
    }
    public static String getUnitPrice(String itemId) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT unit_price FROM item WHERE item_id = ? ";
        String data= "";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, itemId);

        ResultSet resultSet=pstm.executeQuery();
        while (resultSet.next()) {
            data = resultSet.getString(1);
        }
        if (!data.equals("")){
            return data;
        }
        return "0.00";

    }

}
