package com.keydom.mianren.ih_doctor.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.bean
 * @Description：患者资料对象
 * @Author：song
 * @Date：18/12/25 下午3:00
 * 修改人：xusong
 * 修改时间：18/12/25 下午3:00
 */
public class PatientInfoBean implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id : 29
     * userName : 骑猪追宝马
     * userImage : group1/M00/00/0D/rBAEA1wyBfSABkFgAAxrhkHL9UE182.jpg
     * sex : 0
     * cityAddress : 新疆维吾尔自治区11
     * phoneNumber : 18181900212
     * blackNameList : 0
     * age : 25
     * focusOn : 1
     * groupChatNames :
     * lableList : ["高血压","心脏222"]
     * hospitalUserNames : 松22
     * consultRecordVOList : [{"id":1100198737545912322,"medicalId":1099970069494099969,"inspectList":[{"name":"CT项目1位1","id":1099924568430125058}],"checkoutList":[{"name":"乙肝表面抗素","id":1100193659434950658},{"name":"项目2子项2","id":1100193660034736130}],"prescriptionId":1099970069494099969,"diagnosis":null,"consultTime":"2019-02-26","type":null},{"id":1100330147137150978,"medicalId":0,"inspectList":[],"checkoutList":[],"prescriptionId":0,"diagnosis":null,"consultTime":"2019-02-26","type":null}]
     * remark : null
     */

    private long id;
    private String userName;
    private String userImage;
    private String sex;
    private String cityAddress;
    private String phoneNumber;
    private String blackNameList;
    private String age;
    private String focusOn;
    private String groupChatNames;
    private String hospitalUserNames;
    private String remark;
    private List<String> lableList;
    private List<DiagnoseRecoderItemBean> consultRecordVOList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCityAddress() {
        return cityAddress;
    }

    public void setCityAddress(String cityAddress) {
        this.cityAddress = cityAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBlackNameList() {
        return blackNameList;
    }

    public void setBlackNameList(String blackNameList) {
        this.blackNameList = blackNameList;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getFocusOn() {
        return focusOn;
    }

    public void setFocusOn(String focusOn) {
        this.focusOn = focusOn;
    }

    public String getGroupChatNames() {
        return groupChatNames;
    }

    public void setGroupChatNames(String groupChatNames) {
        this.groupChatNames = groupChatNames;
    }

    public String getHospitalUserNames() {
        return hospitalUserNames;
    }

    public void setHospitalUserNames(String hospitalUserNames) {
        this.hospitalUserNames = hospitalUserNames;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<String> getLableList() {
        return lableList;
    }

    public void setLableList(List<String> lableList) {
        this.lableList = lableList;
    }

    public List<DiagnoseRecoderItemBean> getConsultRecordVOList() {
        return consultRecordVOList;
    }

    public void setConsultRecordVOList(List<DiagnoseRecoderItemBean> consultRecordVOList) {
        this.consultRecordVOList = consultRecordVOList;
    }

}
