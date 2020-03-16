package com.keydom.mianren.ih_patient.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 基础服务费
 */
public class BaseNurseFeeBean {

    /**
     * id : 1
     * fee : 100
     * type : 1
     */

   /* @JSONField(name = "id")
    private long id;*/
    @JSONField(name = "fee")
    private String fee;
   /* @JSONField(name = "type")
    private int type;*/

   /* public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }*/

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }
/*
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }*/
}
