package com.keydom.ih_common.im.model.custom;

import com.alibaba.fastjson.JSONObject;

/**
 * 处方消息
 *
 * @author THINKPAD B
 */
public class ConsultationResultAttachment extends BaseCustomAttachment {

    /**
     * 处方单标题
     */
    private String title;
    /**
     * 处方单内容
     */
    private String content;
    /**
     * 处方单金额
     */
    private String amount;

    /**
     * 配送费
     */
    private String deliveryAmount;
    /**
     * 支付状态
     */
    private int payStatus;

    /**
     * 处方单ID
     */
    private String id;

    /**
     * 处方单号
     */
    private String orderNum;

    public ConsultationResultAttachment() {
        super(ICustomAttachmentType.CONSULTATION_RESULT);
    }


    @Override
    protected void paresData(JSONObject data) {
        title = data.getString("title");
        content = data.getString("content");
        amount = data.getString("amount");
        payStatus = data.getInteger("payStatus");
        id = data.getString("id");
        deliveryAmount = data.getString("deliveryAmount");
        orderNum = data.getString("orderNum");
    }

    @Override
    protected JSONObject packData() {
        JSONObject data = new JSONObject();
        data.put("title", title);
        data.put("content", content);
        data.put("amount", amount);
        data.put("payStatus", payStatus);
        data.put("id", id);
        data.put("deliveryAmount", deliveryAmount);
        data.put("orderNum", orderNum);
        return data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDeliveryAmount() {
        return deliveryAmount;
    }

    public void setDeliveryAmount(String deliveryAmount) {
        this.deliveryAmount = deliveryAmount;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    @Override
    public String toString() {
        return "ConsultationResultAttachment{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", amount='" + amount + '\'' +
                ", deliveryAmount='" + deliveryAmount + '\'' +
                ", payStatus=" + payStatus +
                ", id='" + id + '\'' +
                ", orderNum='" + orderNum + '\'' +
                ", type=" + type +
                '}';
    }
}
