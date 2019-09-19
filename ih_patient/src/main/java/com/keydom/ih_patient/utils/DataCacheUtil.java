package com.keydom.ih_patient.utils;

import android.content.Context;


import com.keydom.Common;
import com.keydom.ih_common.CommonApp;
import com.keydom.ih_patient.bean.entity.LatXyEntity;
import com.keydom.ih_patient.bean.entity.pharmacy.PrescriptionItemEntity;
import com.keydom.ih_patient.utils.pay.ACache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * App数据缓存工具
 *
 * @author Xun.Zhang
 * @version 1.0.0
 */
public class DataCacheUtil {


    public static final String HOT_STREET = "HOT_STREET"; // 热门街道
    public static final String CITY_NAME = "CITY_NAME"; // 城市名
    public static final String CITY_CODE = "CITY_CODE"; // 城市编码
    public static final String LAST_LOGIN_ACCOUNT = "LAST_LOGIN_ACC OUNT"; // 最后登录帐号
    public static final String WIFI_UPDATE_DATA = "WIFI_UPDATE_DATA"; // WIFI下自动更新离线数据

   public static final String PUT_DRUG_BEAN="PUT_DRUG_BEAN";//缓存药品信息
    public static final String LAT_XY="LAT_XY";//缓存药品信息

    private static DataCacheUtil mInstance;
    private static ACache aCache;
    public static final String FIR = "FIR"; //首字母排序城市列表

    public static DataCacheUtil getInstance() {
        if (mInstance == null) {
            synchronized (DataCacheUtil.class) {
                if (mInstance == null) {
                    mInstance = new DataCacheUtil(Common.INSTANCE.getApplication());
                }
            }
        }
        return mInstance;
    }

    private DataCacheUtil(Context context) {
        aCache = ACache.get(context);
    }

    public void remove(String key) {
        aCache.remove(key);
    }

    public void clear() {
        aCache.clear();
    }

    /**
     * 缓存药品信息
     */
    public  List<PrescriptionItemEntity> getPrescriptionItemEntity(){
        return (List<PrescriptionItemEntity>) aCache.getAsObject(PUT_DRUG_BEAN);
    }

    public void putPrescriptionItemEntity(List<PrescriptionItemEntity> data) {
        aCache.put(PUT_DRUG_BEAN, (Serializable) data);
    }
    /**
     * 缓存所在地经纬度
     */
    public  LatXyEntity getlatXy(){
        return (LatXyEntity) aCache.getAsObject(LAT_XY);
    }

    public void putlatXy(LatXyEntity latXyEntity) {
        aCache.put(LAT_XY,  latXyEntity);
    }
    /**
     * 缓存城市名
     */
    public void putCityName(String data) {
        aCache.put(CITY_NAME, data);
    }

    public String getCityName() {
        return aCache.getAsString(CITY_NAME);
    }

    /**
     * 缓存城市编码
     */
    public void putCityCode(String data) {
        aCache.put(CITY_CODE, data);
    }

    public String getCityCode() {
        return aCache.getAsString(CITY_CODE);
    }

    /**
     * 缓存最后登录的帐号
     */
    public void putLoginAccount(String data) {
        aCache.put(LAST_LOGIN_ACCOUNT, data);
    }

    public String getLoginAccount() {
        return aCache.getAsString(LAST_LOGIN_ACCOUNT);
    }

    /**
     * WIFI_UPDATE_DATA
     */
    public void putWifiUpdateData(Boolean needUpdate) {
        aCache.put(WIFI_UPDATE_DATA, needUpdate);
    }

    public Boolean getWifiUpdateData() {
        Object asObject = aCache.getAsObject(WIFI_UPDATE_DATA);
        if (null == asObject) {
            return true;
        } else {
            return (Boolean) aCache.getAsObject(WIFI_UPDATE_DATA);
        }
    }


}
