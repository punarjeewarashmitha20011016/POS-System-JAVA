package entity;

import java.math.BigDecimal;

public class OrderDetails {
    private String itemCode;
    private String orderId;
    private int orderQty;
    private BigDecimal discount;
    private BigDecimal price;

    public OrderDetails() {
    }

    public OrderDetails(String itemCode, String orderId, int orderQty, BigDecimal discount, BigDecimal price) {
        this.itemCode = itemCode;
        this.orderId = orderId;
        this.orderQty = orderQty;
        this.discount = discount;
        this.price = price;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "itemCode='" + itemCode + '\'' +
                ", orderId='" + orderId + '\'' +
                ", orderQty=" + orderQty +
                ", discount=" + discount +
                ", price=" + price +
                '}';
    }
}
