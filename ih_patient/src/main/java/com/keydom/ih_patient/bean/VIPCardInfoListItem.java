package com.keydom.ih_patient.bean;

import java.io.Serializable;

public class VIPCardInfoListItem implements Serializable {

    private static final long serialVersionUID = -2924128045786955734L;

    /**
     * imgFile : group1/M00/00/36/rBAA0l4YQOSATbZRAALVpJsY2M4701.png
     * title : 吃大餐
     */



    private String imgFile;
    private String title;

    public String getImgFile() {
        return imgFile;
    }

    public void setImgFile(String imgFile) {
        this.imgFile = imgFile;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
