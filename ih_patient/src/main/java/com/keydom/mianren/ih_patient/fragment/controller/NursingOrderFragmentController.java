package com.keydom.mianren.ih_patient.fragment.controller;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.activity.nursing_service.NursingOrderFillInActivity;
import com.keydom.mianren.ih_patient.bean.NursingOrderBean;
import com.keydom.mianren.ih_patient.bean.NursingOrderDetailBean;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.constant.TypeEnum;
import com.keydom.mianren.ih_patient.fragment.view.NursingOrderItemView;
import com.keydom.mianren.ih_patient.net.NursingOrderService;
import com.keydom.mianren.ih_patient.utils.pay.alipay.Alipay;
import com.keydom.mianren.ih_patient.utils.pay.weixin.WXPay;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * created date: 2018/12/18 on 15:07
 * des:护理订单控制器
 */
public class NursingOrderFragmentController extends ControllerImpl<NursingOrderItemView> {


    /**
     * 获取护理订单列表
     */
    public void getNursingListData(int state, final TypeEnum typeEnum) {
        showLoading();
        if (typeEnum == TypeEnum.REFRESH) {
            setCurrentPage(1);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("patientId", Global.getUserId());
        map.put("state", state);
        map.put("hospitalId", App.hospitalId);
        map.put("currentPage", getCurrentPage());
        map.put("pageSize", Const.PAGE_SIZE);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(NursingOrderService.class).getNursingOrderData(map), new HttpSubscriber<PageBean<NursingOrderBean>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable PageBean<NursingOrderBean> data) {
                hideLoading();
                getView().getDataSuccess(data.getRecords(),typeEnum);
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
     * 获取单条详情
     */
    public void getDataList(long id, int state) {
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(NursingOrderService.class).getNursingOrderDetail(id, state), new HttpSubscriber<NursingOrderDetailBean>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable NursingOrderDetailBean data) {
                hideLoading();
                if (data.getNursingServiceOrderDetailBaseDto() != null) {
                    data.setState(data.getNursingServiceOrderDetailBaseDto().getState());
                }
                getView().getBasicData(data);
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
     * 退单
     */
    public void goChangeOrder(long id, int state) {
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(NursingOrderService.class).getNursingOrderDetail(id, state), new HttpSubscriber<NursingOrderDetailBean>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable NursingOrderDetailBean data) {
                hideLoading();
                if (data.getNursingServiceOrderDetailBaseDto() != null) {
                    data.setState(data.getNursingServiceOrderDetailBaseDto().getState());
                }
                NursingOrderFillInActivity.start(getContext(), null, "", 0, true, data);
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
    //1为微信支付,2为支付宝支付
    public void goPay(String type, NursingOrderDetailBean bean, BigDecimal price) {
        Map<String, Object> map = new HashMap<>();
        map.put("orderNumber", bean.getNursingServiceOrderDetailBaseDto().getOrderNumber());
        map.put("type", type);
        map.put("totalMoney", price);
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
                            ToastUtil.showMessage(getContext(),"支付成功");
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
}
