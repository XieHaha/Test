package com.keydom.mianren.ih_patient.activity.medical_record.controller;

import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.medical_record.view.MedicalRecordView;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;
import com.keydom.mianren.ih_patient.bean.MedicalRecordBean;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.constant.TypeEnum;
import com.keydom.mianren.ih_patient.net.CardService;
import com.keydom.mianren.ih_patient.net.UserService;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

/**
 * created date: 2019/1/4 on 13:04
 * des:处方记录控制器
 */
public class MedicalRecordController extends ControllerImpl<MedicalRecordView> implements View.OnClickListener, OnRefreshListener, OnLoadMoreListener {

    private String mCardNumber;

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
    public void getIndAllData(String cardNumber,final TypeEnum typeEnum){
        showLoading();
        mCardNumber =cardNumber;
        if (typeEnum == TypeEnum.REFRESH) {
            setCurrentPage(1);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("cardNumber",cardNumber);
        map.put("currentPage", getCurrentPage());
        map.put("pageSize", Const.PAGE_SIZE);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).getIndCountryAlLList(map), new HttpSubscriber<PageBean<MedicalRecordBean>>(getContext(),getDisposable(),false) {
            @Override
            public void requestComplete(@Nullable PageBean<MedicalRecordBean> data) {
                hideLoading();
                getView().getRecordList(data.getRecords(),typeEnum);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                ToastUtils.showShort(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        currentPagePlus();
        getIndAllData(mCardNumber,TypeEnum.LOAD_MORE);

    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        setCurrentPage(1);
        getIndAllData(mCardNumber,TypeEnum.REFRESH);
    }
}
