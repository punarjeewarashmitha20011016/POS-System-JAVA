package dto;

public class ItemDTO {

    private String itemCode;
    private String itemDescription;
    private double size;
    private int qty;
    private double buyingPrice;
    private double unitPrice;

    public ItemDTO() {
    }

    public ItemDTO(String itemCode, String itemDescription, double size, int qty, double buyingPrice, double unitPrice) {
        this.itemCode = itemCode;
        this.itemDescription = itemDescription;
        this.size = size;
        this.qty = qty;
        this.buyingPrice = buyingPrice;
        this.unitPrice = unitPrice;
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

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "ItemDTO{" +
                "itemCode='" + itemCode + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", size=" + size +
                ", qty=" + qty +
                ", buyingPrice=" + buyingPrice +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
