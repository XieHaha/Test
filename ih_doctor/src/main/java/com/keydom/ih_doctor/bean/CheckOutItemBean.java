package com.keydom.ih_doctor.bean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.bean
 * @Description：检查子项对象
 * @Author：song
 * @Date：19/1/14 下午2:02
 * 修改人：xusong
 * 修改时间：19/1/14 下午2:02
 */
public class CheckOutItemBean implements Serializable {
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
        if (price == null) {
            return BigDecimal.ZERO;
        }
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

    /**
     * 多选的情况
     *
     * @return
     */
    public List<CheckOutItemBean> selectedItems() {
        List<CheckOutItemBean> list = new ArrayList<>();
        if (items != null && items.size() > 0) {
            for (CheckOutItemBean item : items) {
                if (item.isSelect()) {
                    list.add(item);
                }
            }
        }
        return list;
    }


    /**
     * 单选的情况
     *
     * @return
     */
    public CheckOutItemBean selectedItem() {
        if (items != null && items.size() > 0) {
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).isSelect()) {
                    return items.get(i);
                }
            }
        }
        return null;
    }


    public BigDecimal getSelectTotalFee() {
        BigDecimal selectTotal = BigDecimal.ZERO;
        for (CheckOutItemBean bean : selectedItems()) {
            selectTotal = selectTotal.add(bean.getPrice());
        }
        return selectTotal;
    }

    public List<SubmitInspectOrderReqBean> getSelectReqList() {
        List<SubmitInspectOrderReqBean> list = new ArrayList<>();
        for (CheckOutItemBean bean : selectedItems()) {
            SubmitInspectOrderReqBean reqBean = new SubmitInspectOrderReqBean();
            reqBean.setId(bean.getProjectId());
            reqBean.setName(bean.getName());
            reqBean.setDeptId(bean.getDeptId());
            reqBean.setDeptName(bean.getDeptName());
            reqBean.setDeptName(bean.getDeptName());
            reqBean.setRemark(bean.getRemark());
            reqBean.setItems(bean.getSelectReqList());
            list.add(reqBean);
        }
        return list;
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

    public void reset() {
        this.setSelect(false);
        if (items != null && items.size() > 0) {
            for (CheckOutItemBean item : items) {
                if (item.isSelect()) {
                    item.reset();
                }
            }
        }
    }

    public CheckOutItemBean copy() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(this);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
        return (CheckOutItemBean) ois.readObject();
    }

    public void setSelectItem(CheckOutItemBean bean) {
        if (items != null && items.size() > 0) {
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getProjectId() == bean.getProjectId()) {
                    items.set(i, bean);
                }
            }
        }
    }

    public boolean filter(String key) {
        if (items != null && items.size() > 0) {
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getName() != null && items.get(i).getName().contains(key)) {
                    return true;
                }
            }
        }
        return false;
    }


}
