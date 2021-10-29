package com.keydom.mianren.ih_doctor.bean;

import java.io.Serializable;

/**
 * @date 21/9/18 17:18
 * @des 签章参数
 */
public class SignPdfInfoBean implements Serializable {
    private static final long serialVersionUID = 9145449887917596121L;
    private String cloudCertPass;
    private int llx;
    private int lly;
    private int urx;
    private int ury;
    private String pageList;

    public String getCloudCertPass() {
        return cloudCertPass;
    }

    public void setCloudCertPass(String cloudCertPass) {
        this.cloudCertPass = cloudCertPass;
    }

    public int getLlx() {
        return llx;
    }

    public void setLlx(int llx) {
        this.llx = llx;
    }

    public int getLly() {
        return lly;
    }

    public void setLly(int lly) {
        this.lly = lly;
    }

    public int getUrx() {
        return urx;
    }

    public void setUrx(int urx) {
        this.urx = urx;
    }

    public int getUry() {
        return ury;
    }

    public void setUry(int ury) {
        this.ury = ury;
    }

    public String getPageList() {
        return pageList;
    }

    public void setPageList(String pageList) {
        this.pageList = pageList;
    }
}
