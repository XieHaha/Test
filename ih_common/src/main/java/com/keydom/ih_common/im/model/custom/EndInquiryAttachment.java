package com.keydom.ih_common.im.model.custom;

import com.alibaba.fastjson.JSONObject;
import com.keydom.ih_common.im.model.def.EndType;

/**
 * @author THINKPAD B
 */
public class EndInquiryAttachment extends BaseCustomAttachment {
    /**
     * 医生申请结束问诊
     */
    public static final int DOCTOR_APPLY_END = 1;
    /**
     * 患者同意医生申请结束问诊
     */
    public static final int PATIENT_AGREE = 2;
    /**
     * 患者拒绝医生申请结束问诊
     */
    public static final int PATIENT_REFUSE = 3;
    /**
     * 患者主动申请结束问诊
     */
    public static final int PATIENT_END = 4;

    /**
     * 需要结束订单ID
     */
    private Long id;

    /**
     * 发起人ID
     */
    private String sponsorId;
    /**
     * 接收者ID
     */
    private String receiverId;

    /**
     * 发起结束问诊消息来源type
     */
    @EndType
    private int endType = 0;

    public EndInquiryAttachment() {
        super(ICustomAttachmentType.END_INQUIRY);
    }

    @Override
    protected void paresData(JSONObject data) {
        id = data.getLong("id");
        sponsorId = data.getString("sponsorId");
        receiverId = data.getString("receiverId");
        endType = data.getInteger("endType");
    }

    @Override
    protected JSONObject packData() {
        JSONObject data = new JSONObject();
        data.put("id", id);
        data.put("sponsorId", sponsorId);
        data.put("receiverId", receiverId);
        data.put("endType", endType);
        return data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(String sponsorId) {
        this.sponsorId = sponsorId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    @EndType
    public int getEndType() {
        return endType;
    }

    public void setEndType(@EndType int endType) {
        this.endType = endType;
    }


    @Override
    public String toString() {
        return "EndInquiryAttachment{" +
                "id=" + id +
                ", sponsorId=" + sponsorId +
                ", receiverId=" + receiverId +
                ", endType=" + endType +
                ", type=" + type +
                '}';
    }
}
