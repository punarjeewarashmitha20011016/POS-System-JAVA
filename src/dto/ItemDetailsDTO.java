package dto;

import java.math.BigDecimal;

public class ItemDetailsDTO {
    private String itemCode;
    private int itemQtyOnHand;
    private BigDecimal unitPrice;
    private BigDecimal discountPrice;

    public ItemDetailsDTO() {
    }

    public ItemDetailsDTO(String itemCode, int itemQtyOnHand,BigDecimal discountPrice,BigDecimal unitPrice) {
        this.itemCode = itemCode;
        this.itemQtyOnHand = itemQtyOnHand;
        this.unitPrice = unitPrice;
        this.discountPrice=discountPrice;
    }

    public ItemDetailsDTO(String itemCode, int itemQtyOnHand, BigDecimal itemUnitPrice) {
        this.itemCode = itemCode;
        this.itemQtyOnHand = itemQtyOnHand;
        this.unitPrice = unitPrice;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getItemQtyOnHand() {
        return itemQtyOnHand;
    }

    public void setItemQtyOnHand(int itemQtyOnHand) {
        this.itemQtyOnHand = itemQtyOnHand;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    @Override
    public String toString() {
        return "ItemDetails{" +
                "itemCode='" + itemCode + '\'' +
                ", itemQtyOnHand=" + itemQtyOnHand +
                ", unitPrice=" + unitPrice +
                ", discountPrice=" + discountPrice +
                '}';
    }
}
