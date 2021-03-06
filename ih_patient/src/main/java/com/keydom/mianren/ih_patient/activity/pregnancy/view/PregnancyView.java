package com.keydom.mianren.ih_patient.activity.pregnancy.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.PregnancyDetailBean;
import com.keydom.mianren.ih_patient.bean.PregnancyRecordItem;
import com.keydom.mianren.ih_patient.constant.TypeEnum;

import java.util.List;

public interface PregnancyView extends BaseView {

    void listPersonInspectionRecordSuccess(List<PregnancyRecordItem> list, TypeEnum typeEnum);

    void listPersonInspectionRecordFailed(String msg);

    void getPregnancyDetailSuccess(PregnancyDetailBean data);

    void getPregnancyDetailFailed(int code, String msg);

    PregnancyDetailBean getPregnancyDetail();

    String getRecordID();

    PregnancyRecordItem getPregnancyRecordItem();
}
