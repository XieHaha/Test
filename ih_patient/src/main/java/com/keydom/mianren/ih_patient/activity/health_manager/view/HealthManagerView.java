package com.keydom.mianren.ih_patient.activity.health_manager.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.HealthManagerMainBean;

/**
 * @author 顿顿
 * @date 20/3/4 10:55
 * @des 健康管理首页
 */
public interface HealthManagerView extends BaseView {

    HealthManagerMainBean getMainBean();
    void requestHealthManagerSuccess(HealthManagerMainBean bean);
    void openChronicDiseaseManageSuccess(int type,String data);
}
