package com.keydom.mianren.ih_patient.constant;

/**
 * @date 20/3/31 10:08
 * @des 协议
 */
public enum AmniocentesisProtocol {
    /**
     * 羊膜腔穿刺门诊及手术网上预约协议
     */
    AMNIOCENTESIS_WEB_RESERVE(1,"http://www.tianfuzhl.com.cn/wechat_mr_test/h5/views/amniocentesis/protocol.html"),

    /**
     * 羊膜腔穿刺术知情同意书
     */
    AMNIOCENTESIS_AGREE_PROTOCOL(2,"http://www.tianfuzhl.com.cn/wechat_mr_test/h5/views/amniocentesis/informed-consent.html"),

    /**
     * 羊膜腔穿刺术术前及术后注意事项
     */
    AMNIOCENTESIS_NOTICE(3,"http://www.tianfuzhl.com.cn/wechat_mr_test/h5/views/amniocentesis/operation-precautions.html");

    int type;
    String url;
    AmniocentesisProtocol(int type, String s) {
        this.type = type;
        this.url = s;
    }

    public int getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }
}
