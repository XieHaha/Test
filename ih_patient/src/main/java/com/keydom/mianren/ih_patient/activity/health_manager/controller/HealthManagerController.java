package com.keydom.mianren.ih_patient.activity.health_manager.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.ChronicDiseaseMainActivity;
import com.keydom.mianren.ih_patient.activity.health_manager.HealthArchivesActivity;
import com.keydom.mianren.ih_patient.activity.health_manager.HealthConsultantActivity;
import com.keydom.mianren.ih_patient.activity.health_manager.view.HealthManagerView;
import com.keydom.mianren.ih_patient.bean.HealthManagerMainBean;
import com.keydom.mianren.ih_patient.bean.entity.ChronicDisease;
import com.keydom.mianren.ih_patient.net.HealthManagerService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 顿顿
 * @date 20/3/4 10:56
 * @des 健康管理
 */
public class HealthManagerController extends ControllerImpl<HealthManagerView> implements View.OnClickListener, ChronicDisease {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.health_manager_archives_layout:
                HealthArchivesActivity.start(getContext(), getView().getMainBean().getPatientId()
                        , getView().getMainBean().getIsPerfect());
                break;
            case R.id.health_manager_report_layout:
                break;
            case R.id.health_manager_online_layout:
                HealthConsultantActivity.start(getContext(),
                        getView().getMainBean().getPatientId());
                break;
            case R.id.health_manager_cardiovascular_layout:
                if (getView().getMainBean().getIsOpenHeartHeadBloodVessel() == 0) {
                    showOpenHintDialog(getContext().getString(R.string.txt_cardiovascular),
                            CHRONIC_DISEASE_CARDIOVASCULAR);
                } else {
                    ChronicDiseaseMainActivity.start(getContext(),
                            getView().getMainBean().getPatientId(), CHRONIC_DISEASE_CARDIOVASCULAR);
                }
                break;
            case R.id.health_manager_hypertension_layout:
                if (getView().getMainBean().getIsOpenHighBloodPressure() == 0) {
                    showOpenHintDialog(getContext().getString(R.string.txt_hypertension),
                            CHRONIC_DISEASE_HYPERTENSION);
                } else {
                    ChronicDiseaseMainActivity.start(getContext(),
                            getView().getMainBean().getPatientId(), CHRONIC_DISEASE_HYPERTENSION);
                }
                break;
            case R.id.health_manager_diabetes_layout:
                if (getView().getMainBean().getIsOpenDiabetes() == 0) {
                    showOpenHintDialog(getContext().getString(R.string.txt_diabetes),
                            CHRONIC_DISEASE_DIABETES);
                } else {
                    ChronicDiseaseMainActivity.start(getContext(),
                            getView().getMainBean().getPatientId(), CHRONIC_DISEASE_DIABETES);
                }
                break;
            default:
                break;
        }
    }

    private void showOpenHintDialog(String name, int type) {
        new GeneralDialog(getContext(), "您还未启用该模块，是否立即启用？", new GeneralDialog.OnCloseListener() {
            @Override
            public void onCommit() {
                openChronicDiseaseManage(name, type);
            }
        }).setTitle("提示").setPositiveButton("确认").show();
    }

    /**
     * 健康管理首页
     */
    public void patientHealthManageIndex() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(HealthManagerService.class).patientHealthManageIndex(), new HttpSubscriber<HealthManagerMainBean>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable HealthManagerMainBean data) {
                getView().requestHealthManagerSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 开通慢病管理
     */
    public void openChronicDiseaseManage(String name, int type) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("name", name);
        params.put("type", type);
        params.put("patientId", getView().getMainBean().getPatientId());
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(HealthManagerService.class).openChronicDiseaseManage(HttpService.INSTANCE.object2Body(params)), new HttpSubscriber<String>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().openChronicDiseaseManageSuccess(type, data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                ToastUtil.showMessage(getContext(), msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
