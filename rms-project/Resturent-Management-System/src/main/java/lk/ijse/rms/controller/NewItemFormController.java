package lk.ijse.rms.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lk.ijse.rms.db.DBConnection;
import lk.ijse.rms.dto.Item;
import lk.ijse.rms.model.EmployeeModel;
import lk.ijse.rms.model.ItemCategoryModel;
import lk.ijse.rms.model.ItemModel;
import lk.ijse.rms.model.SalaryModel;
import lombok.SneakyThrows;

import java.net.URL;
import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;

public class NewItemFormController implements Initializable {
    public AnchorPane root;
    public ComboBox catComb;
    public JFXTextField priceTxt;
    public JFXTextField qtyTxt;
    public JFXTextField descriptionTxt;
    public JFXTextField idTxt;
    public JFXButton saveBtn;
    public Label descriptionLbl;
    public Label categoryLbl;
    public Label priceLbl;

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            catComb.getItems().setAll(ItemCategoryModel.loadCategories());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        idTxt.setText(getNextId());
        descriptionLbl.setVisible(false);
        categoryLbl.setVisible(false);
        priceLbl.setVisible(false);

        descriptionTxt.setText("");
        priceTxt.setText("");
        qtyTxt.setText("");
        catComb.setValue("Select");

    }

    @SneakyThrows
    public void saveBtnOnAction(ActionEvent actionEvent) {
        descriptionLbl.setVisible(false);
        categoryLbl.setVisible(false);
        priceLbl.setVisible(false);
        if(qtyTxt.getText().equals("")){
            qtyTxt.setText("0");
        }
        if (priceTxt.getText().equals("")){
            priceTxt.setText("0");
        }
        String id = idTxt.getText();
        String description = descriptionTxt.getText();
        Double price = Double.valueOf(priceTxt.getText());
        Integer qty = Integer.valueOf(qtyTxt.getText());
        String category = String.valueOf(catComb.getValue());
        String categoryId =ItemCategoryModel.getCategoryId(category);

        if (!id.equals("")&&!description.equals("")&& price!=0.0 && !catComb.getValue().equals("Select")) {
            Item item = new Item(id, description, price, qty, categoryId);
            try {
                boolean isSaved = ItemModel.save(item);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Item Added!").show();
                    idTxt.setText(getNextId());
                    descriptionTxt.setText("");
                    priceTxt.setText("");
                    qtyTxt.setText("");
                    catComb.setValue("Select");
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
                System.out.println(e);
            }
        }else if(description.equals("")){
            descriptionLbl.setVisible(true);
        }else if (price==0.0){
            priceLbl.setVisible(true);
        }else if (catComb.getValue().equals("Select")){
            categoryLbl.setVisible(true);
        }
    }
    private String getNextId() throws SQLException {
        int lastId = Integer.parseInt(ItemModel.getItemIdForNext().substring(1));

        int nextId = lastId + 1;
        String nextIdString = String.format("%04d", nextId);

        if (lastId == 0) {
            return "I0001";
        }

        return "I" + nextIdString;
    }
}
