package com.keydom.ih_doctor.activity.nurse_service.controller;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.ih_doctor.MyApplication;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.nurse_service.CommonNurseServiceOrderDetailActivity;
import com.keydom.ih_doctor.activity.nurse_service.SelectNurseActivity;
import com.keydom.ih_doctor.activity.nurse_service.view.CommonNurseServiceOrderDetailView;
import com.keydom.ih_doctor.bean.CommonNurseServiceOrderDetailBean;
import com.keydom.ih_doctor.bean.MessageEvent;
import com.keydom.ih_doctor.bean.NurseBean;
import com.keydom.ih_doctor.constant.EventType;
import com.keydom.ih_doctor.m_interface.BDMapResultInternet;
import com.keydom.ih_doctor.m_interface.OnExtraOptionDialogListener;
import com.keydom.ih_doctor.m_interface.OnNurseResultListener;
import com.keydom.ih_doctor.m_interface.SingleClick;
import com.keydom.ih_doctor.net.NurseServiceApiService;
import com.keydom.ih_doctor.utils.CalculateTimeUtils;
import com.keydom.ih_doctor.utils.DialogUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * @Name：com.keydom.ih_doctor.fragment.controller
 * @Author：song
 * @Date：18/11/16 下午2:26
 * 修改人：xusong
 * 修改时间：18/11/16 下午2:26
 */
public class CommonNurseServiceOrderDetailController extends ControllerImpl<CommonNurseServiceOrderDetailView> implements View.OnClickListener, OnRefreshListener, OnLoadMoreListener, OnGetRoutePlanResultListener, BDMapResultInternet {
    private Dialog changeDialog;
    private Dialog receiveDialog;
    private OnNurseResultListener mlistener;
    private BDLocation currentLocation;
    @SingleClick(1000)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.spread_out:
                getView().showAndHideBaseInfo();
                break;
            case R.id.receive_bt:
                if (receiveDialog == null) {
                    receiveDialog = DialogUtils.createServiceSureDialog(getContext(), new OnExtraOptionDialogListener() {
                        @Override
                        public void commit(View v, Map<String, Object> map) {
                            try {
                                String dateStr = CalculateTimeUtils.requestDate((Date) map.get(DialogUtils.SELECT_DATE));
                                String startTimeStr = CalculateTimeUtils.getY2mTimeStr((Date) map.get(DialogUtils.SELECT_START_TIME));
                                String endTimeStr = CalculateTimeUtils.getY2mTimeStr((Date) map.get(DialogUtils.SELECT_END_TIME));
                                String inputValue = String.valueOf(map.get(DialogUtils.INPUT_VALUE));
                                saveNursingByNursingOrderIdAndNursingId(dateStr, startTimeStr + "-" + endTimeStr, inputValue);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void extraClick(View v, Object extraValue, OnNurseResultListener listener) {
                        }
                    });
                }
                receiveDialog.show();
                break;
            case R.id.change_order_bt:
                if (changeDialog == null) {
                    changeDialog = DialogUtils.createChangeNurseDialog(getContext(), new OnExtraOptionDialogListener() {
                        @Override
                        public void commit(View v, Map<String, Object> map) {
                            transferNurseOrder();

                        }

                        @Override
                        public void extraClick(View v, Object extraValue, OnNurseResultListener listener) {
                            mlistener = listener;
                            SelectNurseActivity.startActivitySelfDeptOnlyResult(getContext());
                        }
                    });
                }
                changeDialog.show();
                break;
            case R.id.go_bt:
                confirmDepartmentTimeOrArriveTimeServiceRecordByIdAndType(CommonNurseServiceOrderDetailActivity.VISIT_GO);
                break;
            case R.id.arrive_bt:
                confirmDepartmentTimeOrArriveTimeServiceRecordByIdAndType(CommonNurseServiceOrderDetailActivity.VISIT_ARRIVE);
                break;
            case R.id.navigate:
                navigate(getView().getBaseInfo().getServiceAddress());
                break;
            case R.id.user_phone_icon:
                new GeneralDialog(mContext, "确定拨打电话?", new GeneralDialog.OnCloseListener() {
                    @Override
                    public void onCommit() {
                        callPhone(getView().getBaseInfo().getApplyPhone());
                    }
                }).show();
                break;
            case R.id.cancle_change_order_bt:
                new GeneralDialog(getContext(), "你确认要撤销转单吗？", new GeneralDialog.OnCloseListener() {
                    @Override
                    public void onCommit() {
                        cancelTtransferOrder();

                    }
                }).setTitle("提示").show();
                break;
            case R.id.refuse_change_order_bt:
                new GeneralDialog(getContext(), "你确认要退回转单吗？", new GeneralDialog.OnCloseListener() {
                    @Override
                    public void onCommit() {
                        acceptOrder(getView().getRefuseReciveMap());
                    }
                }).setTitle("提示").show();
                break;
            case R.id.accept_receive_bt:
                new GeneralDialog(getContext(), "你确认要接收转单吗？", new GeneralDialog.OnCloseListener() {
                    @Override
                    public void onCommit() {
                        acceptOrder(getView().getAcceptReciveMap());
                    }
                }).setTitle("提示").show();
                break;
            default:

        }
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        currentPagePlus();

    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        setCurrentPage(1);

    }

    public void setNurseName(NurseBean bean) {
        if (mlistener != null && bean != null) {
            mlistener.nurseSelect(bean.getName());
        }
    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

    }

    @Override
    public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

    }


    @Override
    public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

    }


    public void getNurseServiceOrderDetail() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(NurseServiceApiService.class).getNursingServiceRecordDetailByNurseIdAndState(getView().getDetailMap()), new HttpSubscriber<CommonNurseServiceOrderDetailBean>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable CommonNurseServiceOrderDetailBean data) {
                getView().getDetailSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getDetailFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


    /**
     * 转单
     */
    public void transferNurseOrder() {
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(NurseServiceApiService.class).transferNurseOrder(HttpService.INSTANCE.object2Body(getView().getTransferNurseMap())), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                hideLoading();
                if (changeDialog != null) {
                    changeDialog.dismiss();
                }
                getView().transferSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().transferFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 同意接受或者决绝接受转单
     */
    public void acceptOrder(Map<String,Object> map) {
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(NurseServiceApiService.class).acceptOrder(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                hideLoading();
                if(map.get("operator").equals("accept"))
                    getView().acceptOrderSuccess(data);
                else
                    getView().refuseAcceptSuccess(data);

            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                ToastUtil.showMessage(getContext(),"操作失败"+msg);
                EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.NURSE_SERVICE_ORDER_UPDATE).build());
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 取消转单
     */
    public void cancelTtransferOrder() {
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(NurseServiceApiService.class).cancelTtransferOrder(HttpService.INSTANCE.object2Body(getView().getCancelChangeMap())), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                hideLoading();
                getView().cancelChangeSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                ToastUtil.showMessage(getContext(),"操作失败"+msg);
                EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.NURSE_SERVICE_ORDER_UPDATE).build());
                return super.requestError(exception, code, msg);
            }
        });
    }


    /**
     * 接收被指派的服务
     *
     * @param dateStr
     * @param confirmVisitPeriod
     * @param carryingEquipment
     */
    public void saveNursingByNursingOrderIdAndNursingId(String dateStr, String confirmVisitPeriod, String carryingEquipment) {

        Map<String, Object> map = new HashMap<>();
        map.put("confirmVisitTime", dateStr);
        map.put("confirmVisitPeriod", confirmVisitPeriod);
        map.put("id", getView().getOrderId());
        map.put("carryingEquipment", carryingEquipment);
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(NurseServiceApiService.class).confirmNursingServiceRecordById(map), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().receiveSuccess(data);
                hideLoading();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().receiveFailed(msg);
                hideLoading();
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 出发或者到达
     * 0出发，1到达
     *
     * @param
     */
    public void confirmDepartmentTimeOrArriveTimeServiceRecordByIdAndType(final int type) {

        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("id", getView().getOrderId());
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(NurseServiceApiService.class).confirmDepartmentTimeOrArriveTimeServiceRecordByIdAndType(map), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                hideLoading();
                if (type == CommonNurseServiceOrderDetailActivity.VISIT_GO) {
                    getView().goSuccess(data);
                } else {
                    getView().arriveSuccess(data);
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                if (type == CommonNurseServiceOrderDetailActivity.VISIT_GO) {
                    getView().goFailed(msg);
                } else {
                    getView().arriveFailed(msg);
                }
                return super.requestError(exception, code, msg);
            }
        });
    }


    @Override
    public void getBDLocation(BDLocation location) {
        this.currentLocation = location;
        drivingRoute();
    }


    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {

        if (drivingRouteResult != null && getView() != null) {
            List<DrivingRouteLine> routeLineList = drivingRouteResult.getRouteLines();
            if (routeLineList != null && !routeLineList.isEmpty() && routeLineList.size() != 0) {
                getView().getOverlay().removeFromMap();
                getView().getOverlay().setData(routeLineList.get(0));
                getView().getOverlay().addToMap();
                getView().getOverlay().zoomToSpan();
                DecimalFormat df = new DecimalFormat("######0.00");
                getView().getDistanceTv().setText("距离服务地址" + df.format((float) routeLineList.get(0).getDistance() / 1000) + "KM");
            }
        }
    }

    GeoCoder search;

    /**
     * 路径规划和显示
     */
    public void drivingRoute() {
        RoutePlanSearch routePlanSearch = RoutePlanSearch.newInstance();
        routePlanSearch.setOnGetRoutePlanResultListener(this);
        search = getView().getMapUtil().getGeoPointByAddress(MyApplication.userInfo.getCityName(), getView().getBaseInfo().getServiceAddress(), new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

                if (geoCodeResult.getLocation() != null){
                    LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                    LatLng endlatLng = new LatLng(geoCodeResult.getLocation().latitude, geoCodeResult.getLocation().longitude);
                    PlanNode stNode = PlanNode.withLocation(latLng);
                    PlanNode enNode = PlanNode.withLocation(endlatLng);
                    routePlanSearch.drivingSearch((new DrivingRoutePlanOption())
                            .from(stNode)
                            .to(enNode));
                    routePlanSearch.destroy();
                }

                search.destroy();
            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {

            }
        });

    }

    /**
     * 导航
     *
     * @param address
     */
    private void navigate(String address) {
        GeoCoder mSearch = GeoCoder.newInstance();
        OnGetGeoCoderResultListener mListener = new OnGetGeoCoderResultListener() {
            public void onGetGeoCodeResult(GeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    ToastUtil.showMessage(getContext(), "没有搜索到地址");
                    return;
                }
                if (CommonUtils.isInstalled(getContext(), "com.baidu.BaiduMap")) {
                    Intent i1 = new Intent();
                    i1.setData(Uri.parse("baidumap://map/direction?region=" + MyApplication.userInfo.getCityName() + "&origin=" + currentLocation.getLatitude() + "," + currentLocation.getLongitude() + "&title=我的位置" + "&destination=" + address + "&coord_type=bd09ll&mode=driving&src=andr.baidu.openAPIdemo"));
                    getContext().startActivity(i1);
                } else {
                    ToastUtil.showMessage(getContext(), "请安装百度地图");
                }
            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    ToastUtil.showMessage(getContext(), "没有搜索到地址");
                    return;
                }
                if (CommonUtils.isInstalled(getContext(), "com.baidu.BaiduMap")) {
                    Intent i1 = new Intent();
                    i1.setData(Uri.parse("baidumap://map/direction?region=" + MyApplication.userInfo.getCityName() + "&origin=" + currentLocation.getLatitude() + "," + currentLocation.getLongitude() + "&title=我的位置" + "&destination=" + address + "&coord_type=bd09ll&mode=driving&src=andr.baidu.openAPIdemo"));
                    getContext().startActivity(i1);
                } else {
                    ToastUtil.showMessage(getContext(), "请安装百度地图");
                }
            }
        };

        mSearch.setOnGetGeoCodeResultListener(mListener);
        mSearch.geocode(new GeoCodeOption().city(MyApplication.userInfo.getCityName()).address(address));
        mSearch.destroy();
    }


    /**
     * 打电话
     *
     * @param phoneNum
     */
    public void callPhone(String phoneNum) {
        if (phoneNum == null || "".equals(phoneNum)) {
            ToastUtil.showMessage(getContext(), "电话号码为空");
            return;
        }
        RxPermissions rxPermissions = new RxPermissions((AppCompatActivity) getContext());
        rxPermissions.request(Manifest.permission.CALL_PHONE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (granted) {
                            try {
//                                Intent intent = new Intent(Intent.ACTION_CALL);
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                Uri data = Uri.parse("tel:" + phoneNum);
                                intent.setData(data);
                                getContext().startActivity(intent);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });
    }
}
