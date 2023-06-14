package lk.ijse.rms.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.rms.dto.Items;
import lk.ijse.rms.dto.tm.ItemsTM;
import lk.ijse.rms.model.OrderDetailModel;
import lk.ijse.rms.model.OrderModel;
import lombok.SneakyThrows;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import java.net.URL;
import java.util.ResourceBundle;

public class OrderDisplay1Controller implements Initializable {
    public Label OrderId6;
    public TableView itemTbl6;
    public TableColumn itemCol6;
    public Label qty6;
    public Label orderTime6;
    public Label time6;
    public Label status6;
    public JFXButton doneBtn6;
    public TableColumn qtyCol6;

    public Label OrderId5;
    public TableView itemTbl5;
    public TableColumn itemCol5;
    public Label qty5;
    public Label orderTime5;
    public Label time5;
    public Label status5;
    public JFXButton doneBtn5;
    public TableColumn qtyCol5;

    public Label OrderId4;
    public TableView itemTbl4;
    public TableColumn itemCol4;
    public Label qty4;
    public Label orderTime4;
    public Label time4;
    public Label status4;
    public JFXButton doneBtn4;
    public TableColumn qtyCol4;

    public Label OrderId3;
    public TableView itemTbl3;
    public TableColumn itemCol3;
    public Label qty3;
    public Label orderTime3;
    public Label time3;
    public Label status3;
    public JFXButton doneBtn3;
    public TableColumn qtyCol3;

    public Label OrderId2;
    public TableView itemTbl2;
    public TableColumn itemCol2;
    public Label qty2;
    public Label orderTime2;
    public Label time2;
    public Label status2;
    public JFXButton doneBtn2;
    public TableColumn qtyCol2;

    public Label OrderId1;
    public TableView itemTbl1;
    public TableColumn itemCol1;
    public TableColumn qtyCol1;
    public Label qty1;
    public Label orderTime1;
    public Label time1;
    public Label status1;
    public JFXButton doneBtn1;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Timeline timeline = new Timeline(
                new KeyFrame(javafx.util.Duration.seconds(0),
                        event -> {
                            try {
                                order1();
                                order2();
                                order3();
                                order4();
                                order5();
                                order6();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                ),
                new KeyFrame(javafx.util.Duration.seconds(1))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    private void order6() throws SQLException {
        OrderId6.setText(OrderModel.loadData().get(5).getOrderId());
        qty6.setText(String.valueOf(OrderModel.loadData().get(5).getQty()));
        orderTime6.setText(getTime(OrderModel.loadData().get(5).getTime()));
        status6.setText(OrderModel.loadData().get(5).getStatus());
        time6.setText(getTimeDifference(OrderModel.loadData().get(5).getTime()));

        ObservableList<ItemsTM> obList = FXCollections.observableArrayList();

        for (Items item: OrderModel.loadData().get(5).getItems()) {
            obList.add(new ItemsTM(
                    item.getDescription(),
                    item.getQty()
            ));
        }
        itemTbl6.setItems(obList);
        itemCol6.setCellValueFactory(new PropertyValueFactory<>("description"));
        qtyCol6.setCellValueFactory(new PropertyValueFactory<>("qty"));
    }
    private void order5() throws SQLException {
        OrderId5.setText(OrderModel.loadData().get(4).getOrderId());
        qty5.setText(String.valueOf(OrderModel.loadData().get(4).getQty()));
        orderTime5.setText(getTime(OrderModel.loadData().get(4).getTime()));
        status5.setText(OrderModel.loadData().get(4).getStatus());
        time5.setText(getTimeDifference(OrderModel.loadData().get(4).getTime()));

        ObservableList<ItemsTM> obList = FXCollections.observableArrayList();

        for (Items item: OrderModel.loadData().get(4).getItems()) {
            obList.add(new ItemsTM(
                    item.getDescription(),
                    item.getQty()
            ));
        }
        itemTbl5.setItems(obList);
        itemCol5.setCellValueFactory(new PropertyValueFactory<>("description"));
        qtyCol5.setCellValueFactory(new PropertyValueFactory<>("qty"));
    }
    private void order4() throws SQLException {
        OrderId4.setText(OrderModel.loadData().get(3).getOrderId());
        qty4.setText(String.valueOf(OrderModel.loadData().get(3).getQty()));
        orderTime4.setText(getTime(OrderModel.loadData().get(3).getTime()));
        status4.setText(OrderModel.loadData().get(3).getStatus());
        time4.setText(getTimeDifference(OrderModel.loadData().get(3).getTime()));

        ObservableList<ItemsTM> obList = FXCollections.observableArrayList();

        for (Items item: OrderModel.loadData().get(3).getItems()) {
            obList.add(new ItemsTM(
                    item.getDescription(),
                    item.getQty()
            ));
        }
        itemTbl4.setItems(obList);
        itemCol4.setCellValueFactory(new PropertyValueFactory<>("description"));
        qtyCol4.setCellValueFactory(new PropertyValueFactory<>("qty"));
    }
    private void order3() throws SQLException {
        OrderId3.setText(OrderModel.loadData().get(2).getOrderId());
        qty3.setText(String.valueOf(OrderModel.loadData().get(2).getQty()));
        orderTime3.setText(getTime(OrderModel.loadData().get(2).getTime()));
        status3.setText(OrderModel.loadData().get(2).getStatus());
        time3.setText(getTimeDifference(OrderModel.loadData().get(2).getTime()));

        ObservableList<ItemsTM> obList = FXCollections.observableArrayList();

        for (Items item: OrderModel.loadData().get(2).getItems()) {
            obList.add(new ItemsTM(
                    item.getDescription(),
                    item.getQty()
            ));
        }
        itemTbl3.setItems(obList);
        itemCol3.setCellValueFactory(new PropertyValueFactory<>("description"));
        qtyCol3.setCellValueFactory(new PropertyValueFactory<>("qty"));
    }
    private void order2() throws SQLException {
        OrderId2.setText(OrderModel.loadData().get(1).getOrderId());
        qty2.setText(String.valueOf(OrderModel.loadData().get(1).getQty()));
        orderTime2.setText(getTime(OrderModel.loadData().get(1).getTime()));
        status2.setText(OrderModel.loadData().get(1).getStatus());
        time2.setText(getTimeDifference(OrderModel.loadData().get(1).getTime()));

        ObservableList<ItemsTM> obList = FXCollections.observableArrayList();

        for (Items item: OrderModel.loadData().get(1).getItems()) {
            obList.add(new ItemsTM(
                    item.getDescription(),
                    item.getQty()
            ));
        }
        itemTbl2.setItems(obList);
        itemCol2.setCellValueFactory(new PropertyValueFactory<>("description"));
        qtyCol2.setCellValueFactory(new PropertyValueFactory<>("qty"));
    }

    private void order1() throws SQLException {
        OrderId1.setText(OrderModel.loadData().get(0).getOrderId());
        qty1.setText(String.valueOf(OrderModel.loadData().get(0).getQty()));
        orderTime1.setText(getTime(OrderModel.loadData().get(0).getTime()));
        status1.setText(OrderModel.loadData().get(0).getStatus());
        time1.setText(getTimeDifference(OrderModel.loadData().get(0).getTime()));

        ObservableList<ItemsTM> obList = FXCollections.observableArrayList();

        for (Items item: OrderModel.loadData().get(0).getItems()) {
            obList.add(new ItemsTM(
                    item.getDescription(),
                    item.getQty()
            ));
        }
        itemTbl1.setItems(obList);
        itemCol1.setCellValueFactory(new PropertyValueFactory<>("description"));
        qtyCol1.setCellValueFactory(new PropertyValueFactory<>("qty"));
    }

    public void doneBtn6OnAction(ActionEvent actionEvent) {
        try {
            boolean isUpdated = OrderModel.updateStatus(OrderModel.loadData().get(5).getOrderId(),"Done");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void doneBtn5OnAction(ActionEvent actionEvent) {
        try {
            boolean isUpdated = OrderModel.updateStatus(OrderModel.loadData().get(4).getOrderId(),"Done");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void doneBtn4OnAction(ActionEvent actionEvent) {
        try {
            boolean isUpdated = OrderModel.updateStatus(OrderModel.loadData().get(3).getOrderId(),"Done");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void doneBtn3OnAction(ActionEvent actionEvent) {
        try {
            boolean isUpdated = OrderModel.updateStatus(OrderModel.loadData().get(2).getOrderId(),"Done");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void doneBtn2OnAction(ActionEvent actionEvent) {
        try {
            boolean isUpdated = OrderModel.updateStatus(OrderModel.loadData().get(1).getOrderId(),"Done");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void doneBtn1OnAction(ActionEvent actionEvent) {
        try {
            boolean isUpdated = OrderModel.updateStatus(OrderModel.loadData().get(0).getOrderId(),"Done");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static String getTime(String time) {
        String[] parts = time.split(" ");

        return parts[1];
    }
    public static String getTimeDifference(String orderTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime orderDateTime = LocalDateTime.parse(orderTime, formatter);

        LocalDateTime currentDateTime = LocalDateTime.now();
        Duration duration = Duration.between(orderDateTime, currentDateTime);

        long minutes = duration.toMinutes() % 60;
        return String.valueOf(minutes);
    }
}