package com.keydom.mianren.ih_patient.activity.pregnancy.controller;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.pregnancy.PregnancyReverseActivity;
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

import java.util.ArrayList;
import java.util.List;

/**
 * @author 顿顿
 */
public class PregnancyController extends ControllerImpl<PregnancyView> implements View.OnClickListener, BaseQuickAdapter.OnItemClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pregnancy_order_root_Ll:
                PregnancyOrderDetailActivity.start(getContext(), getView().getPregnancyDetail(),
                        getView().getPregnancyRecordItem());
                break;
            case R.id.layout_outpatient_reserve:
                PregnancyReverseActivity.start(getContext(), getView().getPregnancyRecordItem(),
                        Const.PREGNANCY_ORDER_TYPE_DIAGNOSE);
                break;
            case R.id.layout_check_reserve:
                PregnancyReverseActivity.start(getContext(), getView().getPregnancyRecordItem(),
                        Const.PREGNANCY_ORDER_TYPE_CHECK);
                break;
            default:
                break;
        }
    }


    /**
     * APP-获取个人产检列表
     */
    public void listPersonInspectionRecord(SmartRefreshLayout refreshLayout, String cardNumber,
                                           final TypeEnum typeEnum) {
        if (typeEnum == TypeEnum.REFRESH) {
            setCurrentPage(1);
        }
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PregnancyService.class).listPersonInspectionRecord(cardNumber, getCurrentPage(), Const.PAGE_SIZE), new HttpSubscriber<PageBean<PregnancyRecordItem>>(getContext(), getDisposable(), false, false) {
            @Override
            public void requestComplete(@Nullable PageBean<PregnancyRecordItem> data) {
                if (data != null) {
                    List<PregnancyRecordItem> list = data.getRecords();
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    getView().listPersonInspectionRecordSuccess(list, typeEnum);
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
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
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().getPregnancyDetailFailed(code, msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        PregnancyOrderDetailActivity.start(getContext(), getView().getPregnancyDetail(),
                (PregnancyRecordItem) adapter.getItem(position));
    }
}
