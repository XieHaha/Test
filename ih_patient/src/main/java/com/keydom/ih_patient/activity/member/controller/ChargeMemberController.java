package com.keydom.ih_patient.activity.member.controller;

import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.member.view.ChargeMemberView;
import com.keydom.ih_patient.bean.VIPDetailBean;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.net.VIPCardService;
import com.keydom.ih_patient.view.CommonPayDialog;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ChargeMemberController extends ControllerImpl<ChargeMemberView> implements View.OnClickListener {

    CommonPayDialog mCommonPayDialog;



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.charge_member_commit_charge_tv:
                int price = (int) getView().getSelectedPrice();

                if (price % 1000 != 0) {
                    ToastUtils.showShort("请输入金额为1000的整数倍");
                    return;
                }

                mCommonPayDialog = new CommonPayDialog(getContext(),price, new CommonPayDialog.iOnCommitOnClick() {
                    @Override
                    public void commitPay(String type) {
                        //pay(0, "0", Integer.valueOf(type), 0.01);
                        renewalCard(price);
                    }
                });
                mCommonPayDialog.show();
                break;

        }
    }

    /**
     * 获得会员信息
     */
    public void getMyVipCard() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(VIPCardService.class).getMyVipCard(Global.getUserId()), new HttpSubscriber<VIPDetailBean>(getContext(), getDisposable(), true, false) {

            @Override
            public void requestComplete(@Nullable VIPDetailBean data) {
                getView().getMyVipCardSuccess(data);
            }


            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                ToastUtils.showShort(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 续约
     */
    public void renewalCard(double price) {
        Map<String, Object> map = new HashMap<>();
        map.put("renewalAmount", price);
        map.put("registerUserId", Global.getUserId());
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(VIPCardService.class).renewalCard(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<Object>(getContext(), getDisposable(), true, false) {

            @Override
            public void requestComplete(@Nullable Object data) {
                getView().renewalCardSuccess();
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
/*    public void pay(long addressId, String orderNumber, int type, double totalMoney) {
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
    }*/
}
