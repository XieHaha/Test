package com.keydom.ih_doctor.fragment.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_doctor.fragment.view.PatientGroupFragmentView;
import com.keydom.ih_doctor.m_interface.SingleClick;

/**
 * @Name：com.keydom.ih_doctor.fragment.controller
 * @Description：患者管理－群组页面控制器
 * @Author：song
 * @Date：18/11/14 上午10:56
 * 修改人：xusong
 * 修改时间：18/11/14 上午10:56
 */
public class PatientGroupFragmentController extends ControllerImpl<PatientGroupFragmentView> implements View.OnClickListener {
    @SingleClick(1000)
    @Override
    public void onClick(View v) {

    }


    /**
     * 获取所有群
     */
    public void getGroup() {
        ImClient.getTeamProvider().getAllTeams();
    }
}
