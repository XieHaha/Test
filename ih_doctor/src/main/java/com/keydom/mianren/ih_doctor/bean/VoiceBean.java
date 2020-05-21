package com.keydom.mianren.ih_doctor.bean;

import java.io.Serializable;

/**
 * @date 20/5/20 17:13
 * @des
 */
public class VoiceBean implements Serializable {

    private static final long serialVersionUID = 8988375127618926907L;
    private String voiceUrl;
    private long voiceTime;

    public String getVoiceUrl() {
        return voiceUrl;
    }

    public void setVoiceUrl(String voiceUrl) {
        this.voiceUrl = voiceUrl;
    }

    public long getVoiceTime() {
        return voiceTime;
    }

    public void setVoiceTime(long voiceTime) {
        this.voiceTime = voiceTime;
    }
}
