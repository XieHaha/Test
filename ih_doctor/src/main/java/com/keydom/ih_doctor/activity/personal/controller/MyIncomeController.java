package com.keydom.ih_doctor.activity.personal.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_doctor.activity.personal.view.MyIncomeView;
import com.keydom.ih_doctor.bean.MyIncomeBean;
import com.keydom.ih_doctor.net.PersonalApiService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

/**
 * @Name：com.keydom.ih_doctor.activity.controller
 * @Description：我的收入控制器
 * @Author：song
 * @Date：18/11/16 上午9:09
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:09
 */
public class MyIncomeController extends ControllerImpl<MyIncomeView> {


    /**
     * 获取全部收入
     */
    public void getIncome() {
        HashMap<String, Object> map = new HashMap<>();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PersonalApiService.class).wallet(map), new HttpSubscriber<MyIncomeBean>(getContext(),getDisposable(),false) {
            @Override
            public void requestComplete(@Nullable MyIncomeBean data) {
                getView().getWalletSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getWalletFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

}
