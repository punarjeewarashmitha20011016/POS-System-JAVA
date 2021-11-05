package dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CustomDTO {
    private LocalDate orderDate;
    private String orderId;
    private String customerId;
    private String customerName;
    private String itemCode;
    private String itemDescription;
    private int orderQty;
    private BigDecimal discount;
    private BigDecimal price;

    public CustomDTO() {
    }

    public CustomDTO(String itemCode,String itemDescription,int orderQty,BigDecimal totalIncomeFromItems){
        this.itemCode=itemCode;
        this.itemDescription=itemDescription;
        this.orderQty=orderQty;
        this.price=totalIncomeFromItems;
    }
    public CustomDTO(String customerId, String customerName, LocalDate orderDate, String orderId, BigDecimal price) {
        this.orderDate = orderDate;
        this.orderId = orderId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.price = price;
    }

    public CustomDTO(String orderId, String itemCode, int orderQty, BigDecimal discount, BigDecimal price) {
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.orderQty = orderQty;
        this.discount = discount;
        this.price = price;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
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

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
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
        return "CustomDTO{" +
                "orderDate=" + orderDate +
                ", orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", orderQty=" + orderQty +
                ", discount=" + discount +
                ", price=" + price +
                '}';
    }
}
