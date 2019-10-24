package com.keydom.ih_patient.bean;

/**
 * created date: 2019/1/7 on 18:42
 * des:我的医生实体
 */
public class DoctorOrNurseBean{

    /**
     * deptName : 科室二
     * userCode : 00001C00002
     * name : 川阿萨德
     * sex : 1
     * image : group1/M00/
     * jobTitleName : 主任医师
     * imToken : 134498caef
     * imNumber : 00001C00002
     */

    private String deptName;
    private String userCode;
    private String name;
    private String sex;
    private String image;
    private String jobTitleName;
    private String imToken;
    private String imNumber;
    private int type;//type   类型  1表示医生 2表示护士
    private int contentNum;
    private String content;
    private long time;


    public int getContentNum() {
        return contentNum;
    }

    public void setContentNum(int contentNum) {
        this.contentNum = contentNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getJobTitleName() {
        return jobTitleName;
    }

    public void setJobTitleName(String jobTitleName) {
        this.jobTitleName = jobTitleName;
    }

    public String getImToken() {
        return imToken;
    }

    public void setImToken(String imToken) {
        this.imToken = imToken;
    }

    public String getImNumber() {
        return imNumber;
    }

    public void setImNumber(String imNumber) {
        this.imNumber = imNumber;
    }
}
