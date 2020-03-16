package com.keydom.mianren.ih_doctor.activity.patient_manage.controller;

import android.app.Activity;
import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.CommonInputActivity;
import com.keydom.mianren.ih_doctor.activity.doctor_cooperation.SelectDoctorActivity;
import com.keydom.mianren.ih_doctor.activity.patient_manage.ChoosePatientActivity;
import com.keydom.mianren.ih_doctor.activity.patient_manage.NewGroupChatActivity;
import com.keydom.mianren.ih_doctor.activity.patient_manage.view.NewGroupChatView;
import com.keydom.mianren.ih_doctor.bean.GroupResBean;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;
import com.keydom.mianren.ih_doctor.net.MainApiService;
import com.keydom.mianren.ih_doctor.net.PatientManageApiService;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class NewGroupChatController extends ControllerImpl<NewGroupChatView> implements View.OnClickListener, IhTitleLayout.OnRightTextClickListener {
    @SingleClick(1000)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.group_name:
                if (!getView().isUpdate()) {
                    ToastUtil.showMessage(getContext(), "您无法修改该群资料！");
                } else {
                    CommonInputActivity.start(getContext(), NewGroupChatActivity.GROUP_NAME, "群聊名称", getView().getGroupName(), 20);
                }
                break;
            case R.id.group_icon_tv:
                if (!getView().isUpdate()) {
                    ToastUtil.showMessage(getContext(), "您无法修改该群资料！");
                } else {
                    PictureSelector.create((Activity) getContext())
                            .openGallery(PictureMimeType.ofImage()).maxSelectNum(1)
                            .forResult(NewGroupChatActivity.GROUP_ICON);
                }
                break;
            case R.id.group_icon:
                if (!getView().isUpdate()) {
                    ToastUtil.showMessage(getContext(), "您无法修改该群资料！");
                } else {
                    PictureSelector.create((Activity) getContext())
                            .openGallery(PictureMimeType.ofImage()).maxSelectNum(1)
                            .forResult(NewGroupChatActivity.GROUP_ICON);
                }
                break;
            case R.id.group_doctor:
                if (!getView().isUpdate()) {
                    ToastUtil.showMessage(getContext(), "您无法修改该群资料！");
                } else {
                    SelectDoctorActivity.startActivitySelfDeptOnlyResult(getContext(), getView().getSelectDoctors(), true);
                }
                break;
            case R.id.patient_name:
                if (!getView().isUpdate()) {
                    ToastUtil.showMessage(getContext(), "您无法修改该群资料！");
                } else {
                    ChoosePatientActivity.start(getContext(), getView().getSelectPatient());
                }

                break;
        }

    }

    @Override
    public void OnRightTextClick(View v) {
        if (!getView().isUpdate()) {
            ToastUtil.showMessage(getContext(), "您无法修改该群资料！");
            return;
        }
        if (getView().submitCheck()) {
            if (getView().getType() == NewGroupChatActivity.CREATE_GROUP) {
                foundGroupChat();
            } else {
                updateGroupChat();
            }

        }
    }


    /**
     * 上传头像
     *
     * @param path
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
     * 创建团队
     */
    public void foundGroupChat() {
        if(getView().getCreateGroupMap()!=null){
            showLoading();
            ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PatientManageApiService.class).foundGroupChat(HttpService.INSTANCE.object2Body(getView().getCreateGroupMap())), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
                @Override
                public void requestComplete(@Nullable String data) {
                    getView().createGroupSuccess(data);
                    hideLoading();
                }

                @Override
                public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                    getView().createGroupFailed(msg);
                    hideLoading();
                    return super.requestError(exception, code, msg);
                }
            });
        }

    }


    /**
     * 获取群资料
     */
    public void seeGroupChatInfo() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PatientManageApiService.class).seeGroupChatInfo(getView().getQueryGroupMap()), new HttpSubscriber<GroupResBean>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable GroupResBean data) {
                getView().getGroupInfoSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().createGroupFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


    /**
     * 修改群资料
     */
    public void updateGroupChat() {
        if(getView().getCreateGroupMap()!=null){
            ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PatientManageApiService.class).updateGroupChat(HttpService.INSTANCE.object2Body(getView().getCreateGroupMap())), new HttpSubscriber<String>(getContext(), getDisposable(), true) {
                @Override
                public void requestComplete(@Nullable String data) {
                    getView().createGroupSuccess(data);
                }

                @Override
                public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                    getView().createGroupFailed(msg);
                    return super.requestError(exception, code, msg);
                }
            });
        }

    }

}
