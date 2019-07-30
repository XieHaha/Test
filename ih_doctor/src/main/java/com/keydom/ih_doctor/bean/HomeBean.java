package com.keydom.ih_doctor.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.bean
 * @Description：首页列表对象
 * @Author：song
 * @Date：18/12/15 上午10:05
 * 修改人：xusong
 * 修改时间：18/12/15 上午10:05
 */
public class HomeBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<IndexMenuBean> list;

    private AccessInfoBean auth;
    private UserInfo info;

    public List<IndexMenuBean> getList() {
        return list;
    }

    public void setList(List<IndexMenuBean> list) {
        this.list = list;
    }

    public UserInfo getInfo() {
        return info;
    }

    public void setInfo(UserInfo info) {
        this.info = info;
    }

    public AccessInfoBean getAuth() {
        return auth;
    }

    public void setAuth(AccessInfoBean auth) {
        this.auth = auth;
    }
}
