package com.keydom.mianren.ih_doctor.bean;

import java.io.Serializable;

/**
 * @Name：com.keydom.ih_doctor.bean
 * @Description：医生对象
 * @Author：song
 * @Date：18/12/5 下午6:47
 * 修改人：xusong
 * 修改时间：18/12/5 下午6:47
 */
public class GroupInfoBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String groupName;
    private String avatar;
    private String tid;
    private String owner;
    private String groupAdept;

    public String getGroupAdept() {
        return groupAdept;
    }

    public void setGroupAdept(String groupAdept) {
        this.groupAdept = groupAdept;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
