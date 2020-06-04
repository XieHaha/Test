package com.keydom.ih_common.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.bean
 * @Description：提交检查单返回对象
 * @Author：song
 * @Date：19/1/16 下午12:42
 * 修改人：xusong
 * 修改时间：19/1/16 下午12:42
 */
public class SubmitInspectOrderReqBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private long deptId;
    private String name;
    private String remark;
    private String deptName;
    private List<SubmitInspectOrderReqBean> items;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

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


    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public List<SubmitInspectOrderReqBean> getItems() {
        return items;
    }

    public void setItems(List<SubmitInspectOrderReqBean> items) {
        this.items = items;
    }
}
