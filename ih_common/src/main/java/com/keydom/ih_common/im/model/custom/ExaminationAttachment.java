package com.keydom.ih_common.im.model.custom;

import com.alibaba.fastjson.JSONObject;

/**
 * 检查单消息
 *
 * @author THINKPAD B
 */
public class ExaminationAttachment extends BaseCustomAttachment {
    /**
     * 检查单内容
     */
    private String examinationContent;
    /**
     * 检查单id
     */
    private String id;

    /**
     * 检查单金额
     */
    private String amount;
    /**
     * 支付状态
     */
    private int payStatus;

    public ExaminationAttachment() {
        super(ICustomAttachmentType.EXAMINATION);
    }

    @Override
    protected void paresData(JSONObject data) {
        examinationContent = data.getString("examinationContent");
        id = String.valueOf(data.getLong("id"));
        amount = data.getString("amount");
        payStatus = data.getInteger("payStatus");
    }

    @Override
    protected JSONObject packData() {
        JSONObject data = new JSONObject();
        data.put("id", id);
        data.put("amount", amount);
        data.put("payStatus", payStatus);
        data.put("examinationContent", examinationContent);
        return data;
    }

    public String getExaminationContent() {
        return examinationContent;
    }

    public void setExaminationContent(String examinationContent) {
        this.examinationContent = examinationContent;
    }

    public Long getId() {
        return Long.valueOf(id);
    }

    public void setId(Long id) {
        this.id = String.valueOf(id);
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    @Override
    public String toString() {
        return "ExaminationAttachment{" +
                "examinationContent='" + examinationContent + '\'' +
                ", id='" + id + '\'' +
                ", type=" + type +
                '}';
    }
}
