package com.keydom.mianren.ih_patient.activity.hospital_payment.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.HospitalCheckBean;
import com.keydom.mianren.ih_patient.bean.HospitalCountBean;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;

import java.util.Date;
import java.util.List;

/**
 * @author 顿顿
 * @date 20/2/26 10:47
 * @des 住院清单
 */
public interface HospitalCheckListView extends BaseView {

    String getEndDateString();

    String getStartDateString();

    MedicalCardInfo getMedicalCardInfo();

    /**
     * 数据源
     */
    void fillHospitalPaymentData(List<HospitalCheckBean> data);


    String getInHospitalNo();

    Date getCurDate();

    void setSelectDate(Date date);

    void setHospitalCountBean(HospitalCountBean bean);
}
