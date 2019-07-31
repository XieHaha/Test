package com.keydom.ih_patient.activity.nursing_order.controller;

import android.content.Intent;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.nursing_order.ChargeBackOrderActivity;
import com.keydom.ih_patient.activity.nursing_order.view.WaitForAdmissionView;
import com.keydom.ih_patient.bean.NursingOrderDetailBean;
import com.keydom.ih_patient.constant.Type;
import com.keydom.ih_patient.net.NursingOrderService;
import com.keydom.ih_patient.utils.SelectDialogUtils;
import com.keydom.ih_patient.utils.pay.alipay.Alipay;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * created date: 2018/12/19 on 14:55
 * des:待接诊护理订单控制器
 */
public class WaitForAdmissionController extends ControllerImpl<WaitForAdmissionView> implements View.OnClickListener{
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.launch_info:
                getView().launchInfo();
                break;
            case R.id.charge_back:
                //退单
                Intent i = new Intent(getContext(), ChargeBackOrderActivity.class);
                i.putExtra(ChargeBackOrderActivity.ORDERNUM,getView().getOrder().getOrderNumber());
                i.putExtra(ChargeBackOrderActivity.STATUS,getView().getOrder().getState());
                ActivityUtils.startActivity(i);
                break;
            case R.id.go_pay:
                getOrder(getView().getOrder().getId(),getView().getOrder().getState());
                break;
        }
    }

    /**
     * 获取支付数据
     */
    private void getOrder(long id,int state){
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(NursingOrderService.class).getNursingOrderDetail(id, state), new HttpSubscriber<NursingOrderDetailBean>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable NursingOrderDetailBean data) {
                hideLoading();
                SelectDialogUtils.showPayDialog(getContext(), String.valueOf(data.getNursingServiceOrderDetailBaseDto().getReservationSet()), "", type -> {
                    int payType = 0;
                    if (type.equals(Type.WECHATPAY)) {
                        payType = 1;
                    }
                    if (type.equals(Type.ALIPAY)) {
                        payType = 2;
                    }
                    goPay(String.valueOf(payType), data, data.getReservationSet());
                });
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
     * 发起支付
     */
    private void goPay(String type, NursingOrderDetailBean bean, BigDecimal price){
        Map<String, Object> map = new HashMap<>();
        map.put("orderNumber", bean.getNursingServiceOrderDetailBaseDto().getOrderNumber());
        map.put("type", type);
        map.put("totalMoney", price);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(NursingOrderService.class).nursingOrderPay(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                if (StringUtils.isEmpty(data)) {
                    ToastUtils.showShort("返回支付参数为空");
                    return;
                }
                JSONObject js = JSONObject.parseObject(data);
                if (type.equals("1")) {

                }
                if (type.equals("2")) {
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
     * 获取订单详情
     */
    public void getWaitForAdmission(long id,int state){
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(NursingOrderService.class).getNursingOrderDetail(id, state), new HttpSubscriber<NursingOrderDetailBean>(getContext(),getDisposable(),false) {
            @Override
            public void requestComplete(@Nullable NursingOrderDetailBean data) {
                hideLoading();
                getView().getData(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                ToastUtils.showShort(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
