package com.keydom.ih_common.im.model.custom;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachmentParser;


public class CustomAttachParser implements MsgAttachmentParser {
    private static final String KEY_TYPE = "type";
    private static final String KEY_DATA = "data";

    private int key;
    private JSONObject data;

    @Override
    public MsgAttachment parse(String attach) {
        BaseCustomAttachment attachment = null;
        JSONObject object = JSON.parseObject(attach);
        if (object.containsKey(KEY_TYPE)) {
            key = object.getInteger(KEY_TYPE);
        }
        if (object.containsKey(KEY_DATA)) {
            data = object.getJSONObject(KEY_DATA);
        }
        switch (key) {
            case ICustomAttachmentType.INQUIRY:
                attachment = new InquiryAttachment();
                break;
            case ICustomAttachmentType.CONSULTATION_RESULT:
                attachment = new ConsultationResultAttachment();
                break;
            case ICustomAttachmentType.INSPECTION:
                attachment = new InspectionAttachment();
                break;
            case ICustomAttachmentType.EXAMINATION:
                attachment = new ExaminationAttachment();
                break;
            case ICustomAttachmentType.REFERRAL_APPLY:
                attachment = new ReferralApplyAttachment();
                break;
            case ICustomAttachmentType.REFERRAL_DOCTOR:
                attachment = new ReferralDoctorAttachment();
                break;
            case ICustomAttachmentType.END_INQUIRY:
                attachment = new EndInquiryAttachment();
                break;
            case ICustomAttachmentType.DISPOSAL_ADVICE:
                attachment = new DisposalAdviceAttachment();
                break;
            default:
        }
        if (attachment != null && data != null) {
            attachment.fromJson(data);
        }
        return attachment;
    }

    public static String packData(int type, JSONObject data) {
        JSONObject object = new JSONObject();
        object.put(KEY_TYPE, type);
        if (data != null) {
            object.put(KEY_DATA, data);
        }
        return object.toJSONString();
    }
}
