package com.keydom.mianren.ih_patient.activity.reserve_obstetric_hospital.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.bean.DoctorInfo;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.reserve_obstetric_hospital.ObstetricHospitalListActivity;
import com.keydom.mianren.ih_patient.activity.reserve_obstetric_hospital.view.ReserveObstetricHospitalView;
import com.keydom.mianren.ih_patient.bean.DepartmentInfo;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.net.CardService;
import com.keydom.mianren.ih_patient.net.HospitalAppointmentService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

/**
 * @author 顿顿
 * @date 20/3/11 17:00
 * @des
 */
public class ReserveObstetricHospitalController extends ControllerImpl<ReserveObstetricHospitalView> implements View.OnClickListener, IhTitleLayout.OnRightTextClickListener {
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tx_next) {
            if (getView().reserveAble()) {
                submit();
            }
        }
    }

    @Override
    public void OnRightTextClick(View v) {
        ObstetricHospitalListActivity.start(getContext());
    }

    /**
     * 获取科室
     */
    public void getObstetricDepart() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(HospitalAppointmentService.class).getInHospitalDeptList(), new HttpSubscriber<List<DepartmentInfo>>(getContext(), getDisposable(), false, false) {
            @Override
            public void requestComplete(@Nullable List<DepartmentInfo> data) {
                getView().requestDepartSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().requestDepartFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 获取指定科室的医生
     */
    public void getDeptDoctor(String departId, int type) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(HospitalAppointmentService.class).getDeptDoctor(departId, type), new HttpSubscriber<List<DoctorInfo>>(getContext(), getDisposable(), false, false) {
            @Override
            public void requestComplete(@Nullable List<DoctorInfo> data) {
                getView().requestDoctorSuccess(data, type);
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
     * 获取指定科室的医生
     */
    public void submit() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(HospitalAppointmentService.class).submit(HttpService.INSTANCE.object2Body(getView().getParams())), new HttpSubscriber<String>(getContext(), getDisposable(), true, false) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().reserveSuccess();
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
     * 查询所有就诊卡
     */
    public void queryAllCard() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uuid", Global.getUserId());
        map.put("hospital", App.hospitalId);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(CardService.class).getCardList(map), new HttpSubscriber<List<MedicalCardInfo>>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable List<MedicalCardInfo> data) {
                getView().getAllCardSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().getAllCardFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
