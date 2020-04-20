package com.keydom.ih_common.im.model.custom;


import com.alibaba.fastjson.JSONObject;
import com.keydom.ih_common.im.model.custom.bean.InquiryDataBean;

import java.io.Serializable;
import java.util.List;

/**
 * 分诊单消息
 *
 * @author THINKPAD B
 */
public class TriageOrderAttachment extends BaseCustomAttachment implements Serializable {

    private static final long serialVersionUID = -5214008745724645617L;
    /**
     * 问诊单，姓名
     */
    private String patientName;
    /**
     * 问诊单，性别
     */
    private String sex;
    /**
     * 问诊单，年龄
     */
    private String age;
    private String dept;
    private InquiryDataBean inquiryData;
    private String groupTid;
    private String doctorName;
    private String applyTime;
    /**
     * 问诊单，描述
     */
    private String content;
    /**
     * 问诊单，图片地址
     */
    private List<String> images;

    /**
     * 问诊单Id
     */
    private String id;
    /**
     * 申请单id
     */
    private String triageApplyId;

    public TriageOrderAttachment() {
        super(ICustomAttachmentType.TRIAGE_ORDER);
    }

    @Override
    protected void paresData(JSONObject data) {
        patientName = data.getString("patientName");
        sex = data.getString("sex");
        age = data.getString("age");
        content = data.getString("content");
        dept = data.getString("dept");
        groupTid = data.getString("groupTid");
        doctorName = data.getString("doctorName");
        id = data.getString("id");
        applyTime = data.getString("applyTime");
        images = data.getJSONArray("images").toJavaList(String.class);
        inquiryData = data.getJSONObject("inquiryData").toJavaObject(InquiryDataBean.class);
    }

    @Override
    protected JSONObject packData() {
        JSONObject object = new JSONObject();
        object.put("patientName", patientName);
        object.put("sex", sex);
        object.put("age", age);
        object.put("content", content);
        object.put("dept", dept);
        object.put("inquiryData", inquiryData);
        object.put("groupTid", groupTid);
        object.put("doctorName", doctorName);
        object.put("applyTime", applyTime);
        object.put("images", images);
        object.put("id", id);
        return object;
    }


    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public InquiryDataBean getInquiryData() {
        return inquiryData;
    }

    public void setInquiryData(InquiryDataBean inquiryData) {
        this.inquiryData = inquiryData;
    }

    public String getGroupTid() {
        return groupTid;
    }

    public void setGroupTid(String groupTid) {
        this.groupTid = groupTid;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTriageApplyId() {
        return triageApplyId;
    }

    public void setTriageApplyId(String triageApplyId) {
        this.triageApplyId = triageApplyId;
    }
}
