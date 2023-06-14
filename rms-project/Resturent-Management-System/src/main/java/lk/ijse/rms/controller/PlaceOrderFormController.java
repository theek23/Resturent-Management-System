package lk.ijse.rms.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.rms.db.DBConnection;
import lk.ijse.rms.dto.OrderDetail;
import lk.ijse.rms.dto.OrderDetailSummery;
import lk.ijse.rms.dto.Orders;
import lk.ijse.rms.dto.tm.OrderDetailSummeryTM;
import lk.ijse.rms.dto.tm.OrdersSummryTM;
import lk.ijse.rms.dto.tm.OrdersTM;
import lk.ijse.rms.model.*;
import lombok.SneakyThrows;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class PlaceOrderFormController implements Initializable {
    public AnchorPane root;
    public JFXTextField orderIdTxt;
    public JFXTextField customerNameTxt;
    public JFXTextField custPhoneTxt;
    public JFXTextField totalTxt;
    public ComboBox orderStatusComb;
    public ComboBox itemCombo;
    public JFXTextField qtyTxt;
    public ComboBox orderTypeCombo;
    public TableView itemTbl;
    public TableColumn<?, ?> itemCol;
    public TableColumn<?, ?> qtyCol;
    public TableColumn<?, ?> priceCol;
    public JFXButton saveOrderBtn;
    public JFXTextField countTxt;
    public Label addTxt;
    public JFXButton deleteBtn;
    public JFXButton updateBtn;
    public ImageView doneIcon;

    private Integer qtyCount=0;

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            itemCombo.getItems().setAll(ItemModel.loadItems());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        orderIdTxt.setText(getNextOrderId());
        orderTypeCombo.getItems().setAll("Dine in", "Takeaway", "Delivery");
        orderStatusComb.getItems().setAll("Processing", "Done", "Pending Payment");

        itemCombo.getEditor().setOnKeyPressed(event -> {
            String text = itemCombo.getEditor().getText();
            itemCombo.getItems().setAll("");
            try {
                if(!ItemModel.loadItemsByName(text+"%").equals(null)) {
                    if (!ItemModel.loadItemsByName(text + "%").isEmpty()) {
                        itemCombo.getItems().setAll(ItemModel.loadItemsByName(text + "%"));
                        itemCombo.show();
                    } else {
                        itemCombo.getItems().setAll(ItemModel.loadItemsByName("%" + text + "%"));
                        itemCombo.show();
                    }
                }else {
                    System.out.println("Something went wrong");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        countTxt.setText("1");
        addTxt.setVisible(false);
        totalTxt.setText("0.00");
        orderTypeCombo.setValue("Select Type");
        orderStatusComb.setValue("Select Status");
    }
    @SneakyThrows
    public void qtyTxtOnAction(ActionEvent actionEvent) {
        addTxt.setVisible(false);
        deleteBtn.setVisible(false);
        updateBtn.setVisible(false);

        addTxt.setText("Already Added. You can update it by clicking it");
        if (!qtyTxt.getText().equals("")) {
            String orderId = orderIdTxt.getText();
            String itemId = "";
            if (ItemModel.getItemId(itemCombo.getEditor().getText()) != null) {
                itemId =ItemModel.getItemId(itemCombo.getEditor().getText());
            } else {
                System.out.println("Error. Null value ekak enne methanata");
            }
            Integer qty = Integer.valueOf(qtyTxt.getText());
            String time = timeDate();
            Double unitPrice = Double.valueOf(ItemModel.getUnitPrice(itemId));
            String custName = "No Name";
            String custPhone = "No Phone";
            String orderStatus = "Processing";
            Double total = Double.valueOf(totalTxt.getText());
            String empId = "E0002";
            String benchId = "B0001";
            String takeawayId = "T0001";
            String delId = "D0001";
            int count = Integer.parseInt(countTxt.getText());

            Orders orders = new Orders(
                    orderId, custName, custPhone, orderStatus, total, qty, empId, benchId, takeawayId, delId, time);
            OrderDetail orderDetail = new OrderDetail(orderId, itemId, qty, time, unitPrice);

            if (count == 1) {
                try {
                    boolean isSaved = OrderModel.save(orders);
                    if (isSaved) {
                        System.out.println("Done");
                    }
                } catch (SQLException e) {
                    System.out.println("Error");
                    System.out.println(e);
                }
            }
            try {
                boolean isSaved = OrderDetailModel.save(orderDetail);
                if (isSaved) {
                    System.out.println("Done");
                }
            } catch (SQLException e) {
                System.out.println("Error");
                System.out.println(e);
                addTxt.setVisible(true);
            }
            addCountOfActionEvent();
            setItemsToTable(orderId);
            qtyTxt.setText("");
        }else{
            addTxt.setVisible(true);
            addTxt.setText("Please add QTY");
        }
        itemCombo.setValue("");
    }

    public void itemComboOnMouseClicked(MouseEvent mouseEvent) {
        //itemCombo.setValue("");
    }
    public void itemTblOnMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2){
            getItemsFromTable();
            deleteBtn.setVisible(true);
            updateBtn.setVisible(true);
        }
    }

    public void updateBtnOnAction(ActionEvent actionEvent) {
        String orderId = orderIdTxt.getText();
        String itemId="";
        try {
            itemId = ItemModel.getItemId(itemCombo.getEditor().getText());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Integer qty= Integer.valueOf(qtyTxt.getText());
        try {
            boolean isUpdated = OrderDetailModel.update(qty,orderId,itemId);
            if (isUpdated) {
                setItemsToTable(orderId);
            }
        } catch (SQLException e) {
            System.out.println(e);
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }

    public void deleteBtnOnAction(ActionEvent actionEvent) {
        String orderId = orderIdTxt.getText();
        String itemId="";
        try {
            itemId = ItemModel.getItemId(itemCombo.getEditor().getText());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            System.out.println(itemId);
            boolean isDeleted = OrderDetailModel.delete(orderId,itemId);
            if (isDeleted) {
                setItemsToTable(orderId);
            }
        } catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
    }
    @SneakyThrows
    public void saveOrderBtnOnAction(ActionEvent actionEvent) {
        String orderId = orderIdTxt.getText();
        String custName = customerNameTxt.getText();
        if (custName.equals("")){
            custName="No Name";
        }
        String custPhone = custPhoneTxt.getText();
        if (custPhone.equals("")){
            custPhone="No Phone";
        }
        String orderStatus = String.valueOf(orderStatusComb.getValue());
        Integer qty = qtyCount;
        Double orderPrice = Double.valueOf(totalTxt.getText());
        String benchId = "B0001";
        String takeawayId = "T0001";
        String delId = "D0001";
        System.out.println(qtyCount);

        String orderType = String.valueOf(orderTypeCombo.getValue());
        /*switch (orderType){
            case "Dine in":
                benchId=getNextbenchId();
                break;
            case "Takeaway" :
                takeawayId=getNextTakeawayId();
                break;
            case "Delivery" :
                delId = getNextDelId();
        }*/
        try {
            boolean isUpdated = OrderModel.update(custName, custPhone,orderStatus,qty,orderPrice,benchId,takeawayId,delId,orderId);
            if (isUpdated) {
                doneIcon.setVisible(true);
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
                    doneIcon.setVisible(false);
                }));
                timeline.play();
                clearFields();
            }
        } catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }
        try {
            JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/jasper/BillReport.jrxml");
            JRDesignQuery query= new JRDesignQuery();
            query.setText("SELECT orders.order_id, orders.cust_name, DATE(time), o.item_id, i.name, o.qty, i.unit_price,o.qty*o.unitPrice AS Amount,orders.order_price FROM orders\n" +
                    "INNER JOIN orderdetail o on orders.order_id = o.order_id\n" +
                    "INNER JOIN item i on o.item_id = i.item_id WHERE o.order_id = '"+orderId+"';");
            jasperDesign.setQuery(query);

            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public static String timeDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = new Date();

        String formattedDate = dateFormat.format(date);

        return formattedDate;
    }
    private String getNextOrderId() throws SQLException {
        int lastId = Integer.parseInt(OrderModel.getOrderIdForNext().substring(1));

        int nextId = lastId + 1;
        String nextIdString = String.format("%04d", nextId);

        if (lastId == 0) {
            return "O0001";
        }
        return "O" + nextIdString;
    }
    /*private String getNextbenchId() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        ResultSet resultSet = con.createStatement().executeQuery("SELECT bench_id FROM bench ORDER BY bench_id DESC LIMIT 1");

        int lastId = 0;
        if (resultSet.next()) {
            String lastSalaryId = resultSet.getString("bench_id");
            lastId = Integer.parseInt(lastSalaryId.substring(1));
        }

        int nextId = lastId + 1;
        String nextIdString = String.format("%04d", nextId);

        if (lastId == 0) {
            return "B0001";
        }
        return "B" + nextIdString;
    }
    private String getNextTakeawayId() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        ResultSet resultSet = con.createStatement().executeQuery("SELECT takeaway_id FROM takeaway ORDER BY takeaway_id DESC LIMIT 1");

        int lastId = 0;
        if (resultSet.next()) {
            String lastSalaryId = resultSet.getString("takeaway_id");
            lastId = Integer.parseInt(lastSalaryId.substring(1));
        }

        int nextId = lastId + 1;
        String nextIdString = String.format("%04d", nextId);

        if (lastId == 0) {
            return "T0001";
        }
        return "T" + nextIdString;
    }
    private String getNextDelId() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        ResultSet resultSet = con.createStatement().executeQuery("SELECT del_id FROM delivery ORDER BY del_id DESC LIMIT 1");

        int lastId = 0;
        if (resultSet.next()) {
            String lastSalaryId = resultSet.getString("del_id");
            lastId = Integer.parseInt(lastSalaryId.substring(1));
        }

        int nextId = lastId + 1;
        String nextIdString = String.format("%04d", nextId);

        if (lastId == 0) {
            return "D0001";
        }
        return "D" + nextIdString;
    }*/
    private void addCountOfActionEvent(){
        int lastCount=Integer.parseInt(countTxt.getText());
        int nextId=lastCount+1;
        countTxt.setText(String.valueOf(nextId));
    }
    private void setItemsToTable(String orderId) {
        try {
            ObservableList<OrderDetailSummeryTM> obList = FXCollections.observableArrayList();
            List<OrderDetailSummery> ordersList = ItemModel.getSummery(orderId);
            Double total= Double.valueOf(0);
            qtyCount=0;

            for (OrderDetailSummery orderDetailSummery : ordersList) {
                obList.add(new OrderDetailSummeryTM(
                        orderDetailSummery.getItemDescription(),
                        orderDetailSummery.getQty(),
                        orderDetailSummery.getPrice()
                ));
                total=total+orderDetailSummery.getPrice();
                qtyCount=qtyCount+orderDetailSummery.getQty();
            }
            totalTxt.setText(String.valueOf(total));
            itemTbl.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
        itemCol.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("qty"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        itemCol.setStyle("-fx-font-size: 14px;");

        qtyCol.setEditable(true);

    }
    private void getItemsFromTable(){
        OrderDetailSummeryTM rowData = (OrderDetailSummeryTM) itemTbl.getSelectionModel().getSelectedItem();
        itemCombo.setValue(rowData.getItemDescription());
        qtyTxt.setText(String.valueOf(rowData.getQty()));
    }
    private void clearFields(){
        try {
            orderIdTxt.setText(getNextOrderId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        customerNameTxt.setText("");
        custPhoneTxt.setText("");
        orderTypeCombo.setValue("Select Type");
        orderStatusComb.setValue("Select Status");
        countTxt.setText("1");
        addTxt.setVisible(false);
        totalTxt.setText("0.00");
        itemCombo.setValue("");
        itemTbl.setItems(null);
    }
}
