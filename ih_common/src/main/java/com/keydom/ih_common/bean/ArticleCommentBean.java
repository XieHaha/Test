package com.keydom.ih_common.bean;

import java.io.Serializable;

/**
 * @Name：com.keydom.ih_common.bean
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/22 下午4:27
 * 修改人：xusong
 * 修改时间：18/11/22 下午4:27
 */
public class ArticleCommentBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer articleId;
    private Integer criticsId;
    private String criticsName;
    private String criticsImage;
    private String byCriticsName;
    private String byCommentsContext;
    private String myCommentContext;
    private int commentLike;
    private String commentTime;
    private String remark;
    private Integer isLike;
    private String criticsCode;

    public String getCriticsCode() {
        return criticsCode;
    }

    public void setCriticsCode(String criticsCode) {
        this.criticsCode = criticsCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getCriticsId() {
        return criticsId;
    }

    public void setCriticsId(Integer criticsId) {
        this.criticsId = criticsId;
    }

    public String getCriticsName() {
        return criticsName;
    }

    public void setCriticsName(String criticsName) {
        this.criticsName = criticsName;
    }

    public String getCriticsImage() {
        return criticsImage;
    }

    public void setCriticsImage(String criticsImage) {
        this.criticsImage = criticsImage;
    }

    public String getByCriticsName() {
        return byCriticsName;
    }

    public void setByCriticsName(String byCriticsName) {
        this.byCriticsName = byCriticsName;
    }

    public String getByCommentsContext() {
        return byCommentsContext;
    }

    public void setByCommentsContext(String byCommentsContext) {
        this.byCommentsContext = byCommentsContext;
    }

    public String getMyCommentContext() {
        return myCommentContext;
    }

    public void setMyCommentContext(String myCommentContexxt) {
        this.myCommentContext = myCommentContexxt;
    }

    public int getCommentLike() {
        return commentLike;
    }

    public void setCommentLike(int commentLike) {
        this.commentLike = commentLike;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsLike() {
        return isLike;
    }

    public void setIsLike(Integer isLike) {
        this.isLike = isLike;
    }
}
