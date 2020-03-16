package com.keydom.mianren.ih_doctor.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.bean
 * @Description：菜单列表对象
 * @Author：song
 * @Date：18/11/21 上午11:00
 * 修改人：xusong
 * 修改时间：18/11/21 上午11:00
 */
public class MenuBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private UserInfo userInfo;
    private List<IndexMenuItem> menu;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public List<IndexMenuItem> getMenu() {
        return menu;
    }

    public void setMenu(List<IndexMenuItem> menu) {
        this.menu =menu;
    }



}
