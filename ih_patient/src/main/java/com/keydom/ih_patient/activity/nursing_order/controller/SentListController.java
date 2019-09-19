package com.keydom.ih_patient.activity.nursing_order.controller;

import android.content.Intent;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.nursing_order.ChargeBackOrderActivity;
import com.keydom.ih_patient.activity.nursing_order.view.SentListView;
import com.keydom.ih_patient.activity.order_evaluate.OrderEvaluateActivity;
import com.keydom.ih_patient.bean.NursingOrderBean;
import com.keydom.ih_patient.bean.NursingOrderDetailBean;
import com.keydom.ih_patient.bean.NursingOrderService2Bean;
import com.keydom.ih_patient.bean.NursingOrderService3Bean;
import com.keydom.ih_patient.bean.NursingOrderServiceBean;
import com.keydom.ih_patient.bean.NursingOrderServiceHead;
import com.keydom.ih_patient.bean.NursingOrderServiceItem2Bean;
import com.keydom.ih_patient.bean.NursingOrderServiceItem3Bean;
import com.keydom.ih_patient.bean.NursingOrderServiceItemBean;
import com.keydom.ih_patient.bean.OrderEvaluateBean;
import com.keydom.ih_patient.callback.GeneralCallback;
import com.keydom.ih_patient.constant.Type;
import com.keydom.ih_patient.net.NursingOrderService;
import com.keydom.ih_patient.utils.SelectDialogUtils;
import com.keydom.ih_patient.utils.ToastUtil;
import com.keydom.ih_patient.utils.pay.alipay.Alipay;
import com.keydom.ih_patient.utils.pay.weixin.WXPay;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created date: 2018/12/20 on 10:55
 * des:护理订单详情控制器
 */
public class SentListController extends ControllerImpl<SentListView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.launch_info:
                getView().launchInfo();
                break;
            case R.id.go_pay:
                getDataList(getView().getId(), getView().getState(),true);

                break;
            case R.id.evaluate:
                if (getView().getOrderDetail().getState() == NursingOrderDetailBean.STATE6) {
                    Intent i = new Intent(getContext(), ChargeBackOrderActivity.class);
                    i.putExtra(ChargeBackOrderActivity.ORDERNUM,getView().getOrderDetail().getOrderNumber());
                    i.putExtra(ChargeBackOrderActivity.STATUS,getView().getOrderDetail().getState());
                    ActivityUtils.startActivity(i);
                } else {
                    OrderEvaluateActivity.start(getContext(), OrderEvaluateBean.nursing_order_title, Type.NURSING_ORDER_EVALUATE, getView().getOrderDetail());
                }
                break;
        }
    }

    /**
     * 获取订单详情数据
     */
    public void getDataList(long id, int state,boolean isPay) {
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(NursingOrderService.class).getNursingOrderDetail(id, state), new HttpSubscriber<NursingOrderDetailBean>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable NursingOrderDetailBean data) {
                hideLoading();
                getView().getBasicData(data,isPay);
                List<MultiItemEntity> multiItemData = getMultiItemData(data);
                getView().getListData(multiItemData);
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
     * 展示支付弹框
     */
    public void showPayDialog(NursingOrderBean bean) {
        SelectDialogUtils.showPayDialog(getContext(), String.valueOf(bean.getPrice()), "", new GeneralCallback.SelectPayMentListener() {
            @Override
            public void getSelectPayMent(String type) {
                int payType = 0;
                if (type.equals(Type.WECHATPAY)) {
                    payType = 1;
                }
                if (type.equals(Type.ALIPAY)) {
                    payType = 2;
                }
                goPay(String.valueOf(payType), bean);
            }
        });
    }

    /**
     * 发起支付
     */
    //1为微信支付,2为支付宝支付
    private void goPay(String type, NursingOrderBean bean) {
        Map<String, Object> map = new HashMap<>();
        map.put("orderNumber", bean.getOrderNumber());
        map.put("type", type);
        map.put("totalMoney", bean.getPrice());
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(NursingOrderService.class).nursingOrderPay(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                hideLoading();
                if (StringUtils.isEmpty(data)) {
                    ToastUtils.showShort("返回支付参数为空");
                    return;
                }
                JSONObject js = JSONObject.parseObject(data);
                if (type.equals("1")) {
                    WXPay.getInstance().doPay(getContext(), data, new WXPay.WXPayResultCallBack() {
                        @Override
                        public void onSuccess() {
                            getView().paySuccess();
                            ToastUtils.showShort("支付成功");
                        }

                        @Override
                        public void onError(int error_code) {
                            ToastUtil.shortToast(getContext(), "支付失败" + error_code
                            );
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
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
                hideLoading();
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
     * 处理子订单、服务记录、器械list
     */
    private List<MultiItemEntity> getMultiItemData(NursingOrderDetailBean bean) {
        List<MultiItemEntity> data = new ArrayList<>();
        List<NursingOrderServiceItem2Bean> subOrders = bean.getSubOrders();
        //子订单
        if (subOrders != null && subOrders.size() != 0) {
            for (int i = 0; i < subOrders.size(); i++) {
                NursingOrderService2Bean service2Bean = new NursingOrderService2Bean();
                service2Bean.setFrequency(i + 1);
                service2Bean.addSubItem(subOrders.get(i));
                data.add(service2Bean);
            }
        }
        //服务记录
        List<NursingOrderServiceBean> orderDetailEachService = bean.getOrderDetailEachService();
        if (orderDetailEachService != null && orderDetailEachService.size() != 0) {
            NursingOrderServiceHead head = new NursingOrderServiceHead("服务记录");
            data.add(head);
            for (int i = 0; i < orderDetailEachService.size(); i++) {
                List<NursingOrderServiceItemBean> list = orderDetailEachService.get(i).getList();
                if (list != null && list.size() != 0) {
                    NursingOrderServiceBean serviceBean = orderDetailEachService.get(i);
                    for (int j = 0; j < list.size(); j++) {
                        NursingOrderServiceItemBean serviceItemBean = list.get(j);
                        if (j == 0) {
                            serviceItemBean.setTop(true);
                        }
                        if (j == list.size() - 1) {
                            serviceItemBean.setBottom(true);
                        }
                        serviceBean.addSubItem(serviceItemBean);
                    }
                    data.add(serviceBean);
                }
            }
        }
        //器械记录
        List<NursingOrderService3Bean> equipmentItem = bean.getEquipmentItem();
        if (equipmentItem != null && equipmentItem.size() != 0) {
            NursingOrderServiceHead head = new NursingOrderServiceHead("器材/耗材");
            data.add(head);
            for (int i = 0; i < equipmentItem.size(); i++) {
                List<NursingOrderServiceItem3Bean> detailEquipment = equipmentItem.get(i).getDetailEquipment();
                if (detailEquipment != null && detailEquipment.size() != 0) {
                    NursingOrderService3Bean service3Bean = equipmentItem.get(i);
                    for (int j = 0; j < detailEquipment.size(); j++) {
                        NursingOrderServiceItem3Bean serviceItem3Bean = detailEquipment.get(j);
                        serviceItem3Bean.setFrequency(j + 1);

                        serviceItem3Bean.setTotal(service3Bean.getTotalMoney().doubleValue());
                        if (j == 0) {
                            serviceItem3Bean.setTop(true);
                        }
                        if (j == detailEquipment.size() - 1) {
                            serviceItem3Bean.setBottom(true);
                        }
                        service3Bean.addSubItem(serviceItem3Bean);
                    }
                    data.add(service3Bean);
                }
            }
        }
        return data;
    }
}
