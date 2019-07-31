package com.keydom.ih_doctor.bean;

import java.io.Serializable;

/**
 * @Name：com.keydom.ih_doctor.bean
 * @Description：团队医生对象
 * @Author：song
 * @Date：18/12/6 下午3:15
 * 修改人：xusong
 * 修改时间：18/12/6 下午3:15
 */
public class GroupDoctorBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String name;
    private String image;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
