package com.keydom.mianren.ih_patient.activity.diagnose_user_manager.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.HistoryListBean;
import com.keydom.mianren.ih_patient.bean.ManagerUserBean;

/**
 * created date: 2018/12/15 on 10:48
 * des:既往病史view
 */
public interface AnamnesisView extends BaseView{
    /**
     * 获取既往病史列表回调
     */
    void getHistorySuccess(HistoryListBean data);


    /**
     * 保存就诊人
     */
    void addOrEditSuccess(ManagerUserBean manager);

    /**
     * 获取状态
     */
    int getStatus();

    /**
     * 获取就诊人
     */
    ManagerUserBean getManager();


}
