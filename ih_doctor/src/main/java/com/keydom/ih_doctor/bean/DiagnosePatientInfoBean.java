package com.keydom.ih_doctor.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.bean
 * @Description：就诊人对象
 * @Author：song
 * @Date：18/11/21 上午10:14
 * 修改人：xusong
 * 修改时间：18/11/21 上午10:14
 */
public class DiagnosePatientInfoBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String name;
    private int age;
    private String sex;
    private String familyHistory;
    private String allergyHistory;
    private String presentHistory;
    private List<DiagnoseRecoderItemBean> list;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getFamilyHistory() {
        return familyHistory;
    }

    public void setFamilyHistory(String familyHistory) {
        this.familyHistory = familyHistory;
    }

    public String getAllergyHistory() {
        return allergyHistory;
    }

    public void setAllergyHistory(String allergyHistory) {
        this.allergyHistory = allergyHistory;
    }

    public String getPresentHistory() {
        return presentHistory;
    }

    public void setPresentHistory(String presentHistory) {
        this.presentHistory = presentHistory;
    }

    public List<DiagnoseRecoderItemBean> getList() {
        return list;
    }

    public void setList(List<DiagnoseRecoderItemBean> list) {
        this.list = list;
    }


}
