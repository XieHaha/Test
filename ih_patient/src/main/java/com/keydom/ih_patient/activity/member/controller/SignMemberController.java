package com.keydom.ih_patient.activity.member.controller;

import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.member.view.SignMemberView;
import com.keydom.ih_patient.net.PayService;
import com.keydom.ih_patient.utils.pay.alipay.Alipay;
import com.keydom.ih_patient.utils.pay.weixin.WXPay;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class SignMemberController extends ControllerImpl<SignMemberView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pay_commit_tv:
                if(TextUtils.isEmpty(getView().getName())){
                    ToastUtil.showMessage(getContext(),"姓名不能空");
                    return;
                }

                if(TextUtils.isEmpty(getView().getID())){
                    ToastUtil.showMessage(getContext(),"身份证号不能为空");
                    return;
                }

                //pay(0, "", getView().getPayType(), 20000);
                break;
        }
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
                if (type == 2) {
                    JSONObject js = JSONObject.parseObject(data);
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
                } else if (type == 1) {
                    WXPay.getInstance().doPay(getContext(), data, new WXPay.WXPayResultCallBack() {
                        @Override
                        public void onSuccess() {
                            getView().paySuccess();
                            ToastUtils.showShort("支付成功");
                        }

                        @Override
                        public void onError(int error_code) {
                            ToastUtil.showMessage(getContext(), "支付失败" + error_code
                            );
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                ToastUtils.showShort(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
