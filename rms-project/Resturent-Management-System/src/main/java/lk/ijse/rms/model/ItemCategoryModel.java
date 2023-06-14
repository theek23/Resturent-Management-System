package lk.ijse.rms.model;

import lk.ijse.rms.db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemCategoryModel {
    public static List<String> loadCategories() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        ResultSet resultSet = con.createStatement().executeQuery("SELECT cat_type FROM itemcategory");

        List<String> data =new ArrayList<>();

        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }
    public static String getCategoryId(String category) throws SQLException{
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT cat_id FROM itemcategory WHERE cat_type = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, category);

        ResultSet resultSet = pstm.executeQuery();
        String categoryId="";
        if (resultSet.next()) {
            categoryId = resultSet.getString(1);
        }
        return categoryId;
    }
}
