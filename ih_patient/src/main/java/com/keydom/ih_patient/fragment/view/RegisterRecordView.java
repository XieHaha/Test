package com.keydom.ih_patient.fragment.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.bean.RegistrationRecordInfo;

import java.util.List;

/**
 * 挂号记录view
 */
public interface RegisterRecordView extends BaseView {

    /**
     * 获取记录成功
     */
    void getRegistrarionRecordListSuccess(List<RegistrationRecordInfo> dataList);

    /**
     * 获取记录失败
     */
    void getRegistrarionRecordListFailed(String errMsg);
}
