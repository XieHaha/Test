package com.keydom.mianren.ih_doctor.bean;

import java.io.Serializable;

/**
 * @date 20/5/27 16:59
 * @des 会诊视频 音频moudle
 */
public class RecordVideoInfoBean implements Serializable {
    private static final long serialVersionUID = -7852083763204374157L;
    private String id;
    private String recordId;
    private String url;
    private String createTime;
    private String duration;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
