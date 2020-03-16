package com.keydom.mianren.ih_patient.activity.prescription_check.controller;

import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.prescription_check.view.PrescriptionView;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;
import com.keydom.mianren.ih_patient.bean.PrescriptionTitleBean;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.constant.TypeEnum;
import com.keydom.mianren.ih_patient.net.CardService;
import com.keydom.mianren.ih_patient.net.PrescriptionService;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created date: 2019/1/17 on 20:03
 * des:处方控制器
 */
public class PrescriptionController extends ControllerImpl<PrescriptionView> implements View.OnClickListener, OnRefreshListener, OnLoadMoreListener {

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
     * 获取就诊卡信息
     */
    public void fillData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uuid", Global.getUserId());
        map.put("hospital", App.hospitalId);
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(CardService.class).getCardList(map), new HttpSubscriber<List<MedicalCardInfo>>(getContext(),getDisposable(),false,false) {
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
     * 获取记录列表
     */
    public void getPrescriptionList(String cardNumber,final TypeEnum typeEnum){
        mCardNumber = cardNumber;
        if (typeEnum == TypeEnum.REFRESH) {
            setCurrentPage(1);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("hospitalId", App.hospitalId);
        map.put("cardNumber", cardNumber);
        map.put("currentPage", getCurrentPage());
        map.put("pageSize", Const.PAGE_SIZE);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PrescriptionService.class).prescriptionListPatient(map), new HttpSubscriber<PageBean<PrescriptionTitleBean>>(getContext(),getDisposable(),true,true) {
            @Override
            public void requestComplete(@Nullable PageBean<PrescriptionTitleBean> data) {
                hideLoading();
                List<MultiItemEntity> entities = translateList(data.getRecords());
                getView().listDataCallBack(entities,typeEnum);
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
     * 处理基础数据list
     */
    private List<MultiItemEntity> translateList(List<PrescriptionTitleBean> data){
        List<MultiItemEntity> entities = new ArrayList<>();
        if (data==null){
            return entities;
        }
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getItems()!=null){
                int num = 1;
                for (int j = 0; j < data.get(i).getItems().size(); j++) {
                    data.get(i).getItems().get(j).setNum("处方"+num+"：");
                    num++;
                    if (j == data.get(i).getItems().size()-1){
                        data.get(i).getItems().get(j).setBottom(true);
                    }
                    data.get(i).addSubItem(data.get(i).getItems().get(j));
                }
            }
            entities.add(data.get(i));
        }
        return entities;
    }




    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        currentPagePlus();
        getPrescriptionList(mCardNumber, TypeEnum.LOAD_MORE);

    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        setCurrentPage(1);
        getPrescriptionList(mCardNumber,TypeEnum.REFRESH);
    }
}
