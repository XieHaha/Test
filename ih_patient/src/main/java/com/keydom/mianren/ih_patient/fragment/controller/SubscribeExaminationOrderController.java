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
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.order_physical_examination.PhysicalExaminationDetailActivity;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.PhysicalExaInfo;
import com.keydom.mianren.ih_patient.bean.SubscribeExaminationBean;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.constant.TypeEnum;
import com.keydom.mianren.ih_patient.fragment.view.SubscribeExaminationOrderView;
import com.keydom.mianren.ih_patient.net.ReservationService;
import com.keydom.mianren.ih_patient.utils.pay.alipay.Alipay;
import com.keydom.mianren.ih_patient.utils.pay.weixin.WXPay;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * created date: 2018/12/12 on 17:23
 * des: 体检预约订单控制器
 */
public class SubscribeExaminationOrderController extends ControllerImpl<SubscribeExaminationOrderView> {
    public static final int ALL = 0;
    public static final int PAY = 1;
    public static final int EXAMINATION = 2;
    public static final int COMPELETE = 3;

    /**
     * 获取体检列表
     */
    public void getExaminationData(int status,final TypeEnum typeEnum) {
        showLoading();
        if (typeEnum == TypeEnum.REFRESH) {
            setCurrentPage(1);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("registerUserId", Global.getUserId());
        map.put("state", status);
        map.put("hospitalId", App.hospitalId);
        map.put("currentPage", getCurrentPage());
        map.put("pageSize", Const.PAGE_SIZE);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ReservationService.class).getExaminationOrderList(map), new HttpSubscriber<PageBean<SubscribeExaminationBean>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable PageBean<SubscribeExaminationBean> data) {
                hideLoading();
                getView().getDataSuccess(data.getRecords(),typeEnum);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().getDataFailed(msg);
                return super.requestError(exception, code, msg);
            }

        });
    }

    /**
     * 刷新页面数据
     */
    private void refreshData(SubscribeExaminationBean bean) {
        EventBus.getDefault().post(new Event(EventType.UPDATESUBSCRIBEEXAM,ALL));
        switch (bean.getItemState()) {
            case PAY:
                EventBus.getDefault().post(new Event(EventType.UPDATESUBSCRIBEEXAM,PAY));
                break;
            case EXAMINATION:
                EventBus.getDefault().post(new Event(EventType.UPDATESUBSCRIBEEXAM,EXAMINATION));
                break;
            case COMPELETE:
                EventBus.getDefault().post(new Event(EventType.UPDATESUBSCRIBEEXAM,COMPELETE));
                break;
        }
    }

    /**
     * 退单操作
     */
    public void chargeBackExaminationOrder(SubscribeExaminationBean bean) {
        showLoading();
        Map<String, Object> map = new HashMap<>();
        map.put("orderNumber", bean.getOrderNumber());
        map.put("totalMoney", bean.getFee());
        map.put("userId", Global.getUserId());
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ReservationService.class).findhealthCheckComborefund(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<Object>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable Object data) {
                hideLoading();
                ToastUtils.showShort(getContext().getResources().getString(R.string.charge_back_success));
                EventBus.getDefault().post(new Event(EventType.UPDATESUBSCRIBEEXAM,null));
//                EventBus.getDefault().post(new Event(EventType.UPDATESUBSCRIBEEXAM,EXAMINATION));
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
     * 创建支付订单
     */
    public void cancelExaminationOrder(SubscribeExaminationBean bean) {
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ReservationService.class).findhealthCheckComboreCancel(bean.getId()), new HttpSubscriber<Object>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable Object data) {
                hideLoading();
                new GeneralDialog(getContext(), getContext().getResources().getString(R.string.cancel_success),
                        () -> {
                            EventBus.getDefault().post(new Event(EventType.UPDATESUBSCRIBEEXAM,ALL));
                            EventBus.getDefault().post(new Event(EventType.UPDATESUBSCRIBEEXAM,PAY));
                            EventBus.getDefault().post(new Event(EventType.UPDATESUBSCRIBEEXAM,COMPELETE));
                        })
                        .setPositiveButton("确认")
                        .setNegativeButtonIsGone(true)
                        .show();
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
     * 删除体检订单
     */
    public void deleteExaminationOrder(final SubscribeExaminationBean bean) {
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ReservationService.class).deleteExaminationOrder(bean.getId()), new HttpSubscriber<Object>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable Object data) {
                hideLoading();
                bean.setItemState(COMPELETE);
                new GeneralDialog(getContext(), getContext().getResources().getString(R.string.delete_success),
                        () -> refreshData(bean))
                        .setPositiveButton("确认")
                        .setNegativeButtonIsGone(true)
                        .show();

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
     * 再次购买体检套餐
     */
    public void goBuyExaminationOrder(long id) {
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ReservationService.class).findhealthCheckComboInfo(id), new HttpSubscriber<PhysicalExaInfo>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable PhysicalExaInfo data) {
                hideLoading();
                Global.setSelectedPhysicalExa(data);
                PhysicalExaminationDetailActivity.start(getContext());
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
    public void goPayExaminationOrder(String type,SubscribeExaminationBean bean) {
        Map<String, Object> map = new HashMap<>();
        map.put("orderNumber", bean.getOrderNumber());
        map.put("type", type);
        map.put("totalMoney",bean.getFee());
        showLoading();
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
