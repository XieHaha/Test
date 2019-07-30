package com.keydom.ih_doctor.activity.online_diagnose.controller;

import android.view.View;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.online_diagnose.view.PrescriptionTempletView;
import com.keydom.ih_doctor.bean.PrescriptionTempletBean;
import com.keydom.ih_doctor.net.DiagnoseApiService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PrescriptionTempletController extends ControllerImpl<PrescriptionTempletView> implements IhTitleLayout.OnRightTextClickListener, View.OnClickListener {


    @Override
    public void OnRightTextClick(View v) {
    }

    /**
     * 获取处方模板列表
     * <p>
     * 0 个人 1 医院
     */
    public void getPrescriptionTempletList() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(DiagnoseApiService.class).getPrescriptionTemplateList(getView().getType()), new HttpSubscriber<List<PrescriptionTempletBean>>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable List<PrescriptionTempletBean> data) {
                getView().getTempletListSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getTempletListFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

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
        OptionsPickerView pvOptions = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                getView().setDept(list.get(options1), String.valueOf(options1));
                getPrescriptionTempletList();
            }
        }).build();
        pvOptions.setPicker(list);
        pvOptions.show();
    }


}
