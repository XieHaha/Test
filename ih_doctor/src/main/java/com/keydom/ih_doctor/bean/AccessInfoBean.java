package com.keydom.ih_doctor.bean;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 文章
 * </p>
 *
 * @author 赵参谋
 * @since 2018-11-19
 */
public class AccessInfoBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private int access;
    private List<ServiceBean> service;

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }

    public List<ServiceBean> getService() {
        return service;
    }

    public void setService(List<ServiceBean> service) {
        this.service = service;
    }
}
