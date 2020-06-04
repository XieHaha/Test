package com.keydom.mianren.ih_doctor.activity.online_diagnose.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.CommonInputActivity;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.ApplyForCheckActivity;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.ChooseInspectItemActivity;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.DiagnoseCheckSelectItemActivity;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.DiagnoseInputActivity;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.view.ApplyForCheckView;
import com.keydom.ih_common.bean.CheckOutGroupBean;
import com.keydom.mianren.ih_doctor.bean.OrderApplyResponse;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;
import com.keydom.mianren.ih_doctor.net.DiagnoseApiService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                    if (getView().getInspectItemList() == null) {
                        ToastUtil.showMessage(getContext(), "没有查询到检查项目");
                    } else {
                        ChooseInspectItemActivity.start(getContext(),
                                getView().getInspectItemList(), null);
                    }
                } else {
                    DiagnoseCheckSelectItemActivity.start(getContext(),
                            getView().getCheckOutSelectItemList());
                }
                break;
            case R.id.diagnose_tv:
                DiagnoseInputActivity.start(getContext(), getView().getMainDec());
                break;
            case R.id.disease_tv:
                CommonInputActivity.start(getContext(), ApplyForCheckActivity.INPUT_DISEASE,
                        "病情描述", getView().getDisease(), 500, 10);
                break;
        }

    }

    /**
     * 提交保存检验报告
     */
    private void saveCheckout() {
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(DiagnoseApiService.class).saveCheckout(HttpService.INSTANCE.object2Body(getView().getTestMap())), new HttpSubscriber<List<OrderApplyResponse>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable List<OrderApplyResponse> data) {
                hideLoading();
                getView().saveTestOrderSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                hideLoading();
                getView().saveTestOrderFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });

    }

    /**
     * 提交保存检查报告
     */
    private void saveInspect() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(DiagnoseApiService.class).saveCheckout(HttpService.INSTANCE.object2Body(getView().getInspectMap())), new HttpSubscriber<List<OrderApplyResponse>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable List<OrderApplyResponse> data) {
                getView().saveInspectOrderSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().saveInspectOrderFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


    /**
     * 获取检查项目列表
     */
    public void inspectList() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(DiagnoseApiService.class).inspectList(MyApplication.userInfo.getHospitalId()), new HttpSubscriber<List<CheckOutGroupBean>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable List<CheckOutGroupBean> data) {
                getView().getInspectItemListSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().getInspectItemListFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    @Override
    public void OnRightTextClick(View v) {
        saveOrder();
    }

    /**
     * 提供统一的提交方法，在这里进行判断到底是检查提交还是检验提交
     */
    public void saveOrder() {
        if (getView().isInspect()) {
            if (getView().isSaveInspectOrder()) {
                saveInspect();
            } else {
                ToastUtil.showMessage(getContext(), "请完善检查项目后再提交！");
            }

        } else {
            if (getView().isSaveCheckOutOrder()) {
                saveCheckout();
            } else {
                ToastUtil.showMessage(getContext(), "请完善检验项目后再提交！");
            }

        }
    }

    /**
     * 删除检查／检验单
     *
     * @param orderId
     */
    public void deleteInquisition(long orderId) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", orderId);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(DiagnoseApiService.class).deleteInquisition(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
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
