package com.keydom.mianren.ih_patient.activity.pregnancy.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.pregnancy.PregnancyDetailActivity;
import com.keydom.mianren.ih_patient.activity.pregnancy.PregnancyOrderDetailActivity;
import com.keydom.mianren.ih_patient.activity.pregnancy.view.PregnancyView;
import com.keydom.mianren.ih_patient.bean.PregnancyDetailBean;
import com.keydom.mianren.ih_patient.bean.PregnancyRecordItem;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.constant.TypeEnum;
import com.keydom.mianren.ih_patient.net.PregnancyService;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PregnancyController extends ControllerImpl<PregnancyView> implements View.OnClickListener {


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pregnancy_order_root_Ll:
                PregnancyOrderDetailActivity.start(getContext(),getView().getPregnancyDetail(),getView().getRecordID());
                break;
            case R.id.pregnancy_order_now_tv:
                PregnancyDetailActivity.start(getContext(),getView().getRecordID(),Const.PREGNANCY_ORDER_TYPE_ALL);
                break;
            case R.id.pregnancy_order_check_tv:
                PregnancyDetailActivity.start(getContext(),getView().getRecordID(),Const.PREGNANCY_ORDER_TYPE_CHECK);
                break;
            case R.id.pregnancy_order_doctor_tv:
                PregnancyDetailActivity.start(getContext(),getView().getRecordID(),Const.PREGNANCY_ORDER_TYPE_DIAGNOSE);
                break;
        }
    }


    /**
     * APP-获取个人产检列表
     */
    public void listPersonInspectionRecord(SmartRefreshLayout refreshLayout, String cardNumber, final TypeEnum typeEnum) {
        if (typeEnum == TypeEnum.REFRESH) {
            setCurrentPage(1);
        }
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PregnancyService.class).listPersonInspectionRecord(cardNumber, getCurrentPage(), Const.PAGE_SIZE), new HttpSubscriber<PageBean<PregnancyRecordItem>>(getContext(), getDisposable(), false, false) {
            @Override
            public void requestComplete(@Nullable PageBean<PregnancyRecordItem> data) {
                if (data != null) {
                    getView().listPersonInspectionRecordSuccess(data.getRecords(), typeEnum);
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().listPersonInspectionRecordFailed(msg);
                refreshLayout.finishLoadMore();
                refreshLayout.finishRefresh();
                return super.requestError(exception, code, msg);
            }
        });

    }


    /**
     * 获取宝妈关怀信息
     */
    public void getPregnancyDetail(String cardNumber) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PregnancyService.class).getPregnancyDetail(cardNumber), new HttpSubscriber<PregnancyDetailBean>(getContext(), getDisposable(), false, false) {
            @Override
            public void requestComplete(@Nullable PregnancyDetailBean data) {
                if (data != null) {
                    getView().getPregnancyDetailSuccess(data);
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getPregnancyDetailFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });

    }
}
