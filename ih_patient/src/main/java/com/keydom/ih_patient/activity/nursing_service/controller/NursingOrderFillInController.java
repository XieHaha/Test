package com.keydom.ih_patient.activity.nursing_service.controller;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;

import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_patient.App;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.nursing_service.ChooseNursingServiceActivity;
import com.keydom.ih_patient.activity.nursing_service.FaceDetectExpActivity;
import com.keydom.ih_patient.activity.nursing_service.NursingApplyOrderActivity;
import com.keydom.ih_patient.activity.nursing_service.NursingChoosePatientActivity;
import com.keydom.ih_patient.activity.nursing_service.NursingOrderFillInActivity;
import com.keydom.ih_patient.activity.nursing_service.view.NursingOrderFillInView;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.bean.HospitaldepartmentsInfo;
import com.keydom.ih_patient.bean.NursingServiceOrderInfo;
import com.keydom.ih_patient.callback.SingleClick;
import com.keydom.ih_patient.constant.EventType;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.net.NursingService;
import com.keydom.ih_patient.net.OrderService;
import com.keydom.ih_patient.net.UploadService;
import com.keydom.ih_patient.utils.DateUtils;
import com.keydom.ih_patient.utils.LocalizationUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * created date: 2018/12/24 on 19:15
 * des:护理订单填写控制器
 */
public class NursingOrderFillInController extends ControllerImpl<NursingOrderFillInView> implements View.OnClickListener, AdapterView.OnItemClickListener {
    @SingleClick(1000)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.jump_to_choose_service_tv:
                Intent i = new Intent(getContext(), ChooseNursingServiceActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(ChooseNursingServiceActivity.PROJECT_LIST, (Serializable) getView().getSelectProjectList());
                i.putExtras(bundle);
                ActivityUtils.startActivity(i);
                break;
            case R.id.scaler_minus_layout:
                getView().numReduce();
                break;
            case R.id.scaler_add_layout:
                getView().numAdd();
                break;
            case R.id.choose_service_time_tv:
                chooseTime();
                break;
            case R.id.jump_to_choose_service_object_tv:
                NursingChoosePatientActivity.start(getContext());
                break;
            case R.id.jump_to_choose_department_tv:
//                if (NoFastClickUtils.isFastClick()) {
//                    if (getView().isHaveProfessionalProject()) {
//                        queryDataList(getView().getHospitalAreaId());
//                    } else {
//                        ToastUtil.showMessage(getContext(), "当前选择护理项目中没有专科护理服务，无需指定科室");
//                    }
//                }
                break;
            case R.id.commit_nursing_order_tv:
                if (getView().isChange()) {
                    patientRewriteOrderAndSubmit(getView().getChangeMap());
                } else {
                    commitNursingProjectOrder(getView().getCommitMap());
                }
                break;
            case R.id.jump_to_facial_recognition_tv:
//                FacialRecognitionActivity.start(getContext());
                RxPermissions rxPermissions = new RxPermissions((FragmentActivity) getContext());
                rxPermissions.request(Manifest.permission.CAMERA).subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            getContext().startActivity(new Intent(getContext(), FaceDetectExpActivity.class));
                        } else
                            ToastUtil.showMessage(getContext(), "未获取摄像头使用权限，无法使用二维码功能");
                    }
                });

                break;

           /* case R.id.pic_first_img:
                PictureSelector.create((Activity) getContext())
                        .openGallery(PictureMimeType.ofImage())
                        .maxSelectNum(1)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                break;
            case R.id.pic_second_img:
                PictureSelector.create((Activity) getContext())
                        .openGallery(PictureMimeType.ofImage())
                        .maxSelectNum(1)
                        .forResult(PictureConfig.REQUEST_CAMERA);
                break;*/
        }
    }

    /**
     * 选择时间区间
     */
    private void chooseTime() {
        OptionsPickerView optionsPickerView = null;
        if (optionsPickerView == null)
            optionsPickerView = DateUtils.showDateIntervalChooseDialog(getContext(), 14, 0, 24, new DateUtils.DateIntervalSelectedListener() {
                @Override
                public void getSelectedDateInterval(String date, String startTime, String endTime) {
                    getView().timeChoose(date, startTime, endTime);
                }
            });
        optionsPickerView.show();
    }

    /**
     * 查询科室
     */
    public void queryDataList(long hospitailAreaId) {
        Map<String, Object> map = new java.util.HashMap<>();
        map.put("hospitalId", App.hospitalId);
        map.put("hospitalAreaId", hospitailAreaId);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(OrderService.class).getDepartmentList(map), new HttpSubscriber<List<HospitaldepartmentsInfo>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable List<HospitaldepartmentsInfo> data) {
                hideLoading();
                getView().getDepartmentData(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                ToastUtils.showShort(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 上传图片
     */
    public void upLoadPic(String path) {
        File file = new File(path);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("application/otcet-stream"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UploadService.class).upload(body), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                hideLoading();
                getView().uploadImgSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().uploadImgFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 修改护理订单提交
     */
    public void patientRewriteOrderAndSubmit(Map<String, Object> map) {
        if (map != null) {
            ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(NursingService.class).patientRewriteOrderAndSubmit(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<Object>(getContext(), getDisposable(), true, true) {
                @Override
                public void requestComplete(@Nullable Object data) {
                    ToastUtils.showShort("修改成功");
                    Event event = new Event(EventType.CHANGE_NURSING_SUCCESS, null);
                    EventBus.getDefault().post(event);
                    ActivityUtils.finishActivity(NursingOrderFillInActivity.class);
                }

                @Override
                public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                    ToastUtils.showShort(msg);
                    return super.requestError(exception, code, msg);
                }
            });
        }
    }

    /**
     * 提交护理订单
     */
    public void commitNursingProjectOrder(Map<String, Object> map) {
        if (map != null) {
            ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(NursingService.class).patientCreateReservationOrder(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<NursingServiceOrderInfo>(getContext(), getDisposable(), true, true) {
                @Override
                public void requestComplete(@Nullable NursingServiceOrderInfo data) {
                    Logger.e("请求成功");
                    String fileName = "nurseEditData" + Global.getUserId() + "_" + App.hospitalId;
                    LocalizationUtils.deleteFileFromLocal(getContext(), fileName);
                    getView().setIsNeedSaveEdit(false);
                    EventBus.getDefault().post(new Event(EventType.CREATE_NURSING_SUCCESS, null));
                    NursingApplyOrderActivity.start(getContext(), data);
                }

                @Override
                public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                    Logger.e("请求失败" + msg);
                    ToastUtil.showMessage(getContext(), msg);
                    return super.requestError(exception, code, msg);
                }
            });
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (getView().getLastItemClick(position)) {
            if (getView().getImgSize() < 9) {
                PictureSelector.create((Activity) getContext())
                        .openGallery(PictureMimeType.ofImage())
                        .maxSelectNum(9 - getView().getImgSize())
                        .forResult(PictureConfig.CHOOSE_REQUEST);
            } else {
                ToastUtil.showMessage(mContext, "最多只能选择九张图片");
            }

        } else
//            CommonUtils.previewImage(getContext(), getView().getPicUrl(position));
            CommonUtils.previewImageList(getContext(), getView().getPicList(), position, true);
    }

}
