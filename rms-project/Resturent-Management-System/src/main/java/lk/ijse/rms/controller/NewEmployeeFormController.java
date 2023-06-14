package lk.ijse.rms.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.rms.db.DBConnection;
import lk.ijse.rms.dto.Employee;
import lk.ijse.rms.model.EmployeeModel;
import lk.ijse.rms.model.SalaryModel;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.ResourceBundle;

public class NewEmployeeFormController implements Initializable {
    public AnchorPane root;
    public ComboBox empTypeCombo;
    public JFXTextField empIdTxt;
    public JFXTextField empName;
    public JFXTextField empPhone;
    public JFXButton saveBtn;
    public Label emptyEmpLbl;
    public Label selectLbl;
    public Label emptyPhoneLbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        empTypeCombo.getItems().addAll("Cashier","Chef","Cleaner","Waiter","Owner");
        try {
            empIdTxt.setText(getNextId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        empName.setText("");
        empPhone.setText("");
        empTypeCombo.setValue("");
        empTypeCombo.setValue("Select");

        emptyEmpLbl.setVisible(false);
        emptyPhoneLbl.setVisible(false);
        selectLbl.setVisible(false);
    }

    public void saveBtnOnAction(ActionEvent actionEvent) {
        emptyEmpLbl.setVisible(false);
        emptyPhoneLbl.setVisible(false);
        selectLbl.setVisible(false);

        String id= empIdTxt.getText();
        String name= empName.getText();
        String phone= empPhone.getText();
        String type = String.valueOf(empTypeCombo.getValue());
        String date= date();
        if (!name.equals("")&&!phone.equals("")&&!type.equals("Select")){
            Employee employee= new Employee(id,name,phone,type,date);
            try {
                boolean isSaved = EmployeeModel.save(employee);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Employee Added!").show();
                    empIdTxt.setText(getNextId());
                    empName.setText("");
                    empPhone.setText("");
                    empTypeCombo.setValue("");
                    empTypeCombo.setValue("Select");
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
            }
        }else if(name.equals("")){
            emptyEmpLbl.setVisible(true);
        }else if(phone.equals("")){
            emptyPhoneLbl.setVisible(true);
        }else if(type.equals("Select")){
            selectLbl.setVisible(true);
        }
    }
    public static String date() {
        LocalDate currentDate = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);

        return formattedDate;
    }
    private String getNextId() throws SQLException {
        int lastId = Integer.parseInt(EmployeeModel.getEmpIdForNext().substring(1));

        int nextId = lastId + 1;
        String nextIdString = String.format("%04d", nextId);

        if (lastId == 0) {
            return "E0001";
        }

        return "E" + nextIdString;
    }
}
