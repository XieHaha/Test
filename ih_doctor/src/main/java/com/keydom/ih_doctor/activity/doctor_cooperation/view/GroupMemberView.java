package com.keydom.ih_doctor.activity.doctor_cooperation.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_doctor.bean.DeptDoctorBean;

import java.util.List;
import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface GroupMemberView extends BaseView {

    /**
     * 获取请求参数
     *
     * @return 请求参数
     */
    Map<String, Object> getQueryMap();

    /**
     * 获取所有成员成功
     *
     * @param list 团队所有成员数据
     */
    void getGroupMemberSuccess(List<DeptDoctorBean> list);

    /**
     * 获取团队成员失败
     *
     * @param errMsg 失败信息
     */
    void getGroupMemberFailed(String errMsg);

    /**
     * 获取团队ID
     *
     * @return 团队ID
     */
    long getGroupId();

    /**
     * 获取已经存在的团队成员列表
     *
     * @return 已经存在的团队成员数据
     */
    List<DeptDoctorBean> getSelectedList();


}