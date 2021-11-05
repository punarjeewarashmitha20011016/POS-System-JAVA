package controller;

import bo.BOFactory;
import bo.custom.MakeAnOrderBO;
import com.jfoenix.controls.JFXTextField;
import dto.OrderDTO;
import dto.OrderDetailsDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import view.tdm.OrderTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsFormController {
    public TableView<OrderTm> tblOrderView;
    public TableColumn colOrderId;
    public TableColumn colCustomerId;
    public TableColumn colOrderDate;
    public TableColumn colOrderTime;
    public TableColumn colOrderCost;
    public JFXTextField txtSearchOrderDetails;
    private MakeAnOrderBO orderBO = (MakeAnOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.MakeAnOrder);
    public static String orderIdInOrderDetailsForm;

    public void initialize() {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colOrderTime.setCellValueFactory(new PropertyValueFactory<>("orderTime"));
        colOrderCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        tblOrderView.refresh();
        try {
            getAllOrders();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private void getAllOrders() throws SQLException {
        tblOrderView.getItems().clear();
        ArrayList<OrderDTO> allOrders = orderBO.getAllOrders();
        for (OrderDTO ord : allOrders
        ) {
            tblOrderView.getItems().add(new OrderTm(ord.getOrderId(), ord.getCustomerId(), ord.getOrderDate(), ord.getOrderTime(), ord.getCost()));
        }
    }

    public void searchOrderDetails(ActionEvent actionEvent) throws IOException {
        orderIdInOrderDetailsForm=txtSearchOrderDetails.getText();
        try {
            ArrayList<OrderDetailsDTO> orderDetailsDTOS = orderBO.searchOrderDetails(txtSearchOrderDetails.getText());
            for (int i = 0; i < orderDetailsDTOS.size(); i++) {
                if (txtSearchOrderDetails.getText().equals(orderDetailsDTOS.get(i).getOrderId())) {
                    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/OrderItemDetailsForm.fxml"));
                    Parent load = loader.load();
                    Scene scene = new Scene(load);
                    Stage primaryStage = new Stage();
                    primaryStage.setScene(scene);
                    primaryStage.setTitle("Order Item Details");
                    primaryStage.show();
                    return;
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again No Matching Order Id's exists..", ButtonType.CLOSE).show();
                    return;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
