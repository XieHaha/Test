package com.keydom.mianren.ih_patient.activity.member.controller;

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
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.member.view.SignMemberView;
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

public class SignMemberController extends ControllerImpl<SignMemberView> implements View.OnClickListener {

    CommonPayDialog mCommonPayDialog;


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.pay_commit_tv) {
            if (true) {
                ToastUtil.showMessage(getContext(), "暂不支持线上办理，请联系医务人员");
                return;
            }
            if (null == App.userInfo || !App.userInfo.isCertification()) {
                ToastUtil.showMessage(getContext(), "还未实名认证，请实名认证再开通相关服务");
                return;
            }


            if (TextUtils.isEmpty(getView().getName())) {
                ToastUtil.showMessage(getContext(), "姓名不能空");
                return;
            }

            if (TextUtils.isEmpty(getView().getID())) {
                ToastUtil.showMessage(getContext(), "身份证号不能为空");
                return;
            }

            if (!getView().isCheckAgreement()) {
                ToastUtil.showMessage(getContext(), "请阅读并同意相关会员服务协议");
                return;
            }

            if (mCommonPayDialog.isShowing()) {
                mCommonPayDialog.dismiss();
            }
            mCommonPayDialog.show();
        }
    }


    public void init() {
        mCommonPayDialog = new CommonPayDialog(getContext(), getView().getVipCardInfo().getPrice(), new CommonPayDialog.iOnCommitOnClick() {
            @Override
            public void commitPay(int type) {
                addCardForMobile(getView().getName(), getView().getID(),type);
            }
        });
    }


    /**
     * 办理会员卡
     */
    public void addCardForMobile(String cardHolder, String idCard, int payType) {
        Map<String, Object> map = new HashMap<>();
        map.put("cardHolder", cardHolder);
        map.put("hospitalId", App.hospitalId);
        map.put("idCard", idCard);
        map.put("cardTypeId", SharePreferenceManager.getVIPCardTypeID());
        map.put("registerUserId", Global.getUserId());
        map.put("payType", payType);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(VIPCardService.class).addCardForMobile(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>(getContext(), getDisposable(), true, false) {

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
