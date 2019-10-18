package com.keydom.ih_common.im.model.custom;

import com.alibaba.fastjson.JSONObject;

public class UserFollowUpAttachment extends BaseCustomAttachment {


    private String doctorName;

    private String id;

    private String fileName;

    private String url;

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public UserFollowUpAttachment() {
        super(ICustomAttachmentType.USER_FOLLOW_UP);
    }

    @Override
    protected void paresData(JSONObject data) {
        id = data.getString("id");
        doctorName = data.getString("doctorName");
        fileName = data.getString("fileName");
        url = data.getString("url");
    }

    @Override
    protected JSONObject packData() {
        JSONObject data = new JSONObject();
        data.put("id", id);
        data.put("doctorName", doctorName);
        data.put("fileName", fileName);
        data.put("url", url);
        return data;
    }

    @Override
    public String toString() {
        return "UserFollowUpAttachment{" +
                "doctorName='" + doctorName + '\'' +
                ", id='" + id + '\'' +
                ", fileName='" + fileName + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
