package com.keydom.ih_patient.bean;

import java.io.Serializable;

public class ChargeMemberPriceItemBean implements Serializable {
    private static final long serialVersionUID = 8566241631708095780L;

    public ChargeMemberPriceItemBean(int price, boolean isSelected, boolean isEditText) {
        this.price = price;
        this.isSelected = isSelected;
        this.isEditText = isEditText;
    }

    private int price;
    private boolean isSelected;
    private boolean isEditText;

    public boolean isEditText() {
        return isEditText;
    }

    public void setEditText(boolean editText) {
        isEditText = editText;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChargeMemberPriceItemBean that = (ChargeMemberPriceItemBean) o;
        return price == that.price &&
                isSelected == that.isSelected &&
                isEditText == that.isEditText;
    }

    @Override
    public int hashCode() {
        return String.valueOf(price).hashCode();
    }
}
