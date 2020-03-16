package com.keydom.mianren.ih_doctor.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Name：com.keydom.ih_doctor.bean
 * @Description：申请转诊返回对象
 * @Author：song
 * @Date：18/11/21 上午10:14
 * 修改人：xusong
 * 修改时间：18/11/21 上午10:14
 */
public class DiagnoseFillOutResBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private String referralExplain;
    private String diseaseData;
    private BigDecimal fee;

    public String getDiseaseData() {
        return diseaseData;
    }

    public void setDiseaseData(String diseaseData) {
        this.diseaseData = diseaseData;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getReferralExplain() {
        return referralExplain;
    }

    public void setReferralExplain(String referralExplain) {
        this.referralExplain = referralExplain;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }
}
