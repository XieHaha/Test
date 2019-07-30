package com.keydom.ih_common.net;

import com.keydom.ih_common.bean.Article;
import com.keydom.ih_common.bean.ArticleCommentBean;
import com.keydom.ih_common.bean.ArticleLikeBean;
import com.keydom.ih_common.bean.DiagnoseOrderDetailBean;
import com.keydom.ih_common.bean.HealthArticalInfo;
import com.keydom.ih_common.bean.HealthArticleCommentBean;
import com.keydom.ih_common.bean.NoticeInfoBean;
import com.keydom.ih_common.net.result.HttpResult;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * @Name：com.keydom.ih_doctor.net
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/15 下午7:09
 * 修改人：xusong
 * 修改时间：18/11/15 下午7:09
 */
public interface ApiService {

    /**
     * 点赞 取消点赞
     *
     * @param
     * @return
     */
    @POST("user/article/updateArticleLike")
    Observable<HttpResult<String>> likeArticle(@Body RequestBody body);


    /**
     * 收藏 取消收藏
     *
     * @param
     * @return
     */
    @POST("user/hospitalUserCollect/updateMyCollect")
    Observable<HttpResult<String>> collect(@Body RequestBody body);


    /**
     * 获取点赞列表
     *
     * @param
     * @return
     */
    @GET("user/hospitalUserLike/articleLikeInfo")
    Observable<HttpResult<ArticleLikeBean>> articleLikeInfo(@QueryMap Map<String, Object> maps);


    /**
     * 获取评论列表
     *
     * @param
     * @return
     */
    @GET("user/articleComment/articleCommentList")
    Observable<HttpResult<List<ArticleCommentBean>>> articleCommentList(@QueryMap Map<String, Object> maps);


    /**
     * 增加评论
     *
     * @param
     * @return
     */
    @POST("user/articleComment/addArticleComment")
    Observable<HttpResult<ArticleCommentBean>> addArticleComment(@Body RequestBody body);

    /**
     * 评论点赞 取消点赞
     *
     * @param
     * @return
     */
    @POST("user/articleComment/updateArticleCommentLike")
    Observable<HttpResult<String>> updateArticleCommentLike(@Body RequestBody body);

    /**
     * 关注 取消关注
     *
     * @param
     * @return
     */
    @POST("user/attentionDoctor/updateMyAttention")
    Observable<HttpResult<String>> updateMyAttention(@Body RequestBody body);

    /**
     * 获取文章详情
     *
     * @param
     * @return
     */
    @POST("user/article/articleInfo")
    Observable<HttpResult<Article>> articleInfo(@Body RequestBody body);

    /**
     * 获取通知公告详情
     *
     * @param maps
     * @return
     */
    @GET("user/notice/getNoticeInfoByDoctor")
    Observable<HttpResult<NoticeInfoBean>> getNoticeInfo(@QueryMap Map<String, Object> maps);

    /**
     * 获取健康知识详情
     *
     * @param
     * @return
     */
    @GET("user/patient/getHealthKnowledgeInfo")
    Observable<HttpResult<HealthArticalInfo>> getHealthKnowledgeInfo(@QueryMap Map<String, Object> body);


    /**
     * 获取健康知识评论列表
     *
     * @param
     * @return
     */
    @GET("user/healthKnowledg/CommentList")
    Observable<HttpResult<List<HealthArticleCommentBean>>> healthArticleCommentList(@QueryMap Map<String, Object> maps);

    /**
     * 健康知识评论点赞 取消点赞
     *
     * @param
     * @return
     */
    @POST("user/healthKnowledg/updateCommentLike")
    Observable<HttpResult<String>> updateCommentLike(@Body RequestBody body);

    /**
     * 健康知识增加评论
     *
     * @param
     * @return
     */
    @POST("user/healthKnowledg/addComment")
    Observable<HttpResult<HealthArticleCommentBean>> addHealthArticleComment(@Body RequestBody body);


    /**
     * 删除评论
     *
     * @param
     * @return
     */
    @GET("user/articleComment/deleteArticleComment")
    Observable<HttpResult<String>> deleteArticleComment(@QueryMap Map<String, Object> maps);
    /**
     * 删除健康知识评论
     *
     * @param
     * @return
     */
    @POST("/user/healthKnowledg/delComment")
    Observable<HttpResult<String>> delComment(@QueryMap Map<String, Object> maps);

    /**
     * 获取问诊单详情
     * @param id
     * @return
     */
    @GET("user/online/getPatientInquisitionById")
    Observable<HttpResult<DiagnoseOrderDetailBean>> getPatientInquisitionById(@Query("orderId") long id);


}
