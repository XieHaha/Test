package com.keydom.mianren.ih_patient.utils.pay.bean;

/**
 * created date: 2018/12/14 on 15:09
 * des:微信支付发起实体
 * author: HJW HP
 */
public class WxPayBean {

    /**
     * appid : wxd8b44896acbb7ad6
     * partnerid : 1506992981
     * prepayid : wx14145806336224c963e9311c0140783873
     * noncestr : BcO6yNd3CX3gHSAr
     * sign : 028AF411E96F029613438618047F778B
     * timestamp : 1544770685955
     */

    private String appid;
    private String partnerid;
    private String prepayid;
    private String noncestr;
    private String sign;
    private String timestamp;
    private String packageValue;

    public String getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
