package lk.ijse.rms.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lk.ijse.rms.dto.Employee;
import lk.ijse.rms.dto.Item;
import lk.ijse.rms.dto.tm.EmployeeTM;
import lk.ijse.rms.dto.tm.ItemTM;
import lk.ijse.rms.model.EmployeeModel;
import lk.ijse.rms.model.ItemModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ItemFormController implements Initializable {

    public TableView itemTbl;
    public JFXButton newItemBtn;
    public JFXButton backBtn;
    public BorderPane root;
    public JFXButton newItemBtn1;
    public JFXTextField searchTxt;
    public JFXButton searchBtn;
    public JFXTextField itemIdTxt;
    public JFXTextField descriptionTxt;
    public JFXTextField priceTxt;
    public JFXTextField qtyTxt;
    public JFXTextField categoryTxt;
    public JFXButton deleteBtn;
    public JFXButton updateBtn;
    public TableColumn idCol;
    public TableColumn nameCol;
    public TableColumn priceCol;
    public TableColumn qtyCol;
    public TableColumn catIdCol;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

    public void newItemBtnOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/newItemForm.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Item");
        stage.centerOnScreen();
        stage.show();
    }

    public void searchBtnOnAction(ActionEvent actionEvent) {

    }

    public void deleteBtnOnAction(ActionEvent actionEvent) {

    }

    public void updateBtnOnAction(ActionEvent actionEvent) {

    }
    private void getAllValues() {
        try {
            ObservableList<ItemTM> obList = FXCollections.observableArrayList();
            List<Item> ordersList = ItemModel.getAll();

            for (Item item : ordersList) {
                obList.add(new ItemTM(
                        item.getItemId(),
                        item.getName(),
                        item.getUnitPrice(),
                        item.getQtyOnHand(),
                        item.getCatId()
                ));
            }
            itemTbl.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }
    private void setCellValueFactory() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        catIdCol.setCellValueFactory(new PropertyValueFactory<>("catId"));
    }
}
