package com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.view;

import android.view.View;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.AmniocentesisReserveBean;

import java.util.Date;

/**
 * @date 20/3/9 16:09
 * @des 羊水穿刺预约申请
 */
public interface AmniocentesisApplyView extends BaseView {
    /**
     * 日期选择回调
     */
    void onDateSelect(View view, Date date);

    /**
     * 获取验证码成功
     */
    void getMsgCodeSuccess();

    /**
     * 获取失败
     */
    void getMsgCodeFailed(String errMsg);

    String getPhone();

    AmniocentesisReserveBean getReserveBean();
}
