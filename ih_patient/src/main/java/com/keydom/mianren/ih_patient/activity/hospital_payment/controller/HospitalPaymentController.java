package com.keydom.mianren.ih_patient.activity.hospital_payment.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.hospital_payment.view.HospitalPaymentView;
import com.keydom.mianren.ih_patient.activity.online_diagnoses_order.ChoosePatientActivity;
import com.keydom.mianren.ih_patient.bean.HospitalPaymentBean;
import com.keydom.mianren.ih_patient.bean.HospitalRecordRootBean;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.constant.Type;
import com.keydom.mianren.ih_patient.net.CardService;
import com.keydom.mianren.ih_patient.net.HospitalPaymentService;
import com.keydom.mianren.ih_patient.net.UserService;
import com.keydom.mianren.ih_patient.utils.SelectDialogUtils;
import com.keydom.mianren.ih_patient.utils.pay.alipay.Alipay;
import com.keydom.mianren.ih_patient.utils.pay.weixin.WXPay;
import com.orhanobut.logger.Logger;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 住院预缴金
 *
 * @author 顿顿
 */
public class HospitalPaymentController extends ControllerImpl<HospitalPaymentView> implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hospital_payment_patient_layout:
                ChoosePatientActivity.start(getContext(), -1, false);
                break;
            case R.id.tv_sure:
                createInHospitalOrder();
                break;
            default:
                break;
        }
    }

    /**
     * 查询所有就诊卡
     */
    public void queryAllCard() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uuid", Global.getUserId());
        map.put("hospital", App.hospitalId);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(CardService.class).getCardList(map), new HttpSubscriber<List<MedicalCardInfo>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable List<MedicalCardInfo> data) {
                getView().getAllCardSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 获取患者住院信息和订单信息
     */
    public void getHospitalPayment() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(HospitalPaymentService.class).getInHospitalRecord(getView().getMedicalCardNumber()), new HttpSubscriber<HospitalRecordRootBean>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable HospitalRecordRootBean data) {
                getView().fillHospitalPaymentData(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                ToastUtil.showMessage(getContext(), msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 创建住院预缴订单
     */
    public void createInHospitalOrder() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("eleCardNumber", getView().getMedicalCardNumber());
        map.put("fee", getView().getFee());
        map.put("inHospitalNo", getView().getInHospitalNo());
        map.put("patientId", getView().getMedicalCardInfo().getId());
        map.put("patientName", getView().getMedicalCardInfo().getName());
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(HospitalPaymentService.class).createInHospitalOrder(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<HospitalPaymentBean>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable HospitalPaymentBean data) {
                if (Global.isMember()) {
                    SelectDialogUtils.showPrePaidDialog(getContext(), false, data.getFee()
                                    .setScale(2, BigDecimal.ROUND_HALF_UP) + "", "",
                            type -> {
                                //预付费用户
                                Map<String, Object> payMap = new HashMap<>();
                                payMap.put("orderId", data.getId());
                                payMap.put("type", 4);
                                inquiryPay(payMap, 4);
                            });
                } else {
                    SelectDialogUtils.showPayDialog(getContext(), data.getFee()
                                    .setScale(2, BigDecimal.ROUND_HALF_UP) + "", "住院预缴",
                            type -> {
                                Map<String, Object> payMap = new HashMap<>();
                                payMap.put("orderId", data.getId());
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
                ToastUtil.showMessage(getContext(), msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 发起支付
     */
    private void inquiryPay(Map<String, Object> map, int type) {
        if (map != null) {
            ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).inquiryPay(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>(getContext(), getDisposable(), true, false) {
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
                    ToastUtil.showMessage(getContext(), msg);
                    return super.requestError(exception, code, msg);
                }
            });
        }
    }

    /**
     * 提交成功后提示
     */
    private void showApplySuccess() {
        new GeneralDialog(getContext(), "支付成功", () -> getView().createOrderSuccess()).setTitle("提示")
                .setCancel(false).setNegativeButtonIsGone(true).setPositiveButton("确认")
                .show();
    }
}
