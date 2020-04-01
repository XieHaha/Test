package com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.AmniocentesisBean;
import com.keydom.mianren.ih_patient.constant.TypeEnum;

import java.util.List;

/**
 * @date 20/3/11 14:25
 * @des 羊水穿刺预约查询及取消
 */
public interface AmniocentesisRecordView extends BaseView {
    void onAmniocentesisRecordSuccess(TypeEnum typeEnum, List<AmniocentesisBean> records);

    void onAmniocentesisRecordFailed();

    void onAmniocentesisCancelSuccess(int position);
}
