package com.keydom.mianren.ih_patient.activity.order_doctor_register.controller;

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
import com.keydom.mianren.ih_patient.activity.card_operate.CardoperateActivity;
import com.keydom.mianren.ih_patient.activity.order_doctor_register.view.ChooseMedicalCardView;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;
import com.keydom.mianren.ih_patient.bean.PaymentOrderBean;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.net.CardService;
import com.keydom.mianren.ih_patient.net.OrderService;
import com.keydom.mianren.ih_patient.utils.pay.alipay.Alipay;
import com.keydom.mianren.ih_patient.utils.pay.weixin.WXPay;
import com.orhanobut.logger.Logger;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 选择就诊卡控制器
 */
public class ChooseMedicalCardController extends ControllerImpl<ChooseMedicalCardView> implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.commit_tv:
                //getView().showPayDialog();
                userOrderNumber(getView().getCreateOrderNumQueryMap());
                break;
            case R.id.jump_to_card_operate_tv:
                CardoperateActivity.start(getContext());
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
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(CardService.class).getCardList(map), new HttpSubscriber<List<MedicalCardInfo>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable List<MedicalCardInfo> data) {
                hideLoading();
                getView().getAllCardSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().getAllCardFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 生成挂号订单
     */
    public void userOrderNumber(Map<String, Object> map) {
        if (map != null) {
            showLoading();
            ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(OrderService.class).userOrderNumber(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<PaymentOrderBean>(getContext(), getDisposable(), false) {
                @Override
                public void requestComplete(@Nullable PaymentOrderBean data) {
                    hideLoading();
                    getView().createOrderNumSuccess(data);
                }

                @Override
                public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                    hideLoading();
                    getView().createOrderNumFailed(msg);
                    return super.requestError(exception, code, msg);
                }
            });
        }

    }

    /**
     * 挂号订单支付
     */
    public void hospitalFeeByOrderNumber(Map<String, Object> map, int type) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(OrderService.class).hospitalFeeByOrderNumber(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>(getContext(), getDisposable(), false, false) {
            @Override
            public void requestComplete(@Nullable String data) {
                if(type==2){
                    try {
                        JSONObject object = new JSONObject(data);
                        Logger.e("return_msg:" + object.getString("return_msg"));
                        new Alipay(getContext(), object.getString("return_msg"), new Alipay.AlipayResultCallBack() {
                            @Override
                            public void onSuccess() {
                                ToastUtil.showMessage(getContext(), "支付成功");
                                new GeneralDialog(getContext(), "挂号成功，可在挂号订单中进行查看", new GeneralDialog.OnCloseListener() {
                                    @Override
                                    public void onCommit() {
                                        getView().completeOrder();
                                    }
                                }).setTitle("提示").setCancel(false).setNegativeButtonIsGone(true).setPositiveButton("确认").show();
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
                }else if(type==1){
                    WXPay.getInstance().doPay(getContext(), data, new WXPay.WXPayResultCallBack() {
                        @Override
                        public void onSuccess() {
                            ToastUtil.showMessage(getContext(), "支付成功");
                            new GeneralDialog(getContext(), "挂号成功，可在挂号订单中进行查看", new GeneralDialog.OnCloseListener() {
                                @Override
                                public void onCommit() {
                                    getView().completeOrder();
                                }
                            }).setTitle("提示").setCancel(false).setNegativeButtonIsGone(true).setPositiveButton("确认").show();
                        }

                        @Override
                        public void onError(int error_code) {
                            ToastUtil.showMessage(getContext(),"支付失败"+error_code
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
                ToastUtil.showMessage(getContext(), "拉取订单失败，请重试");
                return super.requestError(exception, code, msg);
            }
        });
    }

}
