package com.keydom.mianren.ih_doctor.activity.doctor_cooperation.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_doctor.bean.GroupInfoBean;
import com.keydom.mianren.ih_doctor.bean.GroupInfoRes;

import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface DoctorCooperationView extends BaseView {

    /**
     * 获取团队成功
     *
     * @param res 团队数据
     */
    void getGroupSuccess(GroupInfoRes res);

    /**
     * 获取团队失败
     *
     * @param msg 失败信息
     */
    void getGroupFailed(String msg);

    /**
     * 获取团队列表
     *
     * @return 团队列表
     */
    List<GroupInfoBean> getGroup();

    /**
     * 获取当前选择的团队
     *
     * @return 当前选择的团队
     */
    GroupInfoBean getCurrentGroup();

    /**
     * 是否可以创建团队
     *
     * @return
     */
    int getIsCreate();


    //团队交流红点
    void showOrHideGroupExchangeRedPoint(boolean isShow);
}