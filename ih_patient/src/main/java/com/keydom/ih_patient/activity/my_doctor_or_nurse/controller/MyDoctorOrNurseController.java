package com.keydom.ih_patient.activity.my_doctor_or_nurse.controller;

import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_patient.App;
import com.keydom.ih_patient.activity.my_doctor_or_nurse.view.MyDoctorOrNurseView;
import com.keydom.ih_patient.bean.DoctorOrNurseBean;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.net.UserService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created date: 2019/1/2 on 14:25
 * des:我的关注医生控制器
 */
public class MyDoctorOrNurseController extends ControllerImpl<MyDoctorOrNurseView> {
    /**
     * 获取我的关注列表
     */
    public void getMyFollowList(int type) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("userId", Global.getUserId());
        map.put("hospitalId", App.hospitalId);
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).getMyFollowList(map), new HttpSubscriber<List<DoctorOrNurseBean>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable List<DoctorOrNurseBean> data) {
                hideLoading();
                if (data != null && data.size()!=0) {
                    for (int i = 0; i < data.size(); i++) {
                        data.get(i).setType(type);
                    }
                    getView().myFollowsCallBack(data);
                    getChatList();
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                ToastUtils.showShort(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 获取聊天列表
     */
    public void getChatList(){
        ImClient.queryRecentContacts(result -> {
            List<DoctorOrNurseBean> data = getView().returnFollows();
            if (data==null){
                return;
            }
            for (int i = 0; i < data.size(); i++) {
                for (int j = 0; j < result.size(); j++) {
                    if (data.get(i).getImNumber()!=null && data.get(i).getImNumber().toLowerCase().equals(result.get(j).getContactId())){
                        data.get(i).setContent(result.get(j).getContent());
                        data.get(i).setContentNum(result.get(j).getUnreadCount());
                        data.get(i).setTime(result.get(j).getTime());
                    }
                }
            }
            getView().mateFollows(data);
        });
    }
}
