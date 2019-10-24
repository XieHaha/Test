package com.keydom.ih_patient.activity.payment_records.controller;

import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_patient.App;
import com.keydom.ih_patient.activity.payment_records.view.PaiedRecordView;
import com.keydom.ih_patient.bean.PayRecordBean;
import com.keydom.ih_patient.constant.Const;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.constant.TypeEnum;
import com.keydom.ih_patient.net.PayService;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * 已缴费控制器
 */
public class PaiedRecordController extends ControllerImpl<PaiedRecordView> {
    /**
     * 获取缴费记录
     */
    public void getConsultationPayList(SmartRefreshLayout refreshLayout,int state, final TypeEnum typeEnum) {
        Map<String, Object> map = new HashMap<>();
        map.put("hospitalId", App.hospitalId);
        map.put("registerUserId", Global.getUserId());
        map.put("state", state);
        map.put("currentPage", getCurrentPage());
        map.put("pageSize", Const.PAGE_SIZE);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PayService.class).getConsultationPayList(map), new HttpSubscriber<PageBean<PayRecordBean>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable PageBean<PayRecordBean> data) {
                if (data != null) {
                    getView().paymentListCallBack(data.getRecords(),typeEnum);
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                if (!"token解析失败".equals(msg))
                    ToastUtils.showLong(msg);
                refreshLayout.finishLoadMore();
                refreshLayout.finishRefresh();
                return super.requestError(exception, code, msg);
            }
        });

    }
}
