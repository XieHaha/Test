package com.keydom.mianren.ih_doctor.bean;

import java.io.Serializable;
import java.util.List;

public class WarrantDetailBean  implements Serializable {


    /**
     * doctorId : 179
     * doctorName : 史平绿
     * doctorImage : group1/M00/00/3B/rBAEA1zFEZSANXVgAAwz1lCV7Jk149.png
     * returnVisitState : 1
     * interrogationState : 1
     * interrogationOrder : 1
     * termValidity : “”
     * state : 1
     * creatorId : 218
     * list : [{"userImage":"\u201c\u201d","userId":"48","age":27,"sex":"0","userName":"谢"},{"userImage":"group1/M00/00/47/rBAEA1zrsmeAIuh2AAOVNxBHzNs796.jpg","userId":"62","age":29,"sex":"0","userName":"测试账号"},{"userImage":"group1/M00/00/47/rBAEA1zrslSAEqmZAABZ9c7cnl0863.jpg","userId":"1081392257166422017","age":22,"sex":"1","userName":"高距骨姑姑玉玉姑姑姑父局域咕咕咕玉vuv"}]
     */

    private String doctorId;
    private String doctorName;
    private String doctorImage;
    private int returnVisitState;
    private int interrogationState;
    private int interrogationOrder;
    private String termValidity;
    private int state;
    private String creatorId;
    private List<ListBean> list;

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorImage() {
        return doctorImage;
    }

    public void setDoctorImage(String doctorImage) {
        this.doctorImage = doctorImage;
    }

    public int getReturnVisitState() {
        return returnVisitState;
    }

    public void setReturnVisitState(int returnVisitState) {
        this.returnVisitState = returnVisitState;
    }

    public int getInterrogationState() {
        return interrogationState;
    }

    public void setInterrogationState(int interrogationState) {
        this.interrogationState = interrogationState;
    }

    public int getInterrogationOrder() {
        return interrogationOrder;
    }

    public void setInterrogationOrder(int interrogationOrder) {
        this.interrogationOrder = interrogationOrder;
    }

    public String getTermValidity() {
        return termValidity;
    }

    public void setTermValidity(String termValidity) {
        this.termValidity = termValidity;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable{
        /**
         * userImage : “”
         * userId : 48
         * age : 27
         * sex : 0
         * userName : 谢
         */

        private String userImage;
        private String userId;
        private int age;
        private String sex;
        private String userName;

        public String getUserImage() {
            return userImage;
        }

        public void setUserImage(String userImage) {
            this.userImage = userImage;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
