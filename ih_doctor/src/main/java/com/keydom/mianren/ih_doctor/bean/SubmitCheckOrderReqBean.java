package com.keydom.mianren.ih_doctor.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.bean
 * @Description：提交检验单返回对象
 * @Author：song
 * @Date：19/1/16 下午12:42
 * 修改人：xusong
 * 修改时间：19/1/16 下午12:42
 */
public class SubmitCheckOrderReqBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private long deptId;
    private String name;
    private String specimenName;
    private String deptName;
    private List<SubmitCheckOrderReqBean> items;

    public long getDeptId() {
        return deptId;
    }

    public void setDeptId(long deptId) {
        this.deptId = deptId;
    }

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

    public String getSpecimenName() {
        return specimenName;
    }

    public void setSpecimenName(String specimenName) {
        this.specimenName = specimenName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public List<SubmitCheckOrderReqBean> getItems() {
        return items;
    }

    public void setItems(List<SubmitCheckOrderReqBean> items) {
        this.items = items;
    }
}
