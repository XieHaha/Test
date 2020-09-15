package com.keydom.mianren.ih_patient.activity.pregnancy.controller;

import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_patient.activity.pregnancy.view.PregnancyRecordView;
import com.keydom.mianren.ih_patient.bean.PregnancyRecordBean;
import com.keydom.mianren.ih_patient.net.PregnancyService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * created date: 2019/1/4 on 13:04
 * des:处方记录控制器
 *
 * @author 顿顿
 */
public class PregnancyRecordController extends ControllerImpl<PregnancyRecordView> {

    /**
     * 获取产检病历记录
     */
    public void getIndAllData() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PregnancyService.class).getAntOutpatientRecord(getView().getMedicalCardInfo().getEleCardNumber()), new HttpSubscriber<List<PregnancyRecordBean>>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable List<PregnancyRecordBean> data) {
                getView().requestRecordSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                if (code == 300) {
                    getView().requestRecordSuccess(new ArrayList<>());
                } else {
                    ToastUtils.showShort(msg);
                }
                return super.requestError(exception, code, msg);
            }
        });
    }
}
