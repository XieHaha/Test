package com.keydom.mianren.ih_doctor.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.bean
 * @Description：服务子项
 * @Author：song
 * @Date：18/11/22 下午9:35
 * 修改人：xusong
 * 修改时间：18/11/22 下午9:35
 */
public class ServiceItemBean implements Serializable,MultiItemEntity {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String code;
    private Integer state;
    private List<ServiceContentBean> doctorServiceSubVoList;

    public List<ServiceContentBean> getDoctorServiceSubVoList() {
        return doctorServiceSubVoList;
    }

    public void setDoctorServiceSubVoList(List<ServiceContentBean> doctorServiceSubVoList) {
        this.doctorServiceSubVoList = doctorServiceSubVoList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public int getItemType() {
        return -1;
    }
}
