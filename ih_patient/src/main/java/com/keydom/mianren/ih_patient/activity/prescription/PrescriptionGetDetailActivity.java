package com.keydom.mianren.ih_patient.activity.prescription;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.keydom.ih_common.base.BaseActivity;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.prescription.view.AddressMessageView;
import com.keydom.mianren.ih_patient.activity.prescription.view.BaseInfoView;
import com.keydom.mianren.ih_patient.activity.prescription.view.ReviewInfo;
import com.keydom.mianren.ih_patient.adapter.FragentPagerPrescriAdapter;
import com.keydom.mianren.ih_patient.adapter.LogisticDetailAdapter;
import com.keydom.mianren.ih_patient.bean.entity.LatXyEntity;
import com.keydom.mianren.ih_patient.bean.entity.LogisticsEntity;
import com.keydom.mianren.ih_patient.bean.entity.LogisticsEnum;
import com.keydom.mianren.ih_patient.bean.entity.PharmacyEntity;
import com.keydom.mianren.ih_patient.bean.entity.pharmacy.PharmacyBean;
import com.keydom.mianren.ih_patient.bean.entity.pharmacy.PrescriptionItemEntity;
import com.keydom.mianren.ih_patient.bean.entity.pharmacy.infoEntity;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.fragment.ContentFragment;
import com.keydom.mianren.ih_patient.net.PrescriptionService;
import com.keydom.mianren.ih_patient.utils.CommUtil;
import com.keydom.mianren.ih_patient.utils.DataCacheUtil;
import com.keydom.mianren.ih_patient.utils.GotoActivityUtil;
import com.keydom.mianren.ih_patient.utils.MapUtil;
import com.keydom.mianren.ih_patient.view.MyViewPager;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

public class PrescriptionGetDetailActivity extends BaseActivity implements View.OnClickListener {


    public static final String PRESCRIPTION_ID = "prescription_id";
    public static final String ACQUIRE_MEDICINE = "acquire_medicine";
    //???0-???????????????1-???????????????
    public static final int TAKE_MEDICINE = 0;
    public static final int RECEIVE_MEDICINE = 1;


    private Button mBtnGoMap;
    private LinearLayout mLinearAddress, mLinearPrescription, mLinaLog, mLinaBottom, mLinaLogic;
    private RelativeLayout mLinAdd, mReHePrescription;
    private View mViewPrescription;
    private Button mBtnCloseAddress, mBtnCloseBase, mBtnClosePrescription, mBtnCloseInfo,
            mBtnCloseLog;

    private MyViewPager mViewPager;
    private RecyclerView mRecyclerView;
    private LogisticDetailAdapter mLogisticDetailAdapter;
    private TabLayout mTabLayout;
    private List<Fragment> fragments;
    private Button mBtnCancel, mBtnConfirm;

    private SmartRefreshLayout mSmartRefreshLayout;
    private FragmentManager fm;
    private BaseInfoView mBaseInfoView;
    private ReviewInfo mReviewInfo;
    String city;

    private double latx = 39.9037448095;
    private double laty = 116.3980007172;
    private String mAddress = "???????????????";

    private double lat;
    private double lng;

    private double mLat;
    private double mLng;

    private String mDrugstoreAddress;
    private String mDrugstore;

    boolean flag = true;
    String data[] = {"?????????????????????", "?????????????????????", "?????????????????????"};
    private List<PharmacyBean> mDatas;
    private String mPharmacyAddress;
    /**
     * ??????????????????
     */
    public static final double DEFAULT_GPS_LAT = 30.663919;
    /**
     * ??????????????????
     */
    public static final double DEFAULT_GPS_LNG = 104.071144;
    /**
     * ??????????????????
     *
     * @return
     */
    // ????????????
    LocationClient mLocClient;

    private LocationClient locationClient;
    public MapView mapView = null;
    public BaiduMap baiduMap = null;
    //???????????????
    BitmapDescriptor mCurrentMarker = null;
    boolean isFirstLoc = true;// ??????????????????
    private List<Overlay> mOverlayList = new LinkedList<>();
    private String mPrescriptionId = "100100";
    private int mAcquireMedicine = TAKE_MEDICINE;
    private String mWallBill = null;
    private TextView mWallCode;
    private TextView mLogicCompany;
    private TextView mDeliveryTimeTv;
    private AddressMessageView mAddressMessageView;
    private TextView mTotalSumPriceTv; //????????????


    @Override
    public int getLayoutRes() {
        return R.layout.activity_prescription_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        // initData();
        mWallCode = findViewById(R.id.prescription_detail_wallcode_tv);
        mLogicCompany = findViewById(R.id.prescription_detail_courier_tv);
        mDeliveryTimeTv = findViewById(R.id.prescription_detail_delivery_time_tv);

        /**
         * ???????????????
         */
        mapView = (MapView) findViewById(R.id.map_view);
        mBtnGoMap = findViewById(R.id.btn_go_map);

        mLinearAddress = findViewById(R.id.linear_address);
        mLinAdd = findViewById(R.id.lina_add);
        mLinearPrescription = findViewById(R.id.linear_prescription);
        mLinaLog = findViewById(R.id.lina_log);
        mLinaBottom = findViewById(R.id.linear_bottom);
        mRecyclerView = findViewById(R.id.recycler_title);
        mLinaLogic = findViewById(R.id.lina_logic);

        mBtnCloseAddress = findViewById(R.id.btn_close_address);
        mBtnCloseBase = findViewById(R.id.btn_close_base);
        mBtnClosePrescription = findViewById(R.id.btn_close_prescription);
        mBtnCloseInfo = findViewById(R.id.btn_close_info);
        mBtnCloseLog = findViewById(R.id.btn_close_log);

        mReHePrescription = findViewById(R.id.re_he_prescription);
        mViewPrescription = findViewById(R.id.view_prescription);


        mBaseInfoView = findViewById(R.id.base_view);

        mReviewInfo = findViewById(R.id.re_view);
        mSmartRefreshLayout = findViewById(R.id.refresh_layout);

        mBtnCancel = findViewById(R.id.btn_cancel);
        mBtnConfirm = findViewById(R.id.btn_confirm);
        mViewPager = findViewById(R.id.vp_prescription);
        mTabLayout = findViewById(R.id.tab_prescription);
        mTotalSumPriceTv = findViewById(R.id.tv_combined_price);


        mAddressMessageView = findViewById(R.id.view_address);

        LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
        layoutmanager.setOrientation(LinearLayoutManager.VERTICAL);
        //??????RecyclerView ??????
        mRecyclerView.setLayoutManager(layoutmanager);
        mRecyclerView.setNestedScrollingEnabled(false);
        mLogisticDetailAdapter = new LogisticDetailAdapter(this);
        mRecyclerView.setAdapter(mLogisticDetailAdapter);


        mBtnGoMap.setOnClickListener(this);

        mBtnCancel.setOnClickListener(this);
        mBtnConfirm.setOnClickListener(this);
        mBtnCloseAddress.setOnClickListener(this);
        mBtnCloseBase.setOnClickListener(this);
        mBtnClosePrescription.setOnClickListener(this);
        mBtnCloseInfo.setOnClickListener(this);
        mBtnCloseLog.setOnClickListener(this);

        /**
         * ???????????????
         */
        baiduMap = mapView.getMap();
        //??????????????????
        //   baiduMap.setMyLocationEnabled(true);
        /**
         * ??????
         */
        mLocClient = new LocationClient(this);

        //        MyLocationListener myListener = new MyLocationListener();
        //        mLocClient.registerLocationListener(myListener);
        //        LocationClientOption option = new LocationClientOption();
        //        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        // ?????????????????????????????????????????????????????????????????????????????????
        //        option.setOpenGps(true); // ??????gps
        //        option.setCoorType("bd09ll"); // ??????????????????
        //        option.setScanSpan(3000);
        //        option.setIsNeedAddress(true);
        //        mLocClient.setLocOption(option);
        //        mLocClient.start();
        initLocation();

        mBaseInfoView.setOnImageClickListener(new BaseInfoView.OnImageClickListener() {
            @Override
            public void onMoreClick(View v, PharmacyEntity entity) {
                GotoActivityUtil.gotoZxingActivity(PrescriptionGetDetailActivity.this,
                        entity.getAcquireMedicineCode());
                Logger.e("piii-" + entity);
            }
        });
        /**
         * ??????
         */
        mSmartRefreshLayout.setEnableRefresh(true);
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                getPrescriptionBaseInfo(mPrescriptionId);
                getHttpPrescriptionItemList();
            }
        });


        if (getIntent() != null && getIntent().getExtras() != null) {
            mPrescriptionId = getIntent().getExtras().getString(PRESCRIPTION_ID);
            mAcquireMedicine = getIntent().getExtras().getInt(ACQUIRE_MEDICINE);
        }

        /**
         * ??????????????????????????????
         */
        if (mAcquireMedicine == TAKE_MEDICINE) {
            mLinAdd.setVisibility(View.VISIBLE);
            mLinearAddress.setVisibility(View.VISIBLE);

            mLinaLog.setVisibility(View.GONE);
            mLinaBottom.setVisibility(View.GONE);
        } else if (mAcquireMedicine == RECEIVE_MEDICINE) {
            mLinAdd.setVisibility(View.GONE);
            mLinearAddress.setVisibility(View.GONE);

            mLinaLog.setVisibility(View.VISIBLE);
            mLinaBottom.setVisibility(View.GONE);
        }
        getPrescriptionBaseInfo(mPrescriptionId);
        getHttpPrescriptionItemList();

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("????????????");
    }

    /**
     * ??????????????????????????????
     */
    public void init() {
        fragments = new ArrayList<>();
        double subTotal = 0;
        if (!CommUtil.isEmpty(mDatas)) {
            for (int i = 0; i < mDatas.size(); i++) {
                ContentFragment fragment = ContentFragment.newInstance(mDatas.get(i).getData(), i
                        , mViewPager);
                Logger.e("ii=" + mDatas.get(0));
                fragments.add(fragment);
                subTotal += Double.valueOf(mDatas.get(i).getSubtotal());
            }

        }
        mTotalSumPriceTv.setText("???" + String.format("%.2f", subTotal));
        FragentPagerPrescriAdapter fragentPagerPrescriAdapter =
                new FragentPagerPrescriAdapter(getSupportFragmentManager(), fragments, mDatas);
        mViewPager.setAdapter(fragentPagerPrescriAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mViewPager.resetHeight(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.resetHeight(0);
        mTabLayout.setupWithViewPager(mViewPager);
        if (!CommUtil.isEmpty(mDatas)) {
            for (int i = 0; i < mDatas.size(); i++) {
                mTabLayout.getTabAt(i).setCustomView(getTabView(i));
            }
        }

    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_detail_title, null);
        TextView txt_title = view.findViewById(R.id.tv_title);

        if (!CommUtil.isEmpty(mDatas.get(position).getPrescriptionType())) {
            int num = position + 1;
            if (mDatas.get(position).getPrescriptionType().equals("0")) {
                txt_title.setText("??????" + num + "??????");
            } else {
                txt_title.setText("??????" + num + "??????");
            }
        }
        //  txt_title.setText(mDatas.get(position).getPrescriptionType());

        ImageView img_title = view.findViewById(R.id.image_dots);
        img_title.setImageResource(R.drawable.select_dots_gray);
        return view;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //???activity??????onResume???????????????mMapView. onResume ()
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //???activity??????onPause???????????????mMapView. onPause ()
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //???activity??????onDestroy???????????????mMapView.onDestroy()
        mapView.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_go_map:
                openMap(mDrugstoreAddress);
                // GotoActivityUtil.gotoQueryLogisticActivity(PrescriptionGetDetailActivity.this);
                break;
            case R.id.btn_close_address:
                if (flag) {
                    mLinearAddress.setVisibility(View.GONE);
                    flag = false;
                } else {
                    mLinearAddress.setVisibility(View.VISIBLE);
                    flag = true;
                }
                break;
            case R.id.btn_close_base:
                if (flag) {
                    mBaseInfoView.setVisibility(View.GONE);
                    flag = false;
                } else {
                    mBaseInfoView.setVisibility(View.VISIBLE);
                    flag = true;
                }
                break;
            case R.id.btn_close_prescription:
                if (flag) {
                    mLinearPrescription.setVisibility(View.GONE);
                    mReHePrescription.setVisibility(View.GONE);
                    mViewPrescription.setVisibility(View.GONE);
                    flag = false;
                } else {
                    mLinearPrescription.setVisibility(View.VISIBLE);
                    mReHePrescription.setVisibility(View.VISIBLE);
                    mViewPrescription.setVisibility(View.VISIBLE);
                    flag = true;
                }
                break;
            case R.id.btn_close_info:
                if (flag) {
                    mReviewInfo.setVisibility(View.GONE);
                    flag = false;
                } else {
                    mReviewInfo.setVisibility(View.VISIBLE);
                    flag = true;
                }
                break;
            case R.id.btn_close_log:
                if (flag) {
                    mLinaLogic.setVisibility(View.GONE);
                    flag = false;
                } else {
                    mLinaLogic.setVisibility(View.VISIBLE);
                    flag = true;
                }
                break;
            case R.id.btn_cancel:
                new GeneralDialog(this, "?????????????????????", new GeneralDialog.OnCloseListener() {
                    @Override
                    public void onCommit() {
                        refuseOrder(mPrescriptionId, mWallBill);
                    }
                }).setTitle("??????").setNegativeButton("??????").setPositiveButton("??????").show();
                break;
            case R.id.btn_confirm:
                new GeneralDialog(this, "??????????????????", new GeneralDialog.OnCloseListener() {
                    @Override
                    public void onCommit() {
                        confirmOrder(mPrescriptionId, mWallBill);
                    }
                }).setTitle("??????").setNegativeButton("??????").setPositiveButton("??????").show();
                break;
            default:
                break;
        }
    }

    /**
     * ????????????
     */
    public void openMap(String drugstoreAddress) {
        LatLng latLng = MapUtil.BD09ToGCJ02(new LatLng(lat, lng));
        if (MapUtil.isBaiduMapInstalled()) {
            MapUtil.openBaiDuNavi(PrescriptionGetDetailActivity.this, latLng.latitude,
                    latLng.longitude, "????????????", mLat, mLng, drugstoreAddress);
        } else if (MapUtil.isGdMapInstalled()) {
            MapUtil.openGaoDeNavi(PrescriptionGetDetailActivity.this, latLng.latitude,
                    latLng.longitude, "????????????", mLat, mLng, drugstoreAddress);
        } else if (MapUtil.isTencentMapInstalled()) {
            MapUtil.openTencentMap(PrescriptionGetDetailActivity.this, latLng.latitude,
                    latLng.longitude, "????????????", mLat, mLng, drugstoreAddress);
        } else {
            Uri uri = Uri.parse("market://details?id=com.baidu.BaiduMap");//id?????????
            Intent it = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(it);
        }
    }

    /**
     * ???????????????
     */
    @SuppressLint("CheckResult")
    private void initLocation() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (granted) {
                            Logger.e("???????????????");
                            if (locationClient == null)
                                locationClient =
                                        new LocationClient(PrescriptionGetDetailActivity.this);
                            MyLocationListener myListener = new MyLocationListener();
                            locationClient.registerLocationListener(myListener);
                            LocationClientOption option = new LocationClientOption();
                            option.setIsNeedAddress(true);
                            locationClient.setLocOption(option);
                            locationClient.start();
                        } else {
                            Logger.e("???????????????");
                            ToastUtil.showMessage(PrescriptionGetDetailActivity.this,
                                    "????????????????????????????????????????????????????????????");

                        }
                    }
                });

    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {

            String addr = location.getAddrStr();    //????????????????????????
            String country = location.getCountry();    //????????????
            String province = location.getProvince();    //????????????
            city = location.getCity();    //????????????
            String district = location.getDistrict();    //????????????
            String street = location.getStreet();    //??????????????????
            lat = location.getLatitude();
            lng = location.getLongitude();
            LatXyEntity latXyEntity = new LatXyEntity();
            if (lat != 0) {
                latXyEntity.setLat(lat);
            }
            if (lat != 0) {
                latXyEntity.setLng(lng);
            }
            DataCacheUtil.getInstance().putlatXy(latXyEntity);
            Logger.e("????????????????????????" + country + province + city + addr + lat);
        }
    }

    //    private void initData() {
    //        mDatas = new ArrayList<String>();
    //        for (int j = 0; j < data.length; j++) {
    //            mDatas.add(data[j]);
    //        }
    //    }


    /**
     * ????????????????????????????????????
     * ????????????
     *
     * @param prescriptionId
     * @param acquireMedicine
     */
    public void getHttpInfo(String prescriptionId, String acquireMedicine) {
        Map<String, Object> map = new HashMap<>();
        map.put("prescriptionId", prescriptionId);
        map.put("acquireMedicine", acquireMedicine);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PrescriptionService.class).getPrescriptionDetails(map), new HttpSubscriber<PharmacyEntity>(this, getDisposable(), true, true) {
            @Override
            public void requestComplete(@Nullable PharmacyEntity data) {
                Logger.e("nnnn=" + data);
                if (data != null) {
                    refreshViewInfo(data);
                    mDrugstoreAddress = data.getDrugsStoreAddress();
                    mDrugstore = data.getDrugstore();
                    if (!CommUtil.isEmpty(Global.getLocationCity()) && !CommUtil.isEmpty(data.getDrugsStoreAddress())) {
                        getAddressLatAndLng(Global.getLocationCity(), data.getDrugsStoreAddress());
                    }
                } else {
                    pageEmpty();
                }
                mSmartRefreshLayout.finishRefresh();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * ????????????????????????
     */
    private void getHttpPrescriptionItemList() {
        Map<String, Object> map = new HashMap<>();
        map.put("prescriptionId", mPrescriptionId);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PrescriptionService.class).getprescriptionItemList(map), new HttpSubscriber<List<PharmacyBean>>(this, getDisposable(), true, true) {
            @Override
            public void requestComplete(@Nullable List<PharmacyBean> data) {
                if (!CommUtil.isEmpty(data)) {
                    refreshView(data);
                    List<PrescriptionItemEntity> prescriptionItemEntities = new ArrayList<>();
                    for (int i = 0; i < data.size(); i++) {
                        prescriptionItemEntities.addAll(data.get(i).getData());
                    }
                    //                    List<PrescriptionItemEntity>
                    //                    prescriptionItemEntities=new ArrayList<>();
                    //                    for (PrescriptionItemEntity prescriptionItemEntity :
                    //                    data.get(0).getData()) {
                    //                        prescriptionItemEntities.add(prescriptionItemEntity);
                    //                    }
                    DataCacheUtil.getInstance().putPrescriptionItemEntity(prescriptionItemEntities);
                }

                mSmartRefreshLayout.finishRefresh();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                return super.requestError(exception, code, msg);
            }
        });

    }

    /**
     * ????????????????????????
     *
     * @param id
     */
    public void getLogistics(String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("prescriptionId", id);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PrescriptionService.class).getLogisticInfoByPrescriptionId(map), new HttpSubscriber<LogisticsEntity>(this, getDisposable(), true, true) {
            @Override
            public void requestComplete(@Nullable LogisticsEntity logisticsEntity) {
                if (!CommUtil.isEmpty(logisticsEntity.getWaybill())) {
                    mWallCode.setText(logisticsEntity.getWaybill());
                    mWallBill = logisticsEntity.getWaybill();
                }
                if (!CommUtil.isEmpty(logisticsEntity.getCarrier())) {
                    mLogicCompany.setText(logisticsEntity.getCarrier());
                }
                if (!CommUtil.isEmpty(logisticsEntity.getDeliveryTime())) {
                    mDeliveryTimeTv.setText(logisticsEntity.getDeliveryTime());
                }

                Logger.e("uuuu=" + logisticsEntity);
                List<LogisticsEntity> logisticsEntityList = new ArrayList<>();

                //todo ????????????????????????
                if (!CommUtil.isEmpty(logisticsEntity.getInfoList())) {
                    LogisticsEntity new3 = new LogisticsEntity();

                    new3.setTitle(logisticsEntity.getStatus().getName());
                    //new3.setTitle(getTitleValue(logisticsEntity.getStatus()));
                    new3.setInfoList(logisticsEntity.getInfoList());
                    logisticsEntityList.add(new3);

                    Logger.e("3=" + logisticsEntityList);
                }
                //todo ?????????????????????
                if (!CommUtil.isEmpty(logisticsEntity.getDeliveryTime())) {
                    infoEntity infoEntity = new infoEntity();
                    infoEntity.setAcceptTime(logisticsEntity.getOrderTime());
                    infoEntity.setAcceptStation("??????????????????" + logisticsEntity.getDrugstore() + "??????");
                    List<infoEntity> logistics = new ArrayList<>();
                    logistics.add(infoEntity);

                    LogisticsEntity new2 = new LogisticsEntity();
                    new2.setTitle("?????????");
                    new2.setInfoList(logistics);
                    logisticsEntityList.add(new2);

                    Logger.e("2=" + logisticsEntityList);
                }
                //todo ?????????????????????
                if (!CommUtil.isEmpty(logisticsEntity.getOrderTime())) {

                    infoEntity infoEntity = new infoEntity();
                    infoEntity.setAcceptTime(logisticsEntity.getOrderTime());
                    infoEntity.setAcceptStation("?????????????????????????????????????????????????????????");
                    List<infoEntity> logistics = new ArrayList<>();
                    logistics.add(infoEntity);

                    LogisticsEntity new1 = new LogisticsEntity();
                    new1.setTitle("?????????");
                    new1.setInfoList(logistics);

                    logisticsEntityList.add(new1);
                    Logger.e("1=" + logisticsEntityList);
                }
                if (!CommUtil.isEmpty(logisticsEntityList)) {
                    refreshViewLogistic(logisticsEntityList);
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                Logger.e("msg=" + msg);
                Logger.e("code=" + code);
                // loadingFail(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    private void refreshViewLogistic(List<LogisticsEntity> logisticsEntities) {
        Logger.e("4=" + logisticsEntities);
        mLogisticDetailAdapter.setList(logisticsEntities);
    }

    public String getTitleValue(int status) {
        String mTitle = "";
        switch (status) {
            case 0:
                mTitle = LogisticsEnum.ON_WAY.getName();
                break;
            case 1:
                mTitle = LogisticsEnum.TOOK_A.getName();
                break;
            case 2:
                mTitle = LogisticsEnum.QUESTION.getName();
                break;
            case 3:
                mTitle = LogisticsEnum.FINISH.getName();
                break;
            case 4:
                mTitle = LogisticsEnum.BACK_SIGN.getName();
                break;
            case 5:
                mTitle = LogisticsEnum.CITY_SEND.getName();
                break;
            case 6:
                mTitle = LogisticsEnum.BACK_GOODS.getName();

                break;
            case 7:
                mTitle = LogisticsEnum.TURN.getName();

                break;
        }
        return mTitle;
    }

    /**
     * ????????????
     *
     * @param entity
     */
    private void refreshViewInfo(PharmacyEntity entity) {
        Logger.e("lllll=" + entity);
        mBaseInfoView.getData(entity);
        mAddressMessageView.getData(entity);
        mReviewInfo.getData(entity);
        if (!CommUtil.isEmpty(entity.getPharmacyAddress())) {
            mPharmacyAddress = entity.getPharmacyAddress();
        }
    }

    private void refreshView(List<PharmacyBean> data) {
        mDatas = new ArrayList<>();
        mDatas = data;
        init();
    }

    /**
     * ????????????????????????
     */
    private void getAddressLatAndLng(String cityName, String address) {
        GeoCoder mSearch = GeoCoder.newInstance();
        OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
            public void onGetGeoCodeResult(GeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR)
                    ToastUtil.showMessage(PrescriptionGetDetailActivity.this, "????????????????????????");
                else {
                    mLat = result.getLocation().latitude;
                    mLng = result.getLocation().longitude;
                    Logger.e("????????????????????????:  lat=" + result.getLocation().latitude + "lng=" + result.getLocation().longitude);
                    addMarkerMap(result.getLocation());
                    // getController().getHospital(getQueryMap(selectedLocation, result
                    // .getLocation().latitude, result.getLocation().longitude));
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

    private void addMarkerMap(LatLng result) {
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(result)
                .zoom(17)
                .build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        baiduMap.setMapStatus(mMapStatusUpdate);
        LatLng point = result;
        BitmapDescriptor bttmap = null;
        View item_view =
                LayoutInflater.from(PrescriptionGetDetailActivity.this).inflate(R.layout.map_overlay_layout, null);
        TextView tv_storeName = (TextView) item_view.findViewById(R.id.overlay_name);
        ImageView imageView = (ImageView) item_view.findViewById(R.id.overlay_icon);
        tv_storeName.setText(mDrugstore);
        imageView.setImageResource(R.mipmap.mine_location_yellow);
        bttmap = BitmapDescriptorFactory.fromView(item_view);
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bttmap);
        baiduMap.addOverlay(option);
    }


    /**
     * ????????????????????????????????????
     * ????????????
     *
     * @param prescriptionId
     */
    public void getPrescriptionBaseInfo(String prescriptionId) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", prescriptionId);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PrescriptionService.class).getPrescriptionByIdOrCode(map), new HttpSubscriber<PharmacyEntity>(this, getDisposable(), true, true) {
            @Override
            public void requestComplete(@Nullable PharmacyEntity data) {
                Logger.e("nnnn=" + data);
                if (data != null) {
                    refreshViewInfo(data);

                    if (mAcquireMedicine == RECEIVE_MEDICINE && data.getPaystatus().getValue().equals("3")) {
                        mLinaBottom.setVisibility(View.VISIBLE);
                    } else {
                        mLinaBottom.setVisibility(View.GONE);
                    }
                    mWallBill = data.getWaybill();
                    mDrugstoreAddress = data.getDrugsStoreAddress();
                    mDrugstore = data.getDrugstore();
                    mLat = data.getDrugsStoreLatitude();
                    mLng = data.getDrugsStoreLongitude();
                    if (0 != mLng && 0 != mLat) {
                        //??????Maker?????????
                        LatLng point = new LatLng(mLat, mLng);
                        addMarkerMap(point);
                    } else {
                        if (!CommUtil.isEmpty(Global.getLocationCity()) && !CommUtil.isEmpty(data.getDrugsStoreAddress())) {
                            getAddressLatAndLng(Global.getLocationCity(),
                                    data.getDrugsStoreAddress());
                        }
                    }

                    getLogistics(data.getPrescriptionId());

                } else {
                    pageEmpty();
                }
                mSmartRefreshLayout.finishRefresh();
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
     *
     * @param prescriptionId
     * @param waybill
     */
    public void confirmOrder(String prescriptionId, String waybill) {
        Map<String, Object> map = new HashMap<>();
        map.put("prescriptionId", prescriptionId);
        map.put("waybill", waybill);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PrescriptionService.class).confirmOrder(map), new HttpSubscriber<String>(this, getDisposable(), true, true) {
            @Override
            public void requestComplete(@Nullable String data) {
                mSmartRefreshLayout.autoRefresh();
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
     *
     * @param prescriptionId
     * @param waybill
     */
    public void refuseOrder(String prescriptionId, String waybill) {
        Map<String, Object> map = new HashMap<>();
        map.put("prescriptionId", prescriptionId);
        map.put("waybill", waybill);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PrescriptionService.class).refuseOrder(map), new HttpSubscriber<String>(this, getDisposable(), true, true) {
            @Override
            public void requestComplete(@Nullable String data) {
                mSmartRefreshLayout.autoRefresh();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                return super.requestError(exception, code, msg);
            }
        });
    }
}
