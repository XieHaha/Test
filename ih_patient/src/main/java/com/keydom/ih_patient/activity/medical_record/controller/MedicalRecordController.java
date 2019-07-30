package com.keydom.ih_patient.activity.medical_record.controller;

import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_patient.App;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.medical_record.view.MedicalRecordView;
import com.keydom.ih_patient.bean.MedicalCardInfo;
import com.keydom.ih_patient.bean.MedicalRecordBean;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.net.CardService;
import com.keydom.ih_patient.net.UserService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

/**
 * created date: 2019/1/4 on 13:04
 * des:处方记录控制器
 */
public class MedicalRecordController extends ControllerImpl<MedicalRecordView> implements View.OnClickListener{
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.choose_patient_tv:
                getView().showCardPopupWindow();
                break;
        }
    }

    /**
     * 获取就诊卡列表
     */
    public void fillData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uuid", Global.getUserId());
        map.put("hospital", App.hospitalId);
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(CardService.class).getCardList(map), new HttpSubscriber<List<MedicalCardInfo>>(getContext(),getDisposable(),false) {
            @Override
            public void requestComplete(@Nullable List<MedicalCardInfo> data) {
                hideLoading();
                getView().fillDataList(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                ToastUtils.showShort(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 获取电子处方记录
     */
    //type 0诊疗 1咨询 diagnosis 诊断
    public void getIndAllData(String cardNumber){
        showLoading();
        HashMap<String, Object> map = new HashMap<>();
        map.put("cardNumber",cardNumber);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).getIndCountryAlLList(map), new HttpSubscriber<List<MedicalRecordBean>>(getContext(),getDisposable(),false) {
            @Override
            public void requestComplete(@Nullable List<MedicalRecordBean> data) {
                hideLoading();
                getView().getRecordList(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                ToastUtils.showShort(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
