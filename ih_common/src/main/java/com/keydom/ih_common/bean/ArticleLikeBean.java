package com.keydom.ih_common.bean;

import java.util.List;

/**
 * @Name：com.keydom.ih_common.bean
 * @Description：描述信息
 * @Author：song
 * @Date：18/12/10 上午10:18
 * 修改人：xusong
 * 修改时间：18/12/10 上午10:18
 */
public class ArticleLikeBean {

    private int total;
    private int size;
    private int current;
    private List<ArticleLikeUserBean> records;
    private int pages;

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

    public List<ArticleLikeUserBean> getRecords() {
        return records;
    }

    public void setRecords(List<ArticleLikeUserBean> records) {
        this.records = records;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
