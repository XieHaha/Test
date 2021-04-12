package com.keydom.mianren.ih_patient.activity.health_manager.controller;

import android.app.Activity;
import android.view.View;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.KeyboardUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.HealthSummaryDetailActivity;
import com.keydom.mianren.ih_patient.activity.health_manager.view.HealthSummaryView;
import com.keydom.mianren.ih_patient.bean.HealthSummaryBean;
import com.keydom.mianren.ih_patient.net.HealthManagerService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Calendar;

/**
 * @author 顿顿
 * @date 20/3/4 10:56
 * @des 健康总结
 */
public class HealthSummaryController extends ControllerImpl<HealthSummaryView> implements View.OnClickListener, BaseQuickAdapter.OnItemClickListener {
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.health_summary_select_time_tv) {
            Calendar startDate = Calendar.getInstance();
            Calendar endDate = Calendar.getInstance();
            //过去10年数据
            startDate.add(Calendar.YEAR, -9);
            KeyboardUtils.hideSoftInput((Activity) getContext());
            TimePickerView view = new TimePickerBuilder(getContext(),
                    (date, v1) -> getView().onSelectDate(date)).setRangDate(startDate,
                    endDate).setType(new boolean[]{true, false, false, false, false, false}).build();
            view.show();
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        HealthSummaryBean summaryBean = (HealthSummaryBean) adapter.getItem(position);
        HealthSummaryDetailActivity.start(getContext(), summaryBean.getId(),
                getView().getPatientId());
    }

    /**
     * 获取健康总结列表
     */
    public void patientHealthConclusionList() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(HealthManagerService.class).patientHealthConclusionList(HttpService.INSTANCE.object2Body(getView().getParams())), new HttpSubscriber<PageBean<HealthSummaryBean>>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable PageBean<HealthSummaryBean> data) {
                getView().requestHealthSummaryListSuccess(data.getRecords());
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().requestHealthSummaryListFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
