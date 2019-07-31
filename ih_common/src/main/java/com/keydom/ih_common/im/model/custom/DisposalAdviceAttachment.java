package com.keydom.ih_common.im.model.custom;

import com.alibaba.fastjson.JSONObject;

public class DisposalAdviceAttachment extends BaseCustomAttachment {

    /**
     * 处置建议Id
     */
    private long id;
    /**
     * 处置建议内容
     */
    private String content;

    public DisposalAdviceAttachment() {
        super(ICustomAttachmentType.DISPOSAL_ADVICE);
    }

    @Override
    protected void paresData(JSONObject data) {
        id = data.getLong("id");
        content = data.getString("content");
    }

    @Override
    protected JSONObject packData() {
        JSONObject data = new JSONObject();
        data.put("id", id);
        data.put("content", content);
        return data;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "DisposalAdviceAttachment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", type=" + type +
                '}';
    }
}
