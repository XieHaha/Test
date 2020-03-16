package com.keydom.mianren.ih_patient.activity.my_message.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.MessageBean;
import com.keydom.mianren.ih_patient.bean.NoticeBean;

import java.util.List;

/**
 * 我的消息view
 */
public interface MyMessageView extends BaseView {

    /**
     * 获取我的消息列表
     */
    void getMessageListSuccess(List<MessageBean> messageList);

    /**
     * 获取失败
     */
    void getMessageListFailed(String errMsg);


    void getNoticeSuccess(NoticeBean noticeBean);

    void getNoticeFailed(String errMsg);
}
