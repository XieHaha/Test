package com.keydom.mianren.ih_doctor.activity.online_consultation.controller;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.KeyboardUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.view.picker.view.TimePickerHelper;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.doctor_cooperation.FillOutApplyActivity;
import com.keydom.mianren.ih_doctor.activity.doctor_cooperation.SelectDoctorActivity;
import com.keydom.mianren.ih_doctor.activity.online_consultation.view.ConsultationApplyView;
import com.keydom.ih_common.bean.DoctorInfo;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;
import com.keydom.mianren.ih_doctor.net.ConsultationService;
import com.keydom.mianren.ih_doctor.net.MainApiService;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @date 3月24日
 * 会诊
 */
public class ConsultationApplyController extends ControllerImpl<ConsultationApplyView> implements View.OnClickListener, AdapterView.OnItemClickListener {
    @SingleClick(1000)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.consultation_apply_doctor_layout:
                SelectDoctorActivity.startActivityConsultationResult(getContext(),
                        getView().getSelectedDoctor(), true);
                break;
            case R.id.consultation_apply_grade_layout:
                showGrade();
                break;
            case R.id.consultation_apply_time_layout:
                KeyboardUtils.hideSoftInput((Activity) getContext());
                TimePickerHelper.showTimePicker(mContext, new boolean[]{false, true, true, true,
                        true, false}, date -> getView().setApplyDate(date));
                break;
            case R.id.consultation_apply_commit_tv:
                if (getView().verifyCommit()) {
                    submit();
                }
                break;
            default:
        }

    }

    /**
     * 选择性别
     */
    public void showGrade() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(),
                (options1, option2, options3, v) -> getView().setGrade(options1)).build();
        pvOptions.setPicker(getView().getGradeStr());
        pvOptions.show();
    }


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
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                hideLoading();
                getView().uploadFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 获取mdt排班医生
     */
    public void getMdtSchDoctor() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ConsultationService.class).getMdtSchDoctor(), new HttpSubscriber<List<DoctorInfo>>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable List<DoctorInfo> data) {
                getView().requestMdtSchDoctorSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().requestMdtSchDoctorFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    public void submit() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ConsultationService.class).consultationOrderApply(HttpService.INSTANCE.object2Body(getView().getOperateMap())), new HttpSubscriber<String>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().saveSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().saveFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (getView().getLastItemClick(position)) {
            if (getView().getImgSize() < FillOutApplyActivity.MAX_IMAGE) {
                PictureSelector.create((Activity) getContext())
                        .openGallery(PictureMimeType.ofImage()).maxSelectNum(FillOutApplyActivity.MAX_IMAGE - getView().getImgSize())
                        .forResult(PictureConfig.CHOOSE_REQUEST);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("无法选择");
                builder.setMessage("最多只能上传9张图片，请先删除多余的图片！");
                builder.create().show();
            }

        } else {
            CommonUtils.previewImageList(getContext(), getView().getImgList(), position, false);
        }
    }
}
