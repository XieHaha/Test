package com.keydom.mianren.ih_patient.activity.hospital_payment.controller;

import android.app.Activity;
import android.view.View;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.KeyboardUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.hospital_payment.view.HospitalCheckListView;
import com.keydom.mianren.ih_patient.bean.HospitalCheckBean;
import com.keydom.mianren.ih_patient.bean.HospitalCountBean;
import com.keydom.mianren.ih_patient.net.HospitalPaymentService;
import com.keydom.mianren.ih_patient.utils.DateUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 住院清单
 *
 * @author 顿顿
 */
public class HospitalCheckListController extends ControllerImpl<HospitalCheckListView> implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hospital_last_tv:
                getView().setSelectDate(DateUtils.getInterValDay(getView().getCurDate(), -1));
                break;
            case R.id.hospital_next_tv:
                getView().setSelectDate(DateUtils.getInterValDay(getView().getCurDate(), +1));
                break;
            case R.id.hospital_date_tv:
                KeyboardUtils.hideSoftInput((Activity) getContext());
                TimePickerView pvTime = new TimePickerBuilder(getContext(),
                        (date, v1) -> getView().setSelectDate(date)).build();
                pvTime.show();
                break;
            default:
                break;
        }
    }

    /**
     * 获取所有次数住院信息
     */
    public void getInHospitalNoList() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(HospitalPaymentService.class).getInHospitalNoList(getView().getMedicalCardInfo().getEleCardNumber()), new HttpSubscriber<HospitalCountBean>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable HospitalCountBean data) {
                getView().setHospitalCountBean(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 查询住院清单分类列表
     */
    public void getHospitalCostType() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("beginDate", getView().getStartDateString());
        map.put("endDate", getView().getEndDateString());
        //        map.put("cardNo", getView().getMedicalCardInfo().getEleCardNumber());
        map.put("cardNo", "226780");
        map.put("expenseTypeCode", "");
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(HospitalPaymentService.class).getHospitalCostType(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<List<HospitalCheckBean>>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable List<HospitalCheckBean> data) {
                getView().fillHospitalPaymentData(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                if (code == 300) {
                    getView().fillHospitalPaymentData(new ArrayList<>());
                }
                return super.requestError(exception, code, msg);
            }
        });
    }
}
