package com.keydom.ih_doctor.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.bean
 * @Description：处方药品列表详情
 * @Author：song
 * @Date：18/11/21 上午10:14
 * 修改人：xusong
 * 修改时间：18/11/21 上午10:14
 */
public class PrescriptionDrugDetailBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private int type;
    private int cate;
    private String name;
    private List<List<DrugBean>> items;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCate() {
        return cate;
    }

    public void setCate(int cate) {
        this.cate = cate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<List<DrugBean>> getItems() {
        return items;
    }

    public void setItems(List<List<DrugBean>> items) {
        this.items = items;
    }
}
