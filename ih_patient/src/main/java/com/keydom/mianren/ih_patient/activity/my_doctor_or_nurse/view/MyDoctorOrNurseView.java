package com.keydom.mianren.ih_patient.activity.my_doctor_or_nurse.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.DoctorOrNurseBean;
import com.keydom.mianren.ih_patient.constant.TypeEnum;

import java.util.List;

/**
 * created date: 2019/1/2 on 14:25
 * des:我的医生view
 */
public interface MyDoctorOrNurseView extends BaseView {
    /**
     * 获取我的关注列表
     */
    void myFollowsCallBack(List<DoctorOrNurseBean> list , TypeEnum typeEnum);

    /**
     * 返回我的关注
     */
    List<DoctorOrNurseBean> returnFollows();

    /**
     * 获取聊天列表
     */
    void mateFollows(List<DoctorOrNurseBean> list);
}
