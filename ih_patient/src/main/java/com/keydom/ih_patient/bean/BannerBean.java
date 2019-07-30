package com.keydom.ih_patient.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * banner实体
 */
public class BannerBean implements Serializable {

    /**
     * id : 144
     * pictureName : u=753435570,1075989207&fm=200&gp=0.jpg
     * picturePath : group1/M00/00/0A/rBAEA1wkMZGARJdvAABYZT-rYQQ490.jpg
     * pictureUrl :
     */

    @JSONField(name = "id")
    private long id;
    @JSONField(name = "pictureName")
    private String pictureName;
    @JSONField(name = "picturePath")
    private String picturePath;
    @JSONField(name = "pictureUrl")
    private String pictureUrl;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
