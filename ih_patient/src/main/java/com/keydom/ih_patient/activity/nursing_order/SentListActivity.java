package com.keydom.ih_patient.activity.nursing_order;

import android.annotation.SuppressLint;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.TextureMapView;
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
import com.baidu.mapapi.utils.DistanceUtil;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.nursing_order.controller.SentListController;
import com.keydom.ih_patient.activity.nursing_order.view.SentListView;
import com.keydom.ih_patient.adapter.NursingChargeBackImgAdapter;
import com.keydom.ih_patient.adapter.NursingOrderAdapter;
import com.keydom.ih_patient.adapter.NursingOrderServiceAdapter;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.bean.NursingOrderBean;
import com.keydom.ih_patient.bean.NursingOrderDetailBean;
import com.keydom.ih_patient.constant.EventType;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * created date: 2018/12/20 on 10:55
 * des:护理订单详情页面
 */
public class SentListActivity extends BaseControllerActivity<SentListController> implements SentListView, OnGetRoutePlanResultListener {
    public static final String ID = "id";
    public static final String STATE = "state";

    private RecyclerView mRecyclerView;
    private View mHeadView;
    private ImageView mCircle;
    private TextView mOrderType;
    private TextView mOrderNum;
    private TextView mName;
    private TextView mPhone;
    private TextView mAddress;
    private TextView mTime;
    private TextView mServiceObj;
    private TextView mHospital;
    private TextView mProject;
    private TextView mProjectContent;
    private RelativeLayout mLaunchInfo;
    private ImageView mBottomLine;

    private TextView mDemandDes;
    private RecyclerView mImgRv;
    private TextView mServiceDes;
    private TextView mServiceCost;
    private LinearLayout mOtherGroup;
    private NursingChargeBackImgAdapter mImgAdapter;
    //地图相关
    private LinearLayout mMapGroup;
    private TextureMapView mMapView;
    private BaiduMap mMap;
    private TextView mDistance;
    private TextView mHospitalAddress;
    private TextView mServiceAddress;

    //底部按钮
    private LinearLayout mBottomGroup;
    private TextView mEvaluteBtn;
    private TextView mPayBtn;

    /**
     * 订单实体
     */
    private NursingOrderBean mOrderBean;
    private NursingOrderServiceAdapter mServiceAdapter;
    /**
     * 路径规划
     */
    RoutePlanSearch mRouteSearch = null;

    /**
     * 距离数组
     */
    private int[] distanceArr = new int[]{20, 50, 100, 200, 500, 1000, 2000, 5000, 10000, 20000, 25000, 50000, 100000, 200000, 500000, 1000000, 2000000, 5000000, 10000000};
    /**
     * 缩放等级数组
     */
    private int[] levelArr = new int[]{21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3};
    private int mDZoom = 11;//默认11级

    /**
     * 订单状态
     */
    private int mState;
    private long mId;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_sent_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("订单详情");
        getView();
        mOrderBean = new NursingOrderBean();
        mMap = mMapView.getMap();


        mRouteSearch = RoutePlanSearch.newInstance();
        mRouteSearch.setOnGetRoutePlanResultListener(this);
        mState = getIntent().getIntExtra(STATE, 0);
        mId = getIntent().getLongExtra(ID, 0);


        mMap.setOnMapTouchListener(motionEvent -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                mRecyclerView.requestDisallowInterceptTouchEvent(false);
            } else {
                mRecyclerView.requestDisallowInterceptTouchEvent(true);
            }
        });
        mMapView.setVisibility(View.GONE);
        mEvaluteBtn.setVisibility(View.GONE);
       hintState();

        mServiceAdapter = new NursingOrderServiceAdapter(new ArrayList<>());
        mServiceAdapter.addHeaderView(mHeadView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mServiceAdapter);

        getController().getDataList(mId, mState,false);
    }

    private void hintState(){
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setCornerRadius(ConvertUtils.dp2px(getResources().getDimension(R.dimen.dp_7)));
        drawable.setColor(getResources().getColor(R.color.white));
        int color = 0;
        String stateStr = "";
        switch (mState) {
            case NursingOrderDetailBean.STATE6:
                stateStr = "已派单";
                color = getResources().getColor(R.color.nursing_status_yellow);
                break;
            case NursingOrderDetailBean.STATE2:
                stateStr = "在途中";
                color = getResources().getColor(R.color.nursing_status_purple);
                break;
            case NursingOrderDetailBean.STATE3:
                stateStr = "服务中";
                color = getResources().getColor(R.color.register_success_color);
                break;
            case NursingOrderDetailBean.STATE4:
                stateStr = "已完成";
                color = getResources().getColor(R.color.list_tab_color);
                mBottomGroup.setVisibility(View.VISIBLE);
                mEvaluteBtn.setVisibility(View.VISIBLE);
                break;
            case NursingOrderDetailBean.Evaluted:
                stateStr = "已评价";
                color = getResources().getColor(R.color.list_tab_color);
                mBottomGroup.setVisibility(View.VISIBLE);
                mEvaluteBtn.setVisibility(View.GONE);
                break;
            case NursingOrderDetailBean.STATE5:
                stateStr = "未支付";
                color = getResources().getColor(R.color.nursing_status_red);
                mBottomGroup.setVisibility(View.VISIBLE);
                break;
            case NursingOrderDetailBean.STATE1:
                stateStr = "护士已接单";
                color = getResources().getColor(R.color.nursing_status_red);
                break;
            case NursingOrderAdapter.CHARGE_BACK:
                stateStr = "已退单";
                color = getResources().getColor(R.color.nursing_status_red);
                mBottomGroup.setVisibility(View.GONE);
                break;
        }
        drawable.setStroke(ConvertUtils.dp2px(getResources().getDimension(R.dimen.dp_3)), color);
        mCircle.setImageDrawable(drawable);
        mOrderType.setText(stateStr);
        mOrderType.setTextColor(color);
    }

    /**
     * 查找控件
     */
    private void getView() {
        mHeadView = LayoutInflater.from(getContext()).inflate(R.layout.activity_sent_list_head, null);
        mCircle = mHeadView.findViewById(R.id.circle);
        mOrderType = mHeadView.findViewById(R.id.order_type);
        mOrderNum = mHeadView.findViewById(R.id.number_content);
        mName = mHeadView.findViewById(R.id.name_content);
        mPhone = mHeadView.findViewById(R.id.phone_content);
        mAddress = mHeadView.findViewById(R.id.address_content);
        mTime = mHeadView.findViewById(R.id.time_content);
        mServiceObj = mHeadView.findViewById(R.id.service_object_content);
        mHospital = mHeadView.findViewById(R.id.hospital_content);
        mProject = mHeadView.findViewById(R.id.project);
        mProjectContent = mHeadView.findViewById(R.id.project_content);
        mLaunchInfo = mHeadView.findViewById(R.id.launch_info);
        mLaunchInfo.setOnClickListener(getController());
        mDemandDes = mHeadView.findViewById(R.id.demand_content);
        mImgRv = mHeadView.findViewById(R.id.img_rv);
        mServiceDes = mHeadView.findViewById(R.id.service_cost_content);
        mOtherGroup = mHeadView.findViewById(R.id.other_group);
        mServiceCost = mHeadView.findViewById(R.id.money);
        mMapGroup = mHeadView.findViewById(R.id.map_group);
        mMapView = mHeadView.findViewById(R.id.mapView);
        mDistance = mHeadView.findViewById(R.id.distance);
        mHospitalAddress = mHeadView.findViewById(R.id.hospital_address);
        mServiceAddress = mHeadView.findViewById(R.id.service_address);

        mBottomLine = mHeadView.findViewById(R.id.bottom_line);
        mRecyclerView = this.findViewById(R.id.recyclerView);

        mBottomGroup = this.findViewById(R.id.bottom_group);
        mPayBtn = this.findViewById(R.id.go_pay);
        mPayBtn.setOnClickListener(getController());
        mEvaluteBtn = this.findViewById(R.id.evaluate);
        mEvaluteBtn.setOnClickListener(getController());
    }

    @Override
    public void launchInfo() {
        mLaunchInfo.setVisibility(View.GONE);
        mProject.setVisibility(View.VISIBLE);
        mProjectContent.setVisibility(View.VISIBLE);
        mBottomLine.setVisibility(View.VISIBLE);
        mOtherGroup.setVisibility(View.VISIBLE);
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void getBasicData(NursingOrderDetailBean data,boolean isPay) {
        NursingOrderDetailBean dto = data.getNursingServiceOrderDetailBaseDto();
        if (data == null || dto == null) {
            return;
        }
        mOrderBean.setOrderNumber(dto.getOrderNumber());
        mOrderBean.setState(dto.getState());
        mState = dto.getState();
        hintState();
        mName.setText(dto.getUserName());
        mOrderNum.setText(dto.getOrderNumber());
        mPhone.setText(dto.getApplyPhone());
        mAddress.setText(dto.getServiceAddress());
        String sex = "";
        if ("1".equals(dto.getPatientSex())) {
            sex = "女";
        }
        if ("0".equals(dto.getPatientSex())) {
            sex = "男";
        }
        mServiceObj.setText(dto.getPatientName() + " " + sex + " " + dto.getPatientAge() + "岁");
        mHospital.setText(dto.getHospital());
        mTime.setText(dto.getTime());
        mDemandDes.setText(dto.getConditionDesciption());
        mProjectContent.setText("¥" + dto.getReservationSet() + "元");
        if (dto.getConditionImage() != null) {
            mImgAdapter = new NursingChargeBackImgAdapter(new ArrayList<>());
            mImgAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    String url = Const.IMAGE_HOST + (String) adapter.getData().get(position);
                    CommonUtils.previewImage(getContext(),url);
                }
            });
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mImgRv.setLayoutManager(linearLayoutManager);
            mImgRv.setAdapter(mImgAdapter);
            List<String> imgs = Arrays.asList(dto.getConditionImage().replace("，", ",").split(","));
            if(imgs!=null&&"".equals(imgs.get(0))){
                mImgAdapter.setNewData(new ArrayList<>());
            }else {
                mImgAdapter.setNewData(imgs);
            }

        }
        if (dto.getOrderDetailItems() != null) {
            int j = 1;
            StringBuffer str = new StringBuffer();
            BigDecimal totolFee=new BigDecimal("0");
            for (int i = 0; i < dto.getOrderDetailItems().size(); i++) {
                NursingOrderDetailBean.OrderDetailItemsBean orderDetailItemsBean = dto.getOrderDetailItems().get(i);
                str.append(j);
                j += 1;
                str.append("、");
                str.append(orderDetailItemsBean.getServiceName());
                str.append("服务费¥");
                str.append(orderDetailItemsBean.getTotalPrice());
                str.append("元");
                str.append("/");
                str.append(orderDetailItemsBean.getFrequency());
                str.append("次");
                str.append("\n");
                totolFee= totolFee.add(orderDetailItemsBean.getTotalPrice());
            }
            mServiceDes.setText(str);
            mServiceCost.setText("¥" + totolFee + "元");
        }
        if (mState == NursingOrderDetailBean.STATE1 || mState == NursingOrderDetailBean.STATE2 || mState == NursingOrderDetailBean.STATE3 || mState == NursingOrderDetailBean.STATE4 || mState == NursingOrderDetailBean.Evaluted) {
            BigDecimal total = new BigDecimal(0.00);
            boolean needPay = false;
            if (data.getSubOrders() != null && data.getSubOrders().size() != 0) {
                for (int i = 0; i < data.getSubOrders().size(); i++) {
                    if (data.getSubOrders().get(i) != null && data.getSubOrders().get(i).getPay() != 1) {
                        if (data.getSubOrders().get(i).getFee() != null) {
                            needPay = true;
                            total = total.add(data.getSubOrders().get(i).getFee());
                        }
                    }
                }
            }
            if (data.getEquipmentItem() != null && data.getEquipmentItem().size() != 0) {
                for (int i = 0; i < data.getEquipmentItem().size(); i++) {
                    if (data.getEquipmentItem().get(i) != null && data.getEquipmentItem().get(i).getTotalMoney() != null && data.getEquipmentItem().get(i).getPay() != 1) {
                        needPay = true;
                        total = total.add(data.getEquipmentItem().get(i).getTotalMoney());
                    }
                }
            }
            if (needPay) {
                mBottomGroup.setVisibility(View.VISIBLE);
                mPayBtn.setVisibility(View.VISIBLE);
                mEvaluteBtn.setVisibility(View.GONE);
                mOrderBean.setPrice(total);
                mPayBtn.setText("确认并支付（¥" + total + "元）");
            }
        }
//        if (mState == NursingOrderDetailBean.STATE4) {
//            BigDecimal total = new BigDecimal(0.00);
//            boolean needPay = false;
//            if (data.getEquipmentItem() != null && data.getEquipmentItem().size() != 0) {
//                for (int i = 0; i < data.getEquipmentItem().size(); i++) {
//                    if (data.getEquipmentItem().get(i) != null && data.getEquipmentItem().get(i).getTotalMoney() != null && data.getEquipmentItem().get(i).getPay() != 1) {
//                        needPay = true;
//                        total = total.add(data.getEquipmentItem().get(i).getTotalMoney());
//                    }
//                }
//            }
//            if (needPay) {
//                mBottomGroup.setVisibility(View.VISIBLE);
//                mPayBtn.setVisibility(View.VISIBLE);
//                mEvaluteBtn.setVisibility(View.VISIBLE);
//                mOrderBean.setPrice(total);
//                mPayBtn.setText("确认并支付（¥" + total + "元）");
//            }
//        }
        if (mState == NursingOrderDetailBean.STATE5) {
            BigDecimal total = dto.getReservationSet();
            mBottomGroup.setVisibility(View.VISIBLE);
            mPayBtn.setVisibility(View.VISIBLE);
            mOrderBean.setPrice(total);
            mPayBtn.setText("确认并支付（¥" + total + "元）");
        }
        if (mState == NursingOrderDetailBean.STATE2) {
            mHospitalAddress.setText(dto.getHospitalAddress());
            mServiceAddress.setText(dto.getServiceAddress());
            mMapGroup.setVisibility(View.VISIBLE);
            mMapView.setVisibility(View.VISIBLE);
            mMapView.onResume();
            try {
                getAddressLatAndLng(new LatLng(Double.parseDouble(dto.getLatitude()), Double.parseDouble(dto.getLongitude())), dto.getCity(), dto.getServiceAddress());
            } catch (Exception e) {

            }
        }
        if (isPay){
            getController().showPayDialog(mOrderBean);
        }
    }

    @Override
    public void getListData(List<MultiItemEntity> list) {
        mServiceAdapter.setNewData(list);
    }

    @Override
    public NursingOrderBean getOrderDetail() {
        return mOrderBean;
    }

    @Override
    public long getId() {
        return mId;
    }

    @Override
    public int getState() {
        return mState;
    }

    @Override
    public void paySuccess() {
        EventBus.getDefault().post(new Event(EventType.NURSING_PAY_SUCCESS, null));
        finish();
    }

    /**
     * 设置地图缩放级别
     */
    private int setLevel(LatLng llStart, LatLng llEnd) {
        //起点： latitude纬度           longitude经度
        int distance = (int) DistanceUtil.getDistance(llStart, llEnd);
        //            distanceStr = "距离约" + distance + "米";

        int level = getLevel(distance);

        //设置缩放级别
        return levelArr[level];
    }

    /**
     * 根据距离计算出差值数组,并排序（取正数）
     */
    private int getLevel(int distance) {
        int level = -1;
        int min = 10000000;
        for (int i = 0; i < distanceArr.length; i++) {
            if (distanceArr[i] - distance > 0 && distanceArr[i] - distance < min) {
                min = distanceArr[i] - distance;
                level = i;
            }
        }
        return level;
    }

    /**
     * 计算两点间直线距离
     */
    public double getDistance(LatLng start, LatLng end) {

        double lon1 = (Math.PI / 180) * start.longitude;
        double lon2 = (Math.PI / 180) * end.longitude;
        double lat1 = (Math.PI / 180) * start.latitude;
        double lat2 = (Math.PI / 180) * end.latitude;

        // 地球半径
        double R = 6371;

        // 两点间距离 km，如果想要米的话，结果*1000就可以了
        double d = Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1))
                * R;

        return d;
    }

    /**
     * 获取到地址的详细经纬度
     */
    private void getAddressLatAndLng(LatLng start, String cityName, String address) {
        GeoCoder mSearch = GeoCoder.newInstance();
        OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
            public void onGetGeoCodeResult(GeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    ToastUtils.showShort("目的地定位失败");
                    Logger.e("没有检索到结果");
                } else {
                    Logger.e("获取地理编码结果:  lat=" + result.getLocation().latitude + "lng=" + result.getLocation().longitude);
                    LatLng end = new LatLng(result.getLocation().latitude, result.getLocation().longitude);

                    double distance = getDistance(start, end);
                    BigDecimal bd = new BigDecimal(distance);
                    DecimalFormat df = new DecimalFormat("#0.0");
                    mDistance.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mDistance.setText("距离服务地址" + df.format(bd) + "km");
                        }
                    }, 500);

                    int level = setLevel(start, end);
                    mRouteSearch.drivingSearch(new DrivingRoutePlanOption().from(PlanNode.withLocation(start)).to(PlanNode.withLocation(end)));
                    MapStatus mMapStatus = new MapStatus.Builder()
                            .target(result.getLocation())
                            .zoom(levelArr[level])
                            .build();
                    MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                    mMap.setMapStatus(mMapStatusUpdate);
                }
            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {

            }
        };

        mSearch.setOnGetGeoCodeResultListener(listener);
        mSearch.geocode(new GeoCodeOption()
                .city(cityName)
                .address(address));
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
    public void onGetDrivingRouteResult(DrivingRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            LogUtils.e("未找到地图结果");
            ToastUtils.showShort("未找到地图结果");
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            // result.getSuggestAddrInfo()
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            List<DrivingRouteLine> routeLines = result.getRouteLines();
            if (routeLines != null && routeLines.size() > 0) {
                Collections.sort(routeLines, (DrivingRouteLine o1, DrivingRouteLine o2) ->
                        o1.getDistance() - o2.getDistance());

                DrivingRouteOverlay overlay = new DrivingRouteOverlay(this, mMap);
                mMap.setOnMarkerClickListener(overlay);
                overlay.setData(routeLines.get(0));
                overlay.addToMap();
                overlay.zoomToSpan();

            } else {
                LogUtils.d("route result", "结果数<0");
            }
        }
    }

    @Override
    public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

    }


    @Override
    protected void onPause() {
        // activity 暂停时同时暂停地图控件
        super.onPause();
        mMapView.onPause();
//        MKOLUpdateElement temp = mOffline.getUpdateInfo(GY_CITY_ID);
//        if (temp != null && temp.status == MKOLUpdateElement.DOWNLOADING) {
//            mOffline.pause(GY_CITY_ID);
//        }
    }

    @Override
    protected void onResume() {
        // activity 恢复时同时恢复地图控件
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onDestroy() {
//        mOffline.destroy();
        super.onDestroy();
        if (mRouteSearch != null) {
            mRouteSearch.destroy();
        }
        mMapView.onDestroy();
        // activity 销毁时同时销毁地图控件

    }
}

