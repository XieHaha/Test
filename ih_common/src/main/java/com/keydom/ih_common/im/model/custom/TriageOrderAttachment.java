package com.keydom.ih_common.im.model.custom;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * 分诊单消息
 *
 * @author THINKPAD B
 */
public class TriageOrderAttachment extends BaseCustomAttachment implements Serializable {

    private static final long serialVersionUID = 6863912969780974835L;
    /**
     * 问诊单，姓名
     */
    private String name;
    /**
     * 问诊单，性别
     */
    private String sex;
    /**
     * 问诊单，年龄
     */
    private String age;
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
    private long id;

    public TriageOrderAttachment() {
        super(ICustomAttachmentType.TRIAGE_ORDER);
    }

    @Override
    protected void paresData(JSONObject data) {
        name = data.getString("name");
        sex = data.getString("sex");
        age = data.getString("age");
        content = data.getString("content");
        images = JSON.parseArray(data.getString("images"), String.class);
        id = data.getLong("id");
    }

    @Override
    protected JSONObject packData() {
        JSONObject data = new JSONObject();
        data.put("name", name);
        data.put("sex", sex);
        data.put("age", age);
        data.put("content", content);
        data.put("images", JSON.toJSONString(images));
        data.put("id", id);
        return data;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
