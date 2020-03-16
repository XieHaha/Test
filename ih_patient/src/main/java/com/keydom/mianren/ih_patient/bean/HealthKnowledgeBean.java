package com.keydom.mianren.ih_patient.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

public class HealthKnowledgeBean implements Serializable {

    /**
     * total : 45
     * size : 2
     * current : 2
     * records : [{"id":134,"hospitalId":"1078843924124262400","title":"aaa","summary":"ss","image":"","content":"<p>s<\/p>","creatorCode":"47","createTime":"2019-05-15 18:02:30","pageView":35,"remark":null,"labels":null,"commentQuantity":14,"isDel":0},{"id":133,"hospitalId":"1078843924124262400","title":"234","summary":"234","image":"","content":"<p>22<\/p>","creatorCode":"47","createTime":"2019-05-15 18:02:21","pageView":12,"remark":null,"labels":null,"commentQuantity":0,"isDel":0}]
     * pages : 23
     */

    @JSONField(name ="total")
    private String total;
    @JSONField(name ="size")
    private int size;
    @JSONField(name ="current")
    private int current;
    @JSONField(name ="pages")
    private String pages;
    @JSONField(name ="records")
    private List<KnowledgeBean> records;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
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

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public List<KnowledgeBean> getRecords() {
        return records;
    }

    public void setRecords(List<KnowledgeBean> records) {
        this.records = records;
    }

    public static class KnowledgeBean {
        /**
         * id : 134
         * hospitalId : 1078843924124262400
         * title : aaa
         * summary : ss
         * image : 
         * content : <p>s</p>
         * creatorCode : 47
         * createTime : 2019-05-15 18:02:30
         * pageView : 35
         * remark : null
         * labels : null
         * commentQuantity : 14
         * isDel : 0
         */

        @JSONField(name ="id")
        private int id;
        @JSONField(name ="hospitalId")
        private String hospitalId;
        @JSONField(name ="title")
        private String title;
        @JSONField(name ="summary")
        private String summary;
        @JSONField(name ="imageList")
        private List<String> imageList;
        @JSONField(name ="content")
        private String content;
        @JSONField(name ="creatorCode")
        private String creatorCode;
        @JSONField(name ="createTime")
        private String createTime;
        @JSONField(name ="pageView")
        private int pageView;
        @JSONField(name ="remark")
        private String remark;
        @JSONField(name ="lablelist")
        private List<String> lablelist;
        @JSONField(name ="commentQuantity")
        private int commentQuantity;
        @JSONField(name ="isDel")
        private int isDel;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getHospitalId() {
            return hospitalId;
        }

        public void setHospitalId(String hospitalId) {
            this.hospitalId = hospitalId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public List<String> getImageList() {
            return imageList;
        }

        public void setImageList(List<String> imageList) {
            this.imageList = imageList;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreatorCode() {
            return creatorCode;
        }

        public void setCreatorCode(String creatorCode) {
            this.creatorCode = creatorCode;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getPageView() {
            return pageView;
        }

        public void setPageView(int pageView) {
            this.pageView = pageView;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public List<String> getLablelist() {
            return lablelist;
        }

        public void setLablelist(List<String> lablelist) {
            this.lablelist = lablelist;
        }

        public int getCommentQuantity() {
            return commentQuantity;
        }

        public void setCommentQuantity(int commentQuantity) {
            this.commentQuantity = commentQuantity;
        }

        public int getIsDel() {
            return isDel;
        }

        public void setIsDel(int isDel) {
            this.isDel = isDel;
        }
    }
}
