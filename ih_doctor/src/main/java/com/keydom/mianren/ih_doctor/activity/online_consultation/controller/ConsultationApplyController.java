package com.keydom.mianren.ih_doctor.activity.online_consultation.controller;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.KeyboardUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.doctor_cooperation.FillOutApplyActivity;
import com.keydom.mianren.ih_doctor.activity.doctor_cooperation.SelectDoctorActivity;
import com.keydom.mianren.ih_doctor.activity.online_consultation.view.ConsultationApplyView;
import com.keydom.mianren.ih_doctor.bean.DiagnoseFillOutResBean;
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
                TimePickerView pickerView = new TimePickerBuilder(getContext(),
                        (date, v12) -> getView().setApplyDate(date)).build();
                pickerView.show();
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


    public void submit() {
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(GroupCooperateApiService.class).save(HttpService.INSTANCE.object2Body(getView().getOperateMap())), new HttpSubscriber<DiagnoseFillOutResBean>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable DiagnoseFillOutResBean data) {
                hideLoading();
                getView().saveSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                hideLoading();
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
