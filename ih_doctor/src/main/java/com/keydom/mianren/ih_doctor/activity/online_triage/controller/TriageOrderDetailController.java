package com.keydom.mianren.ih_doctor.activity.online_triage.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.online_triage.view.TriageOrderDetailView;
import com.keydom.mianren.ih_doctor.bean.DiagnoseOrderDetailBean;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;
import com.keydom.mianren.ih_doctor.net.GroupCooperateApiService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 *
 */
public class TriageOrderDetailController extends ControllerImpl<TriageOrderDetailView> implements View.OnClickListener {
    /**
     * 接收操作
     */
    private static final int RECEIVE_OPERATE = 1;
    /**
     * 退回操作
     */
    private static final int BACK_OPERATE = -1;

    @SingleClick(1000)
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.return_bt) {
            getView().showDialog();
        } else if (v.getId() == R.id.receive_bt) {
            operate(RECEIVE_OPERATE);
        } else if (v.getId() == R.id.check_dialog_close) {
            getView().hideDialog();
        } else if (v.getId() == R.id.check_dialog_submit) {
            if ("".equals(getView().getDialogValue())) {
                ToastUtil.showMessage(getContext(), "请输入审核意见");
            } else {
                getView().hideDialog();
                operate(BACK_OPERATE);
            }
        }

    }


    /**
     * 获取详情
     */
    public void getDetail() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(GroupCooperateApiService.class).getDetail(getView().getQueryMap()), new HttpSubscriber<DiagnoseOrderDetailBean>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable DiagnoseOrderDetailBean data) {
                getView().getDetailSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().getDetailFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 接收、拒绝操作
     *
     * @param option 操作类型
     */
    public void operate(int option) {
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(GroupCooperateApiService.class).operate(HttpService.INSTANCE.object2Body(getView().getoperateMap(option))), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                hideLoading();
                getView().operationSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                hideLoading();
                getView().operationFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


}
