package com.keydom.ih_doctor.bean;

import java.io.Serializable;

/**
 * @Name：com.keydom.ih_doctor.bean
 * @Description：护士对象
 * @Author：song
 * @Date：18/12/26 下午1:18
 * 修改人：xusong
 * 修改时间：18/12/26 下午1:18
 */
public class NurseBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private long id;
    private String jobTitleName;
    private String image;

    public String getJobTitleName() {
        return jobTitleName;
    }

    public void setJobTitleName(String jobTitleName) {
        this.jobTitleName = jobTitleName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
