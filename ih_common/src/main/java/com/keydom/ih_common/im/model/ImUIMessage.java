package com.keydom.ih_common.im.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.ih_common.im.model.custom.ConsultationResultAttachment;
import com.keydom.ih_common.im.model.custom.DisposalAdviceAttachment;
import com.keydom.ih_common.im.model.custom.EndInquiryAttachment;
import com.keydom.ih_common.im.model.custom.ExaminationAttachment;
import com.keydom.ih_common.im.model.custom.GetDrugsAttachment;
import com.keydom.ih_common.im.model.custom.ICustomAttachmentType;
import com.keydom.ih_common.im.model.custom.InquiryAttachment;
import com.keydom.ih_common.im.model.custom.InspectionAttachment;
import com.keydom.ih_common.im.model.custom.ReceiveDrugsAttachment;
import com.keydom.ih_common.im.model.custom.ReferralApplyAttachment;
import com.keydom.ih_common.im.model.custom.ReferralDoctorAttachment;
import com.keydom.ih_common.im.model.custom.UserFollowUpAttachment;
import com.keydom.ih_common.im.model.def.SentStatus;
import com.netease.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import org.jetbrains.annotations.NotNull;

/**
 * @author THINKPAD B
 */
public class ImUIMessage implements MultiItemEntity {

    @NotNull
    private IMMessage mMessage;

    private @SentStatus
    int sentStatus;


    @SentStatus
    public int getSentStatus() {
        return sentStatus;
    }

    public void setSentStatus(@SentStatus int sentStatus) {
        this.sentStatus = sentStatus;
    }

    public ImUIMessage(@NotNull IMMessage message, int sentStatus) {
        mMessage = message;
        this.sentStatus = sentStatus;
    }

    @NotNull
    public IMMessage getMessage() {
        return mMessage;
    }

    public void setMessage(@NotNull IMMessage message) {
        mMessage = message;
    }

    @Override
    public int getItemType() {
        if (mMessage.getMsgType().getValue() != MsgTypeEnum.custom.getValue()) {
            return mMessage.getMsgType().getValue();
        } else {
            if (mMessage.getAttachment() instanceof InquiryAttachment) {
                return ICustomAttachmentType.INQUIRY;
            } else if (mMessage.getAttachment() instanceof ConsultationResultAttachment) {
                return ICustomAttachmentType.CONSULTATION_RESULT;
            } else if (mMessage.getAttachment() instanceof InspectionAttachment) {
                return ICustomAttachmentType.INSPECTION;
            } else if (mMessage.getAttachment() instanceof ExaminationAttachment) {
                return ICustomAttachmentType.EXAMINATION;
            } else if (mMessage.getAttachment() instanceof ReferralApplyAttachment) {
                return ICustomAttachmentType.REFERRAL_APPLY;
            } else if (mMessage.getAttachment() instanceof ReferralDoctorAttachment) {
                return ICustomAttachmentType.REFERRAL_DOCTOR;
            } else if (mMessage.getAttachment() instanceof EndInquiryAttachment) {
                return ICustomAttachmentType.END_INQUIRY;
            } else if (mMessage.getAttachment() instanceof DisposalAdviceAttachment) {
                return ICustomAttachmentType.DISPOSAL_ADVICE;
            } else if(mMessage.getAttachment() instanceof GetDrugsAttachment){
                return ICustomAttachmentType.GET_DRUGS;
            }else if(mMessage.getAttachment() instanceof ReceiveDrugsAttachment){
                return ICustomAttachmentType.RECEIVE_DRUGS;
            }else if(mMessage.getAttachment() instanceof UserFollowUpAttachment){
                return ICustomAttachmentType.USER_FOLLOW_UP;
            } else {
                return MsgTypeEnum.undef.getValue();
            }
        }
    }

    /**
     * 解析网易云信消息
     *
     * @return
     */
    public static ImUIMessage obtain(IMMessage message, MsgDirectionEnum direct) {
        return new ImUIMessage(message, direct == MsgDirectionEnum.Out ? ImMessageConstant.SENDING : ImMessageConstant.FINISH);
    }

    /**
     * 解析网易云信消息
     *
     * @return
     */
    public static ImUIMessage obtain(IMMessage message) {
        return new ImUIMessage(message, ImMessageConstant.FINISH);
    }
}
