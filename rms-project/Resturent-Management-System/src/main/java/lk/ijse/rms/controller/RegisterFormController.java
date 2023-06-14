package lk.ijse.rms.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lk.ijse.rms.db.DBConnection;
import lk.ijse.rms.dto.User;
import lk.ijse.rms.model.EmployeeModel;
import lk.ijse.rms.model.UserModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterFormController {
    public Button RegisterBtn;
    public JFXTextField employeeIdTxt;
    public JFXTextField usernameTxt;
    public JFXTextField pwTxtField;
    public JFXPasswordField pwFieldTxt;
    public ImageView hidePwBtn;
    public ImageView viewPwBtn;
    public Label incorrectUserNameLbl;
    public Label validPwLbl;

    public void viewPwBtnOnMouseClicked(MouseEvent mouseEvent) {
        viewPwBtn.setVisible(false);
        hidePwBtn.setVisible(true);
        pwTxtField.setVisible(true);
        pwFieldTxt.setVisible(false);
        pwTxtField.setDisable(false);
        pwTxtField.setText(pwFieldTxt.getText());
    }

    public void hidePwBtnOnMouseClicked(MouseEvent mouseEvent) {
        hidePwBtn.setVisible(false);
        viewPwBtn.setVisible(true);
        pwFieldTxt.setVisible(true);
        pwTxtField.setVisible(false);
        pwFieldTxt.setDisable(false);
        pwFieldTxt.setText(pwTxtField.getText());
    }

    public void pwFieldTxtTextChanged(ActionEvent actionEvent) {

    }

    public void pwTxtFieldTextChanged(ActionEvent actionEvent) {

    }

    public void RegisterBtnOnAction(ActionEvent actionEvent) {
        incorrectUserNameLbl.setVisible(false);
        if (usernameTxt.getText().matches("^[a-z]{1,11}$")){
            String userId = "U9999";
            try {
                userId = getNextId();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            String empId = employeeIdTxt.getText();
            String username = usernameTxt.getText();
            String pw = pwFieldTxt.getText();

            User user = new User(userId,empId,username,pw);
            try {
                boolean isSaved = UserModel.save(user);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "User Added!").show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
            }
        }else {
            incorrectUserNameLbl.setVisible(true);
        }
    }
    private String getNextId() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        ResultSet resultSet = con.createStatement().executeQuery("SELECT user_id FROM user ORDER BY emp_id DESC LIMIT 1");

        int lastId = 0;
        if (resultSet.next()) {
            String lastSalaryId = resultSet.getString("user_id");
            lastId = Integer.parseInt(lastSalaryId.substring(1));
        }

        int nextId = lastId + 1;
        String nextIdString = String.format("%04d", nextId);

        if (lastId == 0) {
            return "U0001";
        }

        return "U" + nextIdString;
    }
}
