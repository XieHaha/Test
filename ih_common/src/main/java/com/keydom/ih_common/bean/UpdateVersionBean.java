package com.keydom.ih_common.bean;

import java.io.Serializable;

public class UpdateVersionBean implements Serializable {
    private static final long serialVersionUID = -8579978882613280079L;

    private Integer id;
    /**
     * 应用系统
     */
    private String applicationSystem;
    /**
     * 系统平台
     */
    private String systemPlatform;
    /**
     * 医院id
     */
    private String hospitalId;
    /**
     * 医院名
     */
    private String hospitalName;
    /**
     * 版本号
     */
    private Integer versionNumber;
    /**
     * 版本名
     */
    private String versionName;
    /**
     * 是否强制更新
     *  0 不强制 ， 1 强制
     */
    private Integer isForcedUpdate = 1;
    /**
     * 更新地址
     */
    private String url;
    /**
     * 更新内容
     */
    private String updateContent;
    /**
     * 创建时间
     */
    private String createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApplicationSystem() {
        return applicationSystem;
    }

    public void setApplicationSystem(String applicationSystem) {
        this.applicationSystem = applicationSystem;
    }

    public String getSystemPlatform() {
        return systemPlatform;
    }

    public void setSystemPlatform(String systemPlatform) {
        this.systemPlatform = systemPlatform;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public Integer getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(Integer versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public Integer getIsForcedUpdate() {
        return isForcedUpdate;
    }

    public void setIsForcedUpdate(Integer isForcedUpdate) {
        this.isForcedUpdate = isForcedUpdate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
