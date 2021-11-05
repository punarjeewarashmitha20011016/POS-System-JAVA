package dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class OrderDTO {
    private String orderId;
    private String customerId;
    private LocalDate orderDate;
    private String orderTime;
    private BigDecimal cost;
    private ArrayList<ItemDetailsDTO> items;

    public OrderDTO() {
    }

    public OrderDTO(BigDecimal cost) {
        this.cost = cost;
    }

    public OrderDTO(String orderId, String customerId, LocalDate orderDate, String orderTime, BigDecimal cost, ArrayList<ItemDetailsDTO> items) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.cost = cost;
        this.items = items;
    }

    public OrderDTO(String orderId, String customerId, LocalDate orderDate, String orderTime, BigDecimal cost) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.cost = cost;
    }

    public OrderDTO(String orderId, String customerId, ArrayList<ItemDetailsDTO> orderDetails) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.items = orderDetails;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public ArrayList<ItemDetailsDTO> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemDetailsDTO> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", cost=" + cost +
                ", items=" + items +
                '}';
    }
}
