package lk.ijse.rms.model;

import lk.ijse.rms.db.DBConnection;
import lk.ijse.rms.dto.Employee;
import lk.ijse.rms.dto.Salary;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
public class EmployeeModel {
    public static boolean save(Employee employee) throws SQLException {
        Connection con =null;
        try {
            con = DBConnection.getInstance().getConnection();
            String sql = "INSERT INTO employee(emp_id, name, phone, type, date) " +
                    "VALUES(?, ?, ?, ?,?)";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, employee.getEmpId());
            pstm.setString(2, employee.getName());
            pstm.setString(3, employee.getPhone());
            pstm.setString(4, employee.getType());
            pstm.setString(5, employee.getDate());

            return pstm.executeUpdate() > 0;
        }catch (SQLException e){
            con.rollback();
            return false;
        }
    }
    public static boolean update(String id, String name, String phone, String type) throws SQLException {
        Connection con =null;
        try {
            con = DBConnection.getInstance().getConnection();
            String sql = "UPDATE employee SET name = ?, phone = ?, type = ? WHERE emp_id = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, name);
            pstm.setString(2, phone);
            pstm.setString(3, type);
            pstm.setString(4, id);

            return pstm.executeUpdate() > 0;
        }catch (SQLException e){
            con.rollback();
            return false;
        }
    }
    public static boolean delete(String id) throws SQLException {
        Connection con =null;
        try {
            con = DBConnection.getInstance().getConnection();
            String sql = "DELETE FROM employee WHERE emp_id = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, id);

            return pstm.executeUpdate() > 0;
        }catch (SQLException e){
            con.rollback();
            return false;
        }
    }
    public static List<String> loadNames() throws SQLException {
        Connection  con = DBConnection.getInstance().getConnection();
        ResultSet resultSet = con.createStatement().executeQuery("SELECT name FROM employee");

        List<String> data =new ArrayList<>();

        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }

    public static List<Employee> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM employee";

        List<Employee> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new Employee(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return data;
    }
    public static  String getEmpIdForNext() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        ResultSet resultSet = con.createStatement().executeQuery("SELECT emp_id FROM employee ORDER BY emp_id DESC LIMIT 1");
        String lastSalaryId="";
        if (resultSet.next()) {
            lastSalaryId = resultSet.getString("emp_id");

        }
        return lastSalaryId;
    }
    public static  String getEmpIdFromName(String empName) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql ="SELECT emp_id FROM employee WHERE name = ?";

        PreparedStatement pstm = con.prepareStatement(sql);

        pstm.setString(1, empName);

        ResultSet resultSet = pstm.executeQuery();
        String empID="";
        if (resultSet.next()) {
            empID = resultSet.getString("emp_id");

        }
        return empID;
    }
}
