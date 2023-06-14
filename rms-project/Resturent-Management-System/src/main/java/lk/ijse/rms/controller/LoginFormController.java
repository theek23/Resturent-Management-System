package lk.ijse.rms.controller;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.rms.model.EmployeeModel;
import lk.ijse.rms.model.UserModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {

    public BorderPane root;
    public ImageView viewPwBtn;
    public ImageView hidePwBtn;
    public Button loginBtn;
    public Text forgetPw;
    public JFXTextField usernameTxt;
    public JFXTextField pwTxtField;
    public JFXPasswordField pwFieldTxt;
    public JFXCheckBox rememberChkBtn;
    public Label incorrectUserLbl;
    public Label incorrectPwLbl;
    public Label incorrectUPLbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        incorrectUserLbl.setVisible(false);
        incorrectPwLbl.setVisible(false);
    }

    public void forgetPwOnMouseClicked(MouseEvent mouseEvent) {
    }

    public void loginBtnOnAction(ActionEvent actionEvent) throws IOException {
        /*Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/newDashboardForm.fxml"))));
        stage.setTitle("DASHBOARD");
        stage.centerOnScreen();
        //stage.setFullScreen(true);
        stage.show();*/
        incorrectUserLbl.setVisible(false);
        incorrectPwLbl.setVisible(false);
        String username=usernameTxt.getText();
        String password=pwFieldTxt.getText();
        try {
            String pw = UserModel.login(username);
            if (!pw.equals("mukuth na")){
                if (password.equals(pw)){
                    Stage stage = (Stage) root.getScene().getWindow();
                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/newDashboardForm.fxml"))));
                    stage.setTitle("DASHBOARD");
                    stage.centerOnScreen();
                    //stage.setFullScreen(true);
                    stage.show();
                }else {
                    incorrectPwLbl.setVisible(true);
                }
            }else {
                incorrectUserLbl.setVisible(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
            System.out.println(e);
        }
    }


    public void hidePwBtnOnMouseClicked(MouseEvent mouseEvent) {
        hidePwBtn.setVisible(false);
        viewPwBtn.setVisible(true);
        pwFieldTxt.setVisible(true);
        pwTxtField.setVisible(false);
        pwFieldTxt.setDisable(false);
        pwFieldTxt.setText(pwTxtField.getText());
    }

    public void viewPwBtnOnMouseClicked(MouseEvent mouseEvent) {
        viewPwBtn.setVisible(false);
        hidePwBtn.setVisible(true);
        pwTxtField.setVisible(true);
        pwFieldTxt.setVisible(false);
        pwTxtField.setDisable(false);
        pwTxtField.setText(pwFieldTxt.getText());
        /*pwFieldTxt.setDisable(true);
        viewPwBtn.setDisable(true);*/
    }

    public void pwTxtFieldTextChanged(InputMethodEvent inputMethodEvent) {
        /*if (!pwFieldTxt.getText().equals(null)){
            hidePwBtn.setDisable(false);
        }*/
    }

    public void pwFieldTxtTextChanged(InputMethodEvent inputMethodEvent) {
        /*if (!pwFieldTxt.getText().equals(null)){
            viewPwBtn.setDisable(false);
        }*/
    }

    public void RegisterBtnOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/registerForm.fxml"));

        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setTitle("Place order");
        stage.centerOnScreen();

        stage.show();;
    }
}
