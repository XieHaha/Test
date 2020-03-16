package com.keydom.mianren.ih_patient.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * 包名实体
 */
public class PackageData implements Serializable {

    private static final long serialVersionUID = -4393399066319282776L;
    /**
     * country : [{"id":1,"priority":1,"nationCode":"201","nationName":"中国","remark":"201"},{"id":2,"priority":2,"nationCode":"202","nationName":"巴基斯坦","remark":"201"}]
     * province : [{"id":1,"priority":1,"code":"510000","name":"四川省","abbreviation":"201","remark":"201","city":[{"id":10001,"priority":1,"code":"510100","name":"成都市","abbreviation":"201","remark":"201","provinceId":1,"area":[{"id":10000001,"priority":1,"code":"510104","name":"锦江区","abbreviation":"201","remark":"201","cityId":10001},{"id":10000002,"priority":2,"code":"510108","name":"成华区","abbreviation":"201","remark":"201","cityId":10001}]},{"id":10002,"priority":2,"code":"510300","name":"自贡市","abbreviation":"201","remark":"201","provinceId":1,"area":[{"id":10000003,"priority":3,"code":"510303","name":"贡井区","abbreviation":"201","remark":"201","cityId":10002},{"id":10000004,"priority":4,"code":"510321","name":"荣县","abbreviation":"201","remark":"201","cityId":10002}]},{"id":10013,"priority":"201","code":"435","name":"344325","abbreviation":"34","remark":"201","provinceId":1,"area":[]},{"id":10014,"priority":"201","code":"5555","name":"55555","abbreviation":"3333","remark":"201","provinceId":1,"area":[]}]},{"id":2,"priority":2,"code":"520000","name":"贵州省","abbreviation":"201","remark":"201","city":[{"id":10003,"priority":3,"code":"520100","name":"贵阳市","abbreviation":"201","remark":"201","provinceId":2,"area":[{"id":10000005,"priority":5,"code":"520102","name":"南明区","abbreviation":"201","remark":"201","cityId":10003},{"id":10000006,"priority":6,"code":"520103","name":"云岩区","abbreviation":"201","remark":"201","cityId":10003},{"id":10000007,"priority":7,"code":"520201","name":"钟山区","abbreviation":"201","remark":"201","cityId":10003},{"id":10000008,"priority":8,"code":"520222","name":"盘县","abbreviation":"201","remark":"201","cityId":10003}]},{"id":10004,"priority":4,"code":"520200","name":"六盘水市","abbreviation":"201","remark":"201","provinceId":2,"area":[]}]}]
     * nation : [{"id":1,"priority":1,"notionCode":"101","nationName":"汉族","remark":"201"},{"id":2,"priority":2,"notionCode":"201","nationName":"蒙古族","remark":"201"},{"id":3,"priority":3,"notionCode":"301","nationName":"回族","remark":"201"}]
     * version : 1.0
     */

    @JSONField(name = "version")
    private String version;
    @JSONField(name = "country")
    private List<CountryBean> country;
    @JSONField(name = "province")
    private List<ProvinceBean> province;
    @JSONField(name = "nation")
    private List<NationBean> nation;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<CountryBean> getCountry() {
        return country;
    }

    public void setCountry(List<CountryBean> country) {
        this.country = country;
    }

    public List<ProvinceBean> getProvince() {
        return province;
    }

    public void setProvince(List<ProvinceBean> province) {
        this.province = province;
    }

    public List<NationBean> getNation() {
        return nation;
    }

    public void setNation(List<NationBean> nation) {
        this.nation = nation;
    }

    public static class CountryBean implements Serializable{
        private static final long serialVersionUID = 2373612039967581896L;
        /**
         * id : 1
         * priority : 1
         * nationCode : 201
         * nationName : 中国
         * remark : 201
         */

        @JSONField(name = "id")
        private long id;
        @JSONField(name = "priority")
        private int priority;
        @JSONField(name = "nationCode")
        private String nationCode;
        @JSONField(name = "nationName")
        private String nationName;
        @JSONField(name = "remark")
        private String remark;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public String getNationCode() {
            return nationCode;
        }

        public void setNationCode(String nationCode) {
            this.nationCode = nationCode;
        }

        public String getNationName() {
            return nationName;
        }

        public void setNationName(String nationName) {
            this.nationName = nationName;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }

    public static class ProvinceBean implements Serializable{
        private static final long serialVersionUID = 5924246467273088030L;
        /**
         * id : 1
         * priority : 1
         * code : 510000
         * name : 四川省
         * abbreviation : 201
         * remark : 201
         * city : [{"id":10001,"priority":1,"code":"510100","name":"成都市","abbreviation":"201","remark":"201","provinceId":1,"area":[{"id":10000001,"priority":1,"code":"510104","name":"锦江区","abbreviation":"201","remark":"201","cityId":10001},{"id":10000002,"priority":2,"code":"510108","name":"成华区","abbreviation":"201","remark":"201","cityId":10001}]},{"id":10002,"priority":2,"code":"510300","name":"自贡市","abbreviation":"201","remark":"201","provinceId":1,"area":[{"id":10000003,"priority":3,"code":"510303","name":"贡井区","abbreviation":"201","remark":"201","cityId":10002},{"id":10000004,"priority":4,"code":"510321","name":"荣县","abbreviation":"201","remark":"201","cityId":10002}]},{"id":10013,"priority":"201","code":"435","name":"344325","abbreviation":"34","remark":"201","provinceId":1,"area":[]},{"id":10014,"priority":"201","code":"5555","name":"55555","abbreviation":"3333","remark":"201","provinceId":1,"area":[]}]
         */

        @JSONField(name = "id")
        private long id;
        @JSONField(name = "priority")
        private int priority;
        @JSONField(name = "code")
        private String code;
        @JSONField(name = "name")
        private String name;
        @JSONField(name = "abbreviation")
        private String abbreviation;
        @JSONField(name = "remark")
        private String remark;
        @JSONField(name = "city")
        private List<CityBean> city;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAbbreviation() {
            return abbreviation;
        }

        public void setAbbreviation(String abbreviation) {
            this.abbreviation = abbreviation;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public List<CityBean> getCity() {
            return city;
        }

        public void setCity(List<CityBean> city) {
            this.city = city;
        }

        public static class CityBean implements Serializable{
            private static final long serialVersionUID = -908114675627345707L;
            /**
             * id : 10001
             * priority : 1
             * code : 510100
             * name : 成都市
             * abbreviation : 201
             * remark : 201
             * provinceId : 1
             * area : [{"id":10000001,"priority":1,"code":"510104","name":"锦江区","abbreviation":"201","remark":"201","cityId":10001},{"id":10000002,"priority":2,"code":"510108","name":"成华区","abbreviation":"201","remark":"201","cityId":10001}]
             */

            @JSONField(name = "id")
            private long id;
            @JSONField(name = "priority")
            private int priority;
            @JSONField(name = "code")
            private String code;
            @JSONField(name = "name")
            private String name;
            @JSONField(name = "abbreviation")
            private String abbreviation;
            @JSONField(name = "remark")
            private String remark;
            @JSONField(name = "provinceId")
            private long provinceId;
            @JSONField(name = "area")
            private List<AreaBean> area;

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public int getPriority() {
                return priority;
            }

            public void setPriority(int priority) {
                this.priority = priority;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAbbreviation() {
                return abbreviation;
            }

            public void setAbbreviation(String abbreviation) {
                this.abbreviation = abbreviation;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public long getProvinceId() {
                return provinceId;
            }

            public void setProvinceId(long provinceId) {
                this.provinceId = provinceId;
            }

            public List<AreaBean> getArea() {
                return area;
            }

            public void setArea(List<AreaBean> area) {
                this.area = area;
            }

            public static class AreaBean implements Serializable{
                private static final long serialVersionUID = 4258105612702236639L;
                /**
                 * id : 10000001
                 * priority : 1
                 * code : 510104
                 * name : 锦江区
                 * abbreviation : 201
                 * remark : 201
                 * cityId : 10001
                 */

                @JSONField(name = "id")
                private long id;
                @JSONField(name = "priority")
                private int priority;
                @JSONField(name = "code")
                private String code;
                @JSONField(name = "name")
                private String name;
                @JSONField(name = "abbreviation")
                private String abbreviation;
                @JSONField(name = "remark")
                private String remark;
                @JSONField(name = "cityId")
                private long cityId;

                public long getId() {
                    return id;
                }

                public void setId(long id) {
                    this.id = id;
                }

                public int getPriority() {
                    return priority;
                }

                public void setPriority(int priority) {
                    this.priority = priority;
                }

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getAbbreviation() {
                    return abbreviation;
                }

                public void setAbbreviation(String abbreviation) {
                    this.abbreviation = abbreviation;
                }

                public String getRemark() {
                    return remark;
                }

                public void setRemark(String remark) {
                    this.remark = remark;
                }

                public long getCityId() {
                    return cityId;
                }

                public void setCityId(long cityId) {
                    this.cityId = cityId;
                }
            }
        }
    }

    public static class NationBean implements Serializable{
        private static final long serialVersionUID = -7477148178062957726L;
        /**
         * id : 1
         * priority : 1
         * notionCode : 101
         * nationName : 汉族
         * remark : 201
         */

        @JSONField(name = "id")
        private long id;
        @JSONField(name = "priority")
        private int priority;
        @JSONField(name = "notionCode")
        private String notionCode;
        @JSONField(name = "nationName")
        private String nationName;
        @JSONField(name = "remark")
        private String remark;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public String getNotionCode() {
            return notionCode;
        }

        public void setNotionCode(String notionCode) {
            this.notionCode = notionCode;
        }

        public String getNationName() {
            return nationName;
        }

        public void setNationName(String nationName) {
            this.nationName = nationName;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
