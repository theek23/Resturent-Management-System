package lk.ijse.rms.model;

import lk.ijse.rms.db.DBConnection;
import lk.ijse.rms.dto.Employee;
import lk.ijse.rms.dto.Salary;
import lk.ijse.rms.dto.User;

import java.sql.*;
import java.util.Properties;

public class UserModel {
    private static final String URL = "jdbc:mysql://localhost:3306/batakade";
    private static final Properties props = new Properties();

    static {
        props.setProperty("User", "root");
        props.setProperty("password", "12345");
    }
    public static String login(String username) throws SQLException {
        Connection con =null;
        try {
            con = DBConnection.getInstance().getConnection();
            String sql = "SELECT password FROM user WHERE username = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, username);

            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            } else {
                return "mukuth na";
            }
        }catch (SQLException e){
            con.rollback();
            return "mukuth na";
        }
    }
    public static boolean save(User user) throws SQLException {
        Connection con =null;
        try {
            con = DBConnection.getInstance().getConnection();
            String sql = "INSERT INTO user(user_id, emp_id, username, password) " +
                    "VALUES(?, ?, ?, ?)";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, user.getUserId());
            pstm.setString(2, user.getEmpId());
            pstm.setString(3, user.getUsername());
            pstm.setString(4, user.getPassword());

            return pstm.executeUpdate() > 0;
        }catch (SQLException e){
            con.rollback();
            return false;
        }
    }
}
