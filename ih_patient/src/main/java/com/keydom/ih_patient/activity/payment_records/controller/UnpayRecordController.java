package com.keydom.ih_patient.activity.payment_records.controller;

import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_patient.App;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.payment_records.PaymentRecordActivity;
import com.keydom.ih_patient.activity.payment_records.UnpayRecordFragment;
import com.keydom.ih_patient.activity.payment_records.view.UnpayRecordView;
import com.keydom.ih_patient.bean.LocationInfo;
import com.keydom.ih_patient.bean.PayRecordBean;
import com.keydom.ih_patient.bean.PaymentOrderBean;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.net.LocationService;
import com.keydom.ih_patient.net.PayService;
import com.keydom.ih_patient.utils.pay.alipay.Alipay;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 未缴费控制器
 */
public class UnpayRecordController extends ControllerImpl<UnpayRecordView> implements View.OnClickListener {
    /**
     * 获取未缴费记录
     */
    public void getConsultationPayList(SmartRefreshLayout refreshLayout) {
        Map<String, Object> map = new HashMap<>();
        map.put("hospitalId", App.hospitalId);
        map.put("registerUserId", Global.getUserId());
        map.put("state", PaymentRecordActivity.NO_PAY);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PayService.class).getConsultationPayList(map), new HttpSubscriber<List<PayRecordBean>>(getContext(), getDisposable(), false, true) {
            @Override
            public void requestComplete(@Nullable List<PayRecordBean> data) {
                if (data != null) {
                    getView().paymentListCallBack(data);
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                if (refreshLayout.isRefreshing()) {
                    refreshLayout.finishRefresh();
                }
                ToastUtils.showLong(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pay_tv:
//                List<PayRecordBean> selectList = getView().getSelectList();
//                if (selectList.size() > 0) {
//                    String hint = "";
//                    if (selectList.size() > 1) {
//                        String card = selectList.get(0).getEleCardNumber();
//                        int type = selectList.get(0).getType();
//                        for (int i = 0; i < selectList.size(); i++) {
//                            PayRecordBean payRecordBean = selectList.get(i);
//                            if (payRecordBean.getType() == UnpayRecordFragment.CAN_MERGE && !payRecordBean.getEleCardNumber().equals(card)) {
//                                hint = "不能同时为多个就诊人合并缴费";
//                            }
//                            if (payRecordBean.getType() != type) {
//                                hint = "不能同时缴纳诊间费用和预约挂号、在线问诊、护理服务和预约体检项目费用";
//                            }
//                            if (payRecordBean.getType() == UnpayRecordFragment.CANNOT_MERGE) {
//                                hint = "不能同时缴纳预约挂号、在线问诊、护理服务和预约体检项目费用";
//                            }
//                        }
//                    }
//                    if (!StringUtils.isEmpty(hint)) {
//                        ToastUtils.showLong(hint);
//                    } else {
//                        boolean needDispatch = false;
//                        for (int i = 0; i < getView().getSelectList().size(); i++) {
//                            if (getView().getSelectList().get(i).getRecordState() == 8) {
//                                needDispatch = true;
//                            }
//                        }
//                        createOrder(needDispatch, getView().getDocument(), getView().getTotalPay());
//                    }
//                } else {
//                    ToastUtils.showShort("请选择订单");
//                }
// 新需求
//
                if (getView().getSelectList().size() > 0) {
                    boolean needDispatch = false;
                    for (int i = 0; i < getView().getSelectList().size(); i++) {
                        if (getView().getSelectList().get(i).getRecordState() == 8) {
                            needDispatch = true;
                        }
                    }
                    createOrder(needDispatch, getView().getDocument(), getView().getTotalPay());
                } else {
                    ToastUtils.showShort("请选择订单");
                }
                break;
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
     * 发起支付   //支付方式 1微信 2支付宝
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
                JSONObject js = JSONObject.parseObject(data);
                if (type == 1) {

                }
                if (type == 2) {
                    if (!js.containsKey("return_msg")) {
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
