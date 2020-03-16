package com.keydom.mianren.ih_patient.activity.nursing_service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.common_document.CommonDocumentActivity;
import com.keydom.mianren.ih_patient.activity.nursing_service.controller.NursingChooseHospitalController;
import com.keydom.mianren.ih_patient.activity.nursing_service.view.NursingChooseHospitalView;
import com.keydom.mianren.ih_patient.bean.BaseNurseFeeBean;
import com.keydom.mianren.ih_patient.bean.CommonDocumentBean;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.HospitalLocationInfo;
import com.keydom.mianren.ih_patient.bean.LocationInfo;
import com.keydom.mianren.ih_patient.bean.NursingProjectInfo;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.keydom.mianren.ih_patient.constant.Global;
import com.orhanobut.logger.Logger;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 选择医院页面
 */
public class NursingChooseHospitalActivity extends BaseControllerActivity<NursingChooseHospitalController> implements NursingChooseHospitalView {
    /**
     * 启动
     */
    public static void start(Context context, NursingProjectInfo nursingProjectInfo) {
        Intent intent = new Intent(context, NursingChooseHospitalActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("nursingProjectInfo", nursingProjectInfo);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    private TextView choose_location_tv, nursing_service_notice_tv, cancel_tv, nursing_order_tv, notice_tv,overlay_name,service_label;
    private MapView nursing_service_mv;
    private BaiduMap mMap;
    private CheckBox nursing_service_notice_ck;
    private NursingProjectInfo nursingProjectInfo;
    private boolean isAgreement = false;
    private String serviceAddress = null;
    private long hospitalAreaId = -1;
    private LocationInfo selectedLocation;
    private boolean isSelectedLocationAvailable = false;
    private boolean isHasServiceHospital = false;
    private boolean isCanContinue=false;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_nursing_chooselocation_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        RichText.initCacheDir(this);
        setTitle("护理服务");
        nursingProjectInfo = (NursingProjectInfo) getIntent().getSerializableExtra("nursingProjectInfo");
        choose_location_tv = findViewById(R.id.choose_location_tv);
        notice_tv = findViewById(R.id.notice_tv);
        overlay_name=findViewById(R.id.overlay_name);
        choose_location_tv.setOnClickListener(getController());
        service_label=findViewById(R.id.service_label);
        service_label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonDocumentActivity.start(getContext(),CommonDocumentBean.CODE_13);

            }
        });
        nursing_service_notice_tv = findViewById(R.id.nursing_service_notice_tv);
        nursing_service_notice_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AgreementActivity.startNurseAgreement(getContext());
                CommonDocumentActivity.start(getContext(),CommonDocumentBean.CODE_2);
            }
        });
        cancel_tv = findViewById(R.id.cancel_tv);
        nursing_order_tv = findViewById(R.id.nursing_order_tv);
        nursing_order_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isCanContinue){
                    ToastUtil.showMessage(getContext(), "正在拉取服务医院，请稍等");
                    return;
                }else {
                    if (serviceAddress == null) {
                        ToastUtil.showMessage(getContext(), "您还未选择服务地址");
                        return;
                    }
                    if (!isHasServiceHospital) {
                        ToastUtil.showMessage(getContext(), "当前服务范围内暂无可服务医院，无法提供预约服务");
                        return;
                    }
                    if(hospitalAreaId==-1){
                        ToastUtil.showMessage(getContext(), "当前医院不在服务范围内，请从地图上选取服务范围内的医院");
                        return;
                    }
                    if (isAgreement) {
                        NursingOrderFillInActivity.start(getContext(), nursingProjectInfo, serviceAddress, hospitalAreaId, false, null);
                    } else {
                        ToastUtil.showMessage(getContext(), "您还未阅读并同意护理服务相关用户协议");
                    }

                }

            }
        });
        nursing_service_mv = findViewById(R.id.nursing_service_mv);
        nursing_service_notice_ck = findViewById(R.id.nursing_service_notice_ck);
        nursing_service_notice_ck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isAgreement = b;
            }
        });
        cancel_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//        initMap();
        EventBus.getDefault().register(this);
        getController().getDefaultServiceAddress();
        getController().getBaseFee();
    }

    /**
     * 判断地址是否删除
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void checkLocationIsDeleted(Event event) {
        if (event.getType() == EventType.CHECKLOCATIONISDELETED) {
            getController().getDefaultServiceAddress();
        }
    }

    /**
     * 获取位置
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getLocation(Event event) {
        if (event.getType() == EventType.SENDLOCATION) {
            mMap.clear();
            selectedLocation = (LocationInfo) event.getData();
            String address = selectedLocation.getProvinceName() + selectedLocation.getCityName() + selectedLocation.getAreaName() + selectedLocation.getAddress();
            choose_location_tv.setText(address);
            serviceAddress = address;
            getAddressLatAndLng(selectedLocation.getCityName(), selectedLocation.getAddress());
        }
    }

    /**
     * 获取请求map
     */
    private Map<String, Object> getQueryMap(String cityName, double latitude, double longitude) {
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("hospitalId", App.hospitalId);
       /* if (locationInfo.getProvinceCode() != null) {
            bodyMap.put("provinceCode", locationInfo.getProvinceCode());
        } else {
            return null;
        }
        if (locationInfo.getCityCode() != null) {
            bodyMap.put("cityCode", locationInfo.getCityCode());
        }
        if (locationInfo.getAreaCode() != null) {
            bodyMap.put("areaCode", locationInfo.getAreaCode());
        }*/
        bodyMap.put("cityName", cityName);
        bodyMap.put("latitude", latitude);
        bodyMap.put("longitude", longitude);
        return bodyMap;
    }

    /**
     * 初始化地图
     */
    private void initMap() {
        mMap = nursing_service_mv.getMap();

        mMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Bundle bundle = marker.getExtraInfo();
                if (bundle != null) {
                    HospitalLocationInfo hospitalLocationInfo = (HospitalLocationInfo) bundle.getSerializable("hospitalLocationInfo");
                    HospitalLocationInfo.HospitalAreaBean hospitalAreaBean = (HospitalLocationInfo.HospitalAreaBean) bundle.getSerializable("hospitalAreaBean");
                    String hintStr="";
                    if( hospitalAreaBean.getName().contains(hospitalLocationInfo.getHospitalName()))
                        hintStr=hospitalAreaBean.getName();
                    else
                        hintStr=hospitalLocationInfo.getHospitalName()+hospitalAreaBean.getName();
                    String finalHintStr = hintStr;
                    new GeneralDialog(getContext(), "是否切换医院至" +hintStr , new GeneralDialog.OnCloseListener() {
                        @Override
                        public void onCommit() {
                            if (App.hospitalId != hospitalLocationInfo.getId()) {
                                App.hospitalId = hospitalLocationInfo.getId();
                                App.hospitalName = hospitalLocationInfo.getHospitalName();
                                for (int i = 0; i <Global.getHospitalList().size() ; i++) {
                                    if(Global.getHospitalList().get(i).getId()== hospitalLocationInfo.getId()){
                                        Global.getHospitalList().get(i).setSelected(true);
                                    }else
                                        Global.getHospitalList().get(i).setSelected(false);

                                }
                                EventBus.getDefault().post(new Event(EventType.UPDATEHOSPITAL, null));
                                nursingProjectInfo = null;
                            }
                            hospitalAreaId = hospitalAreaBean.getId();
                            ToastUtil.showMessage(getContext(), "当前医院已经切换至" + finalHintStr);
                        }
                    })
                            .setTitle("提示")
                            .setNegativeButton("否")
                            .setPositiveButton("是")
                            .show();

                }

                return true;
            }
        });

        mMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {
                isCanContinue=false;
            }

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {


            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                //改变结束之后，获取地图可视范围的中心点坐标
                Logger.e("百度地图状态变更");
                LatLng latLng = mapStatus.target;
                getAddressByLatAndLng(latLng.latitude,latLng.longitude);
            }
        });
    }


    @Override
    protected void onResume() {
        nursing_service_mv.onResume();
        super.onResume();

    }

    @Override
    protected void onPause() {
        nursing_service_mv.onPause();
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        nursing_service_mv.onDestroy();
        EventBus.getDefault().unregister(this);
        super.onDestroy();

    }

    @Override
    public void getHospitalLocationInfoSuccess(List<HospitalLocationInfo> hospitalLocationInfoList) {
        hospitalAreaId=-1;
        isCanContinue=true;
        mMap.clear();
        if (hospitalLocationInfoList.size() == 0) {
            isHasServiceHospital = false;
            mMap.clear();
        } else {
            isHasServiceHospital = true;
            for (HospitalLocationInfo hospitalLocationInfo : hospitalLocationInfoList) {
                if (hospitalLocationInfo.getHospitalAreaList() != null && hospitalLocationInfo.getHospitalAreaList().size() != 0) {
                    if (hospitalLocationInfo.getId() == App.hospitalId) {
                        hospitalAreaId = hospitalLocationInfo.getHospitalAreaList().get(0).getId();
                    }
                    for (HospitalLocationInfo.HospitalAreaBean hospitalAreaBean : hospitalLocationInfo.getHospitalAreaList()) {
                        if (hospitalAreaBean.getLatitude() != null && hospitalAreaBean.getLongitude() != null) {
                            LatLng point = new LatLng(Double.parseDouble(hospitalAreaBean.getLatitude()), Double.parseDouble(hospitalAreaBean.getLongitude()));
                            BitmapDescriptor bttmap = null;
                            Marker marker;
                            View item_view = LayoutInflater.from(getContext()).inflate(R.layout.map_overlay_layout, null);
                            TextView tv_storeName = (TextView) item_view.findViewById(R.id.overlay_name);
                            ImageView imageView = (ImageView) item_view.findViewById(R.id.overlay_icon);
                            if (hospitalAreaBean.getName().contains(hospitalLocationInfo.getHospitalName())) {
                                tv_storeName.setText(hospitalLocationInfo.getHospitalName());
                            } else
                                tv_storeName.setText(hospitalLocationInfo.getHospitalName() + hospitalAreaBean.getName());
                            imageView.setImageResource(R.drawable.hospital_overlay);
                            bttmap = BitmapDescriptorFactory.fromView(item_view);
                            OverlayOptions option = new MarkerOptions()
                                    .position(point)
                                    .icon(bttmap);
                            marker = (Marker) mMap.addOverlay(option);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("hospitalLocationInfo", hospitalLocationInfo);
                            bundle.putSerializable("hospitalAreaBean", hospitalAreaBean);
                            marker.setExtraInfo(bundle);
                        }

                    }
                }

            }
        }

    }

    @Override
    public void getHospitalLocationFailed(String errMsg) {
        isCanContinue=true;
    }

    @Override
    public void getDefaultAddressSuccess(List<LocationInfo> data) {
        initMap();
        if(data!=null&&data.size()>0){
            for (LocationInfo locationInfo : data) {
                if (locationInfo.getIsdefault() == 1) {
                    selectedLocation = locationInfo;
                    String address = locationInfo.getProvinceName() + locationInfo.getCityName() + locationInfo.getAreaName() + locationInfo.getAddress();
                    choose_location_tv.setText(address);
                    serviceAddress = address;
                    getAddressLatAndLng(locationInfo.getCityName(), locationInfo.getAddress());
                }
            }
        }else {
            choose_location_tv.setText(Global.getLocationAddress());
            serviceAddress = Global.getLocationAddress();
            getAddressLatAndLng(Global.getLocationCityName(),Global.getLocationAddress());
        }
       /* if (selectedLocation == null) {


        } else {
            if (data.size() == 0) {
                isSelectedLocationAvailable = false;
            } else {
                for (LocationInfo locationInfo : data) {
                    if (selectedLocation.getId() == locationInfo.getId()) {
                        isSelectedLocationAvailable = true;
                        break;
                    } else {
                        isSelectedLocationAvailable = false;
                        continue;
                    }
                }
            }

            if (!isSelectedLocationAvailable) {
                selectedLocation = null;
                ToastUtil.showMessage(getContext(), "当前选中地址已被删除，请重新选择");
                choose_location_tv.setText("选择服务地址");
                serviceAddress = null;
                mMap.clear();
            }
        }*/


    }

    @Override
    public void getDefaultAddressFailed(String errMsg) {
        ToastUtil.showMessage(getContext(), "获取默认地址失败，请选择服务地址");
    }

    @Override
    public void getBaseFee(BaseNurseFeeBean baseNurseFeeBean) {
        if(baseNurseFeeBean!=null){
            String content="1、提交前请详细阅读服务描述<br>2、每次上门服务包括基础服务费<font color='#F83535'>¥" + (TextUtils.isEmpty(baseNurseFeeBean.getFee()) ? 0.01 : baseNurseFeeBean.getFee()) + "元。</font>";
//            notice_tv.setText(content);
            RichText.from(content).bind(this)
                    .showBorder(false)
                    .size(ImageHolder.MATCH_PARENT, ImageHolder.WRAP_CONTENT)
                    .into(notice_tv);

            if(TextUtils.isEmpty(baseNurseFeeBean.getFee())){
                Global.setBaseFee(new BigDecimal(0.01));
            }else{
                Global.setBaseFee(new BigDecimal(baseNurseFeeBean.getFee()));
            }

        }else {
//            notice_tv.setText("1、提交前请详细阅读服务描述  \n2、每次上门服务包括基础服务费¥0元。");
            String content="1、提交前请详细阅读服务描述<br>2、每次上门服务包括基础服务费<font color='#F83535'>¥0元。</font>";
            RichText.from(content).bind(this)
                    .showBorder(false)
                    .size(ImageHolder.MATCH_PARENT, ImageHolder.WRAP_CONTENT)
                    .into(notice_tv);
        }

    }

    @Override
    public void getBaseFeeFailed(String errMsg) {
        notice_tv.setText("获取注意事项失败");
        ToastUtil.showMessage(getContext(), "获取注意事项失败" + errMsg);

    }

    /**
     * 通过地址获取坐标
     */
    private void getAddressLatAndLng(String cityName, String address) {
        GeoCoder mSearch = GeoCoder.newInstance();
        OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
            public void onGetGeoCodeResult(GeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR)
                    ToastUtil.showMessage(getContext(), "服务位置定位失败");
                else {
                    Logger.e("获取地理编码结果:  lat=" + result.getLocation().latitude + "lng=" + result.getLocation().longitude);
                    MapStatus mMapStatus = new MapStatus.Builder()
                            .target(result.getLocation())
                            .zoom(15)
                            .build();
                    overlay_name.setText(address);
                    MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                    mMap.setMapStatus(mMapStatusUpdate);
                  /*  LatLng point = result.getLocation();
                    BitmapDescriptor bttmap = null;
                    View item_view = LayoutInflater.from(getContext()).inflate(R.layout.map_overlay_layout, null);
                    TextView tv_storeName = (TextView) item_view.findViewById(R.id.overlay_name);
                    ImageView imageView = (ImageView) item_view.findViewById(R.id.overlay_icon);
                    tv_storeName.setText("服务位置");
                    imageView.setImageResource(R.drawable.my_location_overlay);
                    bttmap = BitmapDescriptorFactory.fromView(item_view);
                    OverlayOptions option = new MarkerOptions()
                            .position(point)
                            .icon(bttmap);
                    mMap.addOverlay(option);*/
                    getController().getHospital(getQueryMap(cityName, result.getLocation().latitude, result.getLocation().longitude));
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
    /**
     * 通过坐标获取地址
     */
    private void getAddressByLatAndLng(double latitude,double longitude){
        GeoCoder mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                Logger.e("打印转换后的地址" + reverseGeoCodeResult.getAddress());
                choose_location_tv.setText(reverseGeoCodeResult.getAddress());
                serviceAddress=reverseGeoCodeResult.getAddress();
                overlay_name.setText(reverseGeoCodeResult.getAddress());
                Logger.e("cityName=="+reverseGeoCodeResult.getAddressDetail().city);
                getController().getHospital(getQueryMap(reverseGeoCodeResult.getAddressDetail().city,latitude, longitude));
            }
        });
        //下面是传入对应的经纬度
        mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(new LatLng(latitude, longitude)));

    }
}
