package com.keydom.mianren.ih_doctor.activity.personal.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_doctor.activity.personal.view.MyVisitingCardView;
import com.keydom.mianren.ih_doctor.bean.UserCard;
import com.keydom.mianren.ih_doctor.net.PersonalApiService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

/**
 * @Name：com.keydom.ih_doctor.activity.controller
 * @Description：我的名片控制器
 * @Author：song
 * @Date：18/11/16 上午9:09
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:09
 */
public class MyVisitingCardController extends ControllerImpl<MyVisitingCardView> {

    /**
     * 获取医生二维码
     */
    public void getCard() {
        HashMap<String, Object> map = new HashMap<>();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PersonalApiService.class).getCard(map), new HttpSubscriber<UserCard>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable UserCard data) {
                getView().getCardSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getCardFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

}
