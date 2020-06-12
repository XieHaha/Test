package com.keydom.mianren.ih_doctor.fragment.controller;

import android.view.View;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.bean.DoctorPrescriptionDetailBean;
import com.keydom.mianren.ih_doctor.bean.PrescriptionTempletBean;
import com.keydom.mianren.ih_doctor.fragment.view.PrescriptionTempletFragmentView;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;
import com.keydom.mianren.ih_doctor.net.DiagnoseApiService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.fragment.controller
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/16 下午2:26
 * 修改人：xusong
 * 修改时间：18/11/16 下午2:26
 */
public class PrescriptionTempletFragmentController extends ControllerImpl<PrescriptionTempletFragmentView> implements IhTitleLayout.OnRightTextClickListener, View.OnClickListener {


    @Override
    public void OnRightTextClick(View v) {
    }

    /**
     * 获取处方模板列表
     * <p>
     * 0 个人 1 医院
     */
    public void getPrescriptionTempletList() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(DiagnoseApiService.class).getPrescriptionTemplateList(getView().getRequestType()), new HttpSubscriber<List<PrescriptionTempletBean>>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable List<PrescriptionTempletBean> data) {
                getView().getTempletListSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().getTempletListFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 获取历史处方
     * <p>
     * 0 个人 1 医院
     */
    public void getPrescriptionTempletHistoryList() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(DiagnoseApiService.class).getPrescriptionTempletHistoryList(getView().getPatientId()), new HttpSubscriber<List<DoctorPrescriptionDetailBean>>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable List<DoctorPrescriptionDetailBean> data) {
                getView().requestPrescriptionTempletHistorySuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                return super.requestError(exception, code, msg);
            }
        });
    }

    @SingleClick(1000)
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.type_tv) {
            selectType();
        }
    }


    private void selectType() {
        final List<String> list = new ArrayList<>();
        list.add("个人");
        list.add("科室");
        list.add("公共");
        OptionsPickerView pvOptions = new OptionsPickerBuilder(mContext,
                (options1, option2, options3, v) -> {
                    getView().setDept(list.get(options1), String.valueOf(options1));
                    if (getView().isOutPrescription()) {
                        getPrescriptionTempletList();
                    } else {
                        getPrescriptionTempletHistoryList();
                    }
                }).build();
        pvOptions.setPicker(list);
        pvOptions.show();
    }


}
