package com.keydom.ih_common.event;

import java.io.Serializable;

public class VoiceInputEvent implements Serializable {

    private static final long serialVersionUID = -248821458622654962L;

    public VoiceInputEvent(String voiceStr) {
        this.voiceStr = voiceStr;
    }

    String voiceStr;

    public String getVoiceStr() {
        return voiceStr;
    }

    public void setVoiceStr(String voiceStr) {
        this.voiceStr = voiceStr;
    }
}
