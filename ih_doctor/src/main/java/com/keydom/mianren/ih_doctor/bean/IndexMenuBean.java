package com.keydom.mianren.ih_doctor.bean;

import java.io.Serializable;

/**
 * @Name：com.keydom.ih_doctor.bean
 * @Description：首页菜单子项对象
 * @Author：song
 * @Date：18/11/22 下午9:54
 * 修改人：xusong
 * 修改时间：18/11/22 下午9:54
 */
public class IndexMenuBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String code;
    private String name;
    private boolean isRedPointShow=false;

    public boolean isRedPointShow() {
        return isRedPointShow;
    }

    public void setRedPointShow(boolean redPointShow) {
        isRedPointShow = redPointShow;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
