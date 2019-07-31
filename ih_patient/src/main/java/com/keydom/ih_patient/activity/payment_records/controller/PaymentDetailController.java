package com.keydom.ih_patient.activity.payment_records.controller;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_patient.activity.payment_records.view.PaymentDetailView;
import com.keydom.ih_patient.bean.PayDetailHead;
import com.keydom.ih_patient.bean.PayDetailLine;
import com.keydom.ih_patient.bean.PayDetailTotal;
import com.keydom.ih_patient.bean.PayRecordDetailBean;
import com.keydom.ih_patient.net.PayService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * created date: 2019/1/15 on 15:29
 * des:缴费详情控制器
 */
public class PaymentDetailController extends ControllerImpl<PaymentDetailView> {
    /**
     * 获取缴费详情数据
     */
    public void getConsultationPayInfo(String documentNo) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PayService.class).getConsultationPayInfo(documentNo), new HttpSubscriber<PayRecordDetailBean>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable PayRecordDetailBean data) {
                List<MultiItemEntity> multiItemEntities = transformList(data);
                getView().getDetailCallBack(data,multiItemEntities);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                ToastUtils.showShort(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 处理基础数据中缴费list
     */
    private List<MultiItemEntity> transformList(PayRecordDetailBean data) {
        List<MultiItemEntity> entities = new ArrayList<>();
        if (data == null){
            return entities;
        }
        entities.add(new PayDetailHead());
        if (data.getProjectList() != null) {
            for (int i = 0; i < data.getProjectList().size(); i++) {
                entities.add(data.getProjectList().get(i));
                if (data.getProjectList().get(i).getSonProject() != null) {
                    for (int j = 0; j < data.getProjectList().get(i).getSonProject().size(); j++) {
                        entities.add(data.getProjectList().get(i).getSonProject().get(j));
                    }
                }
                entities.add(new PayDetailLine());
                PayDetailTotal payDetailTotal = new PayDetailTotal();
                payDetailTotal.setMoney(String.valueOf(data.getProjectList().get(i).getProjectClassificationSumFee()));
                entities.add(payDetailTotal);
            }
        }
        return entities;
    }
}
