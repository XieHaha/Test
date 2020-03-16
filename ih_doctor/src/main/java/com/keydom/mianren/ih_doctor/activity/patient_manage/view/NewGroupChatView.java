package com.keydom.mianren.ih_doctor.activity.patient_manage.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_doctor.bean.DeptDoctorBean;
import com.keydom.mianren.ih_doctor.bean.GroupResBean;
import com.keydom.mianren.ih_doctor.bean.ImPatientInfo;

import java.util.List;
import java.util.Map;

public interface NewGroupChatView extends BaseView {


    /**
     * 获取团队名称
     *
     * @return 团队名称
     */
    String getGroupName();

    /**
     * 获取选择的医生列表
     *
     * @return 医生对象列表
     */
    List<DeptDoctorBean> getSelectDoctors();

    /**
     * 上传头像成功
     *
     * @param path 头像地址
     */
    void uploadSuccess(String path);

    /**
     * 上传头像失败
     *
     * @param errMsg 失败提示信息
     */
    void uploadFailed(String errMsg);

    /**
     * 创建团队成功
     *
     * @param msg 成功信息
     */
    void createGroupSuccess(String msg);

    /**
     * 创建团队失败
     *
     * @param errMsg 失败提示信息
     */
    void createGroupFailed(String errMsg);

    /**
     * 获取选择的患者列表
     *
     * @return 患者对象列表
     */
    List<ImPatientInfo> getSelectPatient();

    /**
     * 获取创建团队请求数据
     *
     * @return 创建团队请求数据
     */
    Map<String, Object> getCreateGroupMap();


    /**
     * 获取群资料
     *
     * @return
     */
    Map<String, Object> getQueryGroupMap();

    /**
     * 判断是否可以提交
     *
     * @return true 可以提交，false 不能提交
     */
    boolean submitCheck();

    void getGroupInfoSuccess(GroupResBean groupResBean);

    int getType();

    boolean isUpdate();


}
