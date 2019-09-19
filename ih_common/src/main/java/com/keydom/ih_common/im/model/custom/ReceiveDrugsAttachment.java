package com.keydom.ih_common.im.model.custom;

import com.alibaba.fastjson.JSONObject;

public class ReceiveDrugsAttachment extends BaseCustomAttachment {

    /**
     * 取药Id
     */
    private String id;
    /**
     * 取药内容
     */
    private String content;

    //总费用
    private double amount;

    //支付状态
    private int payStatus;

    //标题
    private String title;

    //订单号
    private String orderNum;

    //配送费用
    private double deliveryAmount;

    public ReceiveDrugsAttachment() {
        super(ICustomAttachmentType.RECEIVE_DRUGS);
    }

    @Override
    protected void paresData(JSONObject data) {
        id = data.getString("id");
        content = data.getString("content");
        amount = data.getDouble("amount");
        payStatus = data.getInteger("payStatus");
        title = data.getString("title");
        orderNum = data.getString("orderNum");
        deliveryAmount = data.getDouble("deliveryAmount");
    }

    @Override
    protected JSONObject packData() {
        JSONObject data = new JSONObject();
        data.put("id", id);
        data.put("content", content);
        data.put("amount", amount);
        data.put("payStatus", payStatus);
        data.put("title", title);
        data.put("orderNum", orderNum);
        data.put("deliveryAmount", deliveryAmount);
        return data;
    }

    @Override
    public String toString() {
        return "GetDrugsAttachment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", amount=" + amount +
                ", payStatus=" + payStatus +
                ", title='" + title + '\'' +
                ", orderNum='" + orderNum + '\'' +
                ", deliveryAmount=" + deliveryAmount +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public double getDeliveryAmount() {
        return deliveryAmount;
    }

    public void setDeliveryAmount(double deliveryAmount) {
        this.deliveryAmount = deliveryAmount;
    }
}
