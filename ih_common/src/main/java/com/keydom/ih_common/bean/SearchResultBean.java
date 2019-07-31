package com.keydom.ih_common.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.ih_common.constant.SearchConst;

import java.io.Serializable;
import java.util.List;

/**
 * @Author：song
 * @Date：19/4/9 下午3:48
 * 修改人：xusong
 * 修改时间：19/4/9 下午3:48
 */
public class SearchResultBean implements Serializable {
    /**
     * type 用户0 科室1 文章2
     */
    private static final long serialVersionUID = 1L;
    private CommonListBean user;
    private CommonListBean dept;
    private CommonListBean article;
    private CommonListBean order;
    private CommonListBean patient;

    public CommonListBean getOrder() {
        return order;
    }

    public void setOrder(CommonListBean order) {
        this.order = order;
    }

    public CommonListBean getPatient() {
        return patient;
    }

    public void setPatient(CommonListBean patient) {
        this.patient = patient;
    }

    public static class CommonListBean<T> {
        /**
         * 列表类型
         */
        private int type;
        /**
         * 列表名称
         */
        private String name;
        /**
         * 医生列表
         */
        private List<T> list;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<T> getList() {
            return list;
        }

        public void setList(List<T> list) {
            this.list = list;
        }
    }

    public static class UserBean implements MultiItemEntity {

        //医生ID
        private long id;
        /**
         * 医院ID
         */
        private long hospitalId;
        /**
         * 医生姓名
         */
        private String name;
        /**
         * 医生code
         */
        private String userCode;
        /**
         * 医生性别
         */
        private String sex;
        /**
         * 医生职称
         */
        private String jobTitleName;
        /**
         * 科室
         */
        private String adept;
        /**
         * 简介
         */
        private String intro;

        private String image;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getHospitalId() {
            return hospitalId;
        }

        public void setHospitalId(long hospitalId) {
            this.hospitalId = hospitalId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUserCode() {
            return userCode;
        }

        public void setUserCode(String userCode) {
            this.userCode = userCode;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getJobTitleName() {
            return jobTitleName;
        }

        public void setJobTitleName(String jobTitleName) {
            this.jobTitleName = jobTitleName;
        }

        public String getAdept() {
            return adept;
        }

        public void setAdept(String adept) {
            this.adept = adept;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        @Override
        public int getItemType() {
            return SearchConst.SEARCH_USER;
        }
    }

    public static class DeptBean implements MultiItemEntity {
        /**
         * 科室ID
         */
        private long id;
        /**
         * 科室所属医院ID
         */
        private long hospitalId;
        /**
         * 科室名称
         */
        private String name;
        /**
         * 科室简介
         */
        private String intro;

        private String image;
        private long hospitalAreaId;
        private String hospitalAreaName;

        public String getHospitalAreaName() {
            return hospitalAreaName;
        }

        public void setHospitalAreaName(String hospitalAreaName) {
            this.hospitalAreaName = hospitalAreaName;
        }

        public long getHospitalAreaId() {
            return hospitalAreaId;
        }

        public void setHospitalAreaId(long hospitalAreaId) {
            this.hospitalAreaId = hospitalAreaId;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getHospitalId() {
            return hospitalId;
        }

        public void setHospitalId(long hospitalId) {
            this.hospitalId = hospitalId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        @Override
        public int getItemType() {
            return SearchConst.SEARCH_DEPT;
        }
    }

    public static class TitleItemBean implements MultiItemEntity {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public int getItemType() {
            return SearchConst.TITLE_ITEM;
        }
    }

    public static class OrderBean implements MultiItemEntity {

        private long id;
        private long hospitalId;
        private String orderType;
        private String name;
        private String patientImage;
        private String doctorCode;
        private String conditionDesc;
        private String suggest;
        private String patientCode;
        private int state;
        private int sex;
        private int age;
        private String createTime;

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getPatientCode() {
            return patientCode;
        }

        public void setPatientCode(String patientCode) {
            this.patientCode = patientCode;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getHospitalId() {
            return hospitalId;
        }

        public void setHospitalId(long hospitalId) {
            this.hospitalId = hospitalId;
        }

        public String getOrderType() {
            return orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPatientImage() {
            return patientImage;
        }

        public void setPatientImage(String patientImage) {
            this.patientImage = patientImage;
        }

        public String getDoctorCode() {
            return doctorCode;
        }

        public void setDoctorCode(String doctorCode) {
            this.doctorCode = doctorCode;
        }

        public String getConditionDesc() {
            return conditionDesc;
        }

        public void setConditionDesc(String conditionDesc) {
            this.conditionDesc = conditionDesc;
        }

        public String getSuggest() {
            return suggest;
        }

        public void setSuggest(String suggest) {
            this.suggest = suggest;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        @Override
        public int getItemType() {
            return SearchConst.SEARCH_ORDER;
        }
    }

    public static class BottomItemBean implements MultiItemEntity {

        private String name;
        private int type;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public int getItemType() {
            return SearchConst.BOTTOM_ITEM;
        }
    }

    public static class NomoreData implements MultiItemEntity {


        @Override
        public int getItemType() {
            return SearchConst.BOTTOM_NO_MORE_DATA;
        }
    }

    public static class PatientBean implements MultiItemEntity {
        private long id;
        private String patientNumber;
        private String name;
        private String image;
        private String phoneNumber;
        private String address;
        private String patientCode;

        public String getPatientCode() {
            return patientCode;
        }

        public void setPatientCode(String patientCode) {
            this.patientCode = patientCode;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getPatientNumber() {
            return patientNumber;
        }

        public void setPatientNumber(String patientNumber) {
            this.patientNumber = patientNumber;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        @Override
        public int getItemType() {
            return SearchConst.SEARCH_PATIENT;
        }
    }

    public static class ArticleBean implements MultiItemEntity {
        /**
         * 文章ID
         */
        private long id;
        /**
         * 医院ID
         */
        private long hospitalId;
        /**
         * 文章标题
         */
        private String title;
        /**
         * 文章发布人
         */
        private String summary;
        /**
         * 文章内容
         */
        private String content;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getHospitalId() {
            return hospitalId;
        }

        public void setHospitalId(long hospitalId) {
            this.hospitalId = hospitalId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public int getItemType() {
            return SearchConst.SEARCH_ARTICLE;
        }
    }

    public CommonListBean getUser() {
        return user;
    }

    public void setUser(CommonListBean user) {
        this.user = user;
    }

    public CommonListBean getDept() {
        return dept;
    }

    public void setDept(CommonListBean dept) {
        this.dept = dept;
    }

    public CommonListBean getArticle() {
        return article;
    }

    public void setArticle(CommonListBean article) {
        this.article = article;
    }
}
