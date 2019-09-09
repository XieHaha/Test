package com.keydom.ih_doctor.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.bean
 * @Description：用户登录实体
 * @Author：song
 * @Date：18/12/15 上午9:51
 * 修改人：xusong
 * 修改时间：18/12/15 上午9:51
 */
public class LoginBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Integer> roleIds;
    private String token;
    private String imToken;
    private String userCode;
    private String phoneNumber;
    private String msspId;
    private int nurseMonitorState;
    private String hospitalId;

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getMsspId() {
        return msspId;
    }

    public void setMsspId(String msspId) {
        this.msspId = msspId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getNurseMonitorState() {
        return nurseMonitorState;
    }

    public void setNurseMonitorState(int nurseMonitorState) {
        this.nurseMonitorState = nurseMonitorState;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getImToken() {
        return imToken;
    }

    public void setImToken(String imToken) {
        this.imToken = imToken;
    }
}
