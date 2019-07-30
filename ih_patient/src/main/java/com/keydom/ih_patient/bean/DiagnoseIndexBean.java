package com.keydom.ih_patient.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * 挂号订单
 */
public class DiagnoseIndexBean {

    /**
     * attentionList : [{"uuid":"00001C00042","name":"测试护士","avatar":"group1/M00/00/0D/rBAEA1wwGQOAQWJJAAMSNcO96mI625.png","jobTitle":"副主任医师","adept":"GGvjbddd"}]
     * pay : 7
     * proceeding : 2
     * comment : 2
     * finish : 2
     * accept : 2
     */

    @JSONField(name = "pay")
    private int pay;
    @JSONField(name = "proceeding")
    private int proceeding;
    @JSONField(name = "comment")
    private int comment;
    @JSONField(name = "finish")
    private int finish;
    @JSONField(name = "accept")
    private int accept;
    @JSONField(name = "attentionList")
    private List<AttentionListBean> attentionList;

    @JSONField(name = "unpayTag")
    private int unpayTag;

    public int getUnpayTag() {
        return unpayTag;
    }

    public void setUnpayTag(int unpayTag) {
        this.unpayTag = unpayTag;
    }

    public int getPay() {
        return pay;
    }

    public void setPay(int pay) {
        this.pay = pay;
    }

    public int getProceeding() {
        return proceeding;
    }

    public void setProceeding(int proceeding) {
        this.proceeding = proceeding;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public int getFinish() {
        return finish;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }

    public int getAccept() {
        return accept;
    }

    public void setAccept(int accept) {
        this.accept = accept;
    }

    public List<AttentionListBean> getAttentionList() {
        return attentionList;
    }

    public void setAttentionList(List<AttentionListBean> attentionList) {
        this.attentionList = attentionList;
    }

    public static class AttentionListBean {
        /**
         * uuid : 00001C00042
         * name : 测试护士
         * avatar : group1/M00/00/0D/rBAEA1wwGQOAQWJJAAMSNcO96mI625.png
         * jobTitle : 副主任医师
         * adept : GGvjbddd
         */

        @JSONField(name = "uuid")
        private String uuid;
        @JSONField(name = "name")
        private String name;
        @JSONField(name = "avatar")
        private String avatar;
        @JSONField(name = "jobTitle")
        private String jobTitle;
        @JSONField(name = "adept")
        private String adept;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
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

        public String getAdept() {
            return adept;
        }

        public void setAdept(String adept) {
            this.adept = adept;
        }
    }
}
