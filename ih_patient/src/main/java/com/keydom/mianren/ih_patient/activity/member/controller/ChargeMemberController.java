package com.keydom.mianren.ih_patient.activity.member.controller;

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
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.member.view.ChargeMemberView;
import com.keydom.mianren.ih_patient.bean.VIPDetailBean;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.net.VIPCardService;
import com.keydom.mianren.ih_patient.utils.pay.alipay.Alipay;
import com.keydom.mianren.ih_patient.utils.pay.weixin.WXPay;
import com.keydom.mianren.ih_patient.view.CommonPayDialog;

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

                if (0 == price) {
                    ToastUtils.showShort("请选择或输入金额");
                    return;
                }

                mCommonPayDialog = new CommonPayDialog(getContext(), price, new CommonPayDialog.iOnCommitOnClick() {
                    @Override
                    public void commitPay(int type) {
                        //renewalCard(price,type);
                        renewalCard(0.01,type);
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
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(VIPCardService.class).getMyVipCard(Global.getUserId()), new HttpSubscriber<VIPDetailBean>(getContext(), getDisposable(), false, false) {

            @Override
            public void requestComplete(@Nullable VIPDetailBean data) {
                getView().getMyVipCardSuccess(data);
            }


            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                ToastUtils.showShort(msg);
                getView().getMyVipCardFail(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 续约
     */
    public void renewalCard(double price, int payType) {
        Map<String, Object> map = new HashMap<>();
        map.put("renewalAmount", price);
        map.put("registerUserId", Global.getUserId());
        map.put("payType", payType);
        map.put("hospitalId", App.hospitalId);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(VIPCardService.class).renewalCard(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>(getContext(), getDisposable(), true, false) {

            @Override
            public void requestComplete(@Nullable String data) {
                if (StringUtils.isEmpty(data)) {
                    ToastUtils.showShort("返回支付参数为空");
                    return;
                }
                if (payType == Const.ALI_PAY) {
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
                } else if (payType == Const.WECHAT_PAY) {
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
