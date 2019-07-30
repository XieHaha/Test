package com.keydom.ih_doctor.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.bean
 * @Description：评价列表对象
 * @Author：song
 * @Date：18/12/17 下午6:58
 * 修改人：xusong
 * 修改时间：18/12/17 下午6:58
 */
public class EvaluationRes implements Serializable {
    private static final long serialVersionUID = 1L;
    private int consultCount;
    private int inquiryCount;
    private List<EvaluationBean> list;

    public int getConsultCount() {
        return consultCount;
    }

    public void setConsultCount(int consultCount) {
        this.consultCount = consultCount;
    }

    public int getInquiryCount() {
        return inquiryCount;
    }

    public void setInquiryCount(int inquiryCount) {
        this.inquiryCount = inquiryCount;
    }

    public List<EvaluationBean> getList() {
        return list;
    }

    public void setList(List<EvaluationBean> list) {
        this.list = list;
    }
}
