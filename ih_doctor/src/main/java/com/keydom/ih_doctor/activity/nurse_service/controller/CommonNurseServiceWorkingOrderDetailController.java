package com.keydom.ih_doctor.activity.nurse_service.controller;

import android.Manifest;
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
import com.keydom.ih_doctor.activity.nurse_service.ChooseNursingServiceActivity;
import com.keydom.ih_doctor.activity.nurse_service.FinishNurseServiceActivity;
import com.keydom.ih_doctor.activity.nurse_service.view.CommonNurseServiceWorkingOrderDetailView;
import com.keydom.ih_doctor.bean.CommonNurseServiceOrderDetailBean;
import com.keydom.ih_doctor.m_interface.BDMapResultInternet;
import com.keydom.ih_doctor.m_interface.OnAddServiceItemDialogListener;
import com.keydom.ih_doctor.net.NurseServiceApiService;
import com.keydom.ih_doctor.utils.ToastUtil;
import com.keydom.ih_doctor.view.AddNurseServiceDialog;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
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
public class CommonNurseServiceWorkingOrderDetailController extends ControllerImpl<CommonNurseServiceWorkingOrderDetailView> implements View.OnClickListener, OnRefreshListener, OnLoadMoreListener, OnGetRoutePlanResultListener, OnAddServiceItemDialogListener, BDMapResultInternet {
    AddNurseServiceDialog dialog;
    private BDLocation currentLocation;

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.spread_out:
                getView().showAndHideBaseInfo();
                break;
            case R.id.add_service_bt:
                dialog = new AddNurseServiceDialog(getContext(), getView().getBaseInfo(), getView().getLimit(), this);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
                break;
            case R.id.finish_service_bt:
                FinishNurseServiceActivity.start(getContext(), getView().getBaseInfo());
//                String orderNubmer = (getView().getBaseInfo() == null ? "" : getView().getBaseInfo().getOrderNumber());
//                ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(NurseServiceApiService.class).nurseSearchPatientSubOrderIsPayOrHasSubOrder(orderNubmer), new HttpSubscriber<String>(getContext(), getDisposable(), true) {
//                    @Override
//                    public void requestComplete(@Nullable String data) {
//                        if ("0".equals(data)) {
//                            FinishNurseServiceActivity.start(getContext(), getView().getBaseInfo());
//                        } else {
//                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                            builder.setTitle("无法完成服务");
//                            builder.setMessage("患者存在未支付费用，支付后才可完成！");
//                            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                }
//                            });
//                            builder.create().show();
//                        }
//                    }
//
//                    @Override
//                    public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                        builder.setTitle("无法完成服务");
//                        builder.setMessage("数据错误，无法完成服务！");
//                        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                            }
//                        });
//                        builder.create().show();
//                        return super.requestError(exception, code, msg);
//                    }
//                });

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

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        currentPagePlus();

    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        setCurrentPage(1);

    }


    /**
     * 获取添加服务项目弹窗
     *
     * @return
     */
    public AddNurseServiceDialog getDialog() {
        return dialog;
    }


    /**
     * 获取护理服务订单详情
     */
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
     * 添加服务项目
     *
     * @param map 请求参数
     */
    public void nurseAddProjectToSubOrder(Map<String, Object> map) {
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(NurseServiceApiService.class).nurseAddProjectToSubOrder(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                hideLoading();
                dialog.setAddBtnEnable(true);
                dialog.dismiss();
                getView().addServiceItemSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                dialog.setAddBtnEnable(true);
                getView().addServiceItemFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


    @Override
    public void dialogClick(View v, Map<String, Object> value) {
        switch (v.getId()) {
            case R.id.service_item_tv:
                ChooseNursingServiceActivity.start(getContext(), getView().getSelectSubItem());
                break;
            case R.id.add_btn:
                nurseAddProjectToSubOrder(value);
                break;
        }

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
                    ToastUtil.shortToast(getContext(), "没有搜索到地址");
                    return;
                }
                if (CommonUtils.isInstalled(getContext(), "com.baidu.BaiduMap")) {
                    Intent i1 = new Intent();
                    i1.setData(Uri.parse("baidumap://map/direction?region=" + MyApplication.userInfo.getCityName() + "&origin=" + currentLocation.getLatitude() + "," + currentLocation.getLongitude() + "&title=我的位置" + "&destination=" + address + "&coord_type=bd09ll&mode=driving&src=andr.baidu.openAPIdemo"));
                    getContext().startActivity(i1);
                } else {
                    ToastUtil.shortToast(getContext(), "请安装百度地图");
                }
            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    ToastUtil.shortToast(getContext(), "没有搜索到地址");
                    return;
                }
                if (CommonUtils.isInstalled(getContext(), "com.baidu.BaiduMap")) {
                    Intent i1 = new Intent();
                    i1.setData(Uri.parse("baidumap://map/direction?region=" + MyApplication.userInfo.getCityName() + "&origin=" + currentLocation.getLatitude() + "," + currentLocation.getLongitude() + "&title=我的位置" + "&destination=" + address + "&coord_type=bd09ll&mode=driving&src=andr.baidu.openAPIdemo"));
                    getContext().startActivity(i1);
                } else {
                    ToastUtil.shortToast(getContext(), "请安装百度地图");
                }
            }
        };

        mSearch.setOnGetGeoCodeResultListener(mListener);
        mSearch.geocode(new GeoCodeOption().city(MyApplication.userInfo.getCityName()).address(address));
        mSearch.destroy();
    }
    GeoCoder search;
    /**
     * 路径规划及显示
     */
    public void drivingRoute() {
        RoutePlanSearch routePlanSearch = RoutePlanSearch.newInstance();
        routePlanSearch.setOnGetRoutePlanResultListener(this);
        search=getView().getMapUtil().getGeoPointByAddress(MyApplication.userInfo.getCityName(), getView().getBaseInfo().getServiceAddress(), new OnGetGeoCoderResultListener() {
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

    @Override
    public void getBDLocation(BDLocation location) {
        currentLocation = location;
        drivingRoute();
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


    /**
     * 打电话
     *
     * @param phoneNum
     */
    public void callPhone(String phoneNum) {
        if (phoneNum == null || "".equals(phoneNum)) {
            ToastUtil.shortToast(getContext(), "电话号码为空");
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
