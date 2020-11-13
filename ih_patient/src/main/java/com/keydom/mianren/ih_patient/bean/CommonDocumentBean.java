package com.keydom.mianren.ih_patient.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * created date: 2019/3/28 on 10:08
 * des:文书维护实体类
 */
public class CommonDocumentBean {
    public static final String CODE_1 = "001";//在线问诊用户服务协议
    public static final String CODE_2 = "002";//护理服务用户服务协议
    public static final String CODE_3 = "003";//入院注意事项
    public static final String CODE_4 = "004";//体检中心介绍
    public static final String CODE_5 = "005";//体检须知
    public static final String CODE_6 = "006";//体检流程
    public static final String CODE_7 = "007";//护理知情书
    public static final String CODE_8 = "008";//注册用户协议
    public static final String CODE_9 = "009";//支付用户协议
    public static final String CODE_10 = "010";//在线问诊服务介绍
    public static final String CODE_13 = "013";//护理服务保险条款
    public static final String CODE_14 = "014";//在线问诊保险条款
    public static final String CODE_15 = "015";//团体体检
    public static final String CODE_16 = "016";//护理服务保险条款
    public static final String CODE_17 = "017";//在线问诊保险条款
    public static final String CODE_18 = "018";//团体体检
    public static final String CODE_19 = "019";//无痛分娩注意书
    public static final String CODE_101 = "101";//报告查询
    public static final String CODE_102 = "102";//个体体检
    public static final String CODE_103 = "103";//个性化体检
    public static final String CODE_100 = "100";//孕妇学校

    @JSONField(name = "linkUrl")
    private String url;
    @JSONField(name = "name")
    private String title;
    @JSONField(name = "content")
    private String content;
    @JSONField(name = "code")
    private String type;

    private long id;
    private long hospitalId;
    private long officialDispatchCodeId;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
