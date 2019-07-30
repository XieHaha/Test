package com.keydom.ih_doctor.activity.online_diagnose.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.ih_doctor.MyApplication;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.CommonInputActivity;
import com.keydom.ih_doctor.activity.online_diagnose.ApplyForCheckActivity;
import com.keydom.ih_doctor.activity.online_diagnose.ChooseInspectItemActivity;
import com.keydom.ih_doctor.activity.online_diagnose.DiagnoseCheckSelectItemActivity;
import com.keydom.ih_doctor.activity.online_diagnose.DiagnoseInputActivity;
import com.keydom.ih_doctor.activity.online_diagnose.view.ApplyForCheckView;
import com.keydom.ih_doctor.bean.CheckOutItemBean;
import com.keydom.ih_doctor.bean.OrderApplyResponse;
import com.keydom.ih_doctor.net.DiagnoseApiService;
import com.keydom.ih_doctor.utils.ToastUtil;

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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.apply_test_add_tv:
                if (getView().isInspect()) {
                    if (getView().getInspactItemList() == null) {
                        ToastUtil.shortToast(getContext(), "没有查询到检查项目");
                    } else {
//                        ChooseInspectItemActivity.start(getContext(), getView().getInspactItemList(), getView().getInspactSelectItemList());
                        ChooseInspectItemActivity.start(getContext(), getView().getInspactItemList(), null);
                        ;
                    }
                } else {
                    DiagnoseCheckSelectItemActivity.start(getContext(), getView().getCheckOutSelectItemList());
                }
                break;
            case R.id.diagnose_tv:
                DiagnoseInputActivity.start(getContext(), getView().getMainDec());
                break;
            case R.id.disease_tv:
                CommonInputActivity.start(getContext(), ApplyForCheckActivity.INPUT_DISEASE, "病情描述", getView().getDisease(), 500, 20);
                break;
        }

    }

    /**
     * 提交保存检验报告
     */
    public void saveCheckout() {
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(DiagnoseApiService.class).saveCheckout(HttpService.INSTANCE.object2Body(getView().getTestMap())), new HttpSubscriber<List<OrderApplyResponse>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable List<OrderApplyResponse> data) {
                hideLoading();
                getView().saveTestOrderSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().saveTestOrderFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });

    }

    /**
     * 提交保存检查报告
     */
    public void saveInspect() {
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(DiagnoseApiService.class).saveInspect(HttpService.INSTANCE.object2Body(getView().getInspectMap())), new HttpSubscriber<List<OrderApplyResponse>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable List<OrderApplyResponse> data) {
                hideLoading();
                getView().saveInspactOrderSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().saveInspactOrderFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


    /**
     * 获取检查项目列表
     */
    public void inspectList() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(DiagnoseApiService.class).inspectList(MyApplication.userInfo.getHospitalId()), new HttpSubscriber<List<CheckOutItemBean>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable List<CheckOutItemBean> data) {
                getView().getInspactItemListSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getInspactItemListFailed(msg);
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
            if (getView().isSaveInspactOrder()) {
                saveInspect();
            } else {
                ToastUtil.shortToast(getContext(), "请完善检查项目后再提交！");
            }

        } else {
            if (getView().isSaveCheckOutOrder()) {
                saveCheckout();
            } else {
                ToastUtil.shortToast(getContext(), "请完善检验项目后再提交！");
            }

        }
    }

    /**
     * 删除检查／检验单
     *
     * @param orderId
     */
    public void deleteInquisition(long orderId) {
        showLoading();
        Map<String, Object> map = new HashMap<>();
        map.put("id", orderId);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(DiagnoseApiService.class).deleteInquisition(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                hideLoading();
                getView().deleteOrderSuccess();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().deleteOrderFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


}
