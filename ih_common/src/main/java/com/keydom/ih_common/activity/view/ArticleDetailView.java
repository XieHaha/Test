package com.keydom.ih_common.activity.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_common.bean.Article;
import com.keydom.ih_common.bean.ArticleCommentBean;
import com.keydom.ih_common.bean.ArticleLikeBean;
import com.keydom.ih_common.bean.NoticeInfoBean;
import com.keydom.ih_common.bean.HealthArticalInfo;
import com.keydom.ih_common.bean.HealthArticleCommentBean;

import java.util.HashMap;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Description：文章发布
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface ArticleDetailView extends BaseView {

    /**
     * 关注
     */
    void attentionSuccess(String successMsg);

    void attentionFailed(String errMsg);

    /**
     * 收藏
     */
    void collectSuccess(String successMsg);

    void collectFailed(String errMsg);


    /**
     * 增加评论
     */
    void addArticleCommentSuccess(ArticleCommentBean commentBean);

    void addArticleCommentFailed(String errMsg);

    /**
     * 获取评论列表
     */
    void articleCommentListSuccess(List<ArticleCommentBean> list);

    void articleCommentListFailed(String errMsg);


    /**
     * 获取点赞列表
     */
    void articleLikeInfoSuccess(ArticleLikeBean bean);

    void articleLikeInfoFailed(String errMsg);


    /**
     * 获取文章详情
     */
    void articleInfoSuccess(Article article);

    void articleInfoFailed(String msg);

    /**
     * 获取健康知识详情
     */
    void healthInfoSuccess(HealthArticalInfo healthArticalInfo);

    void healthInfoFailed(String msg);

    /**
     * 获取健康知识评论列表
     */
    void healthArticleCommentListSuccess(List<HealthArticleCommentBean> list);

    void healthArticleCommentListFailed(String errMsg);

    /**
     * 健康知识增加评论
     */
    void addHealthArticleCommentSuccess(HealthArticleCommentBean commentBean);

    void addHealthArticleCommentFailed(String errMsg);


    void getNoticeInfoSuccess(NoticeInfoBean bean);

    void getNoticeInfoFailed(String msg);

    Integer getCollect();

    HashMap<String, Object> getCollectMap();

    HashMap<String, Object> getCommentMap();

    HashMap<String, Object> getDetailMap();

    HashMap<String, Object> getAttentionMap();

    HashMap<String, Object> getCommentLikeMap();

    HashMap<String, Object> getHealthDetailMap();

    HashMap<String, Object> getHealthCommentMap();

    int getType();

    HashMap<String, Object> getLikeInfoMap();

    HashMap<String, Object> getNoticeDetailMap();




}