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
    private int total;
    private int size;
    private int current;
    private int pages;

    private List<EvaluationBean> records;

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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<EvaluationBean> getRecords() {
        return records;
    }

    public void setRecords(List<EvaluationBean> records) {
        this.records = records;
    }
}
