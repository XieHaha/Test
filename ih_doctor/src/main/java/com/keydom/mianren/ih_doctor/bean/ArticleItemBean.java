package com.keydom.mianren.ih_doctor.bean;

import java.io.Serializable;

/**
 * @Name：com.keydom.ih_doctor.bean
 * @Description：文章详情
 * @Author：song
 * @Date：18/11/21 上午10:14
 * 修改人：xusong
 * 修改时间：18/11/21 上午10:14
 */
public class ArticleItemBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private long articleId;
    private String title;
    private String content;
    private int articleType;
    private String image;
    private String submitTime;
    private String submiter;
    private String avatar;
    private int collectionQuantity;
    private int readQuantity;
    private int likeQuantity;
    private int commentQuantity;
    private int auditResult;
    private String auditOpinion;

    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getArticleType() {
        return articleType;
    }

    public void setArticleType(int articleType) {
        this.articleType = articleType;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public String getSubmiter() {
        return submiter;
    }

    public void setSubmiter(String submiter) {
        this.submiter = submiter;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getCollectionQuantity() {
        return collectionQuantity;
    }

    public void setCollectionQuantity(int collectionQuantity) {
        this.collectionQuantity = collectionQuantity;
    }

    public int getReadQuantity() {
        return readQuantity;
    }

    public void setReadQuantity(int readQuantity) {
        this.readQuantity = readQuantity;
    }

    public int getLikeQuantity() {
        return likeQuantity;
    }

    public void setLikeQuantity(int likeQuantity) {
        this.likeQuantity = likeQuantity;
    }

    public int getCommentQuantity() {
        return commentQuantity;
    }

    public void setCommentQuantity(int commentQuantity) {
        this.commentQuantity = commentQuantity;
    }

    public int getAuditResult() {
        return auditResult;
    }

    public void setAuditResult(int auditResult) {
        this.auditResult = auditResult;
    }

    public String getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion;
    }
}
