package com.keydom.ih_common.im.model.custom;

import com.alibaba.fastjson.JSONObject;

/**
 * 换诊消息
 *
 * @author THINKPAD B
 */
public class ReferralDoctorAttachment extends BaseCustomAttachment {
    /**
     * 换诊申请id
     */
    private long id;
    /**
     * 接诊医生名称
     */
    private String name;
    /**
     * 接诊医生头像
     */
    private String avatar;
    /**
     * 接诊医生职称
     */
    private String jobTitle;
    /**
     * 团队名称
     */
    private String teamName;

    /**
     * 发起转诊医生名字
     */
    private String currentName;

    public ReferralDoctorAttachment() {
        super(ICustomAttachmentType.REFERRAL_DOCTOR);
    }

    @Override
    protected void paresData(JSONObject data) {
        id = data.getLongValue("id");
        name = data.getString("name");
        avatar = data.getString("avatar");
        jobTitle = data.getString("jobTitle");
        teamName = data.getString("teamName");
        currentName = data.getString("currentName");
    }

    @Override
    protected JSONObject packData() {
        JSONObject data = new JSONObject();
        data.put("id", id);
        data.put("name", name);
        data.put("avatar", avatar);
        data.put("jobTitle", jobTitle);
        data.put("teamName", teamName);
        data.put("currentName", currentName);
        return data;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getCurrentName() {
        return currentName;
    }

    public void setCurrentName(String currentName) {
        this.currentName = currentName;
    }
}
