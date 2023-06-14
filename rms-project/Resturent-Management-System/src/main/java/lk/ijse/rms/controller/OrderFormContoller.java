package lk.ijse.rms.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.rms.dto.OrderDetailSummery;
import lk.ijse.rms.dto.OrdersSummry2;
import lk.ijse.rms.dto.tm.OrderDetailSummeryTM;
import lk.ijse.rms.dto.tm.OrdersSummry2TM;
import lk.ijse.rms.model.ItemModel;
import lk.ijse.rms.model.OrderDetailModel;
import lk.ijse.rms.model.OrderModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class OrderFormContoller implements Initializable {
    public AnchorPane root;
    public JFXButton newOrderBtn;
    public Label date;
    public Label time;
    public TableView ordersTbl;

    public TableColumn orderIdCol;
    public TableColumn priceCol;
    public TableColumn timeCol;
    public TableColumn tblIdCol;
    public TableColumn takeawayIdCol;
    public TableColumn statusCol;

    public JFXButton deleteBtn;
    public JFXButton updateBtn;

    public TableView itemsTbl;
    public TableColumn descripCol;
    public TableColumn qtyCol;
    public TableColumn qtyCol1;
    public TableColumn totalCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeDate();
        /*Timeline timeline = new Timeline(
                new KeyFrame(javafx.util.Duration.seconds(10),
                        event -> {
                            getAllValues();
                            setCellValueFactory();
                        }
                ),
                new KeyFrame(javafx.util.Duration.seconds(1))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();*/
        getAllValues();
        setCellValueFactory();
    }


    public void newOrderBtnOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/placeOrderForm.fxml"));

        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setTitle("Place order");
        stage.centerOnScreen();

        stage.show();
    }

    public void dashboardBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/newDashboardForm.fxml"))));
        stage.setTitle("DASHBOARD");
        stage.centerOnScreen();
        //stage.setFullScreen(true);
        stage.show();
    }

    public void orderBtnOnAction(ActionEvent actionEvent) {

    }

    public void empBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/employeeForm.fxml"))));
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

    public void supBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/supplierForm.fxml"))));
        stage.setTitle("Suppliers");
        stage.centerOnScreen();
        //stage.setFullScreen(true);
        stage.show();
    }

    public void billsBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/billsForm.fxml"))));
        stage.setTitle("Suppliers");
        stage.centerOnScreen();
        //stage.setFullScreen(true);
        stage.show();
    }
    public void display1OnAction(ActionEvent actionEvent) throws IOException {
        BorderPane borderPane = FXMLLoader.load(getClass().getResource("/view/orderDisplayForm1.fxml"));

        Scene scene = new Scene(borderPane);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setTitle("Display 1");
        stage.centerOnScreen();

        stage.show();
    }

    public void display2OnAction(ActionEvent actionEvent) {

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
    private void getAllValues() {
        try {
            ObservableList<OrdersSummry2TM> obList = FXCollections.observableArrayList();
            List<OrdersSummry2> ordersList = OrderModel.loadDatatoOrders();

            for (OrdersSummry2 ordersSummry2 : ordersList) {
                obList.add(new OrdersSummry2TM(
                        ordersSummry2.getOrderId(),
                        ordersSummry2.getPrice(),
                        ordersSummry2.getTime(),
                        ordersSummry2.getTableId(),
                        ordersSummry2.getTakeawayId(),
                        ordersSummry2.getStatus()
                ));
            }
            ordersTbl.setItems(obList);
            orderIdCol.setStyle("-fx-font-size: 14px;");
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }
    private void setCellValueFactory() {
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        //decripCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        //qtyCol.setCellValueFactory(new PropertyValueFactory<>("qty"));
        totalCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        tblIdCol.setCellValueFactory(new PropertyValueFactory<>("tableId"));
        takeawayIdCol.setCellValueFactory(new PropertyValueFactory<>("takeawayId"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

    }

    public void ordersTblOnMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2){
            OrdersSummry2TM order = (OrdersSummry2TM) ordersTbl.getSelectionModel().getSelectedItem();
            setItemTable(order.getOrderId());
            deleteBtn.setVisible(true);
            updateBtn.setVisible(true);
        }
    }
    private void setItemTable(String orderId){
        try {
            ObservableList<OrderDetailSummeryTM> obList = FXCollections.observableArrayList();
            List<OrderDetailSummery> ordersList = ItemModel.getSummery(orderId);

            for (OrderDetailSummery orderDetailSummery : ordersList) {
                obList.add(new OrderDetailSummeryTM(
                        orderDetailSummery.getItemDescription(),
                        orderDetailSummery.getQty(),
                        orderDetailSummery.getPrice()
                ));
            }
            itemsTbl.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
        descripCol.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("qty"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    public void updateBtnOnAction(ActionEvent actionEvent) {
        OrdersSummry2TM order = (OrdersSummry2TM) ordersTbl.getSelectionModel().getSelectedItem();
        System.out.println(order.getOrderId());
        try {
            boolean isDeleted = OrderModel.deleteAll(order.getOrderId());
            if (isDeleted){
                getAllValues();
                setCellValueFactory();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBtnOnAction(ActionEvent actionEvent) {
        OrdersSummry2TM order = (OrdersSummry2TM) ordersTbl.getSelectionModel().getSelectedItem();
        try {
            boolean isDeleted = OrderModel.deleteAll(order.getOrderId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
