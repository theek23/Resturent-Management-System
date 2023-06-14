package lk.ijse.rms.model;

import lk.ijse.rms.db.DBConnection;
import lk.ijse.rms.dto.Orders;
import lk.ijse.rms.dto.OrdersSummry;
import lk.ijse.rms.dto.Salary;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SalaryModel {
    public static List<String> loadNames() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        ResultSet resultSet = con.createStatement().executeQuery("SELECT name FROM employee");

        List<String> data =new ArrayList<>();

        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }
    public static boolean save(Salary salary) throws SQLException {
        Connection con =null;
        try {
            con = DBConnection.getInstance().getConnection();
            String sql = "INSERT INTO salary(salary_id, dateTime, amount, OT, emp_id) " +
                    "VALUES(?, ?, ?, ?,?)";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, salary.getSalaryId());
            pstm.setString(2, salary.getDateTime());
            pstm.setDouble(3, salary.getAmount());
            pstm.setDouble(4, salary.getOT());
            pstm.setString(5, salary.getEmpId());

            return pstm.executeUpdate() > 0;
        }catch (SQLException e){
            con.rollback();
            return false;
        }
    }
    public static boolean update(String id, String amount, String ot) throws SQLException {
        Connection con =null;
        try {
            con = DBConnection.getInstance().getConnection();
            String sql = "UPDATE salary SET amount = ?, OT = ? WHERE salary_id = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setDouble(1, Double.parseDouble(amount));
            pstm.setDouble(2, Double.parseDouble(ot));
            pstm.setString(3, id);

            return pstm.executeUpdate() > 0;
        }catch (SQLException e){
            con.rollback();
            return false;
        }
    }
    public static boolean delete(String salaryId) throws SQLException {
        Connection con =null;
        try {
            con = DBConnection.getInstance().getConnection();
            String sql = "DELETE FROM salary WHERE salary_id = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, salaryId);

            return pstm.executeUpdate() > 0;
        }catch (SQLException e){
            con.rollback();
            return false;
        }
    }
    public static Salary search(String code) throws SQLException {
        Connection con =null;
        try {
            con = DBConnection.getInstance().getConnection();
            String sql = "SELECT * FROM salary WHERE salary_id = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, code);

            ResultSet resultSet = pstm.executeQuery();
            if(resultSet.next()) {
                return new Salary(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4),
                        resultSet.getDouble(4)
                );
            }
            return null;
        }catch (SQLException e){
            con.rollback();
            return null;
        }
    }
    public static List<Salary> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT salary_id,emp_id,dateTime,amount,OT FROM salary";

        List<Salary> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new Salary(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getDouble(5)
            ));
        }
        return data;
    }
    public static String getSalIdForNext() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        ResultSet resultSet = con.createStatement().executeQuery("SELECT salary_id FROM salary ORDER BY salary_id DESC LIMIT 1");
        String lastSalaryId="";
        if (resultSet.next()) {
            lastSalaryId = resultSet.getString("salary_id");

        }
        return lastSalaryId;
    }
}
