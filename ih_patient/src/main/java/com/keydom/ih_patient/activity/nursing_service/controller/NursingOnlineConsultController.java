package com.keydom.ih_patient.activity.nursing_service.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_patient.App;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.nursing_service.view.NursingOnlineConsultView;
import com.keydom.ih_patient.activity.online_diagnoses_order.OnlineDiagnonsesOrderActivity;
import com.keydom.ih_patient.bean.DiagnoseIndexBean;
import com.keydom.ih_patient.bean.DiagnosesAndNurDepart;
import com.keydom.ih_patient.bean.HospitalAreaInfo;
import com.keydom.ih_patient.bean.RecommendDocAndNurBean;
import com.keydom.ih_patient.constant.TypeEnum;
import com.keydom.ih_patient.net.InquiryService;
import com.keydom.ih_patient.net.OrderService;
import com.keydom.ih_patient.net.UserService;
import com.orhanobut.logger.Logger;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

/**
 * 护理服务在线问诊控制器
 */
public class NursingOnlineConsultController extends ControllerImpl<NursingOnlineConsultView> implements View.OnClickListener {

    /**
     * 获取主页数据
     */
    public void getHomeData(Map<String,Object> map){
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(InquiryService.class).getHomeData(map), new HttpSubscriber<DiagnoseIndexBean>(getContext(),getDisposable(),false,false) {
            @Override
            public void requestComplete(@Nullable DiagnoseIndexBean data) {
                getView().getHomeDataSuccess(data);
            }
        });
    }

    /**
     * 获取推荐服务
     */
    public void getRecommendNurse(Map<String,Object> map, TypeEnum type){
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(InquiryService.class).getListHomeRecommendDoctor(map), new HttpSubscriber<PageBean<RecommendDocAndNurBean>>(getContext(),getDisposable(),false,false) {
            @Override
            public void requestComplete(@Nullable PageBean<RecommendDocAndNurBean> data) {
                getView().getRecommendSuccess(data.getRecords(), type);
            }
        });
    }

    /**
     * 获取科室信息
     */
    public void getListCateDeptByHospital(Map<String,Object> map){
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).getListCateDeptByHospital(map), new HttpSubscriber<List<DiagnosesAndNurDepart>>(getContext(),getDisposable(),false,false) {
            @Override
            public void requestComplete(@Nullable List<DiagnosesAndNurDepart> data) {
                getView().getDepartListSuccess(data);
            }
        });
    }
    /**
     * 查询医院
     */
    public void queryHospitalAreaList(){
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(OrderService.class).findHospitalAreaList(App.hospitalId), new HttpSubscriber<List<HospitalAreaInfo>>(getContext(),getDisposable(),false) {
            @Override
            public void requestComplete(@Nullable List<HospitalAreaInfo> data) {
                getView().getAreaList(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getAreaListFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.choose_depart_tv:
                getListCateDeptByHospital(getView().getQueryDeptMap());
                break;
            case R.id.choose_area_tv:
                queryHospitalAreaList();
                break;
            case R.id.nursing_online_search_tv:
                Logger.e("触发点击");

                getView().showHospitalPopupWindow();
                break;
            case R.id.await_pay_cv:
                OnlineDiagnonsesOrderActivity.start(getContext(),getView().getUnpaytag(),1);
                break;
            case R.id.await_diagnose_cv:
                OnlineDiagnonsesOrderActivity.start(getContext(),OnlineDiagnonsesOrderActivity.WAITEDIAGNOSES,1);
                break;
            case R.id.diagnose_cv:
                OnlineDiagnonsesOrderActivity.start(getContext(),OnlineDiagnonsesOrderActivity.DIAGNOSESING,1);
                break;
            case R.id.finished_cv:
                OnlineDiagnonsesOrderActivity.start(getContext(),OnlineDiagnonsesOrderActivity.COMPLETEDIAGNOSES,1);
                break;
            case R.id.await_evaluation_cv:
                OnlineDiagnonsesOrderActivity.start(getContext(),OnlineDiagnonsesOrderActivity.COMPLETEDIAGNOSES,1);
                break;
        }
    }
}
