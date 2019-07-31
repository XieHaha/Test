package com.keydom.ih_doctor.bean;
import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 文章评论
 * </p>
 * @since 2018-11-21
 */
public class ArticleComment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;
    /**
     * 文章ID
     */
    private Integer articleId;
    /**
     * 评论人id
     */
    private Integer criticsId;
    /**
     * 评论人名称
     */
    private String criticsName;
    /**
     * 评论人头像
     */
    private String criticsImage;
    /**
     * 被评论人名称
     */
    private String byCriticsName;
    /**
     * 被评论内容
     */
    private String byCommentsContext;
    /**
     * 我的评论
     */
    private String myCommentContexxt;
    /**
     * 评论点赞数
     */
    private Integer commentLike;
    /**
     * 评论日期
     */
    private Date commentTime;
    /**
     * 备注
     */
    private String remark;

    /**
     * 是否点赞
     * @return
     */
    private Integer isLike;

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

    public String getMyCommentContexxt() {
        return myCommentContexxt;
    }

    public void setMyCommentContexxt(String myCommentContexxt) {
        this.myCommentContexxt = myCommentContexxt;
    }

    public Integer getCommentLike() {
        return commentLike;
    }

    public void setCommentLike(Integer commentLike) {
        this.commentLike = commentLike;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
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

    @Override
    public String toString() {
        return "ArticleComment{" +
        ", id=" + id +
        ", articleId=" + articleId +
        ", criticsId=" + criticsId +
        ", criticsImage=" + criticsImage +
        ", byCriticsName=" + byCriticsName +
        ", byCommentsContext=" + byCommentsContext +
        ", myCommentContexxt=" + myCommentContexxt +
        ", commentLike=" + commentLike +
        ", commentTime=" + commentTime +
        ", remark=" + remark +
        "}";
    }
}
