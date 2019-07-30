package com.keydom.ih_patient.activity.location_manage.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.location_manage.view.AddLocationView;
import com.keydom.ih_patient.bean.PackageData;
import com.keydom.ih_patient.callback.GeneralCallback;
import com.keydom.ih_patient.net.LocationService;
import com.keydom.ih_patient.utils.SelectDialogUtils;
import com.orhanobut.logger.Logger;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

/**
 * 添加地址控制器
 */
public class AddLocationController extends ControllerImpl<AddLocationView> implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.location_add_commit:
                Logger.e("location_add_commit->onclick");
                if("add_location".equals(getView().getType())){
                    commitAdd(getView().getAddMap());
                }else if("edit_location".equals(getView().getType())){
                    commitEdit(getView().getEditMap());
                }
                break;
            case R.id.add_region_choose_tv:
                SelectDialogUtils.showRegionSelectDialog(getContext(),getView().getProvinceName(),getView().getCityName(),getView().getAreaName(),new GeneralCallback.SelectRegionListener() {
                    @Override
                    public void getSelectedRegion(List<PackageData.ProvinceBean> data, int position1, int position2, int position3) {
                        getView().saveRegion(data, position1, position2, position3);
                    }
                });
                break;
        }
    }

    /**
     * 添加提交
     */
    private void commitAdd(Map<String,Object> map) {
        if(map!=null){
            showLoading();
            ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(LocationService.class).addAddress(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<Object>(getContext(),getDisposable(),false) {
                @Override
                public void requestComplete(@org.jetbrains.annotations.Nullable Object data) {
                    hideLoading();
                    getView().addSuccess();
                }

                @Override
                public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                    hideLoading();
                    getView().addFailed(msg);
                    return super.requestError(exception, code, msg);
                }
            });
        }

    }

    /**
     * 编辑提交
     */
    private void commitEdit(Map<String,Object> map) {
        if(map!=null){
            showLoading();
            ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(LocationService.class).editAddress(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<Object>(getContext(),getDisposable(),false) {
                @Override
                public void requestComplete(@org.jetbrains.annotations.Nullable Object data) {
                    hideLoading();
                    getView().editSuccess();
                }
                @Override
                public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                    hideLoading();
                    getView().editFailed(msg);
                    return super.requestError(exception, code, msg);
                }
            });
        }

    }

}
