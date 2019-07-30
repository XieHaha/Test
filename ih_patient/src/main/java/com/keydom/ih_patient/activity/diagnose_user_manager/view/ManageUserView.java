package com.keydom.ih_patient.activity.diagnose_user_manager.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.bean.ManagerUserBean;

import java.util.List;

/**
 * 就诊人管理view
 */
public interface ManageUserView extends BaseView {

    /**
     * 获取就诊人列表
     */
    void getMangerUserList(List<ManagerUserBean> data);

    /**
     * 删除成功回调
     */
    void deleteSuccess(int pos);
}
