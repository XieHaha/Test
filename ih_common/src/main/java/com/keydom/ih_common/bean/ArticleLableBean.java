package com.keydom.ih_common.bean;

import java.io.Serializable;

/**
 * @Name：com.keydom.ih_common.bean
 * @Description：描述信息
 * @Author：song
 * @Date：18/12/4 下午7:16
 * 修改人：xusong
 * 修改时间：18/12/4 下午7:16
 */
public class ArticleLableBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private int lableType;
    private String lableName;
    private String createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLableType() {
        return lableType;
    }

    public void setLableType(int lableType) {
        this.lableType = lableType;
    }

    public String getLableName() {
        return lableName;
    }

    public void setLableName(String lableName) {
        this.lableName = lableName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
