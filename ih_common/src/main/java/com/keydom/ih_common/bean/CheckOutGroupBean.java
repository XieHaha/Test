package com.keydom.ih_common.bean;

import android.text.TextUtils;

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
public class CheckOutGroupBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private long projectId;
    private long deptId;
    private String deptName;
    private String specimenName;
    private String remark;
    private BigDecimal price = BigDecimal.ZERO;
    private List<CheckOutGroupBean> items;
    private BigDecimal totalFee = BigDecimal.ZERO;
    private boolean select = false;

    private String advice;
    private String applicationCode;
    private String applicationName;
    private String executeDeptCode;
    private String executeDeptName;
    private String fee;
    private String insCheckApplicationId;
    private String id;
    private String insCheckCateCode;
    private String insCheckCateName;
    private String isDel;
    private String name;
    private String cateCode;

    private String itemCode;
    private String itemName;
    private String mneCode;
    private String unitCode;
    private String unitName;
    private String specs;
    private String type;
    private String is_del;

    private String insCheckItemName;
    private String insCheckItemCode;
    private String insCheckApplicationCateId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCateCode() {
        return cateCode;
    }

    public void setCateCode(String cateCode) {
        this.cateCode = cateCode;
    }

    public String getInsCheckItemName() {
        return insCheckItemName;
    }

    public void setInsCheckItemName(String insCheckItemName) {
        this.insCheckItemName = insCheckItemName;
    }

    public String getInsCheckItemCode() {
        return insCheckItemCode;
    }

    public void setInsCheckItemCode(String insCheckItemCode) {
        this.insCheckItemCode = insCheckItemCode;
    }

    public String getInsCheckApplicationCateId() {
        return insCheckApplicationCateId;
    }

    public void setInsCheckApplicationCateId(String insCheckApplicationCateId) {
        this.insCheckApplicationCateId = insCheckApplicationCateId;
    }

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

    public List<CheckOutGroupBean> getItems() {
        return items;
    }

    public void setItems(List<CheckOutGroupBean> items) {
        this.items = items;
    }

    /**
     * 多选的情况
     *
     * @return
     */
    public List<CheckOutGroupBean> selectedItems() {
        List<CheckOutGroupBean> list = new ArrayList<>();
        if (items != null && items.size() > 0) {
            for (CheckOutGroupBean item : items) {
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
    public CheckOutGroupBean selectedItem() {
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
        for (CheckOutGroupBean bean : selectedItems()) {
            selectTotal = selectTotal.add(bean.getPrice());
        }
        return selectTotal;
    }

    public BigDecimal totalFee() {
        if (items != null && items.size() > 0) {
            for (CheckOutGroupBean item : items) {
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
            for (CheckOutGroupBean item : items) {
                if (item.isSelect()) {
                    item.reset();
                }
            }
        }
    }

    public CheckOutGroupBean copy() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(this);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
        return (CheckOutGroupBean) ois.readObject();
    }

    public void setSelectItem(CheckOutGroupBean bean) {
        if (items != null && items.size() > 0) {
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getProjectId() == bean.getProjectId()) {
                    items.set(i, bean);
                }
            }
        }
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public String getApplicationCode() {
        return applicationCode;
    }

    public void setApplicationCode(String applicationCode) {
        this.applicationCode = applicationCode;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getExecuteDeptCode() {
        return executeDeptCode;
    }

    public void setExecuteDeptCode(String executeDeptCode) {
        this.executeDeptCode = executeDeptCode;
    }

    public String getExecuteDeptName() {
        return executeDeptName;
    }

    public void setExecuteDeptName(String executeDeptName) {
        this.executeDeptName = executeDeptName;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getInsCheckApplicationId() {
        return insCheckApplicationId;
    }

    public void setInsCheckApplicationId(String insCheckApplicationId) {
        this.insCheckApplicationId = insCheckApplicationId;
    }

    public String getInsCheckCateCode() {
        return insCheckCateCode;
    }

    public void setInsCheckCateCode(String insCheckCateCode) {
        this.insCheckCateCode = insCheckCateCode;
    }

    public String getInsCheckCateName() {
        return insCheckCateName;
    }

    public void setInsCheckCateName(String insCheckCateName) {
        this.insCheckCateName = insCheckCateName;
    }

    public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getMneCode() {
        return mneCode;
    }

    public void setMneCode(String mneCode) {
        this.mneCode = mneCode;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIs_del() {
        return is_del;
    }

    public void setIs_del(String is_del) {
        this.is_del = is_del;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        return TextUtils.equals(getId(), ((CheckOutGroupBean) obj).getId());
    }
}
