package view.tdm;

import java.math.BigDecimal;

public class CartTm {
    private String itemCode;
    private String itemDescription;
    private int itemQtyOnHand;
    private BigDecimal itemUnitPrice;
    private BigDecimal discountPrice;
    private BigDecimal total;

    public CartTm() {
    }

    public CartTm(String itemCode, String itemDescription, int itemQtyOnHand, BigDecimal itemUnitPrice, BigDecimal discountPrice, BigDecimal total) {
        this.itemCode = itemCode;
        this.itemDescription = itemDescription;
        this.itemQtyOnHand = itemQtyOnHand;
        this.itemUnitPrice = itemUnitPrice;
        this.discountPrice = discountPrice;
        this.total = total;
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

    public int getItemQtyOnHand() {
        return itemQtyOnHand;
    }

    public void setItemQtyOnHand(int itemQtyOnHand) {
        this.itemQtyOnHand = itemQtyOnHand;
    }

    public BigDecimal getItemUnitPrice() {
        return itemUnitPrice;
    }

    public void setItemUnitPrice(BigDecimal itemUnitPrice) {
        this.itemUnitPrice = itemUnitPrice;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "CartTm{" +
                "itemCode='" + itemCode + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", itemQtyOnHand=" + itemQtyOnHand +
                ", itemUnitPrice=" + itemUnitPrice +
                ", discountPrice=" + discountPrice +
                ", total=" + total +
                '}';
    }
}
