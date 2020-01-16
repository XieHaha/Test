package com.keydom.ih_patient.activity.pregnancy.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.bean.PregnancyRecordItem;
import com.keydom.ih_patient.constant.TypeEnum;

import java.util.List;

public interface PregnancyView extends BaseView {

    void listPersonInspectionRecordSuccess(List<PregnancyRecordItem> list, TypeEnum typeEnum);
}
