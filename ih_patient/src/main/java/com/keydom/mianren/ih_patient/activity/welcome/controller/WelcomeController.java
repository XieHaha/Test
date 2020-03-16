package com.keydom.mianren.ih_patient.activity.welcome.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_patient.activity.index_main.MainActivity;
import com.keydom.mianren.ih_patient.activity.welcome.WelcomeActivity;
import com.keydom.mianren.ih_patient.activity.welcome.view.WelcomeView;
import com.keydom.mianren.ih_patient.bean.CityBean;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.net.UserService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 欢迎页控制器
 */
public class WelcomeController extends ControllerImpl<WelcomeView> {

    /**
     * 初始化位置
     */
    public void initLocation(String keyWord){
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).findByKeyword(keyWord), new HttpSubscriber<List<CityBean.itemBean>>() {
            @Override
            public void requestComplete(@Nullable List<CityBean.itemBean> data) {
                if(data!=null&&data.size()!=0){
                    Global.setLocationCityCode(data.get(0).getCode());

                    MainActivity.start(getContext(),false);
                    ((WelcomeActivity)getContext()).finish();
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                MainActivity.start(getContext(),false);
                ((WelcomeActivity)getContext()).finish();
                return super.requestError(exception, code, msg);
            }
        });
    }
}
