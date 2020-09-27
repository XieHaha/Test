package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 顿顿
 * @date 20/9/27 16:35
 * @des 即将进行的项目  儿保预约
 */
public class ChildHealthDoingBean implements Serializable {
    private static final long serialVersionUID = -6604022922044613997L;

    private int age;
    /**
     * 0 等待医生开具医嘱  1待支付 2已支付 3已取消
     */
    private int state;
    /**
     * 0 即将进行 1正在进行 2已过期
     */
    private int doState;
    private String patientName;
    private String sex;
    private String appointmentTime;
    private String attention;
    private List<ChildHealthProjectItemBean> select;
    private List<ChildHealthProjectItemBean> unSelect;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getDoState() {
        return doState;
    }

    public void setDoState(int doState) {
        this.doState = doState;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }

    public List<ChildHealthProjectItemBean> getSelect() {
        return select;
    }

    public void setSelect(List<ChildHealthProjectItemBean> select) {
        this.select = select;
    }

    public List<ChildHealthProjectItemBean> getUnSelect() {
        return unSelect;
    }

    public void setUnSelect(List<ChildHealthProjectItemBean> unSelect) {
        this.unSelect = unSelect;
    }
}
