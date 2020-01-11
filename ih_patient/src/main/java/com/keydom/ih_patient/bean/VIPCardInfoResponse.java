package com.keydom.ih_patient.bean;

import java.io.Serializable;
import java.util.List;

public class VIPCardInfoResponse implements Serializable {


    private static final long serialVersionUID = -7717821938681563993L;
    /**
     * id : b122175b7ec041b5869e6ed89c6eaaf3
     * cardName : 超级无敌会员卡
     * price : 1000
     * timeLimit : 1
     * picture : group1/M00/00/36/rBAA0l4YQNiAQ1psAAAWx3Ad_N0135.jpg
     * description : 超级无敌爆炸爽cdc
     * privilege : [{"imgFile":"group1/M00/00/36/rBAA0l4YQOSATbZRAALVpJsY2M4701.png","title":"吃大餐"}]
     * infoList : [{"imgFile":"group1/M00/00/36/rBAA0l4YQOSATbZRAALVpJsY2M4701.png","title":"吃大餐"}]
     * note : 伍齐鸣不能用！！1ccc
     * createTime : 2020-01-10 17:31:37
     * updateTime : 2020-01-10 17:31:37
     * isDel : 0
     */

    private String id;
    private String cardName;
    private double price;
    private int timeLimit;
    private String picture;
    private String description;
    private String privilege;
    private String note;
    private String createTime;
    private String updateTime;
    private int isDel;
    private List<VIPCardInfoListItem> infoList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public List<VIPCardInfoListItem> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<VIPCardInfoListItem> infoList) {
        this.infoList = infoList;
    }
}
