package lk.ijse.rms.model;

import lk.ijse.rms.db.DBConnection;
import lk.ijse.rms.dto.Item;
import lk.ijse.rms.dto.Supplier;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class SupplierModel {
    public static boolean save(Supplier supplier) throws SQLException {
        Connection con =null;
        try {
            con = DBConnection.getInstance().getConnection();
            String sql = "INSERT INTO suplier(sup_id, name, phone) " +
                    "VALUES(?, ?, ?)";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, supplier.getSupId());
            pstm.setString(2, supplier.getName());
            pstm.setString(3, supplier.getPhone());

            return pstm.executeUpdate() > 0;
        }catch (SQLException e){
            con.rollback();
            return false;
        }
    }
    public static boolean update(String name, String phone, String supId) throws SQLException {
        Connection con =null;
        try {
            con = DBConnection.getInstance().getConnection();
            String sql = "UPDATE suplier SET name = ?, phone = ? WHERE sup_id = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, name);
            pstm.setString(2, phone);
            pstm.setString(3, supId);

            return pstm.executeUpdate() > 0;
        }catch (SQLException e){
            con.rollback();
            return false;
        }
    }
    public static boolean delete(String supId) throws SQLException {
        Connection con =null;
        try {
            con = DBConnection.getInstance().getConnection();
            String sql = "DELETE FROM suplier WHERE sup_id = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, supId);

            return pstm.executeUpdate() > 0;
        }catch (SQLException e){
            con.rollback();
            return false;
        }
    }
}
