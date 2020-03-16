package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 首页outItem实体
 * @Author：song
 * @Date：19/1/14 下午2:02
 */
public class CheckOutItemBean implements Serializable{
    private static final long serialVersionUID = 1L;
    private long id;
    private long projectId;
    private long deptId;
    private String deptName;
    private String specimenName;
    private String name;
    private String remark;
    private BigDecimal price = BigDecimal.ZERO;
    private List<CheckOutItemBean> items;
    private BigDecimal totalFee = BigDecimal.ZERO;
    private boolean select = false;

    public String getSpecimenName() {
        return specimenName;
    }

    public void setSpecimenName(String specimenName) {
        this.specimenName = specimenName;
    }

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

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public long getDeptId() {
        return deptId;
    }

    public void setDeptId(long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<CheckOutItemBean> getItems() {
        return items;
    }

    public void setItems(List<CheckOutItemBean> items) {
        this.items = items;
    }

    public CheckOutItemBean selectedItem() {
        if (items != null && items.size() > 0) {
            for (CheckOutItemBean item : items) {
                if (item.isSelect()) {
                    return item;
                }
            }
        }
        return null;
    }

    public BigDecimal totalFee() {
        if (items != null && items.size() > 0) {
            for (CheckOutItemBean item : items) {
                if (item.isSelect()) {
                    totalFee = totalFee.add(item.price);
                }
            }
        }
        return totalFee;
    }


}
