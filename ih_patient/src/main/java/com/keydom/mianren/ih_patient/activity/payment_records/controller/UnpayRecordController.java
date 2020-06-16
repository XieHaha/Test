package com.keydom.mianren.ih_patient.activity.payment_records.controller;

import android.view.View;

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
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.payment_records.PaymentRecordActivity;
import com.keydom.mianren.ih_patient.activity.payment_records.UnpayRecordFragment;
import com.keydom.mianren.ih_patient.activity.payment_records.view.UnpayRecordView;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.LocationInfo;
import com.keydom.mianren.ih_patient.bean.PayRecordBean;
import com.keydom.mianren.ih_patient.bean.PaymentOrderBean;
import com.keydom.mianren.ih_patient.bean.PrescriptionDetailBean;
import com.keydom.mianren.ih_patient.bean.PrescriptionDrugBean;
import com.keydom.mianren.ih_patient.bean.entity.pharmacy.PharmacyBean;
import com.keydom.mianren.ih_patient.callback.SingleClick;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.net.LocationService;
import com.keydom.mianren.ih_patient.net.PayService;
import com.keydom.mianren.ih_patient.net.PrescriptionService;
import com.keydom.mianren.ih_patient.utils.CommUtil;
import com.keydom.mianren.ih_patient.utils.pay.alipay.Alipay;
import com.keydom.mianren.ih_patient.utils.pay.weixin.WXPay;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 未缴费控制器
 */
public class UnpayRecordController extends ControllerImpl<UnpayRecordView> implements View.OnClickListener {
    /**
     * 获取未缴费记录
     */
    public void getConsultationPayList(SmartRefreshLayout refreshLayout) {
        Map<String, Object> map = new HashMap<>();
        map.put("patientId", getView().getPatientId());
        map.put("hospitalId", App.hospitalId);
        map.put("registerUserId", Global.getUserId());
        map.put("state", PaymentRecordActivity.NO_PAY);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PayService.class).getConsultationPayList(map), new HttpSubscriber<List<PayRecordBean>>(getContext(), getDisposable(), false, true) {
            @Override
            public void requestComplete(@Nullable List<PayRecordBean> data) {
                if (data != null) {
                    getView().paymentListCallBack(data);
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                if (!"token解析失败".equals(msg))
                    ToastUtils.showLong(msg);
                refreshLayout.finishRefresh();
                return super.requestError(exception, code, msg);
            }
        });
    }

    @SingleClick(1000)
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.pay_tv) {
            List<PayRecordBean> selectList = getView().getSelectList();
            if (selectList.size() > 0) {
                String hint = "";
                //是否为线下支付
                boolean offline = false;
                //判断多订单支付是否包含线上线下
                int isOnline = -1;
                //订单号（多订单 以逗号隔开）
                StringBuilder orderNumbers;
                if (selectList.size() > 1) {
                    String card = selectList.get(0).getEleCardNumber();
                    int type = selectList.get(0).getType();
                    for (int i = 0; i < selectList.size(); i++) {
                        PayRecordBean payRecordBean = selectList.get(i);
                        if (isOnline == -1) {
                            isOnline = payRecordBean.getIsOnline();
                        } else {
                            if (isOnline != payRecordBean.getIsOnline()) {
                                hint = "不能同时为线上、线下订单合并缴费";
                            }
                        }
                        if (payRecordBean.getType() == UnpayRecordFragment.CAN_MERGE && !payRecordBean.getEleCardNumber().equals(card)) {
                            hint = "不能同时为多个就诊人合并缴费";
                        }
                        if (payRecordBean.getType() != type) {
                            hint = "不能同时缴纳诊间费用和预约挂号、在线问诊、护理服务和预约体检项目费用";
                        }
                        if (payRecordBean.getType() == UnpayRecordFragment.CANNOT_MERGE) {
                            hint = "不能同时缴纳预约挂号、在线问诊、护理服务和预约体检项目费用";
                        }
                    }
                }
                if (!StringUtils.isEmpty(hint)) {
                    ToastUtils.showLong(hint);
                    return;
                } else {
                    boolean needDispatch = false;
                    orderNumbers = new StringBuilder();
                    for (int i = 0; i < selectList.size(); i++) {
                        PayRecordBean payRecordBean = selectList.get(i);
                        isOnline = payRecordBean.getIsOnline();
                        if (payRecordBean.getRecordState() == 8) {
                            needDispatch = true;
                        }
                        orderNumbers.append(payRecordBean.getDocumentNo());
                        if (selectList.size() - 1 > i) {
                            orderNumbers.append(",");
                        }
                    }
                    if (isOnline == 0) {
//                        payOffline(getView().getPatientId(),orderNumbers.toString(),1,getView().getTotalPay().doubleValue());
                        getView().goPay(needDispatch, orderNumbers.toString(), "", getView().getTotalPay().doubleValue(), "", false, false);
                    } else {
                        createOrder(needDispatch, getView().getDocument(),
                                getView().getTotalPay(), "", false);
                    }
                }
            } else {
                ToastUtils.showShort("请选择订单");
            }
            // 新需求
            //
            //                if (getView().getSelectList().size() > 0) {
            //                    boolean needDispatch = false;
            //                    for (int i = 0; i < getView().getSelectList().size(); i++) {
            //                        if (getView().getSelectList().get(i).getRecordState()
            //                        == 8) {
            //                            needDispatch = true;
            //                        }
            //                    }
            //                    createOrder(needDispatch, getView().getDocument(), getView
            //                    ().getTotalPay());
            //                } else {
            //                    ToastUtils.showShort("请选择订单");
            //                }
        }
    }


    /**
     * 创建支付订单
     */
    public void createOrder(boolean needDispatch, String document, BigDecimal fee,
                            String prescriptionId, boolean isWaiYan) {
        Map<String, Object> map = new HashMap<>();
        map.put("registerUserId", Global.getUserId());
        map.put("documentNo", document);
        map.put("fee", fee);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PayService.class).generateOrder(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<PaymentOrderBean>(getContext(), getDisposable(), true, true) {
            @Override
            public void requestComplete(@Nullable PaymentOrderBean data) {
                if (isWaiYan) {
                    getView().goPay(needDispatch, data.getOrderNumber(),
                            String.valueOf(data.getOrderId()), data.getFee(), prescriptionId, true, true);
                } else {
                    getView().goPay(needDispatch, data.getOrderNumber(),
                            String.valueOf(data.getOrderId()), data.getFee(), prescriptionId,
                            true, false);
                }

            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                ToastUtils.showShort(msg);
                return super.requestError(exception, code, msg);
            }
        });
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
                switch (type) {
                    case 1:
                        if (StringUtils.isEmpty(data)) {
                            ToastUtils.showShort("返回支付参数为空");
                            return;
                        }
                        WXPay.getInstance().doPay(getContext(), data,
                                new WXPay.WXPayResultCallBack() {
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
                        break;
                    case 2:
                        if (StringUtils.isEmpty(data)) {
                            ToastUtils.showShort("返回支付参数为空");
                            return;
                        }
                        JSONObject js = JSONObject.parseObject(data);
                        if (!js.containsKey("return_msg")) {
                            return;
                        }
                        new Alipay(getContext(), js.getString("return_msg"),
                                new Alipay.AlipayResultCallBack() {
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
                                        getView().refreshData();
                                    }

                                    @Override
                                    public void onCancel() {
                                        ToastUtils.showShort("取消支付");
                                        getView().refreshData();
                                    }
                                }).doPay();
                        break;
                    case 4:
                        getView().paySuccess();
                        EventBus.getDefault().post(new Event(EventType.REFRESHDIAGNOSESORDER,
                                null));
                        ToastUtils.showShort("支付成功");
                        break;
                    default:
                        break;
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                ToastUtils.showShort(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 发起支付(线下)   //支付方式 1微信 2支付宝
     */
    public void payOffline(long patientId, String orderNumber, int type, double totalMoney) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", App.userInfo.getId());
        map.put("patientId", patientId);
        map.put("orderNumber", orderNumber);
        map.put("payType", type);
        map.put("totalMoney", totalMoney);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PayService.class).payHisOfflineDoctorAdvice(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>(getContext(), getDisposable(), true, true) {
            @Override
            public void requestComplete(@Nullable String data) {
                switch (type) {
                    case 1:
                        if (StringUtils.isEmpty(data)) {
                            ToastUtils.showShort("返回支付参数为空");
                            return;
                        }
                        WXPay.getInstance().doPay(getContext(), data,
                                new WXPay.WXPayResultCallBack() {
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
                        break;
                    case 2:
                        if (StringUtils.isEmpty(data)) {
                            ToastUtils.showShort("返回支付参数为空");
                            return;
                        }
                        JSONObject js = JSONObject.parseObject(data);
                        if (!js.containsKey("return_msg")) {
                            return;
                        }
                        new Alipay(getContext(), js.getString("return_msg"),
                                new Alipay.AlipayResultCallBack() {
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
                                        getView().refreshData();
                                    }

                                    @Override
                                    public void onCancel() {
                                        ToastUtils.showShort("取消支付");
                                        getView().refreshData();
                                    }
                                }).doPay();
                        break;
                    case 4:
                        getView().paySuccess();
                        EventBus.getDefault().post(new Event(EventType.REFRESHDIAGNOSESORDER,
                                null));
                        ToastUtils.showShort("支付成功");
                        break;
                    default:
                        break;
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                ToastUtils.showShort(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 获取配送费
     */
    public void getDistributionFee(long addressId) {
        Map<String, Object> map = new HashMap<>();
        map.put("addressId", addressId);
        map.put("hospitald", App.hospitalId);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PayService.class).getDeliveryCost(map), new HttpSubscriber<String>(getContext(), getDisposable(), true, true) {
            @Override
            public void requestComplete(@Nullable String data) {
                hideLoading();
                getView().getDistributionFee(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                hideLoading();
                ToastUtils.showShort(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 获取地址列表
     */
    public void getLocationList() {
        showLoading();
        Map<String, Object> map = new HashMap<>();
        map.put("userId", Global.getUserId());
        map.put("currentPage", "1");
        map.put("pageSize", 50);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(LocationService.class).getAddressList(map), new HttpSubscriber<PageBean<LocationInfo>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable PageBean<LocationInfo> data) {
                hideLoading();
                getView().getLocationList(data.getRecords());
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                hideLoading();
                return super.requestError(exception, code, msg);
            }
        });
    }


    /**
     * 获取药品
     */
    public void getPrescriptionDetailDrugs(String address, String id) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PrescriptionService.class).getDetailById(id), new HttpSubscriber<PrescriptionDetailBean>(getContext(), getDisposable(), true, true) {
            @Override
            public void requestComplete(@org.jetbrains.annotations.Nullable PrescriptionDetailBean data) {
                if (null != data && !CommUtil.isEmpty(data.getList())) {
                    getHttpFindDrugstores(address, data.getList());
                }

            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                ToastUtils.showShort(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 获得药店名字获取配送费
     */
    private void getHttpFindDrugstores(String mAddress, List<List<PrescriptionDrugBean>> drugs) {
        Map<String, Object> map = new HashMap<>();
        map.put("address", mAddress);
        map.put("drugs", drugs);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PrescriptionService.class).getFindDrugstoresByDistribution(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<List<PharmacyBean>>(mContext, getDisposable(), true, true) {
            @Override
            public void requestComplete(@org.jetbrains.annotations.Nullable List<PharmacyBean> data) {
                if (!CommUtil.isEmpty(data)) {
                    getView().setPharmacyBeans(data);
                    getView().refreshDeliveryCostView(data);
                    getView().refreshPriceView(data);
                }

            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                return super.requestError(exception, code, msg);
            }
        });

    }


    /**
     * 更新处方订单
     */
    public void updatePrescriptionOrder(int payType, boolean isSendDrugsToHome, boolean isOnline,
                                        String prescriptionId, String orderNum,
                                        PharmacyBean pharmacyBean, LocationInfo locationInfo) {
        Map<String, Object> map = new HashMap<>();

        if (isSendDrugsToHome) {
            map.put("consigneeAddress",
                    locationInfo.getProvinceName() + locationInfo.getCityName() + locationInfo.getAreaName() + locationInfo.getAddress());
            map.put("consigneeName", locationInfo.getAddressName());
            map.put("consigneePhone", locationInfo.getPhone());
            map.put("delivery", "1");

        } else {
            map.put("delivery", "0");
        }
        map.put("drugstore", pharmacyBean.getDrugstore());
        map.put("drugstoreCode", pharmacyBean.getDrugstoreCode());
        map.put("drugsStoreAddress", pharmacyBean.getDrugstoreAddress());
        map.put("isOnline", isOnline ? "0" : "1");
        map.put("fee", pharmacyBean.getSumFee());
        map.put("id", prescriptionId);
        map.put("orderNumber", orderNum);
        map.put("items", pharmacyBean.getDrugsDtos());
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PrescriptionService.class).updatePrescriptionOrder(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>(mContext, getDisposable(), true, false) {
            @Override
            public void requestComplete(@org.jetbrains.annotations.Nullable String data) {
                if (isOnline) {
                    if (isSendDrugsToHome) {
                        pay(locationInfo.getId(), orderNum, payType,
                                Double.valueOf(pharmacyBean.getSumFee()));
                    } else {
                        pay(0, orderNum, payType, Double.valueOf(pharmacyBean.getSumFee()));
                    }
                } else {
                    getView().paySuccess();
                    ToastUtils.showShort("提交成功");
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                return super.requestError(exception, code, msg);
            }
        });

    }
}
