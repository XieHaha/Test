package com.keydom.ih_patient.bean.entity;

public enum LogisticsEnum {

    ON_WAY("0", "在途中"),
    TOOK_A("1", "已揽收"),
    QUESTION("2", "疑难"),
    FINISH("3", "已签收"),
    BACK_SIGN("4", "退签中"),
    CITY_SEND("5", "同城派送中"),
    BACK_GOODS("6", "退回"),
    TURN("7", "转单");
    private String value;
    private String name;

    LogisticsEnum(String value, String name) {
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

