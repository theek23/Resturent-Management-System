package lk.ijse.rms.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class BillsFormController {
    public AnchorPane root;

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
        stage.setTitle("Orders");
        stage.centerOnScreen();
        //stage.setFullScreen(true);
        stage.show();
    }

    public void itemBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/itemForm.fxml"))));
        stage.setTitle("Items");
        stage.centerOnScreen();
        //stage.setFullScreen(true);
        stage.show();
    }

    public void empBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/employeeForm.fxml"))));
        stage.setTitle("Employees");
        stage.centerOnScreen();
        //stage.setFullScreen(true);
        stage.show();
    }

    public void supBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/supplierForm.fxml"))));
        stage.setTitle("Suppliers");
        stage.centerOnScreen();
        //stage.setFullScreen(true);
        stage.show();
    }

    public void billsBtnOnAction(ActionEvent actionEvent) throws IOException {
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
}
