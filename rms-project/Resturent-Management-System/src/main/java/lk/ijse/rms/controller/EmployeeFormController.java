package lk.ijse.rms.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lk.ijse.rms.dto.Employee;
import lk.ijse.rms.dto.Salary;
import lk.ijse.rms.dto.tm.EmployeeTM;
import lk.ijse.rms.dto.tm.SalaryTM;
import lk.ijse.rms.model.EmployeeModel;
import lk.ijse.rms.model.SalaryModel;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class EmployeeFormController implements Initializable {
    private static final String URL = "jdbc:mysql://localhost:3306/batakade";
    private static final Properties props = new Properties();

    static {
        props.setProperty("User", "root");
        props.setProperty("password", "12345");
    }

    public JFXButton backBtn;
    public JFXButton newEmpBtn;
    public JFXButton salaryBtn;
    public BorderPane root;
    public JFXTextField empIdTxt;
    public JFXTextField empNameTxt;
    public JFXTextField phoneTxt;
    public JFXTextField dateTxt;
    public JFXButton updateBtn;
    public JFXButton deleteBtn;
    public Label noResultLbl;
    public ComboBox selectEmployee;
    public ComboBox typeCombo;
    public JFXButton refreshBtn;
    public TableColumn empIdCol;
    public TableColumn nameCol;
    public TableColumn phoneCol;
    public TableColumn typeCol;
    public TableColumn dateCol;
    public TableView employeeTbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeCombo.getItems().addAll("Cashier","Chef","Cleaner","Waiter","Owner");
        selectEmployee.setValue("Select Employee");
        try {
            selectEmployee.getItems().setAll(SalaryModel.loadNames());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        setCellValueFactory();
        getAllValues();
    }
    public void backBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/newDashboardForm.fxml"))));
        stage.setTitle("DASHBOARD");
        stage.centerOnScreen();
        //stage.setFullScreen(true);
        stage.show();
    }

    public void newEmpBtnOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/newemployeeForm.fxml"));

        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setTitle("Place order");
        stage.centerOnScreen();

        stage.show();
    }

    public void salaryBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/salaryForm.fxml"))));
        stage.setTitle("Salary");
        stage.centerOnScreen();
        //stage.setFullScreen(true);
        stage.show();
    }

    public void updateBtnOnAction(ActionEvent actionEvent) {
        String id= empIdTxt.getText();
        String name= empNameTxt.getText();
        String phone= phoneTxt.getText();
        String type= String.valueOf(typeCombo.getValue());
        try {
            boolean isUpdated = EmployeeModel.update(id, name, phone,type);
            new Alert(Alert.AlertType.CONFIRMATION, "Salary updated!").show();
            setCellValueFactory();
            getAllValues();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void deleteBtnOnAction(ActionEvent actionEvent) {
        String id = empIdTxt.getText();

        try {
            boolean isDeleted = EmployeeModel.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "deleted!").show();
                selectEmployee.getItems().setAll(SalaryModel.loadNames());
                setCellValueFactory();
                getAllValues();
                defaultSet();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void selectEmployeeOnAction(ActionEvent actionEvent) {
        try {
            getEmployeeDetails(String.valueOf(selectEmployee.getValue()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void getAllValues() {
        try {
            ObservableList<EmployeeTM> obList = FXCollections.observableArrayList();
            List<Employee> ordersList = EmployeeModel.getAll();

            for (Employee employee : ordersList) {
                obList.add(new EmployeeTM(
                        employee.getEmpId(),
                        employee.getName(),
                        employee.getPhone(),
                        employee.getType(),
                        employee.getDate()
                ));
            }
            employeeTbl.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void setCellValueFactory() {
        empIdCol.setCellValueFactory(new PropertyValueFactory<>("empId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
    }
    private void getEmployeeDetails(String name) throws SQLException {
        defaultSet();
        try(Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "SELECT * FROM employee WHERE name = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, name);

            ResultSet resultSet = pstm.executeQuery();
            if(resultSet.next()) {
                String id = resultSet.getString(1);
                String empName = resultSet.getString(2);
                String phone = resultSet.getString(3);
                String type = resultSet.getString(4);
                String date = resultSet.getString(5);

                noResultLbl.setVisible(false);

                empIdTxt.setText(id);
                empNameTxt.setText(empName);
                phoneTxt.setText(phone);
                typeCombo.setValue(type);
                dateTxt.setText(date);

                empIdTxt.setDisable(false);
                empNameTxt.setDisable(false);
                phoneTxt.setDisable(false);
                typeCombo.setDisable(false);
                dateTxt.setDisable(false);

                updateBtn.setVisible(true);
                deleteBtn.setVisible(true);
            }
        }
    }
    private void defaultSet(){
        empIdTxt.setDisable(true);
        empNameTxt.setDisable(true);
        phoneTxt.setDisable(true);
        typeCombo.setDisable(true);
        dateTxt.setDisable(true);

        empIdTxt.setEditable(false);
        dateTxt.setEditable(false);

        empIdTxt.setText(null);
        empNameTxt.setText(null);
        phoneTxt.setText(null);
        typeCombo.setValue("Select Employee");
        dateTxt.setText(null);
    }

    public void refreshBtnOnAction(ActionEvent actionEvent) throws SQLException {
        selectEmployee.getItems().setAll(SalaryModel.loadNames());
        setCellValueFactory();
        getAllValues();
    }
}
