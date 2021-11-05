package controller;

import bo.BOFactory;
import bo.custom.MakeAnOrderBO;
import dto.OrderDetailsDTO;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import view.tdm.OrderDetailsTm;

import java.sql.SQLException;
import java.util.ArrayList;

import static controller.OrderDetailsFormController.orderIdInOrderDetailsForm;
import static controller.ReturnsFormController.orderIdInReturnsForm;

public class OrderItemDetailsFormController {
    public TableView<OrderDetailsTm> tblOrderDetailsView;
    public TableColumn colItemCode;
    public TableColumn colOrderId;
    public TableColumn colOrderQty;
    public TableColumn colDiscount;
    public TableColumn colPrice;
    private MakeAnOrderBO orderBO = (MakeAnOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.MakeAnOrder);
    String orderId;
    String orderIdReturns;

    public void initialize() throws SQLException {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colOrderQty.setCellValueFactory(new PropertyValueFactory<>("orderQty"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        setDatToTable();
        tblOrderDetailsView.refresh();
    }

    public void setDatToTable() throws SQLException {
        orderId = orderIdInOrderDetailsForm;
        orderIdReturns = orderIdInReturnsForm;
        ArrayList<OrderDetailsDTO> allOrderDetails = null;
        if (orderId != null) {
            allOrderDetails = orderBO.searchOrderDetails(orderId);
        } else if (orderIdReturns != null) {
            allOrderDetails = orderBO.searchOrderDetails(orderIdReturns);
        }
        tblOrderDetailsView.getItems().clear();
        for (OrderDetailsDTO dto : allOrderDetails
        ) {
            tblOrderDetailsView.getItems().add(new OrderDetailsTm(dto.getItemCode(), dto.getOrderId(), dto.getOrderQty(), dto.getDiscount(), dto.getPrice()));
        }
    }
}
