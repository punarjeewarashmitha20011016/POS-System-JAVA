package view.tdm;

public class ReturnsTm {
    private String returnId;
    private String orderId;
    private String customerId;
    private String itemCode;
    private String itemDescription;
    private int returnQty;
    private String returnReason;
    private double total;

    public ReturnsTm() {
    }

    public ReturnsTm(String returnId, String orderId, String customerId, String itemCode, String itemDescription, int returnQty, String returnReason, double total) {
        this.returnId = returnId;
        this.orderId = orderId;
        this.customerId = customerId;
        this.itemCode = itemCode;
        this.itemDescription = itemDescription;
        this.returnQty = returnQty;
        this.returnReason = returnReason;
        this.total = total;
    }

    public String getReturnId() {
        return returnId;
    }

    public void setReturnId(String returnId) {
        this.returnId = returnId;
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

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public int getReturnQty() {
        return returnQty;
    }

    public void setReturnQty(int returnQty) {
        this.returnQty = returnQty;
    }

    public String getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Returns{" +
                "returnId='" + returnId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", returnQty=" + returnQty +
                ", returnReason='" + returnReason + '\'' +
                ", total=" + total +
                '}';
    }
}
