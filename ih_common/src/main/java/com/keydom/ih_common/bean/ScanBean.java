package com.keydom.ih_common.bean;

import java.io.Serializable;

/**
 * @date 20/7/27 11:28
 * @des
 */
public class ScanBean implements Serializable {
    private static final long serialVersionUID = -1257664526625662875L;

    private int type;
    private int signType;
    private String content;
    private String doctorCode;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSignType() {
        return signType;
    }

    public void setSignType(int signType) {
        this.signType = signType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDoctorCode() {
        return doctorCode;
    }

    public void setDoctorCode(String doctorCode) {
        this.doctorCode = doctorCode;
    }
}
