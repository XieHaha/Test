package com.keydom.ih_patient.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.ih_patient.adapter.NursingOrderServiceAdapter;

/**
 * created date: 2018/12/20 on 15:08
 * des:子订单头部
 */
public class NursingOrderServiceItemBean implements MultiItemEntity{
    /**
     * acceptTime : 2018-12-20 15:07:07
     * content : 被派遣护士
     * mark : 张静：护士执业资格号
     * userImage : 头像
     * url : 访问url
     */

    private String acceptTime;
    private String content;
    private String mark;
    private String userImage;
    private String url;
    private boolean isTop;
    private boolean isBottom;

    private String qualificationsCard;

    public String getQualificationsCard() {
        return qualificationsCard;
    }

    public void setQualificationsCard(String qualificationsCard) {
        this.qualificationsCard = qualificationsCard;
    }

    public boolean isTop() {
        return isTop;
    }

    public void setTop(boolean top) {
        isTop = top;
    }

    public boolean isBottom() {
        return isBottom;
    }

    public void setBottom(boolean bottom) {
        isBottom = bottom;
    }

    public String getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int getItemType() {
        return NursingOrderServiceAdapter.TYPE_LEVEL_1;
    }
}
