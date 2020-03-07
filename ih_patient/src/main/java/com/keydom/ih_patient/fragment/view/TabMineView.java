package com.keydom.ih_patient.fragment.view;

import android.widget.LinearLayout;

import com.keydom.ih_common.base.BaseView;

/**
 * 个人中心view
 */
public interface TabMineView extends BaseView {
    /**
     * 获取标题layout
     */
    LinearLayout getTitleLayout();

    /**
     * 展示医院选择弹框
     */
    void showHospitalPopupWindow();

    /**
     * 获取未读消息成功
     */
    void getUnreadMessagaCountSuccess(Integer count);

    /**
     * 获取未读消息失败
     */
    void getUnreadMessageCountFailed(String errMsg);

    /**
     * 获取头像的URL
     * @return
     */
    String getImgStr();

    long getCurUserId();
}
