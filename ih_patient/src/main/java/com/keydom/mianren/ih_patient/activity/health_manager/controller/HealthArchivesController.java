package com.keydom.mianren.ih_patient.activity.health_manager.controller;

import android.view.View;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.HealthAddSurgeryActivity;
import com.keydom.mianren.ih_patient.activity.health_manager.HealthArchivesBaseActivity;
import com.keydom.mianren.ih_patient.activity.health_manager.HealthContactActivity;
import com.keydom.mianren.ih_patient.activity.health_manager.HealthMedicalHistoryActivity;
import com.keydom.mianren.ih_patient.activity.health_manager.HealthSurgeryListActivity;
import com.keydom.mianren.ih_patient.activity.health_manager.view.HealthArchivesView;
import com.keydom.mianren.ih_patient.bean.HealthArchivesBean;
import com.keydom.mianren.ih_patient.net.HealthManagerService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author 顿顿
 * @date 20/3/4 10:56
 * @des 健康档案
 */
public class HealthArchivesController extends ControllerImpl<HealthArchivesView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        OptionsPickerView pickerView;
        switch (v.getId()) {
            case R.id.health_archives_base_info_layout:
                HealthArchivesBaseActivity.start(getContext(), getView().getArchivesBean());
                break;
            case R.id.health_archives_add_contact_tv:
                //初始化
                getView().setCurRelationPosition(-1);
                HealthContactActivity.start(getContext());
                break;
            case R.id.health_archives_select_past_tv:
                HealthMedicalHistoryActivity.start(getContext(),
                        getView().getArchivesBean().getMedicalHistory(),
                        HealthMedicalHistoryActivity.PAST_MEDICAL_HISTORY);
                break;
            case R.id.health_archives_genetic_tv:
                HealthMedicalHistoryActivity.start(getContext(),
                        getView().getArchivesBean().getGeneticHistory(),
                        HealthMedicalHistoryActivity.GENETIC_HISTORY);
                break;
            case R.id.health_archives_add_surgery_tv:
                HealthAddSurgeryActivity.start(getContext());
                break;
            case R.id.health_archives_look_more_tv:
                HealthSurgeryListActivity.start(getContext(),
                        getView().getArchivesBean().getPatientSurgeryHistories());
                break;
            case R.id.health_archives_drink:
                getView().setSelectDrink(1);
                break;
            case R.id.health_archives_non_drink:
                getView().setSelectDrink(0);
                break;
            case R.id.health_archives_drink_frequency_layout:
                pickerView = new OptionsPickerBuilder(getContext(),
                        (options1, option2, options3, v12) -> getView().setDrinkDegree(options1)).build();
                pickerView.setPicker(getView().getDrinkDegreeData());
                pickerView.show();
                break;
            case R.id.health_archives_drink_quantity_layout:
                pickerView = new OptionsPickerBuilder(getContext(),
                        (options1, option2, options3, v12) -> getView().setDrinkNum(options1)).build();
                pickerView.setPicker(getView().getDrinkNumData());
                pickerView.show();
                break;
            case R.id.health_archives_drink_year_layout:
                pickerView = new OptionsPickerBuilder(getContext(),
                        (options1, option2, options3, v12) -> getView().setDrinkYear(options1)).build();
                pickerView.setPicker(getView().getDrinkOrSmokeYearData());
                pickerView.show();
                break;
            case R.id.health_archives_smoke:
                getView().setSelectSmoke(1);
                break;
            case R.id.health_archives_non_smoke:
                getView().setSelectSmoke(0);
                break;
            case R.id.health_archives_smoke_frequency_layout:
                pickerView = new OptionsPickerBuilder(getContext(),
                        (options1, option2, options3, v12) -> getView().setSmokeDegree(options1)).build();
                pickerView.setPicker(getView().getSmokeDegreeData());
                pickerView.show();
                break;
            case R.id.health_archives_smoke_quantity_layout:
                pickerView = new OptionsPickerBuilder(getContext(),
                        (options1, option2, options3, v12) -> getView().setSmokeNum(options1)).build();
                pickerView.setPicker(getView().getSmokeNumData());
                pickerView.show();
                break;
            case R.id.health_archives_smoke_year_layout:
                pickerView = new OptionsPickerBuilder(getContext(),
                        (options1, option2, options3, v12) -> getView().setSmokeYear(options1)).build();
                pickerView.setPicker(getView().getDrinkOrSmokeYearData());
                pickerView.show();
                break;
            default:
                break;
        }
    }

    /**
     * 获取患者健康档案
     */
    public void getPatientInfo() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(HealthManagerService.class).getPatientInfo(getView().getPatientId()), new HttpSubscriber<HealthArchivesBean>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable HealthArchivesBean data) {
                getView().getPatientInfoSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                ToastUtil.showMessage(getContext(), msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 保存患者健康档案
     */
    public void savePatientInfo() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(HealthManagerService.class).savePatientInfo(HttpService.INSTANCE.object2Body(getView().getParams())), new HttpSubscriber<String>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().savePatientInfoSuccess();
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
