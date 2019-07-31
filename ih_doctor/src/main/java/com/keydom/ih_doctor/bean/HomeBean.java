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

    private int auditNoPass;
    private int noAudit;
    private int receiveNurse;
    private int receiveInquiry;
    private int visitNurse;

    public int getAuditNoPass() {
        return auditNoPass;
    }

    public void setAuditNoPass(int auditNoPass) {
        this.auditNoPass = auditNoPass;
    }

    public int getNoAudit() {
        return noAudit;
    }

    public void setNoAudit(int noAudit) {
        this.noAudit = noAudit;
    }

    public int getReceiveNurse() {
        return receiveNurse;
    }

    public void setReceiveNurse(int receiveNurse) {
        this.receiveNurse = receiveNurse;
    }

    public int getReceiveInquiry() {
        return receiveInquiry;
    }

    public void setReceiveInquiry(int receiveInquiry) {
        this.receiveInquiry = receiveInquiry;
    }

    public int getVisitNurse() {
        return visitNurse;
    }

    public void setVisitNurse(int visitNurse) {
        this.visitNurse = visitNurse;
    }
}
