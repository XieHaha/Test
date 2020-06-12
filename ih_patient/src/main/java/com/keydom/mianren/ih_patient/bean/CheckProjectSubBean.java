package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;

/**
 * @date 20/6/12 14:08
 * @des
 */
public class CheckProjectSubBean implements Serializable {
    private static final long serialVersionUID = 2305337889478629142L;

    private String  itemCode;
    private String  itemName;
    private String  quantity;
    private String  price;
    private String  amount;
    private boolean select;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
