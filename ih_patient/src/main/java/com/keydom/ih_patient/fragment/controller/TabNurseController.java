package com.keydom.ih_patient.fragment.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.ih_patient.App;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.login.LoginActivity;
import com.keydom.ih_patient.activity.nursing_order.NursingOrderActivity;
import com.keydom.ih_patient.activity.nursing_service.NursingActivity;
import com.keydom.ih_patient.activity.nursing_service.NursingOnlineConsultActivity;
import com.keydom.ih_patient.activity.nursing_service.NursingProjectDetailActivity;
import com.keydom.ih_patient.bean.BannerBean;
import com.keydom.ih_patient.bean.NursingIndexInfo;
import com.keydom.ih_patient.callback.SingleClick;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.constant.Type;
import com.keydom.ih_patient.fragment.view.TabNurseView;
import com.keydom.ih_patient.net.NursingService;
import com.keydom.ih_patient.net.UserService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 护理服务控制器
 */
public class TabNurseController extends ControllerImpl<TabNurseView> implements View.OnClickListener {
    @SingleClick(1000)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.state_retry2:
                getPatientHomePageByUserId();
                break;
            case R.id.nurse_function_consulting_online:
                if (Global.getUserId() == -1) {
                    new GeneralDialog(getContext(), "暂未登录，是否前往登录？", new GeneralDialog.OnCloseListener() {
                        @Override
                        public void onCommit() {
                            LoginActivity.start(getContext());
                        }
                    }).setTitle("提示").setPositiveButton("确认").show();
                } else
                    NursingOnlineConsultActivity.start(getContext());
                break;
            case R.id.nurse_function_base_nursing:
                NursingActivity.start(getContext(), Type.BASENURSING, getView().getProjectTypeId("基础护理"));
                break;
            case R.id.nurse_function_professional_nursing:
                NursingActivity.start(getContext(), Type.PROFESSIONALNURSING, getView().getProjectTypeId("专科护理"));
                break;
            case R.id.nurse_function_after_pregnancy:
                NursingActivity.start(getContext(), Type.POSTPARTUMNURSING, getView().getProjectTypeId("产后母婴"));
                break;
            case R.id.hot_service_one_layout:
                NursingProjectDetailActivity.start(getContext(), String.valueOf(getView().getProjectId(0)), getView().getProjectName(0));
                break;
            case R.id.hot_service_two_layout:
                NursingProjectDetailActivity.start(getContext(), String.valueOf(getView().getProjectId(1)), getView().getProjectName(1));
                break;
            case R.id.hot_service_three_layout:
                NursingProjectDetailActivity.start(getContext(), String.valueOf(getView().getProjectId(2)), getView().getProjectName(2));
                break;
            case R.id.hot_service_four_layout:
                NursingProjectDetailActivity.start(getContext(), String.valueOf(getView().getProjectId(3)), getView().getProjectName(3));
                break;
            case R.id.hot_service_five_layout:
                NursingProjectDetailActivity.start(getContext(), String.valueOf(getView().getProjectId(4)), getView().getProjectName(4));
                break;
            case R.id.hot_service_six_layout:
                NursingProjectDetailActivity.start(getContext(), String.valueOf(getView().getProjectId(5)), getView().getProjectName(5));
                break;
            case R.id.await_pay_cv:
                if (Global.getUserId() == -1) {
                    new GeneralDialog(getContext(), "暂未登录，是否前往登录？", new GeneralDialog.OnCloseListener() {
                        @Override
                        public void onCommit() {
                            LoginActivity.start(getContext());
                        }
                    }).setTitle("提示").setPositiveButton("确认").show();
                } else
                    NursingOrderActivity.start(getContext(), getView().getUnpaytag());
                break;
            case R.id.await_diagnose_cv:
                if (Global.getUserId() == -1) {
                    new GeneralDialog(getContext(), "暂未登录，是否前往登录？", new GeneralDialog.OnCloseListener() {
                        @Override
                        public void onCommit() {
                            LoginActivity.start(getContext());
                        }
                    }).setTitle("提示").setPositiveButton("确认").show();
                } else
                    NursingOrderActivity.start(getContext(), NursingOrderActivity.WAITEDNURSE);
                break;
            case R.id.diagnose_cv:
                if (Global.getUserId() == -1) {
                    new GeneralDialog(getContext(), "暂未登录，是否前往登录？", new GeneralDialog.OnCloseListener() {
                        @Override
                        public void onCommit() {
                            LoginActivity.start(getContext());
                        }
                    }).setTitle("提示").setPositiveButton("确认").show();
                } else
                    NursingOrderActivity.start(getContext(), NursingOrderActivity.NURSING);
                break;
            case R.id.finished_cv:
                if (Global.getUserId() == -1) {
                    new GeneralDialog(getContext(), "暂未登录，是否前往登录？", new GeneralDialog.OnCloseListener() {
                        @Override
                        public void onCommit() {
                            LoginActivity.start(getContext());
                        }
                    }).setTitle("提示").setPositiveButton("确认").show();
                } else
                    NursingOrderActivity.start(getContext(), NursingOrderActivity.COMPLETENURSE);
                break;
            case R.id.await_evaluation_cv:
                if (Global.getUserId() == -1) {
                    new GeneralDialog(getContext(), "暂未登录，是否前往登录？", new GeneralDialog.OnCloseListener() {
                        @Override
                        public void onCommit() {
                            LoginActivity.start(getContext());
                        }
                    }).setTitle("提示").setPositiveButton("确认").show();
                } else
                    NursingOrderActivity.start(getContext(), NursingOrderActivity.COMPLETENURSE);
                break;
            case R.id.nursing_search_tv:
                getView().showHospitalPopupWindow();
                break;
        }
    }

    /**
     * 获取患者数据
     */
    public void getPatientHomePageByUserId() {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", Global.getUserId());
        map.put("hospitalId", App.hospitalId);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(NursingService.class).getPatientHomePageByUserId(map), new HttpSubscriber<NursingIndexInfo>(getContext(), getDisposable(), false, false) {
            @Override
            public void requestComplete(@Nullable NursingIndexInfo data) {
                getView().getIndexSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getIndexFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 获取banner图片
     */
    public void getBannerPicByHospitalId(Map<String, Object> map) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).getPicture(map), new HttpSubscriber<List<BannerBean>>(getContext(), getDisposable(), false, false) {
            @Override
            public void requestComplete(@Nullable List<BannerBean> data) {
                getView().getPicListSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getPicListFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
