package com.keydom.mianren.ih_doctor.activity.online_diagnose.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.CommonInputActivity;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.ApplyForCheckActivity;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.DiagnoseCheckSelectItemActivity;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.DiagnoseInputActivity;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.view.ApplyForCheckView;
import com.keydom.mianren.ih_doctor.bean.OrderApplyResponse;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;
import com.keydom.mianren.ih_doctor.net.DiagnoseApiService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.activity.controller
 * @Author：song
 * @Date：18/11/16 上午9:09
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:09
 */
public class ApplyForCheckController extends ControllerImpl<ApplyForCheckView> implements View.OnClickListener, IhTitleLayout.OnRightTextClickListener {

    @SingleClick(1000)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.apply_test_add_tv:
                if (getView().isInspect()) {
                    DiagnoseCheckSelectItemActivity.start(getContext(), 2,
                            getView().getCheckOutSelectItemList());
                } else {
                    DiagnoseCheckSelectItemActivity.start(getContext(), 1,
                            getView().getCheckOutSelectItemList());
                }
                break;
            case R.id.diagnose_tv:
                DiagnoseInputActivity.start(getContext(), getView().getDiagnose());
                break;
            case R.id.disease_tv:
                CommonInputActivity.start(getContext(), ApplyForCheckActivity.INPUT_DISEASE,
                        "病情描述", getView().getDisease(), 500, 10);
                break;
        }

    }

    @Override
    public void OnRightTextClick(View v) {
        saveOrder();
    }

    /**
     * 提供统一的提交方法，在这里进行判断到底是检查提交还是检验提交
     */
    public void saveOrder() {
        if (getView().isSaveCheckOutOrder()) {
            saveCheckout();
        } else {
            ToastUtil.showMessage(getContext(), "请完善信息后再提交！");
        }
    }

    /**
     * 提交保存检验报告
     */
    private void saveCheckout() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(DiagnoseApiService.class).saveCheckout(HttpService.INSTANCE.object2Body(getView().getCheckoutParams())), new HttpSubscriber<List<OrderApplyResponse>>(getContext(), getDisposable(), true, false) {
            @Override
            public void requestComplete(@Nullable List<OrderApplyResponse> data) {
                getView().saveTestOrderSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().saveTestOrderFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });

    }


    /**
     * 删除检查／检验单
     */
    public void cancelCheckout(String orderId) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(DiagnoseApiService.class).cancelCheckout(orderId), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().deleteOrderSuccess();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().deleteOrderFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


}
