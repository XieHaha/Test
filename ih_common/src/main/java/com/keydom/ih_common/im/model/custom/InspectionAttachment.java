package com.keydom.ih_common.im.model.custom;

import com.alibaba.fastjson.JSONObject;

/**
 * 检验单消息
 *
 * @author THINKPAD B
 */
public class InspectionAttachment extends BaseCustomAttachment {

    /**
     * 检验项目
     */
    private String inspectionContent;
    /**
     * 检验单ID
     */
    private long id;

    /**
     * 检验单金额
     */
    private String amount;
    /**
     * 支付状态
     */
    private int payStatus;

    public InspectionAttachment() {
        super(ICustomAttachmentType.INSPECTION);
    }

    @Override
    protected void paresData(JSONObject data) {
        id = data.getLongValue("id");
        inspectionContent = data.getString("inspectionContent");
        amount = data.getString("amount");
        payStatus = data.getInteger("payStatus");
    }

    @Override
    protected JSONObject packData() {
        JSONObject data = new JSONObject();
        data.put("id", id);
        data.put("inspectionContent", inspectionContent);
        data.put("payStatus", payStatus);
        data.put("amount", amount);
        return data;
    }

    public String getInspectionContent() {
        return inspectionContent;
    }

    public void setInspectionContent(String inspectionContent) {
        this.inspectionContent = inspectionContent;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        return "InspectionAttachment{" +
                "inspectionContent='" + inspectionContent + '\'' +
                ", id='" + id + '\'' +
                ", type=" + type +
                '}';
    }
}
