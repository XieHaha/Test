package com.keydom.mianren.ih_doctor.bean;


import java.io.Serializable;

public class AgreementBean  implements Serializable {
    public static final String CODE_16 = "016";//四川CA协议
    /**
     * id : 30
     * officialDispatchCodeId : 1111
     * code : 011
     * name : 在线问诊医务人员协议（在线问诊/咨询）
     * hospitalId : 1101049670463119361
     * content : <p>11</p>
     * linkUrl :
     * isDel : 0
     */

    private long id;
    private long officialDispatchCodeId;
    private String code;
    private String name;
    private String hospitalId;
    private String content;
    private String linkUrl;
    private int isDel;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOfficialDispatchCodeId() {
        return officialDispatchCodeId;
    }

    public void setOfficialDispatchCodeId(long officialDispatchCodeId) {
        this.officialDispatchCodeId = officialDispatchCodeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }
}
