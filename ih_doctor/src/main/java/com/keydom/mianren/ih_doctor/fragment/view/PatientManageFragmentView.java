package com.keydom.mianren.ih_doctor.fragment.view;

import android.widget.RelativeLayout;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_doctor.bean.PermissionBean;

/**
 * @Name：com.keydom.ih_doctor.fragment.view
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/16 下午2:25
 * 修改人：xusong
 * 修改时间：18/11/16 下午2:25
 */
public interface PatientManageFragmentView extends BaseView {
    RelativeLayout getTitleLayout();

    void getPermissionSuccess(PermissionBean permissionBean);
    int  getBuildingGroupState();
    int getEmpowerState();
}
