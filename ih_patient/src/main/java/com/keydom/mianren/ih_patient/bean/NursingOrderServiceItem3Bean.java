package com.keydom.mianren.ih_patient.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.mianren.ih_patient.adapter.NursingOrderServiceAdapter;

/**
 * created date: 2018/12/20 on 15:08
 * des:器械头部
 */
public class NursingOrderServiceItem3Bean implements MultiItemEntity {

    private int frequency;
    private double total;
    /**
     * equipmentName : 针筒
     * quantity : 1.0
     * unit : he
     * price : 10.0
     * description : 10ml/g
     */

    private String equipmentName;
    private String quantity;
    private String unit;
    private String price;
    private String description;
    private boolean isTop;
    private boolean isBottom;



    public boolean isTop() {
        return isTop;
    }

    public void setTop(boolean top) {
        isTop = top;
    }

    public boolean isBottom() {
        return isBottom;
    }

    public void setBottom(boolean bottom) {
        isBottom = bottom;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    @Override
    public int getItemType() {
        return NursingOrderServiceAdapter.TYPE_LEVEL_3;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
