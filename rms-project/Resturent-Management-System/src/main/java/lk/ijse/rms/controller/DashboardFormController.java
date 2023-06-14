package lk.ijse.rms.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.rms.db.DBConnection;
import lk.ijse.rms.dto.Orders;
import lk.ijse.rms.dto.tm.OrdersSummryTM;
import lk.ijse.rms.dto.tm.OrdersTM;
import lk.ijse.rms.model.OrderModel;
import lombok.SneakyThrows;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;


public class DashboardFormController implements Initializable {
    public JFXButton orderBtn;
    public JFXButton empBtn;
    public JFXButton itemBtn;
    public JFXButton supBtn;
    public JFXButton billsBtn;
    public AnchorPane root;
    public JFXButton logoutBtn;
    public Rectangle dashboardRect;
    public Rectangle orderRect;
    public Rectangle empRect;
    public Rectangle itemRect;
    public Rectangle supRect;
    public Rectangle billRect;
    public JFXButton dashboardBtn;
    public Label tdyLbl;
    public Label yesterdayLbl;


    public LineChart lineChart;
    public CategoryAxis x;
    public NumberAxis y;
    public Label last7Lbl;
    public Label thisMonthLbl;
    public Label totalLbl;
    public AreaChart areaChart;
    public CategoryAxis areaX;
    public NumberAxis areaY;


    @FXML
    private Label date;

    @FXML
    private Label time;

    @FXML
    private TableColumn<?, ?> itemQTY;

    @FXML
    private TableColumn<?, ?> orderId;

    @FXML
    private TableView<OrdersTM> orderTable;

    @FXML
    private TableColumn<?, ?> price;

    @FXML
    private TableColumn<?, ?> status;

    @FXML
    private TableColumn<?, ?> custName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeDate();
        setCellValueFactory();
        getSummuryAll();
        addLineChart();
        addAreaChart();
        try {
            tdyLbl.setText(String.valueOf(OrderModel.todaySales()));
            yesterdayLbl.setText(String.valueOf(OrderModel.yesterdaySales()));
            last7Lbl.setText(String.valueOf(OrderModel.last7DaysSales()));
            thisMonthLbl.setText(String.valueOf(OrderModel.thisMothSales()));
            totalLbl.setText(String.valueOf(OrderModel.totalSales()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public void orderBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/orderForm.fxml"))));
        stage.setTitle("Suppliers");
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

    public void LogoutBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/loginForm.fxml"))));
        stage.setTitle("Log in");
        stage.centerOnScreen();
        //stage.setFullScreen(true);
        stage.show();
    }

    public void reportBtnOnAction(ActionEvent actionEvent) {

    }
    private void addAreaChart() {
        XYChart.Series series= new XYChart.Series();

        series.getData().add(new XYChart.Data("1",150));
        series.getData().add(new XYChart.Data("10",250));
        series.getData().add(new XYChart.Data("30",756));
        series.getData().add(new XYChart.Data("50",156));
        series.getData().add(new XYChart.Data("70",423));
        series.getData().add(new XYChart.Data("100",120));

        XYChart.Series series2= new XYChart.Series();
        series.setName("This Month");

        series2.getData().add(new XYChart.Data("1",170));
        series2.getData().add(new XYChart.Data("10",520));
        series2.getData().add(new XYChart.Data("30",457));
        series2.getData().add(new XYChart.Data("50",364));
        series2.getData().add(new XYChart.Data("70",425));
        series2.getData().add(new XYChart.Data("100",475));

        areaChart.getData().addAll(series,series2);
        series2.setName("Last Month");
    }
    private void addLineChart() {
        XYChart.Series series= new XYChart.Series();

        series.getData().add(new XYChart.Data("6",150));
        series.getData().add(new XYChart.Data("7",250));
        series.getData().add(new XYChart.Data("8",300));
        series.getData().add(new XYChart.Data("9",450));
        series.getData().add(new XYChart.Data("10",500));
        series.getData().add(new XYChart.Data("11",420));

        XYChart.Series series2= new XYChart.Series();
        series.setName("This Month");

        series2.getData().add(new XYChart.Data("6",170));
        series2.getData().add(new XYChart.Data("7",520));
        series2.getData().add(new XYChart.Data("8",457));
        series2.getData().add(new XYChart.Data("9",364));
        series2.getData().add(new XYChart.Data("10",425));
        series2.getData().add(new XYChart.Data("11",475));

        lineChart.getData().addAll(series,series2);
        //areaChart.getData().addAll(series,series2);
        series2.setName("Last Month");
    }

    private void setCellValueFactory() {
        orderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        itemQTY.setCellValueFactory(new PropertyValueFactory<>("qty"));
        price.setCellValueFactory(new PropertyValueFactory<>("orderPrice"));
        status.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));
        custName.setCellValueFactory(new PropertyValueFactory<>("custName"));
    }

    private void getSummuryAll() {
        try {
            ObservableList<OrdersTM> obList = FXCollections.observableArrayList();
            List<Orders> ordersList = OrderModel.getSummery();

            for (Orders orders : ordersList) {
                obList.add(new OrdersSummryTM(
                        orders.getOrderId(),
                        orders.getQty(),
                        orders.getOrderPrice(),
                        orders.getOrderStatus(),
                        orders.getCustName()
                ));
            }
            orderTable.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
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
    @SneakyThrows
    public void last30DaysBtnOnAction(ActionEvent actionEvent) {
        try {
            JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/jasper/report.jrxml");
            JRDesignQuery query= new JRDesignQuery();
            query.setText("SELECT SUM(order_price) FROM orders;");
            jasperDesign.setQuery(query);

            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}