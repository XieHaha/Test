package com.keydom.ih_common.im.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.ih_common.R;
import com.keydom.ih_common.im.model.ImUIMessage;
import com.keydom.ih_common.im.model.custom.ConsultationResultAttachment;
import com.keydom.ih_common.im.model.custom.ExaminationAttachment;
import com.keydom.ih_common.im.model.custom.ICustomAttachmentType;
import com.keydom.ih_common.im.model.custom.InspectionAttachment;
import com.keydom.ih_common.im.widget.ImProviderContainerView;
import com.netease.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;

import java.util.List;

public class ImMessageAdapter extends BaseMultiItemQuickAdapter<ImUIMessage, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ImMessageAdapter(List<ImUIMessage> data) {
        super(data);
        addItemType(MsgTypeEnum.undef.getValue(), R.layout.im_item_message_unknow);
        addItemType(ICustomAttachmentType.END_INQUIRY, R.layout.im_item_message_unknow);
        addItemType(MsgTypeEnum.text.getValue(), R.layout.im_item_message_text);
        addItemType(MsgTypeEnum.image.getValue(), R.layout.im_item_message_image);
        addItemType(MsgTypeEnum.audio.getValue(), R.layout.im_item_message_voice);
        addItemType(MsgTypeEnum.video.getValue(), R.layout.im_item_message_video);
        addItemType(MsgTypeEnum.tip.getValue(), R.layout.im_item_message_tips);
        addItemType(MsgTypeEnum.avchat.getValue(), R.layout.im_item_message_avchat);
        addItemType(MsgTypeEnum.notification.getValue(), R.layout.im_item_message_tips);
        addItemType(ICustomAttachmentType.INQUIRY, R.layout.im_item_message_inquiry);
        addItemType(ICustomAttachmentType.TRIAGE_ORDER, R.layout.im_item_message_triage_order);
        addItemType(ICustomAttachmentType.CONSULTATION_RESULT, R.layout.im_item_message_consultation_result);
        addItemType(ICustomAttachmentType.INSPECTION, R.layout.im_item_message_inspection);
        addItemType(ICustomAttachmentType.EXAMINATION, R.layout.im_item_message_examination);
        addItemType(ICustomAttachmentType.REFERRAL_APPLY, R.layout.im_item_message_referral_apply);
        addItemType(ICustomAttachmentType.REFERRAL_DOCTOR, R.layout.im_item_message_referral_doctor);
        addItemType(ICustomAttachmentType.DISPOSAL_ADVICE, R.layout.im_item_message_disposal_advice);
        addItemType(ICustomAttachmentType.GET_DRUGS, R.layout.im_item_message_get_drugs);
        addItemType(ICustomAttachmentType.RECEIVE_DRUGS, R.layout.im_item_message_receive_drugs);
        addItemType(ICustomAttachmentType.USER_FOLLOW_UP, R.layout.im_item_message_user_follow_up);
    }

    @Override
    protected void convert(BaseViewHolder helper, ImUIMessage item) {
        initData(helper, item);
        initListener(helper, item);
    }

    private void initData(BaseViewHolder helper, ImUIMessage item) {
        ImProviderContainerView containerView = (ImProviderContainerView) helper.itemView;
        if (item.getItemType() == MsgTypeEnum.undef.getValue() || item.getItemType() == MsgTypeEnum.tip.getValue()) {
            containerView.containerViewCenter();
        } else {
            if (item.getMessage().getDirect() == MsgDirectionEnum.In) {
                containerView.containerViewLeft();
            } else {
                containerView.containerViewRight();
            }
        }
        containerView.setDateTime(getItem(helper.getAdapterPosition() - 1), item);
        containerView.setAvatar(item.getMessage().getFromAccount());
        containerView.setLeftName(item.getMessage());
        containerView.bindView(helper.getView(R.id.im_addition_layout), item);
    }

    private void initListener(BaseViewHolder helper, ImUIMessage item) {
        helper.addOnClickListener(R.id.im_message_avatar_left)
                .addOnClickListener(R.id.im_message_avatar_right)
                .addOnClickListener(R.id.im_message_content);
        if (item.getMessage().getMsgType() == MsgTypeEnum.custom) {
            if (item.getMessage().getAttachment() instanceof ConsultationResultAttachment
                    || item.getMessage().getAttachment() instanceof ExaminationAttachment
                    || item.getMessage().getAttachment() instanceof InspectionAttachment) {
                helper.addOnClickListener(R.id.payment_amount)
                        .addOnClickListener(R.id.result_pay)
                        .addOnClickListener(R.id.image)
                        .addOnClickListener(R.id.bottom_title)
                        .addOnClickListener(R.id.prescription);//处方
            }
        }
    }
}
