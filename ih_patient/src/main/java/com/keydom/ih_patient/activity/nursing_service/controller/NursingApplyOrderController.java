package com.keydom.ih_patient.activity.nursing_service.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.index_main.MainActivity;
import com.keydom.ih_patient.activity.nursing_service.view.NursingApplyOrderView;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.callback.GeneralCallback;
import com.keydom.ih_patient.constant.EventType;
import com.keydom.ih_patient.constant.Type;
import com.keydom.ih_patient.net.NursingService;
import com.keydom.ih_patient.utils.SelectDialogUtils;
import com.keydom.ih_patient.utils.ToastUtil;
import com.keydom.ih_patient.utils.pay.alipay.Alipay;
import com.keydom.ih_patient.utils.pay.weixin.WXPay;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * created date: 2018/12/25 on 15:52
 * des:护理服务订单提交控制器
 */
public class NursingApplyOrderController extends ControllerImpl<NursingApplyOrderView> implements View.OnClickListener{
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pay:
                SelectDialogUtils.showPayDialog(getContext(), getView().getAllFee()+"", "", new GeneralCallback.SelectPayMentListener() {
                    @Override
                    public void getSelectPayMent(String type) {
                        if(Type.ALIPAY.equals(type)){
                            getPayOrder("2");
                        }else if(Type.WECHATPAY.equals(type)){
                            getPayOrder("1");
                        }
                    }
                });
                break;
        }
    }

    /**
     * 创建支付订单
     */
    public void getPayOrder(String type){
        //showLoading();
        Map<String,Object> map=new HashMap<>();
        map.put("type",type);

        map.put("orderNumber",getView().getOrderNum());
        map.put("totalMoney",getView().getAllFee()+"");

        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(NursingService.class).patientPayByOrderNumber(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>(getContext(),getDisposable(),false,false) {
            @Override
            public void requestComplete(@Nullable String data) {
                //hideLoading();
                if(type.equals("2")){
                    try {
                        JSONObject object = new JSONObject(data);
                        Logger.e("return_msg:"+ object.getString("return_msg"));
                        new Alipay(getContext(), object.getString("return_msg"), new Alipay.AlipayResultCallBack() {
                            @Override
                            public void onSuccess() {
                                ToastUtil.shortToast(getContext(),"支付成功");
                                EventBus.getDefault().post(new Event(EventType.CREATE_NURSING_SUCCESS,null));
                                new GeneralDialog(getContext(), "护理服务预约成功，近期请留意订单状态", new GeneralDialog.OnCloseListener() {
                                    @Override
                                    public void onCommit() {

                                        MainActivity.start(getContext(),false);
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
                }else if(type.equals("1")){
                    WXPay.getInstance().doPay(getContext(), data, new WXPay.WXPayResultCallBack() {
                        @Override
                        public void onSuccess() {
                            ToastUtil.shortToast(getContext(),"支付成功");
                            EventBus.getDefault().post(new Event(EventType.CREATE_NURSING_SUCCESS,null));
                            new GeneralDialog(getContext(), "护理服务预约成功，近期请留意订单状态", new GeneralDialog.OnCloseListener() {
                                @Override
                                public void onCommit() {

                                    MainActivity.start(getContext(),false);
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
