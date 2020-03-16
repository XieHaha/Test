package com.keydom.mianren.ih_doctor.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.bean
 * @Description：检验单
 * @Author：song
 * @Date：19/1/10 下午1:20
 * 修改人：xusong
 * 修改时间：19/1/10 下午1:20
 */
public class TestItemBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private long projectId;
    private String name;
    private String deptId;
    private String deptName;
    private BigDecimal price;
    private String remark;
    private List<TestItemBean> items;
    private BigDecimal totalFee = BigDecimal.ZERO;
    private boolean select = false;

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public boolean isSelect() {
        return select;
    }


    public void setSelect(boolean select) {
        this.select = select;
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

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public List<TestItemBean> getItems() {
        return items;
    }

    public void setItems(List<TestItemBean> items) {
        this.items = items;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
