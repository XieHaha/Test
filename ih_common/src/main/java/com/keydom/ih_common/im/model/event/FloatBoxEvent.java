package com.keydom.ih_common.im.model.event;

public class FloatBoxEvent {
    public static final int DEFAULT_CODE = 1000;
    private int code = DEFAULT_CODE;

    public FloatBoxEvent() {
    }

    public FloatBoxEvent(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
