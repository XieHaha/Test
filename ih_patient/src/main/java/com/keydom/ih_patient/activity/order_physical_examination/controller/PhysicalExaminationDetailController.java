package com.keydom.ih_patient.activity.order_physical_examination.controller;

import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.order_physical_examination.view.PhysicalExaminationDetailView;
import com.keydom.ih_patient.bean.SubscribeExaminationBean;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.net.ReservationService;
import com.keydom.ih_patient.utils.pay.alipay.Alipay;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * 体检详情控制器
 */
public class PhysicalExaminationDetailController extends ControllerImpl<PhysicalExaminationDetailView> implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.exa_buy_tv:
                createPhysicalExamOrder(Global.getSelectedPhysicalExa().getId());
                break;
        }
    }

    /**
     * 创建体检订单
     */
    public void createPhysicalExamOrder(long id){
        showLoading();
        Map<String,Object> map = new HashMap<>();
        map.put("healthCheckCombId",id);
        map.put("registerUserId", Global.getUserId());
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ReservationService.class).findhealthCheckComborelowerOrder(map), new HttpSubscriber<SubscribeExaminationBean>(getContext(),getDisposable(),false) {
            @Override
            public void requestComplete(@Nullable SubscribeExaminationBean data) {
                hideLoading();
                getView().choosePayWay(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 发起支付
     */
    //1为微信支付,2为支付宝支付
    public void goPayExaminationOrder(String type,SubscribeExaminationBean bean) {
        Map<String, Object> map = new HashMap<>();
        map.put("orderNumber", bean.getOrderNumber());
        map.put("type", type);
        map.put("totalMoney",bean.getFee());
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ReservationService.class).findhealthCheckComboreGoPay(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable String data) {
                hideLoading();
                if (StringUtils.isEmpty(data)){
                    ToastUtils.showShort("返回支付参数为空");
                    return;
                }
                JSONObject js = JSONObject.parseObject(data);
                if (type.equals("1")){

                }
                if (type.equals("2")){
                    if (!js.containsKey("return_msg")){
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
                hideLoading();
                ToastUtils.showShort(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
