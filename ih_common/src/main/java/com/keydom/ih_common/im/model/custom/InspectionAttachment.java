package com.keydom.ih_common.im.model.custom;

import com.alibaba.fastjson.JSONObject;
import com.keydom.ih_common.bean.InspectionBean;

/**
 * 检验单消息
 *
 * @author THINKPAD B
 */
public class InspectionAttachment extends BaseCustomAttachment {

    /**
     * 检验详情
     */
    private InspectionBean insCheckApplication;
    /**
     * 检验单ID
     */
    private String id;

    /**
     *
     */
    private String insCheckOrderId;
    /**
     * 时间
     */
    private String updateTime;

    public InspectionAttachment() {
        super(ICustomAttachmentType.INSPECTION);
    }

    @Override
    protected void paresData(JSONObject data) {
        id = data.getString("id");
        insCheckApplication = data.getJSONObject("insCheckApplication").toJavaObject(InspectionBean.class);
        insCheckOrderId = data.getString("insCheckOrderId");
        updateTime = data.getString("updateTime");
    }

    @Override
    protected JSONObject packData() {
        JSONObject data = new JSONObject();
        data.put("id", id);
        data.put("insCheckApplication", insCheckApplication);
        data.put("updateTime", updateTime);
        data.put("insCheckOrderId", insCheckOrderId);
        return data;
    }

    public InspectionBean getInsCheckApplication() {
        return insCheckApplication;
    }

    public void setInsCheckApplication(InspectionBean insCheckApplication) {
        this.insCheckApplication = insCheckApplication;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInsCheckOrderId() {
        return insCheckOrderId;
    }

    public void setInsCheckOrderId(String insCheckOrderId) {
        this.insCheckOrderId = insCheckOrderId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "InspectionAttachment{" +
                "insCheckApplication='" + insCheckApplication + '\'' +
                ", id='" + id + '\'' +
                ", type=" + type +
                '}';
    }
}
