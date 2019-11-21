package com.keydom.ih_doctor.activity.consulting_arrange.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.consulting_arrange.view.ConsultingChangeView;
import com.keydom.ih_doctor.m_interface.SingleClick;
import com.keydom.ih_doctor.net.ScheduingService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.activity.controller
 * @Description：门诊排班修改共用
 * @Author：song
 * @Date：18/11/16 上午9:09
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:09
 */
public class ConsultingChangeController extends ControllerImpl<ConsultingChangeView> implements View.OnClickListener {
    @SingleClick(1000)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action_rl:
                getView().changeArrange();
                break;
            default:
        }

    }

    /**
     * 修改门诊排班
     */
    public void changeConsulting() {
        Map map = getView().getChangeMap();
        if (map == null) {
            return;
        }
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ScheduingService.class).updateConsulting(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String resData) {
                hideLoading();
                getView().reqSuccess(resData);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().reqFailed(msg);
                ToastUtil.showMessage(getContext(), "修改失败");
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 添加循环排班
     */
    public void addLoopConsulting() {
        Map map = getView().getAddLoopMap();
        if (map == null) {
            return;
        }
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ScheduingService.class).addLoopConsulting(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String resData) {
                hideLoading();
                getView().reqSuccess(resData);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().reqFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 新增停诊
     */
    public void addStopConsulting() {
        Map map = getView().getAddStopMap();
        if (map == null) {
            return;
        }
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ScheduingService.class).addStopConsulting(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String resData) {
                hideLoading();
                getView().reqSuccess(resData);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().reqFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

}
