package com.keydom.ih_doctor.activity.online_diagnose.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.online_diagnose.view.DiagnoseHandleProposalView;
import com.keydom.ih_doctor.bean.DiagnoseHandleBean;
import com.keydom.ih_doctor.net.DiagnoseApiService;
import com.keydom.ih_doctor.utils.ToastUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @Name：com.keydom.ih_doctor.activity.controller
 * @Author：song
 * @Date：18/11/16 上午9:09
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:09
 */
public class DiagnoseHandleProposalController extends ControllerImpl<DiagnoseHandleProposalView> implements View.OnClickListener {


    /**
     * 提交处置建议
     */
    public void doctorHandleSuggest() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(DiagnoseApiService.class).doctorHandleSuggest(getView().getHandleMap()), new HttpSubscriber<DiagnoseHandleBean>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable DiagnoseHandleBean data) {
                getView().handleSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().handleFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submit_btn) {
            if (getView().checkSubmit()) {
                doctorHandleSuggest();
            } else {
                ToastUtil.shortToast(getContext(), "请输入处置建议");
            }
        }
    }
}
