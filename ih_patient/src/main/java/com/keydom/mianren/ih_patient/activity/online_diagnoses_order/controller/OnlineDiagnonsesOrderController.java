package com.keydom.mianren.ih_patient.activity.online_diagnoses_order.controller;

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
import com.keydom.mianren.ih_patient.activity.online_diagnoses_order.view.OnlineDiagnonsesOrderView;
import com.keydom.mianren.ih_patient.bean.ChildOrderBean;
import com.keydom.mianren.ih_patient.bean.DiagnosesOrderBean;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.LocationInfo;
import com.keydom.mianren.ih_patient.bean.PaymentOrderBean;
import com.keydom.mianren.ih_patient.bean.PrescriptionDetailBean;
import com.keydom.mianren.ih_patient.bean.PrescriptionDrugBean;
import com.keydom.mianren.ih_patient.bean.entity.pharmacy.PharmacyBean;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.constant.TypeEnum;
import com.keydom.mianren.ih_patient.net.LocationService;
import com.keydom.mianren.ih_patient.net.PayService;
import com.keydom.mianren.ih_patient.net.PrescriptionService;
import com.keydom.mianren.ih_patient.net.UserService;
import com.keydom.mianren.ih_patient.utils.CommUtil;
import com.keydom.mianren.ih_patient.utils.pay.alipay.Alipay;
import com.keydom.mianren.ih_patient.utils.pay.weixin.WXPay;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ?????????????????????
 */
public class OnlineDiagnonsesOrderController extends ControllerImpl<OnlineDiagnonsesOrderView> {
    public static final int WAITINGDIAGNONSES = 0;
    public static final int DIAGNONSING = 1;
    public static final int COMPLETEIAGNONSES = 2;

    /**
     * ????????????????????????
     */
    public void getlistPatientInquisition(Map<String, Object> map, int status,
                                          final TypeEnum typeEnum) {
        if (typeEnum == TypeEnum.REFRESH) {
            setCurrentPage(1);
        }
        map.put("state", status);
        map.put("currentPage", getCurrentPage());
        map.put("pageSize", Const.PAGE_SIZE);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).getlistPatientInquisition(map), new HttpSubscriber<PageBean<DiagnosesOrderBean>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable PageBean<DiagnosesOrderBean> data) {
                List<DiagnosesOrderBean> list = data.getRecords();
                if (list == null) {
                    list = new ArrayList<>();
                }
                getView().getDiagnosesOrderListSuccess(list, typeEnum);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().getDiagnosesOrderListFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * ??????????????????
     */
    public void inquiryPay(Map<String, Object> map, DiagnosesOrderBean item, int type) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).inquiryPay(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>(getContext(), getDisposable(), false, false) {
            @Override
            public void requestComplete(@Nullable String data) {
                if (type == 2) {
                    try {
                        JSONObject object = new JSONObject(data);
                        Logger.e("return_msg:" + object.getString("return_msg"));
                        new Alipay(getContext(), object.getString("return_msg"),
                                new Alipay.AlipayResultCallBack() {
                                    @Override
                                    public void onSuccess() {
                                        paySuccess();
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
                } else if (type == 1) {
                    WXPay.getInstance().doPay(getContext(), data,
                            new WXPay.WXPayResultCallBack() {
                                @Override
                                public void onSuccess() {
                                    paySuccess();
                                }

                                @Override
                                public void onError(int error_code) {
                                    ToastUtil.showMessage(getContext(), "????????????" + error_code
                                    );
                                }

                                @Override
                                public void onCancel() {

                                }
                            });
                } else if (type == 4) {
                    paySuccess();
                }
            }
        });
    }

    /**
     * ????????????
     */
    private void paySuccess() {
        ToastUtil.showMessage(getContext(), "????????????");
        new GeneralDialog(getContext(),
                "?????????????????????????????????????????????????????????????????????????????????????????????"
                , () -> EventBus.getDefault().post(new Event(EventType.REFRESHDIAGNOSESORDER,
                null))).setTitle("??????").setCancel(false).setNegativeButtonIsGone(true).setPositiveButton("??????").show();
    }

    /**
     * ??????????????????
     */
    public void returnedInquisition(Map<String, Object> map) {
        if (map != null) {
            ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).returnedInquisition(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<Object>(getContext(), getDisposable(), false, false) {
                @Override
                public void requestComplete(@Nullable Object data) {
                    getView().returnBackSuccess();
                }

                @Override
                public boolean requestError(@NotNull ApiException exception, int code,
                                            @NotNull String msg) {
                    getView().returnBackFailed(msg);
                    return super.requestError(exception, code, msg);
                }
            });
        }
    }

    /**
     * ??????????????????
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
                getView().goPay(needDispatch, data.getOrderNumber(),
                        String.valueOf(data.getOrderId()), data.getFee(), prescriptionId, isWaiYan);
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
     * ????????????
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
                            ToastUtils.showShort("????????????????????????");
                            return;
                        }
                        WXPay.getInstance().doPay(getContext(), data,
                                new WXPay.WXPayResultCallBack() {
                                    @Override
                                    public void onSuccess() {
                                        getView().paySuccess();
                                        EventBus.getDefault().post(new Event(EventType.REFRESHDIAGNOSESORDER,
                                                null));
                                        ToastUtils.showShort("????????????");
                                    }

                                    @Override
                                    public void onError(int error_code) {
                                        ToastUtil.showMessage(getContext(), "????????????" + error_code
                                        );
                                    }

                                    @Override
                                    public void onCancel() {

                                    }
                                });
                        break;
                    case 2:
                        if (StringUtils.isEmpty(data)) {
                            ToastUtils.showShort("????????????????????????");
                            return;
                        }
                        com.alibaba.fastjson.JSONObject js =
                                com.alibaba.fastjson.JSONObject.parseObject(data);
                        if (!js.containsKey("return_msg")) {
                            return;
                        }
                        new Alipay(getContext(), js.getString("return_msg"),
                                new Alipay.AlipayResultCallBack() {
                                    @Override
                                    public void onSuccess() {
                                        getView().paySuccess();
                                        EventBus.getDefault().post(new Event(EventType.REFRESHDIAGNOSESORDER,
                                                null));
                                        ToastUtils.showShort("????????????");
                                    }

                                    @Override
                                    public void onDealing() {
                                        ToastUtils.showShort("????????????????????????");
                                    }

                                    @Override
                                    public void onError(int error_code) {
                                        ToastUtils.showShort("????????????");
                                    }

                                    @Override
                                    public void onCancel() {
                                        ToastUtils.showShort("????????????");
                                    }
                                }).doPay();
                        break;
                    case 4:
                        getView().paySuccess();
                        EventBus.getDefault().post(new Event(EventType.REFRESHDIAGNOSESORDER,
                                null));
                        ToastUtils.showShort("????????????");
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
     * ?????????????????????????????????????????????????????????
     */
    public void getChildOrderBean(long inquiryId, String prescriptionId) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).getUnPaySubOrderInfo(inquiryId), new HttpSubscriber<ChildOrderBean>() {
            @Override
            public void requestComplete(@Nullable ChildOrderBean data) {
                createOrder(data.getIsPrescription() == 1, data.getOrderNumber(),
                        new BigDecimal(data.getFee()), prescriptionId, data.isWaiYan());
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
     * ?????????????????????
     */
    public void getDistributionFee(long addressId) {
        Map<String, Object> map = new HashMap<>();
        map.put("addressId", addressId);
        map.put("hospitald", App.hospitalId);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PayService.class).getDeliveryCost(map), new HttpSubscriber<String>(getContext(), getDisposable(), true, true) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().getDistributionFee(data);
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
     * ??????????????????
     */
    public void getLocationList() {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", Global.getUserId());
        map.put("currentPage", "1");
        map.put("pageSize", 50);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(LocationService.class).getAddressList(map), new HttpSubscriber<PageBean<LocationInfo>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable PageBean<LocationInfo> data) {
                getView().getLocationList(data.getRecords());
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                return super.requestError(exception, code, msg);
            }
        });
    }


    /**
     * ??????????????????
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
            map.put("deliveryCost", getView().getDeliveryCost());
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
                    ToastUtils.showShort("????????????");
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
     * ????????????
     */
    public void getPrescriptionDetailDrugs(String address, String id) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PrescriptionService.class).getDetailById(id), new HttpSubscriber<PrescriptionDetailBean>(getContext(), getDisposable(), true, true) {
            @Override
            public void requestComplete(@org.jetbrains.annotations.Nullable PrescriptionDetailBean data) {
                if (null != data && !CommUtil.isEmpty(data.getList())) {
                    data.getList().get(0).get(0).setPrescriptionId(id);
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
     * ?????????????????????????????????
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

}
