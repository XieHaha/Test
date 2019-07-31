package com.keydom.ih_patient.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * 医院位置
 */
public class HospitalLocationInfo implements Serializable {


    /**
     * hospitalName : 人民医院
     * id : 3
     * longitude : 103.867086
     * latitude : 30.705216
     * hospitalAreaNameAndIdAndLongtitudeAndLatitudeDtoList : [{"longitude":"104.26464","latitude":"30.356477","name":"南区","id":16,"code":"00001A001","hospitalId":3},{"longitude":"104.484258","latitude":"30.637285","name":"北区","id":17,"code":"00001A002","hospitalId":3}]
     */

    @JSONField(name = "hospitalName")
    private String hospitalName;
    @JSONField(name = "id")
    private long id;
    @JSONField(name = "longitude")
    private String longitude;
    @JSONField(name = "latitude")
    private String latitude;
    @JSONField(name = "hospitalAreaNameAndIdAndLongtitudeAndLatitudeDtoList")
    private List<HospitalAreaBean> hospitalAreaList;

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public List<HospitalAreaBean> getHospitalAreaList() {
        return hospitalAreaList;
    }

    public void setHospitalAreaList(List<HospitalAreaBean> hospitalAreaList) {
        this.hospitalAreaList = hospitalAreaList;
    }

    public static class HospitalAreaBean implements Serializable {
        /**
         * longitude : 104.26464
         * latitude : 30.356477
         * name : 南区
         * id : 16
         * code : 00001A001
         * hospitalId : 3
         */

        @JSONField(name = "longitude")
        private String longitude;
        @JSONField(name = "latitude")
        private String latitude;
        @JSONField(name = "name")
        private String name;
        @JSONField(name = "id")
        private long id;
        @JSONField(name = "code")
        private String code;
        @JSONField(name = "hospitalId")
        private long hospitalId;

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public long getHospitalId() {
            return hospitalId;
        }

        public void setHospitalId(long hospitalId) {
            this.hospitalId = hospitalId;
        }
    }
}
