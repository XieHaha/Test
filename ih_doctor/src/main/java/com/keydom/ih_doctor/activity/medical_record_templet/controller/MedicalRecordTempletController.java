package com.keydom.ih_doctor.activity.medical_record_templet.controller;

import android.view.View;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_doctor.MyApplication;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.medical_record_templet.view.MedicalRecordTempletView;
import com.keydom.ih_doctor.bean.MedicalRecordTempletBean;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.net.DiagnoseApiService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created date: 2019/1/8 on 14:42
 * des:
 * author: HJW HP
 */
public class MedicalRecordTempletController extends ControllerImpl<MedicalRecordTempletView> implements View.OnClickListener {

    /**
     * 获取模版列表数据
     *
     * @param type 判断获取的模版类型
     */
    public void getTemplateList(int type) {
        showLoading();
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("deptId", MyApplication.userInfo.getDeptId());
        map.put("keyword", getView().getSearchStr());
        map.put("currentPage", getCurrentPage());
        map.put("pageSize", Const.PAGE_SIZE);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(DiagnoseApiService.class).listMedicalTemplate(map), new HttpSubscriber<List<MedicalRecordTempletBean>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable List<MedicalRecordTempletBean> data) {
                hideLoading();
                getView().templateListRequestCallBack(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                ToastUtils.showShort(msg);
                getView().requestErrorCallBack();
                return super.requestError(exception, code, msg);
            }
        });
    }


    /**
     * 选择模版类型，自动获取刷新选中的类型模版
     */
    private void selectType() {
        final List<String> list = new ArrayList<>();
        list.add("个人");
        list.add("科室");
        list.add("公共");
        OptionsPickerView pvOptions = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                setCurrentPage(1);
                getView().setDept(list.get(options1));
                getView().setType(options1);
            }
        }).build();
        pvOptions.setPicker(list);
        pvOptions.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.department:
                selectType();
                break;
        }
    }
}
