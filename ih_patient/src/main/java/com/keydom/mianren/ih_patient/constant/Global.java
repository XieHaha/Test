package com.keydom.mianren.ih_patient.constant;

import com.keydom.mianren.ih_patient.bean.HospitalAreaInfo;
import com.keydom.mianren.ih_patient.bean.IndexFunction;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;
import com.keydom.mianren.ih_patient.bean.PackageData;
import com.keydom.mianren.ih_patient.bean.PhysicalExaInfo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * APP全局变量配置类
 */
public class Global  {
    private static long userId = -1;
    private static String selectedCardNum;
    private static List<MedicalCardInfo> cardList;
    public static List<MedicalCardInfo> getCardList() {
        return cardList;
    }
    private static PhysicalExaInfo selectedPhysicalExa;
    private static HospitalAreaInfo hospitalAreaInfo;
    private static List<PackageData.ProvinceBean> data;
    private static List<String> provinceItems;
    private static List<List<String>> cityItems;
    private static List<List<List<String>>> areaItems;
    private static Map<String, Long> projectTypeMap;
    private static String locationCountry=null;
    private static String locationProvince=null;
    private static String locationCity=null;
    private static String locationCityCode=null;
    private static String selectedCityName=null;
    private static String selectedCityCode=null;
    private static List<HospitalAreaInfo> hospitalList=new ArrayList<>();
    private static long professionalProjectTypeId=-1;
    private static BigDecimal baseFee;
    private static long currentTimeMillis=0;
    private static int lockCount=0;
    private static long lockTimeMillis=0;
    private static String locationAddress=null;
    private static String locationCityName=null;
    private static double latitude=0;
    private static double longitude=0;
    private static int member=0;


    public static void setMember(int member) {
        Global.member = member;
    }

    public static boolean isMember() {
        return member > 0;
    }

    public static String getLocationAddress() {
        return locationAddress;
    }

    public static void setLocationAddress(String locationAddress) {
        Global.locationAddress = locationAddress;
    }

    public static String getLocationCityName() {
        return locationCityName;
    }

    public static void setLocationCityName(String locationCityName) {
        Global.locationCityName = locationCityName;
    }

    public static double getLatitude() {
        return latitude;
    }

    public static void setLatitude(double latitude) {
        Global.latitude = latitude;
    }

    public static double getLongitude() {
        return longitude;
    }

    public static void setLongitude(double longitude) {
        Global.longitude = longitude;
    }

    public static int getLockCount() {
        return lockCount;
    }

    public static void setLockCount(int lockCount) {
        Global.lockCount = lockCount;
    }

    public static long getLockTimeMillis() {
        return lockTimeMillis;
    }

    public static void setLockTimeMillis(long lockTimeMillis) {
        Global.lockTimeMillis = lockTimeMillis;
    }

    public static long getCurrentTimeMillis() {
        return currentTimeMillis;
    }

    public static void setCurrentTimeMillis(long currentTimeMillis) {
        Global.currentTimeMillis = currentTimeMillis;
    }

    public static BigDecimal getBaseFee() {
        return baseFee;
    }

    public static void setBaseFee(BigDecimal baseFee) {
        Global.baseFee = baseFee;
    }

    public static long getProfessionalProjectTypeId() {
        return professionalProjectTypeId;
    }

    public static void setProfessionalProjectTypeId(long professionalProjectTypeId) {
        Global.professionalProjectTypeId = professionalProjectTypeId;
    }

    public static List<HospitalAreaInfo> getHospitalList() {
        return hospitalList;
    }

    public static void setHospitalList(List<HospitalAreaInfo> hospitalList) {
        Global.hospitalList = hospitalList;
    }

    public static String getLocationCityCode() {
        return locationCityCode;
    }

    public static void setLocationCityCode(String locationCityCode) {
        Global.locationCityCode = locationCityCode;
    }

    public static String getSelectedCityName() {
        return selectedCityName;
    }

    public static void setSelectedCityName(String selectedCityName) {
        Global.selectedCityName = selectedCityName;
    }

    public static String getSelectedCityCode() {
        return selectedCityCode;
    }

    public static void setSelectedCityCode(String selectedCityCode) {
        Global.selectedCityCode = selectedCityCode;
    }

    public static String getLocationCountry() {
        return locationCountry;
    }

    public static void setLocationCountry(String locationCountry) {
        Global.locationCountry = locationCountry;
    }

    public static String getLocationProvince() {
        return locationProvince;
    }

    public static void setLocationProvince(String locationProvince) {
        Global.locationProvince = locationProvince;
    }

    public static String getLocationCity() {
        return locationCity;
    }

    public static void setLocationCity(String locationCity) {
        Global.locationCity = locationCity;
    }

    public static Map<String, Long> getProjectTypeMap() {
        return projectTypeMap;
    }

    public static void setProjectTypeMap(Map<String, Long> projectTypeMap) {
        Global.projectTypeMap = projectTypeMap;
    }

    public static List<PackageData.ProvinceBean> getData() {
        return data;
    }

    public static void setData(List<PackageData.ProvinceBean> data) {
        Global.data = data;
    }

    public static List<String> getProvinceItems() {
        return provinceItems;
    }

    public static void setProvinceItems(List<String> provinceItems) {
        Global.provinceItems = provinceItems;
    }

    public static List<List<String>> getCityItems() {
        return cityItems;
    }

    public static void setCityItems(List<List<String>> cityItems) {
        Global.cityItems = cityItems;
    }

    public static List<List<List<String>>> getAreaItems() {
        return areaItems;
    }

    public static void setAreaItems(List<List<List<String>>> areaItems) {
        Global.areaItems = areaItems;
    }

    public static HospitalAreaInfo getHospitalAreaInfo() {
        return hospitalAreaInfo;
    }

    public static void setHospitalAreaInfo(HospitalAreaInfo hospitalAreaInfo) {
        Global.hospitalAreaInfo = hospitalAreaInfo;
    }

    public static PhysicalExaInfo getSelectedPhysicalExa() {
        return selectedPhysicalExa;
    }

    public static void setSelectedPhysicalExa(PhysicalExaInfo selectedPhysicalExa) {
        Global.selectedPhysicalExa = selectedPhysicalExa;
    }

    public static void setCardList(List<MedicalCardInfo> cardList) {
        Global.cardList = cardList;
    }

    public static String getSelectedCardNum() {
        return selectedCardNum;
    }

    public static void setSelectedCardNum(String selectedCardNum) {
        Global.selectedCardNum = selectedCardNum;
    }

    private static List<IndexFunction> funcitonList=new ArrayList<>();
    public static long getUserId() {
        return userId;
    }

    public static void setUserId(long userId) {
        Global.userId = userId;
    }

    public static List<IndexFunction> getFuncitonList() {
        return funcitonList;
    }

    public static void setFuncitonList(List<IndexFunction> funcitonList) {
        Global.funcitonList.clear();
        Global.funcitonList.addAll(funcitonList);
    }
}
