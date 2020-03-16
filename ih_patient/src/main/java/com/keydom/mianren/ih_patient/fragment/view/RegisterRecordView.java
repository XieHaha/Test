package com.keydom.mianren.ih_patient.fragment.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.RegistrationRecordInfo;
import com.keydom.mianren.ih_patient.constant.TypeEnum;

import java.util.List;

/**
 * 挂号记录view
 */
public interface RegisterRecordView extends BaseView {

    /**
     * 获取记录成功
     */
    void getRegistrarionRecordListSuccess(List<RegistrationRecordInfo> dataList, TypeEnum typeEnum);

    /**
     * 获取记录失败
     */
    void getRegistrarionRecordListFailed(String errMsg);
}
