package lk.ijse.rms.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.rms.db.DBConnection;
import lk.ijse.rms.dto.Supplier;
import lk.ijse.rms.model.EmployeeModel;
import lk.ijse.rms.model.SalaryModel;
import lk.ijse.rms.model.SupplierModel;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class SupplierFormController implements Initializable {
    public AnchorPane root;
    public JFXTextField supId;
    public JFXTextField supName;
    public JFXTextField supPhone;
    public JFXButton saveBtn;
    public JFXButton updateBtn;
    public ImageView deleteBtn;

    public TableView supTbl;
    public TableColumn supIdCol;
    public TableColumn supNameCol;
    public TableColumn supPhoneCol;

    public Label date;
    public Label time;

    public Label savedLbl;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeDate();
        try {
            supId.setText(getNextId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void dashboardBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/newDashboardForm.fxml"))));
        stage.setTitle("DASHBOARD");
        stage.centerOnScreen();
        //stage.setFullScreen(true);
        stage.show();
    }

    public void orderBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/orderForm.fxml"))));
        stage.setTitle("DASHBOARD");
        stage.centerOnScreen();
        //stage.setFullScreen(true);
        stage.show();
    }

    public void itemBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/itemForm.fxml"))));
        stage.setTitle("DASHBOARD");
        stage.centerOnScreen();
        //stage.setFullScreen(true);
        stage.show();
    }

    public void empBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/employeeForm.fxml"))));
        stage.setTitle("DASHBOARD");
        stage.centerOnScreen();
        //stage.setFullScreen(true);
        stage.show();
    }

    public void supBtnOnAction(ActionEvent actionEvent) throws IOException {
    }

    public void billsBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/billsForm.fxml"))));
        stage.setTitle("Suppliers");
        stage.centerOnScreen();
        //stage.setFullScreen(true);
        stage.show();
    }

    public void settingsBtnOnAction(ActionEvent actionEvent) {

    }

    public void logoutBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/loginForm.fxml"))));
        stage.setTitle("Log in");
        stage.centerOnScreen();
        //stage.setFullScreen(true);
        stage.show();
    }

    public void saveBtnOnAction(ActionEvent actionEvent) {
        if (supPhone.getText().matches("^[0-9]{10,}$")) {
            Supplier supplier = new Supplier(supId.getText(), supName.getText(), supPhone.getText());
            try {
                boolean isSaved = SupplierModel.save(supplier);
                if (isSaved) {
                    savedLbl.setVisible(true);
                    savedLbl.setText("Saved !");
                    supId.setText(getNextId());
                    supName.setText("");
                    supPhone.setText("");

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            savedLbl.setVisible(true);
            savedLbl.setText("Enter valid phone number");
        }
    }

    public void updateBtnOnAction(ActionEvent actionEvent) {
        if (supPhone.getText().matches("^[0-9]{10,}$")) {
            try {
                boolean isUpdated = SupplierModel.update(supName.getText(), supPhone.getText(), supId.getText());
                savedLbl.setVisible(true);
                savedLbl.setText("Updated.");
            } catch (SQLException e) {
                e.printStackTrace();

            }
        }else {
            savedLbl.setVisible(true);
            savedLbl.setText("Enter valid phone number");
        }
    }

    public void deleteBtnOnAction(MouseEvent mouseEvent) {
        try {
            boolean isDeleted = SupplierModel.delete(supId.getText());
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "deleted!").show();
                savedLbl.setVisible(true);
                savedLbl.setText("Supplier deleted");
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }
    public void timeDate(){
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        event -> {
                            LocalDate currentDate = LocalDate.now();
                            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
                            date.setText(dateFormatter.format(currentDate));

                            LocalTime currentTime = LocalTime.now();
                            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
                            time.setText(timeFormatter.format(currentTime));
                        }
                ),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    private String getNextId() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        ResultSet resultSet = con.createStatement().executeQuery("SELECT sup_id FROM suplier ORDER BY sup_id DESC LIMIT 1");

        int lastId = 0;
        if (resultSet.next()) {
            String lastSalaryId = resultSet.getString("sup_id");
            lastId = Integer.parseInt(lastSalaryId.substring(1));
        }

        int nextId = lastId + 1;
        String nextIdString = String.format("%04d", nextId);

        if (lastId == 0) {
            return "S0001";
        }

        return "S" + nextIdString;
    }
}
