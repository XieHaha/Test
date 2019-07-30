package com.keydom.ih_common.bean;

/**
 * @Name：com.keydom.ih_common.bean
 * @Description：描述信息
 * @Author：song
 * @Date：18/12/10 下午1:01
 * 修改人：xusong
 * 修改时间：18/12/10 下午1:01
 */
public class NoticeInfoBean {


    private int id;
    private int noticeType;
    private String publisher;
    private String submitTime;
    private String content;
    private String title;
    private int attention;
    private String images;
    private String doctorName;
    private String doctorCity;
    private String doctorDept;
    private String doctorJobTitle;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(int noticeType) {
        this.noticeType = noticeType;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAttention() {
        return attention;
    }

    public void setAttention(int attention) {
        this.attention = attention;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorCity() {
        return doctorCity;
    }

    public void setDoctorCity(String doctorCity) {
        this.doctorCity = doctorCity;
    }

    public String getDoctorDept() {
        return doctorDept;
    }

    public void setDoctorDept(String doctorDept) {
        this.doctorDept = doctorDept;
    }

    public String getDoctorJobTitle() {
        return doctorJobTitle;
    }

    public void setDoctorJobTitle(String doctorJobTitle) {
        this.doctorJobTitle = doctorJobTitle;
    }
}
