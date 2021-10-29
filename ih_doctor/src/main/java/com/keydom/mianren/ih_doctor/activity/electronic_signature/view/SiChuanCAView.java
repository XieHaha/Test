package com.keydom.mianren.ih_doctor.activity.electronic_signature.view;

import com.keydom.ih_common.base.BaseView;

public interface SiChuanCAView extends BaseView {
    boolean isSign();

    boolean isSelect();

    void requestUserSignSuccess(String result);
    void requestUserSignFailed(String result);

    void getSignSuccess(String sign);
    void getSignFailed(String msg);
    /**
     * 勾选
     */
    void setSelect();
}
