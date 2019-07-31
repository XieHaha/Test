package com.keydom.ih_patient.activity.online_diagnoses_order.controller;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.ih_patient.App;
import com.keydom.ih_patient.activity.online_diagnoses_order.view.OnlineDiagnonsesOrderView;
import com.keydom.ih_patient.bean.ChildOrderBean;
import com.keydom.ih_patient.bean.DiagnosesOrderBean;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.bean.LocationInfo;
import com.keydom.ih_patient.bean.PaymentOrderBean;
import com.keydom.ih_patient.constant.EventType;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.net.LocationService;
import com.keydom.ih_patient.net.PayService;
import com.keydom.ih_patient.net.UserService;
import com.keydom.ih_patient.utils.ToastUtil;
import com.keydom.ih_patient.utils.pay.alipay.Alipay;
import com.keydom.ih_patient.utils.pay.weixin.WXPay;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 问诊订单控制器
 */
public class OnlineDiagnonsesOrderController extends ControllerImpl<OnlineDiagnonsesOrderView> {
    public static final int WAITINGDIAGNONSES = 0;
    public static final int DIAGNONSING = 1;
    public static final int COMPLETEIAGNONSES = 2;

    /**
     * 获取问诊订单列表

     */
    public void getlistPatientInquisition(Map<String, Object> map, int status) {
        map.put("state", status);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).getlistPatientInquisition(map), new HttpSubscriber<List<DiagnosesOrderBean>>(getContext(),getDisposable(),false,false) {
            @Override
            public void requestComplete(@Nullable List<DiagnosesOrderBean> data) {
                getView().getDiagnosesOrderListSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getDiagnosesOrderListFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 问诊订单支付
     */
    public void inquiryPay(Map<String, Object> map, DiagnosesOrderBean item, int type) {
        if (map != null) {
            ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).inquiryPay(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>(getContext(),getDisposable(),false,false) {
                @Override
                public void requestComplete(@Nullable String data) {
                    if(type==2){
                        try {
                            JSONObject object = new JSONObject(data);
                            Logger.e("return_msg:" + object.getString("return_msg"));
                            new Alipay(getContext(), object.getString("return_msg"), new Alipay.AlipayResultCallBack() {
                                @Override
                                public void onSuccess() {
                                    ToastUtil.shortToast(getContext(), "支付成功");
                                    new GeneralDialog(getContext(), "问诊订单支付成功，近期请留意订单状态以及接诊医生给你发送的消息", new GeneralDialog.OnCloseListener() {
                                        @Override
                                        public void onCommit() {
                                            EventBus.getDefault().post(new Event(EventType.REFRESHDIAGNOSESORDER, null));
                                        }
                                    }).setTitle("提示").setCancel(false).setNegativeButtonIsGone(true).setPositiveButton("确认").show();
                                }

                                @Override
                                public void onDealing() {

                                }

                                @Override
                                public void onError(int error_code) {

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
                                new GeneralDialog(getContext(), "问诊订单支付成功，近期请留意订单状态以及接诊医生给你发送的消息", new GeneralDialog.OnCloseListener() {
                                    @Override
                                    public void onCommit() {
                                        EventBus.getDefault().post(new Event(EventType.REFRESHDIAGNOSESORDER, null));
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
     * 问诊订单退诊
     */
    public void returnedInquisition(Map<String, Object> map) {
        if (map != null) {
            ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).returnedInquisition(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<Object>(getContext(),getDisposable(),false,false) {
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
    }

    /**
     * 创建支付订单
     */
    public void createOrder(boolean needDispatch, String document, BigDecimal fee) {
        Map<String, Object> map = new HashMap<>();
        map.put("registerUserId", Global.getUserId());
        map.put("documentNo", document);
        map.put("fee", fee);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PayService.class).generateOrder(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<PaymentOrderBean>(getContext(), getDisposable(), true, true) {
            @Override
            public void requestComplete(@Nullable PaymentOrderBean data) {
                getView().goPay(needDispatch, data.getOrderNumber(), data.getFee());
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                ToastUtils.showShort(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 发起支付
     */
    public void pay(long addressId, String orderNumber, int type, double totalMoney) {
        Map<String, Object> map = new HashMap<>();
        map.put("addressId", addressId);
        map.put("orderNumber", orderNumber);
        map.put("type", type);
        map.put("totalMoney", totalMoney);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PayService.class).patientPayByOrderNumber(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>(getContext(), getDisposable(), true, true) {
            @Override
            public void requestComplete(@Nullable String data) {
                hideLoading();
                if (StringUtils.isEmpty(data)) {
                    ToastUtils.showShort("返回支付参数为空");
                    return;
                }

                if (type == 1) {
                    WXPay.getInstance().doPay(getContext(), data, new WXPay.WXPayResultCallBack() {
                        @Override
                        public void onSuccess() {
                            getView().paySuccess();
                            EventBus.getDefault().post(new Event(EventType.REFRESHDIAGNOSESORDER, null));
                            ToastUtils.showShort("支付成功");
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
                if (type == 2) {
                    com.alibaba.fastjson.JSONObject js = com.alibaba.fastjson.JSONObject.parseObject(data);
                    if (!js.containsKey("return_msg")) {
                        return;
                    }
                    new Alipay(getContext(), js.getString("return_msg"), new Alipay.AlipayResultCallBack() {
                        @Override
                        public void onSuccess() {
                            getView().paySuccess();
                            EventBus.getDefault().post(new Event(EventType.REFRESHDIAGNOSESORDER, null));
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
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                ToastUtils.showShort(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 获取子订单群和判断子订单是否包含处方单
     */
    public void getChildOrderBean(long inquiryId){
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).getUnPaySubOrderInfo(inquiryId), new HttpSubscriber<ChildOrderBean>(getContext(),getDisposable(),false,false) {
            @Override
            public void requestComplete(@Nullable ChildOrderBean data) {
                boolean isNeedAddress=false;
                if(data.getIsPrescription()==1)
                    isNeedAddress=true;
                else
                    isNeedAddress=false;
                createOrder(isNeedAddress,data.getOrderNumber(),new BigDecimal(data.getFee()));
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                ToastUtils.showShort(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 获取医院配送费
     */
    public void getDistributionFee(long addressId){
        Map<String, Object> map = new HashMap<>();
        map.put("addressId", addressId);
        map.put("hospitald",App.hospitalId);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PayService.class).getDeliveryCost(map), new HttpSubscriber<String>(getContext(),getDisposable(),true,true) {
            @Override
            public void requestComplete(@Nullable String data) {
                hideLoading();
                getView().getDistributionFee(data);
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
            public void requestComplete(@Nullable List<LocationInfo> data) {
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
