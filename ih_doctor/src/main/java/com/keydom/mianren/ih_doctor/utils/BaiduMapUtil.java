package com.keydom.mianren.ih_doctor.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.keydom.mianren.ih_doctor.m_interface.BDMapResultInternet;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class BaiduMapUtil extends BDAbstractLocationListener {
    private static BaiduMapUtil mInstance;
    private Context mContext;
    private LocationClient mLocationClient = null;
    private BDMapResultInternet bdMapResult;
    private GeoCoder mSearch;

    public BaiduMapUtil(Context context, BDMapResultInternet bdMapResultInternet) {
        this.mContext = context;
        this.bdMapResult = bdMapResultInternet;
        init();
        mSearch = GeoCoder.newInstance();

    }

    public BaiduMapUtil init() {
        mLocationClient = new LocationClient(mContext);
        mLocationClient.registerLocationListener(this);
        LocationClientOption option = new LocationClientOption();

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);

        option.setCoorType("bd09ll");

        option.setScanSpan(5000);

        option.setOpenGps(true);

        option.setLocationNotify(true);

        option.setIgnoreKillProcess(false);

        option.SetIgnoreCacheException(false);

        option.setWifiCacheTimeOut(5 * 60 * 1000);

        option.setEnableSimulateGps(false);

        mLocationClient.setLocOption(option);

        return this;
    }

    public void start() {
        mLocationClient.start();
        mLocationClient.requestLocation();
    }


    public GeoCoder getGeoPointByAddress(String city, String address, OnGetGeoCoderResultListener listener) {
        mSearch.setOnGetGeoCodeResultListener(listener);
        mSearch.geocode(new GeoCodeOption()
                .city(city)
                .address(address));
        return mSearch;
    }

    public GeoPoint getGeoPointBystr(String str) {
        GeoPoint gpGeoPoint = null;
        if (str != null) {
            Geocoder gc = new Geocoder(mContext, Locale.CHINA);
            List<Address> addressList = null;
            try {

                addressList = gc.getFromLocationName(str, 1);
                if (!addressList.isEmpty()) {
                    Address address_temp = addressList.get(0);
                    //计算经纬度
                    double Latitude = address_temp.getLatitude() * 1E6;
                    double Longitude = address_temp.getLongitude() * 1E6;
                    System.out.println("经度：" + Latitude);
                    System.out.println("纬度：" + Longitude);
                    //生产GeoPoint
                    gpGeoPoint = new GeoPoint((int) Latitude, (int) Longitude);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return gpGeoPoint;
    }

    public void stop() {
        mLocationClient.stop();
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        bdMapResult.getBDLocation(bdLocation);
    }
}
