package com.keydom.mianren.ih_doctor.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Name：com.keydom.ih_doctor.bean
 * @Description：普通护士－护理服务－器材
 * @Author：song
 * @Date：18/12/26 下午2:59
 * 修改人：xusong
 * 修改时间：18/12/26 下午2:59
 */
public class DetailEquipment implements Serializable {
    private static final long serialVersionUID = 1L;
    private String equipmentName;
    private double quantity;
    private String unitName;
    private BigDecimal price;
    private String description;

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
