package com.keydom.mianren.ih_doctor.activity.nurse_service.controller;

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
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.nurse_service.SelectNurseActivity;
import com.keydom.mianren.ih_doctor.activity.nurse_service.view.HeadNurseServiceOrderDetailView;
import com.keydom.mianren.ih_doctor.bean.HeadNurseServiceOrderDetailBean;
import com.keydom.mianren.ih_doctor.bean.NurseBean;
import com.keydom.mianren.ih_doctor.m_interface.BDMapResultInternet;
import com.keydom.mianren.ih_doctor.m_interface.OnCheckDialogListener;
import com.keydom.mianren.ih_doctor.m_interface.OnExtraOptionDialogListener;
import com.keydom.mianren.ih_doctor.m_interface.OnNurseResultListener;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;
import com.keydom.mianren.ih_doctor.net.NurseServiceApiService;
import com.keydom.mianren.ih_doctor.utils.DialogUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * @Name：com.keydom.ih_doctor.fragment.controller
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/16 下午2:26
 * 修改人：xusong
 * 修改时间：18/11/16 下午2:26
 */
public class HeadNurseServiceOrderDetailController extends ControllerImpl<HeadNurseServiceOrderDetailView> implements View.OnClickListener, OnGetRoutePlanResultListener, BDMapResultInternet {

    public static final int RETURN_AND_UPDATE = -2;
    public static final int RETURN_AND_BACK = -1;
    private Dialog returnDialog;
    private Dialog updateDialog;
    private Dialog receiveDialog;
    private OnNurseResultListener mlistener;
    private BDLocation currentLocation;
    @SingleClick(1000)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_nurse_spread_out:
                getView().showAndHideBaseInfo();
                break;
            case R.id.receive_bt:
                if (receiveDialog == null) {
                    receiveDialog = DialogUtils.createReceiveDialog(getContext(), new OnExtraOptionDialogListener() {
                        @Override
                        public void commit(View v, Map<String, Object> map) {
                            saveNursingByNursingOrderIdAndNursingId();

                        }

                        @Override
                        public void extraClick(View v, Object extraValue, OnNurseResultListener listener) {
                            mlistener = listener;
                            SelectNurseActivity.startActivitySelfDeptOnlyResult(getContext());
                        }
                    });
                }
                receiveDialog.show();
                break;
            case R.id.update_bt:
                if (updateDialog == null) {
                    updateDialog = DialogUtils.createReturnDialog(getContext(), new OnCheckDialogListener() {
                        @Override
                        public void commit(View v, String value) {
                            updateNursingOrderStateById(RETURN_AND_UPDATE, value);
                        }
                    });
                }
                updateDialog.show();
                break;
            case R.id.return_bt:
                if (returnDialog == null) {
                    returnDialog = DialogUtils.createReturnBackDialog(getContext(), new OnCheckDialogListener() {
                        @Override
                        public void commit(View v, String value) {
                            updateNursingOrderStateById(RETURN_AND_BACK, value);
                        }
                    });
                }
                returnDialog.show();
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
            default:

        }
    }

    /**
     * 获取订单详情
     */
    public void getHeadNurseServiceOrderDetail() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(NurseServiceApiService.class).getNursingOrderDetailById(getView().getDetailMap()), new HttpSubscriber<HeadNurseServiceOrderDetailBean>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable HeadNurseServiceOrderDetailBean data) {
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
     * 接收订单
     */
    public void saveNursingByNursingOrderIdAndNursingId() {
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(NurseServiceApiService.class).saveNursingByNursingOrderIdAndNursingId(getView().getReceiveMap()), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                hideLoading();
                getView().receiveSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().receiveFailed(code, msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 退回修改以及退回
     *
     * @param state  状态区分是退回还是退回修改
     * @param reason 退回原因
     */

    public void updateNursingOrderStateById(int state, String reason) {
        showLoading();
        Map<String, Object> map = getView().getDetailMap();
        map.put("state", state);
        map.put("reason", reason);
        map.put("id", getView().getOrderNumber());
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(NurseServiceApiService.class).updateNursingOrderStateById(map), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                hideLoading();
                getView().backOrderSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().backOrderFailed(code, msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 设置护理名称
     *
     * @param bean
     */
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

    @Override
    public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

    }

    GeoCoder search;

    /**
     * 路径规划
     */
    public void drivingRoute() {
        RoutePlanSearch routePlanSearch = RoutePlanSearch.newInstance();
        routePlanSearch.setOnGetRoutePlanResultListener(this);
        search = getView().getMapUtil().getGeoPointByAddress(MyApplication.userInfo.getCityName(), getView().getBaseInfo().getServiceAddress(), new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
                LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                LatLng endlatLng = new LatLng(geoCodeResult.getLocation().latitude, geoCodeResult.getLocation().longitude);
                PlanNode stNode = PlanNode.withLocation(latLng);
                PlanNode enNode = PlanNode.withLocation(endlatLng);
                routePlanSearch.drivingSearch((new DrivingRoutePlanOption())
                        .from(stNode)
                        .to(enNode));
                routePlanSearch.destroy();
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
     * @param phoneNum 要拨打的电话号码
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
