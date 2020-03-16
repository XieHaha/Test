package com.keydom.mianren.ih_doctor.bean;

import java.io.Serializable;

/**
 * <p>
 * 文章
 * </p>
 *
 * @since 2018-11-19
 */
public class SignIdBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String signJobId;
    private String signData;
    private String qrCode;

    public String getSignJobId() {
        return signJobId;
    }

    public void setSignJobId(String signJobId) {
        this.signJobId = signJobId;
    }

    public String getSignData() {
        return signData;
    }

    public void setSignData(String signData) {
        this.signData = signData;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
}
