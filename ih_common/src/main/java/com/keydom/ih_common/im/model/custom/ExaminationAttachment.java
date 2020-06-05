package com.keydom.ih_common.im.model.custom;

import com.alibaba.fastjson.JSONObject;
import com.keydom.ih_common.bean.InspectionBean;

/**
 * 检查单消息
 *
 * @author THINKPAD B
 */
public class ExaminationAttachment extends BaseCustomAttachment {
    private static final long serialVersionUID = 5290624928074902667L;
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
    private String doctorName;

    private int applicationType;

    public ExaminationAttachment() {
        super(ICustomAttachmentType.EXAMINATION);
    }

    @Override
    protected void paresData(JSONObject data) {
        id = data.getString("id");
        applicationType = data.getInteger("applicationType");
        insCheckApplication =
                data.getJSONObject("insCheckApplication").toJavaObject(InspectionBean.class);
        insCheckOrderId = data.getString("insCheckOrderId");
        updateTime = data.getString("updateTime");
        doctorName = data.getString("doctorName");
    }

    @Override
    protected JSONObject packData() {
        JSONObject data = new JSONObject();
        data.put("id", id);
        data.put("doctorName", doctorName);
        data.put("applicationType", applicationType);
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

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public int getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(int applicationType) {
        this.applicationType = applicationType;
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
