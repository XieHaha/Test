package com.keydom.mianren.ih_doctor.bean;

import java.io.Serializable;

/**
 * <p>
 * 文章
 * </p>
 *
 * @since 2018-11-19
 */
public class SignRegInfoBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String authCode;
    private String msspId;
    private String qrCode;
    private String name;
    private String idCard;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getMsspId() {
        return msspId;
    }

    public void setMsspId(String msspId) {
        this.msspId = msspId;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
}
