package com.keydom.ih_patient.bean.entity;

public enum PharmacyPayEnum {

    //0-未支付，1-已支付,2-已取药/已签收,3-已发货,4-拒收

    UNPAY("0", "未支付"),
    PAID("1", "已支付"),
    GETDRUG("2", "已取药/已签收"),
    SENT("3", "已发货"),
    REJECTED("4", "拒收");
    private String value;
    private String name;

    PharmacyPayEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

