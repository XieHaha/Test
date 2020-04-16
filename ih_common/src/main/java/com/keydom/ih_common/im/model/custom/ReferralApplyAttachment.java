package com.keydom.ih_common.im.model.custom;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * 转诊消息
 *
 * @author THINKPAD B
 */
public class ReferralApplyAttachment extends BaseCustomAttachment {

    /**
     * 转诊单id
     */
    private long id;
    /**
     * 转诊单描述
     */
    private String description;
    /**
     * 转诊单图片(最多只显示两张)
     */
    private List<String> descriptionImage;

    /**
     * 转诊应支付金额
     */
    private String amount;

    public ReferralApplyAttachment() {
        super(ICustomAttachmentType.REFERRAL_APPLY);
    }

    @Override
    protected void paresData(JSONObject data) {
        id = data.getLong("id");
        description = data.getString("description");
        amount = data.getString("amount");
        descriptionImage = JSON.parseArray(data.getString("images"), String.class);
    }

    @Override
    protected JSONObject packData() {
        JSONObject data = new JSONObject();
        data.put("id", id);
        data.put("description", description);
        data.put("amount", amount);
        data.put("images", JSON.toJSONString(descriptionImage));
        return data;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getDescriptionImage() {
        return descriptionImage;
    }

    public void setDescriptionImage(List<String> descriptionImage) {
        this.descriptionImage = descriptionImage;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}

