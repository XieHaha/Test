package com.keydom.mianren.ih_doctor.activity.doctor_cooperation.controller;

import android.app.Activity;
import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.CommonInputActivity;
import com.keydom.mianren.ih_doctor.activity.doctor_cooperation.view.UpdateGroupInfoView;
import com.keydom.mianren.ih_doctor.bean.GroupInfoBean;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;
import com.keydom.mianren.ih_doctor.net.GroupCooperateApiService;
import com.keydom.mianren.ih_doctor.net.MainApiService;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @Name：com.keydom.ih_doctor.activity.controller
 * @Author：song
 * @Date：18/11/16 上午9:09
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:09
 */
public class UpdateGroupInfoController extends ControllerImpl<UpdateGroupInfoView> implements View.OnClickListener, IhTitleLayout.OnRightTextClickListener {
    /**
     * 团队名称
     */
    public static final int GROUP_NAME = 400;
    /**
     * 团队擅长
     */
    public static final int GROUP_GOOD_BE = 401;
    @SingleClick(1000)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.patient_name:
                CommonInputActivity.start(getContext(), GROUP_NAME, "修改团队名称", getView().getGroupName(), 20);
                break;
            case R.id.group_icon:

                PictureSelector.create((Activity) getContext())
                        .openGallery(PictureMimeType.ofImage())
                        .maxSelectNum(1)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                break;
            case R.id.group_be_good:
                CommonInputActivity.start(getContext(), GROUP_GOOD_BE, "修改团队擅长", getView().getGroupGood(), 50);
                break;
            default:
        }

    }

    @Override
    public void OnRightTextClick(View v) {
        if (!getView().checkInput()) {
            return;
        }
        if (getView().getType() == TypeEnum.GROUP_UPDATE) {
            updateGroup();
        } else {
            addGroup();
        }

    }

    /**
     * 上传图片
     *
     * @param path 图片路径
     */
    public void uploadFile(String path) {
        File file = new File(path);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("application/otcet-stream"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(MainApiService.class).upload(body), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                hideLoading();
                getView().uploadSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().uploadFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


    /**
     * 添加团队
     */
    public void addGroup() {
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(GroupCooperateApiService.class).ihGroupAdd(HttpService.INSTANCE.object2Body(getView().getMap())), new HttpSubscriber<GroupInfoBean>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable GroupInfoBean data) {
                getView().addSuccess(data);
                hideLoading();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().addFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


    /**
     * 更新团队
     */
    public void updateGroup() {
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(GroupCooperateApiService.class).ihGroupEdit(HttpService.INSTANCE.object2Body(getView().getMap())), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                hideLoading();
                getView().updateSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().updateFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


}
