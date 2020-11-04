package com.keydom.mianren.ih_patient.activity.online_diagnoses_order.controller;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.blankj.utilcode.util.ActivityUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.bean.DoctorInfo;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.diagnose_user_manager.AnamnesisActivity;
import com.keydom.mianren.ih_patient.activity.index_main.MainActivity;
import com.keydom.mianren.ih_patient.activity.online_diagnoses_order.ChoosePatientActivity;
import com.keydom.mianren.ih_patient.activity.online_diagnoses_order.OnlineDiagnonsesOrderActivity;
import com.keydom.mianren.ih_patient.activity.online_diagnoses_order.PatientMainSuitActivity;
import com.keydom.mianren.ih_patient.activity.online_diagnoses_order.view.DiagnosesApplyView;
import com.keydom.mianren.ih_patient.bean.ManagerUserBean;
import com.keydom.mianren.ih_patient.bean.PayOrderBean;
import com.keydom.mianren.ih_patient.bean.UserInfo;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.constant.Type;
import com.keydom.mianren.ih_patient.net.UploadService;
import com.keydom.mianren.ih_patient.net.UserService;
import com.keydom.mianren.ih_patient.utils.LocalizationUtils;
import com.keydom.mianren.ih_patient.utils.SelectDialogUtils;
import com.keydom.mianren.ih_patient.utils.pay.alipay.Alipay;
import com.keydom.mianren.ih_patient.utils.pay.weixin.WXPay;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.orhanobut.logger.Logger;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 提交问诊控制器
 *
 * @author 顿顿
 */
public class DiagnosesApplyController extends ControllerImpl<DiagnosesApplyView> implements View.OnClickListener, AdapterView.OnItemClickListener {
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.choose_patient_card_tv:
                ChoosePatientActivity.start(getContext(), getView().getType(), true);
                break;
            case R.id.choose_patient_tv:
                if (getView().isHavePatient()) {
                    Intent intent = new Intent(getContext(), AnamnesisActivity.class);
                    intent.putExtra(AnamnesisActivity.MANAGER_USER_BEAN, getView().getPatient());
                    intent.putExtra(AnamnesisActivity.STATUS, 2);
                    intent.putExtra(AnamnesisActivity.ISFROMDIAGNOSEAPPLY, true);
                    ActivityUtils.startActivity(intent);
                } else {
                    ToastUtil.showMessage(getContext(), "没有选中的就诊人");
                }
                break;
            case R.id.commit_tv:
                saveInquisition(getView().getQueryMap(), getView().getPayDesc());
                break;
            case R.id.add_tv:
                //快捷添加病情描述
                PatientMainSuitActivity.start(getContext(), getView().getPatientInputValue());
                break;
            default:
                break;
        }
    }

    /**
     * 查询就诊人列表
     */
    public void getManagerUserList() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).getManagerUserList(Global.getUserId()), new HttpSubscriber<List<ManagerUserBean>>(getContext(), getDisposable(), false, false) {
            @Override
            public void requestComplete(@Nullable List<ManagerUserBean> data) {
                getView().getPatientListSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().getPatientListFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 预付费用户查询接待医生信息
     */
    public void getReceptionDoctor() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).getReceptionDoctor(), new HttpSubscriber<List<DoctorInfo>>(getContext(), getDisposable(), false, false) {
            @Override
            public void requestComplete(@Nullable List<DoctorInfo> data) {
                if (data != null && data.size() > 0) {
                    getView().getReceptionDoctorSuccess(data.get(0));
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 创建图文视频问诊单
     */
    private void saveInquisition(Map<String, Object> map, String payDesc) {
        if (map != null) {
            ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).saveInquisition(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<PayOrderBean>(getContext(), getDisposable(), true, false) {
                @Override
                public void requestComplete(@Nullable PayOrderBean data) {
                    getView().getOrderInfo(data);
                    UserInfo userInfo =
                            (UserInfo) LocalizationUtils.readFileFromLocal(getContext(),
                                    "userInfo");
                    if (userInfo != null && userInfo.getIsVip() == 1) {
                        //预付费用户
                        Map<String, Object> payMap = new HashMap<>();
                        payMap.put("orderId", data.getOrderId());
                        payMap.put("type", 4);
                        inquiryPay(payMap, 4);
                    } else {
                        SelectDialogUtils.showPayDialog(getContext(), data.getFee().setScale(2,
                                BigDecimal.ROUND_HALF_UP) + "", payDesc,
                                type -> {
                                    Map<String, Object> payMap = new HashMap<>();
                                    payMap.put("orderId", data.getOrderId());
                                    if (Type.ALIPAY.equals(type)) {
                                        payMap.put("type", 2);
                                        inquiryPay(payMap, 2);
                                    } else if (Type.WECHATPAY.equals(type)) {
                                        payMap.put("type", 1);
                                        inquiryPay(payMap, 1);
                                    }

                                });
                    }
                }

                @Override
                public boolean requestError(@NotNull ApiException exception, int code,
                                            @NotNull String msg) {
                    getView().applyDiagnosesFailed(msg);
                    return super.requestError(exception, code, msg);
                }
            });
        }
    }

    /**
     * 发起支付
     */
    private void inquiryPay(Map<String, Object> map, int type) {
        if (map != null) {
            ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).inquiryPay(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>(getContext(), getDisposable(), false, false) {
                @Override
                public void requestComplete(@Nullable String data) {
                    if (type == 1) {
                        WXPay.getInstance().doPay(getContext(), data,
                                new WXPay.WXPayResultCallBack() {
                                    @Override
                                    public void onSuccess() {
                                        ToastUtil.showMessage(getContext(), "支付成功");
                                        showApplySuccess();
                                    }

                                    @Override
                                    public void onError(int error_code) {
                                        ToastUtil.showMessage(getContext(), "支付失败-" + error_code);
                                    }

                                    @Override
                                    public void onCancel() {

                                    }
                                });
                    } else if (type == 2) {
                        try {
                            JSONObject object = new JSONObject(data);
                            Logger.e("return_msg:" + object.getString("return_msg"));
                            new Alipay(getContext(), object.getString("return_msg"),
                                    new Alipay.AlipayResultCallBack() {
                                        @Override
                                        public void onSuccess() {
                                            ToastUtil.showMessage(getContext(), "支付成功");
                                            showApplySuccess();
                                        }

                                        @Override
                                        public void onDealing() {

                                        }

                                        @Override
                                        public void onError(int error_code) {
                                            ToastUtil.showMessage(getContext(), "支付失败" + error_code
                                            );
                                        }

                                        @Override
                                        public void onCancel() {

                                        }
                                    }).doPay();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        showApplySuccess();
                    }
                }

                @Override
                public boolean requestError(@NotNull ApiException exception, int code,
                                            @NotNull String msg) {
                    getView().getOrderInfoFailed(msg);
                    return super.requestError(exception, code, msg);
                }
            });
        }
    }

    /**
     * 提交成功后提示
     */
    private void showApplySuccess() {
        //        new GeneralDialog(getContext(),
        //                "问诊订单支付成功，近期请留意订单状态以及接诊医生给你发送的消息"
        //                , () -> MainActivity.start(getContext(), false)).setTitle("提示")
        //                .setCancel(false).setNegativeButtonIsGone(true).setPositiveButton("确认")
        //                .show();
        MainActivity.start(getContext(), false);
        OnlineDiagnonsesOrderActivity.start(getContext(),
                OnlineDiagnonsesOrderActivity.WAITEDIAGNOSES);
    }

    /**
     * 上传图片
     *
     * @param path 文件路径
     */
    public void uploadFile(String path) {
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        if (getView().getLastItemClick(position)) {
            if (getView().getImgSize() < 9) {
                PictureSelector.create((Activity) getContext())
                        .openGallery(PictureMimeType.ofImage())
                        .maxSelectNum(9 - getView().getImgSize())
                        .forResult(PictureConfig.CHOOSE_REQUEST);
            } else {
                ToastUtil.showMessage(mContext, "最多只能选择九张图片");
            }

        } else {
            //            CommonUtils.previewImage(getContext(), getView().getPicUrl(position));
            CommonUtils.previewImageList(getContext(), getView().getPicList(), position, true);
        }
    }

}
