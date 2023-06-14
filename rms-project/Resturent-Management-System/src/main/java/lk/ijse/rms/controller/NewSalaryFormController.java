package lk.ijse.rms.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.rms.db.DBConnection;
import lk.ijse.rms.dto.Salary;
import lk.ijse.rms.model.EmployeeModel;
import lk.ijse.rms.model.ItemModel;
import lk.ijse.rms.model.SalaryModel;
import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.util.*;

import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewSalaryFormController implements Initializable {

    public AnchorPane root;
    public JFXTextField salaryId;
    public JFXTextField amount;
    public JFXTextField ot;
    public ComboBox employeeComb;
    public JFXButton saveSalaryBtn;

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            employeeComb.getItems().setAll(SalaryModel.loadNames());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        salaryId.setText(getNextId());
        employeeComb.setValue("Select");
    }

    @SneakyThrows
    public void saveSalaryBtnOnAction(ActionEvent actionEvent){
        if(!amount.getText().equals("")&& !employeeComb.getValue().equals("Select")) {
            String id = salaryId.getText();
            String time = timeDate();
            Double amnt = Double.parseDouble(amount.getText());
            Double overTime=0.00;
            if (!ot.getText().equals("")){
                overTime = Double.parseDouble(ot.getText());
            }
            String empName = (String) employeeComb.getValue();
            String empId = EmployeeModel.getEmpIdFromName(empName);

            Salary salary = new Salary(id, empId, time, amnt, overTime);
            try {
                boolean isSaved = SalaryModel.save(salary);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Salary Added!").show();
                    amount.setText("");
                    ot.setText("");
                    salaryId.setText(getNextId());
                    employeeComb.setValue("Select");
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
            }
        }else if(amount.getText().equals("")){
            new Alert(Alert.AlertType.WARNING, "Amount cannot be empty").show();String id = salaryId.getText();
        }else if (employeeComb.getValue().equals("Select")){
            new Alert(Alert.AlertType.WARNING, "Please select employee").show();String id = salaryId.getText();
        }
    }
    public static String timeDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = new Date();

        String formattedDate = dateFormat.format(date);

        return formattedDate;
    }

    private String getNextId() throws SQLException {
        int lastId = Integer.parseInt(SalaryModel.getSalIdForNext().substring(1));

        int nextId = lastId + 1;
        String nextIdString = String.format("%04d", nextId);

        if (lastId == 0) {
            return "S0001";
        }

        return "S" + nextIdString;
    }

}
