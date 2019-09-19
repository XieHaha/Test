package com.keydom.ih_patient.activity.im;

import android.support.annotation.Nullable;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.ih_patient.App;
import com.keydom.ih_patient.bean.InquiryBean;
import com.keydom.ih_patient.bean.LocationInfo;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.net.LocationService;
import com.keydom.ih_patient.net.PayService;
import com.keydom.ih_patient.net.UserService;
import com.keydom.ih_patient.utils.CommUtil;
import com.keydom.ih_patient.utils.ToastUtil;
import com.keydom.ih_patient.utils.pay.alipay.Alipay;
import com.keydom.ih_patient.utils.pay.weixin.WXPay;
import com.orhanobut.logger.Logger;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 问诊控制器
 */
public class ConversationController extends ControllerImpl<ConversationView> {

    /**
     * 获取问诊状态
     */
    public void getInquiryStatus() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(InquiryService.class).getOrderDetails(getView().getUserId(), "1", getView().getDoctorCode()),
                new HttpSubscriber<InquiryBean>(getContext(), getDisposable(), true, false) {
                    @Override
                    public void requestComplete(@Nullable InquiryBean data) {
                        getView().loadSuccess(data);
                    }
                });
    }


    /**
     * 患者结束问诊
     */
    public void patientEndInquisition() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(InquiryService.class).patientEndInquisition(getView().getId(), getView().getEndType()),
                new HttpSubscriber<Object>(getContext(), getDisposable(), true, false) {
                    @Override
                    public void requestComplete(@Nullable Object data) {
                        getView().handleApplyEndSuccess();
                    }
                });
    }

    /**
     * 患者关闭问诊
     */
    public void patientFinishInquisition() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(InquiryService.class).patientFinishInquisition(getView().getId()),
                new HttpSubscriber<Object>(getContext(), getDisposable(), true, false) {
                    @Override
                    public void requestComplete(@Nullable Object data) {
                        getView().endSuccess();
                    }
                });
    }

    /**
     * 获取用户状态
     */
    public void userOperateReferral() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", getView().getReferralId());
        map.put("state", getView().getReferralState());
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(InquiryService.class).userOperateReferral(HttpService.INSTANCE.object2Body(map)),
                new HttpSubscriber<Object>(getContext(), getDisposable(), true, false) {
                    @Override
                    public void requestComplete(@Nullable Object data) {
                        getView().operateReferralSuccess(String.valueOf(data));
                    }

                    @Override
                    public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                        ToastUtil.shortToast(getContext(), msg);
                        return true;
                    }
                });
    }

    /**
     * 发起支付
     */
    public void inquiryPay(Map<String, Object> map, int type) {
        if (map != null) {
            ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).inquiryPay(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>() {
                @Override
                public void requestComplete(@org.jetbrains.annotations.Nullable String data) {
                    if(type==2){
                        try {
                            JSONObject object = new JSONObject(data);
                            Logger.e("return_msg:" + object.getString("return_msg"));
                            new Alipay(getContext(), object.getString("return_msg"), new Alipay.AlipayResultCallBack() {
                                @Override
                                public void onSuccess() {
                                    ToastUtil.shortToast(getContext(), "支付成功");
                                    new GeneralDialog(getContext(), "支付成功", new GeneralDialog.OnCloseListener() {
                                        @Override
                                        public void onCommit() {
                                            getView().paySuccess();
                                        }
                                    }).setTitle("提示").setCancel(false).setNegativeButtonIsGone(true).setPositiveButton("确认").show();
                                }

                                @Override
                                public void onDealing() {

                                }

                                @Override
                                public void onError(int error_code) {
                                    ToastUtil.shortToast(getContext(), "支付失败" + error_code);
                                }

                                @Override
                                public void onCancel() {

                                }
                            }).doPay();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else if(type==1){
                        WXPay.getInstance().doPay(getContext(), data, new WXPay.WXPayResultCallBack() {
                            @Override
                            public void onSuccess() {
                                ToastUtil.shortToast(getContext(), "支付成功");
                                new GeneralDialog(getContext(), "支付成功", new GeneralDialog.OnCloseListener() {
                                    @Override
                                    public void onCommit() {
                                        getView().paySuccess();
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
                    }

                }
            });
        }
    }

    /**
     * 判断是否支付
     */
    public void isPay() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(InquiryService.class).isPay(getView().getIsPayId() + ""),
                new HttpSubscriber<Integer>(getContext(), getDisposable(), true, false) {
                    @Override
                    public void requestComplete(@Nullable Integer data) {
                        if(0 == data || 3 == data){ // 0和3是未支付
                            getView().payType(false);
                        }else{
                            getView().payType(true);
                        }

                    }
                });
    }

    /**
     * 退诊
     */
    public void returnedInquisition() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", getView().getId());
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class)
                        .returnedInquisition(HttpService.INSTANCE.object2Body(map)),
                new HttpSubscriber<Object>(getContext(), getDisposable(), true, false) {
                    @Override
                    public void requestComplete(@Nullable Object data) {
                        getView().returnBackSuccess();
                    }

                    @Override
                    public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                        getView().returnBackFailed(msg);
                        return super.requestError(exception, code, msg);
                    }
                });
    }


    /**
     * 确认更换医生
     */
    public void confirmChangeDoctor() {
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", getView().getId());
        map.put("type", getView().getChangeDoctor());
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(InquiryService.class).confirmChangeDoctor(map), new HttpSubscriber<Object>(getContext(), getDisposable(), true, false) {
            @Override
            public void requestComplete(@Nullable Object data) {
                getView().changeDoctorSuccess();
            }
        });
    }

    /**
     * 发起支付
     */
    //支付方式 1微信 2支付宝
    public void pay(long addressId, String orderNumber, int type, double totalMoney) {
        Map<String, Object> map = new HashMap<>();
        map.put("addressId", addressId);
        map.put("orderNumber", orderNumber);
        map.put("type", type);
        map.put("totalMoney", totalMoney);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PayService.class).patientPayByOrderNumber(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>(getContext(), getDisposable(), true, true) {
            @Override
            public void requestComplete(@Nullable String data) {
                if (StringUtils.isEmpty(data)) {
                    ToastUtils.showShort("返回支付参数为空");
                    return;
                }
                try {
                    JSONObject js = new JSONObject(data);

                    if (type == 1) {
                        WXPay.getInstance().doPay(getContext(), data, new WXPay.WXPayResultCallBack() {
                            @Override
                            public void onSuccess() {
                                getView().paySuccess();
                                ToastUtils.showShort("支付成功");
                            }

                            @Override
                            public void onError(int error_code) {
                                ToastUtil.shortToast(getContext(), "支付失败" + error_code
                                );
                            }

                            @Override
                            public void onCancel() {

                            }
                        });
                    }
                    if (type == 2) {
                        if (!js.has("return_msg")) {
                            return;
                        }
                        new Alipay(getContext(), js.getString("return_msg"), new Alipay.AlipayResultCallBack() {
                            @Override
                            public void onSuccess() {
                                getView().paySuccess();
                                ToastUtils.showShort("支付成功");
                            }

                            @Override
                            public void onDealing() {
                                ToastUtils.showShort("等待支付结果确认");
                            }

                            @Override
                            public void onError(int error_code) {
                                ToastUtils.showShort("支付失败");
                            }

                            @Override
                            public void onCancel() {
                                ToastUtils.showShort("取消支付");
                            }
                        }).doPay();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                ToastUtils.showShort(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 获取配送费
     */
    public void getDistributionFee(long addressId) {
        Map<String, Object> map = new HashMap<>();
        map.put("addressId", addressId);
        map.put("hospitald", App.hospitalId);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PayService.class).getDeliveryCost(map), new HttpSubscriber<String>(getContext(), getDisposable(), true, true) {
            @Override
            public void requestComplete(@Nullable String data) {
                hideLoading();
                if(!CommUtil.isEmpty(data)){
                    getView().getDistributionFee(data);
                }

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
     * 获取地址列表
     */
    public void getLocationList() {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", Global.getUserId());
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(LocationService.class).getAddressList(map), new HttpSubscriber<List<LocationInfo>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@org.jetbrains.annotations.Nullable List<LocationInfo> data) {
                hideLoading();
                getView().getLocationList(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                return super.requestError(exception, code, msg);
            }
        });
    }
}
