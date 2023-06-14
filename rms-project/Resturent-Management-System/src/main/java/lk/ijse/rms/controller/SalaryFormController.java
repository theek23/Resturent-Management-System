package lk.ijse.rms.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lk.ijse.rms.dto.Salary;
import lk.ijse.rms.dto.tm.SalaryTM;
import lk.ijse.rms.model.SalaryModel;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class SalaryFormController implements Initializable {
    private static final String URL = "jdbc:mysql://localhost:3306/batakade";
    private static final Properties props = new Properties();

    static {
        props.setProperty("User", "root");
        props.setProperty("password", "12345");
    }
    public JFXTextField searchTxt;
    public JFXButton searchBtn;
    public JFXTextField salaryIdTxt;
    public JFXTextField empNameTxt;
    public JFXTextField dateTimeTxt;
    public JFXTextField amountTxt;
    public JFXTextField otTxt;
    public JFXButton updateBtn;
    public JFXButton deleteBtn;
    public Label noResultLbl;
    public JFXButton refreshBtn;
    public JFXButton salaryReportsBtn;
    @FXML
    private TableView<SalaryTM> salaryTbl;

    @FXML
    private JFXButton backBtn;

    @FXML
    private BorderPane root;

    @FXML
    private JFXButton giveSalaryBtn;

    @FXML
    private TableColumn<Salary, Double> amountCol;

    @FXML
    private TableColumn<Salary, String> dateCol;

    @FXML
    private TableColumn<Salary, String> nameCol;

    @FXML
    private TableColumn<Salary, Double> otCol;

    @FXML
    private TableColumn<Salary, String> salIdCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAllValues();
    }

    public void backBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/employeeForm.fxml"))));
        stage.setTitle("DASHBOARD");
        stage.centerOnScreen();
        //stage.setFullScreen(true);
        stage.show();
    }

    public void giveSalaryBtnOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/newSalaryForm.fxml"));

        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setTitle("Place order");
        stage.centerOnScreen();

        stage.show();
    }
    private void setCellValueFactory() {
        salIdCol.setCellValueFactory(new PropertyValueFactory<>("salaryId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("empId"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        otCol.setCellValueFactory(new PropertyValueFactory<>("OT"));
    }
    private void getAllValues(){
        try {
            ObservableList<SalaryTM> obList = FXCollections.observableArrayList();
            List<Salary> ordersList = SalaryModel.getAll();

            for (Salary salary : ordersList) {
                obList.add(new SalaryTM(
                        salary.getSalaryId(),
                        salary.getEmpId(),
                        salary.getDateTime(),
                        salary.getAmount(),
                        salary.getOT()
                ));
            }
            salaryTbl.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    public void searchBtnOnAction(ActionEvent actionEvent) throws IOException, SQLException {
        String id = searchTxt.getText();
        getSalDetails(id);
    }
    private void defaultSet(){
        salaryIdTxt.setDisable(true);
        empNameTxt.setDisable(true);
        dateTimeTxt.setDisable(true);
        amountTxt.setDisable(true);
        otTxt.setDisable(true);
        salaryIdTxt.setEditable(false);
        empNameTxt.setEditable(false);
        dateTimeTxt.setEditable(false);
        amountTxt.setEditable(false);
        otTxt.setEditable(false);

        salaryIdTxt.setText(null);
        empNameTxt.setText(null);
        dateTimeTxt.setText(null);
        amountTxt.setText(null);
        otTxt.setText(null);
        searchTxt.setText(null);
    }
    private void getSalDetails(String id) throws SQLException {
        try(Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "SELECT salary.salary_id,e.name,salary.dateTime,salary.amount,salary.OT FROM salary INNER JOIN employee e on salary.emp_id = e.emp_id WHERE salary_id = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, id);

            ResultSet resultSet = pstm.executeQuery();
            if(resultSet.next()) {
                String salId = resultSet.getString(1);
                String empName = resultSet.getString(2);
                String time = resultSet.getString(3);
                double amount = resultSet.getDouble(4);
                double ot = resultSet.getDouble(5);

                noResultLbl.setVisible(false);

                salaryIdTxt.setText(salId);
                empNameTxt.setText(empName);
                dateTimeTxt.setText(time);
                amountTxt.setText(String.valueOf(amount));
                otTxt.setText(String.valueOf(ot));

                salaryIdTxt.setDisable(false);
                empNameTxt.setDisable(false);
                dateTimeTxt.setDisable(false);
                amountTxt.setDisable(false);
                amountTxt.setEditable(true);
                otTxt.setDisable(false);
                otTxt.setEditable(true);

                updateBtn.setVisible(true);
                deleteBtn.setVisible(true);
            }else{
                noResultLbl.setVisible(true);
                defaultSet();
            }
        }
    }
    public void updateBtnOnAction(ActionEvent actionEvent) {
        String id= salaryIdTxt.getText();
        String amount= amountTxt.getText();
        String ot= otTxt.getText();
        try {
            boolean isUpdated = SalaryModel.update(id, amount, ot);
            new Alert(Alert.AlertType.CONFIRMATION, "Salary updated!").show();
            setCellValueFactory();
            getAllValues();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void deleteBtnOnAction(ActionEvent actionEvent) {
        String id = salaryIdTxt.getText();

        try {
            boolean isDeleted = SalaryModel.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "deleted!").show();
                setCellValueFactory();
                getAllValues();
                defaultSet();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void refreshBtnOnAction(ActionEvent actionEvent) {
        setCellValueFactory();
        getAllValues();
    }

    public void salaryReportsBtnOnAction(ActionEvent actionEvent) {

    }
}
