package com.keydom.ih_common.bean;

import java.io.Serializable;

/**
 * @date 20/5/20 17:13
 * @des
 */
public class VoiceBean implements Serializable {

    private static final long serialVersionUID = 8988375127618926907L;
    private String url;
    private String createTime;
    private String duration;

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
