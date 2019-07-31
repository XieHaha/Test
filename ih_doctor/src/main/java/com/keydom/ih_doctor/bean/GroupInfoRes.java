package com.keydom.ih_doctor.bean;

import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.bean
 * @Description：团队列表对象
 * @Author：song
 * @Date：18/12/13 上午10:45
 * 修改人：xusong
 * 修改时间：18/12/13 上午10:45
 */
public class GroupInfoRes {
    private int isCreate;
    private List<GroupInfoBean> list;

    public int getIsCreate() {
        return isCreate;
    }

    public void setIsCreate(int isCreate) {
        this.isCreate = isCreate;
    }

    public List<GroupInfoBean> getList() {
        return list;
    }

    public void setList(List<GroupInfoBean> list) {
        this.list = list;
    }
}
