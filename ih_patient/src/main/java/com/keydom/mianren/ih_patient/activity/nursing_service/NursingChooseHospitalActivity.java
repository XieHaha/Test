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
 * ??????????????????
 */
public class NursingChooseHospitalActivity extends BaseControllerActivity<NursingChooseHospitalController> implements NursingChooseHospitalView {
    /**
     * ??????
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
        setTitle("????????????");
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
                    ToastUtil.showMessage(getContext(), "????????????????????????????????????");
                    return;
                }else {
                    if (serviceAddress == null) {
                        ToastUtil.showMessage(getContext(), "???????????????????????????");
                        return;
                    }
                    if (!isHasServiceHospital) {
                        ToastUtil.showMessage(getContext(), "?????????????????????????????????????????????????????????????????????");
                        return;
                    }
                    if(hospitalAreaId==-1){
                        ToastUtil.showMessage(getContext(), "?????????????????????????????????????????????????????????????????????????????????");
                        return;
                    }
                    if (isAgreement) {
                        NursingOrderFillInActivity.start(getContext(), nursingProjectInfo, serviceAddress, hospitalAreaId, false, null);
                    } else {
                        ToastUtil.showMessage(getContext(), "??????????????????????????????????????????????????????");
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
     * ????????????????????????
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void checkLocationIsDeleted(Event event) {
        if (event.getType() == EventType.CHECKLOCATIONISDELETED) {
            getController().getDefaultServiceAddress();
        }
    }

    /**
     * ????????????
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
     * ????????????map
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
     * ???????????????
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
                    new GeneralDialog(getContext(), "?????????????????????" +hintStr , new GeneralDialog.OnCloseListener() {
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
                            ToastUtil.showMessage(getContext(), "???????????????????????????" + finalHintStr);
                        }
                    })
                            .setTitle("??????")
                            .setNegativeButton("???")
                            .setPositiveButton("???")
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
                //???????????????????????????????????????????????????????????????
                Logger.e("????????????????????????");
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
                ToastUtil.showMessage(getContext(), "????????????????????????????????????????????????");
                choose_location_tv.setText("??????????????????");
                serviceAddress = null;
                mMap.clear();
            }
        }*/


    }

    @Override
    public void getDefaultAddressFailed(String errMsg) {
        ToastUtil.showMessage(getContext(), "????????????????????????????????????????????????");
    }

    @Override
    public void getBaseFee(BaseNurseFeeBean baseNurseFeeBean) {
        if(baseNurseFeeBean!=null){
            String content="1???????????????????????????????????????<br>2??????????????????????????????????????????<font color='#F83535'>??" + (TextUtils.isEmpty(baseNurseFeeBean.getFee()) ? 0.01 : baseNurseFeeBean.getFee()) + "??????</font>";
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
//            notice_tv.setText("1???????????????????????????????????????  \n2????????????????????????????????????????????0??????");
            String content="1???????????????????????????????????????<br>2??????????????????????????????????????????<font color='#F83535'>??0??????</font>";
            RichText.from(content).bind(this)
                    .showBorder(false)
                    .size(ImageHolder.MATCH_PARENT, ImageHolder.WRAP_CONTENT)
                    .into(notice_tv);
        }

    }

    @Override
    public void getBaseFeeFailed(String errMsg) {
        notice_tv.setText("????????????????????????");
        ToastUtil.showMessage(getContext(), "????????????????????????" + errMsg);

    }

    /**
     * ????????????????????????
     */
    private void getAddressLatAndLng(String cityName, String address) {
        GeoCoder mSearch = GeoCoder.newInstance();
        OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
            public void onGetGeoCodeResult(GeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR)
                    ToastUtil.showMessage(getContext(), "????????????????????????");
                else {
                    Logger.e("????????????????????????:  lat=" + result.getLocation().latitude + "lng=" + result.getLocation().longitude);
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
                    tv_storeName.setText("????????????");
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
     * ????????????????????????
     */
    private void getAddressByLatAndLng(double latitude,double longitude){
        GeoCoder mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                Logger.e("????????????????????????" + reverseGeoCodeResult.getAddress());
                choose_location_tv.setText(reverseGeoCodeResult.getAddress());
                serviceAddress=reverseGeoCodeResult.getAddress();
                overlay_name.setText(reverseGeoCodeResult.getAddress());
                Logger.e("cityName=="+reverseGeoCodeResult.getAddressDetail().city);
                getController().getHospital(getQueryMap(reverseGeoCodeResult.getAddressDetail().city,latitude, longitude));
            }
        });
        //?????????????????????????????????
        mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(new LatLng(latitude, longitude)));

    }
}
