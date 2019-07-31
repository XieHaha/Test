package com.keydom.ih_patient.activity.online_diagnoses_order.controller;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.blankj.utilcode.util.ActivityUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.diagnose_user_manager.AnamnesisActivity;
import com.keydom.ih_patient.activity.diagnose_user_manager.ManageUserActivity;
import com.keydom.ih_patient.activity.index_main.MainActivity;
import com.keydom.ih_patient.activity.nursing_service.NursingChoosePatientActivity;
import com.keydom.ih_patient.activity.online_diagnoses_order.ChoosePatientActivity;
import com.keydom.ih_patient.activity.online_diagnoses_order.view.DiagnosesApplyView;
import com.keydom.ih_patient.bean.ManagerUserBean;
import com.keydom.ih_patient.bean.PayOrderBean;
import com.keydom.ih_patient.callback.GeneralCallback;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.constant.Type;
import com.keydom.ih_patient.net.UploadService;
import com.keydom.ih_patient.net.UserService;
import com.keydom.ih_patient.utils.SelectDialogUtils;
import com.keydom.ih_patient.utils.ToastUtil;
import com.keydom.ih_patient.utils.pay.alipay.Alipay;
import com.keydom.ih_patient.utils.pay.weixin.WXPay;
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
 */
public class DiagnosesApplyController extends ControllerImpl<DiagnosesApplyView> implements View.OnClickListener, AdapterView.OnItemClickListener {
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.choose_patient_card_tv:
                ChoosePatientActivity.start(getContext(),getView().getType());
                break;
            case R.id.choose_patient_tv:
                if(getView().isHavePatient()){
                    Intent intent=new Intent(getContext(),AnamnesisActivity.class);
                    intent.putExtra(AnamnesisActivity.MANAGER_USER_BEAN, getView().getPatient());
                    intent.putExtra(AnamnesisActivity.STATUS, 2);
                    intent.putExtra(AnamnesisActivity.ISFROMDIAGNOSEAPPLY,true);
                    ActivityUtils.startActivity(intent);
                }
                else
                    ToastUtil.shortToast(getContext(),"没有选中的就诊人");
                break;

            case R.id.conmit_tv:
                saveInquisition(getView().getQueryMap(),getView().getPayDesc());
                break;
        }
    }

    /**
     * 查询就诊人列表
     */
    public void getManagerUserList(){
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).getManagerUserList(Global.getUserId()), new HttpSubscriber<List<ManagerUserBean>>(getContext(),getDisposable(),false,false) {
            @Override
            public void requestComplete(@Nullable List<ManagerUserBean> data) {
                getView().getPatientListSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getPatientListFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 创建图文视频问诊单
     */
    public void saveInquisition(Map<String,Object> map,String payDesc){
        if(map!=null){
            ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).saveInquisition(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<PayOrderBean>(getContext(),getDisposable(),false,false) {
                @Override
                public void requestComplete(@Nullable PayOrderBean data) {
                    getView().getOrderInfo(data);
                    SelectDialogUtils.showPayDialog(getContext(), data.getFee().setScale(2,BigDecimal.ROUND_HALF_UP)+"", payDesc, new GeneralCallback.SelectPayMentListener() {
                        @Override
                        public void getSelectPayMent(String type) {
                            Map<String,Object> payMap=new HashMap<>();
                            payMap.put("orderId",data.getOrderId());
                            if(Type.ALIPAY.equals(type)){
                                payMap.put("type",2);
                                inquiryPay(payMap,2);
                            }else if(Type.WECHATPAY.equals(type)){
                                payMap.put("type",1);
                                inquiryPay(payMap,1);
                            }

                        }
                    });
                }

                @Override
                public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                    getView().applyDiagnosesFailed(msg);
                    return super.requestError(exception, code, msg);
                }
            }) ;
        }
    }

    /**
     * 发起支付
     */
    public void inquiryPay(Map<String,Object> map,int type){
        if(map!=null){
            ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).inquiryPay(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>(getContext(),getDisposable(),false,false) {
                @Override
                public void requestComplete(@Nullable String data) {
                    if(type==1){
                        WXPay.getInstance().doPay(getContext(), data, new WXPay.WXPayResultCallBack() {
                            @Override
                            public void onSuccess() {
                                ToastUtil.shortToast(getContext(),"支付成功");
                                new GeneralDialog(getContext(), "问诊订单支付成功，近期请留意订单状态以及接诊医生给你发送的消息", new GeneralDialog.OnCloseListener() {
                                    @Override
                                    public void onCommit() {
                                        MainActivity.start(getContext(),false);
                                    }
                                }).setTitle("提示").setCancel(false).setNegativeButtonIsGone(true).setPositiveButton("确认").show();
                            }

                            @Override
                            public void onError(int error_code) {
                                ToastUtil.shortToast(getContext(),"支付失败"+error_code
                                );
                            }

                            @Override
                            public void onCancel() {

                            }
                        });
                    }else {
                        try {
                            JSONObject object = new JSONObject(data);
                            Logger.e("return_msg:"+ object.getString("return_msg"));
                            new Alipay(getContext(), object.getString("return_msg"), new Alipay.AlipayResultCallBack() {
                                @Override
                                public void onSuccess() {
                                    ToastUtil.shortToast(getContext(),"支付成功");
                                    new GeneralDialog(getContext(), "问诊订单支付成功，近期请留意订单状态以及接诊医生给你发送的消息", new GeneralDialog.OnCloseListener() {
                                        @Override
                                        public void onCommit() {
                                            MainActivity.start(getContext(),false);
                                        }
                                    }).setTitle("提示").setCancel(false).setNegativeButtonIsGone(true).setPositiveButton("确认").show();
                                }

                                @Override
                                public void onDealing() {

                                }

                                @Override
                                public void onError(int error_code) {
                                    ToastUtil.shortToast(getContext(),"支付失败"+error_code
                                    );
                                }

                                @Override
                                public void onCancel() {

                                }
                            }).doPay();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }

                @Override
                public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                    getView().getOrderInfoFailed(msg);
                    return super.requestError(exception, code, msg);
                }
            }) ;
        }
    }
    /**
     * 上传图片
     * @param path  文件路径
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
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
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
                ToastUtil.shortToast(mContext, "最多只能选择九张图片");
            }

        }else
            CommonUtils.previewImage(getContext(), getView().getPicUrl(position));
    }
}
