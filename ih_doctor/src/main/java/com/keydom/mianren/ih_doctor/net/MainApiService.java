package com.keydom.mianren.ih_doctor.net;

import com.alibaba.fastjson.JSONObject;
import com.keydom.ih_common.bean.SearchResultBean;
import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.mianren.ih_doctor.bean.Article;
import com.keydom.mianren.ih_doctor.bean.ArticleListBean;
import com.keydom.mianren.ih_doctor.bean.DeptBean;
import com.keydom.mianren.ih_doctor.bean.HomeBean;
import com.keydom.mianren.ih_doctor.bean.IndexMenuBean;
import com.keydom.mianren.ih_doctor.bean.MenuBean;
import com.keydom.mianren.ih_doctor.bean.NotificationBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;

/**
 * @Name：com.keydom.ih_doctor.net
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/15 下午7:09
 * 修改人：xusong
 * 修改时间：18/11/15 下午7:09
 */
public interface MainApiService {

    /**
     * 获取首页菜单
     *
     * @param maps
     * @return
     */
    @POST("user/doctor/homePage")
    Observable<HttpResult<MenuBean>> getMenu(@QueryMap Map<String, Object> maps);


    /**
     * 新的获取菜单
     *
     * @param maps
     * @return
     */
    @POST("user/hosptaluser/listMenuByUser")
    Observable<HttpResult<List<IndexMenuBean>>> getIndexMenu(@QueryMap Map<String, Object> maps);


    /**
     * 获取首页信息和userInfo
     *
     * @param maps
     * @return
     */
    @GET("user/hosptaluser/home")
    Observable<HttpResult<HomeBean>> home(@QueryMap Map<String, Object> maps);


    /**
     * 批量开通所有服务
     *
     * @param maps
     * @return
     */
    @POST("user/hospitalUserCenter/batchEnableAllService")
    Observable<HttpResult<String>> batchEnableAllService(@QueryMap Map<String, Object> maps);


    /**
     * 获取同行动态文章列表
     *
     * @param
     * @return
     */
    @GET("user/article/articleList")
    Observable<HttpResult<List<ArticleListBean>>> getTrendsArticle(@QueryMap Map<String, Object> maps);

    /**
     * 获取同行动态文章列表
     *
     * @param
     * @return
     */
    @POST("user/article/stickArticleInfo")
    Observable<HttpResult<ArticleListBean>> getstickArticle(@Body RequestBody body);

    /**
     * 获取我的关注文章列表
     *
     * @param
     * @return
     */
    @GET("user/article/myAttention")
    Observable<HttpResult<List<ArticleListBean>>> getAttentionArticle(@QueryMap Map<String,
            Object> maps);


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
    @POST("user/hospitalUserLike/articleLikeInfo")
    Observable<HttpResult<String>> articleLikeInfo(@Body RequestBody body);


    /**
     * 获取评论列表
     *
     * @param
     * @return
     */
    @POST("user/articleComment/articleCommentList")
    Observable<HttpResult<String>> articleCommentList(@Body RequestBody body);


    /**
     * 增加评论
     *
     * @param
     * @return
     */
    @POST("user/articleComment/addArticleComment")
    Observable<HttpResult<String>> addArticleComment(@Body RequestBody body);

    /**
     * 评论点赞 取消点赞
     *
     * @param
     * @return
     */
    @POST("user/articleComment/updateArticleCommentLike")
    Observable<HttpResult<String>> updateArticleCommentLike(@Body RequestBody body);

    /**
     * 获取文章详情
     *
     * @param
     * @return
     */
    @POST("user/article/articleInfo")
    Observable<HttpResult<Article>> articleInfo(@Body RequestBody body);

    /**
     * 发布文章
     *
     * @param
     * @return
     */
    @POST("user/article/addArticle")
    Observable<HttpResult<String>> addArticle(@Body RequestBody body);


    /**
     * 发布公告
     *
     * @param
     * @return
     */
    @POST("user/notice/saveNotice")
    Observable<HttpResult<String>> addNotification(@Body RequestBody body);

    /**
     * 获取通知列表
     *
     * @return
     */
    @GET("user/notice/list")
    Observable<HttpResult<List<NotificationBean>>> getNotification(@QueryMap Map<String, Object> maps);

    @Multipart
    @POST("api/file/upload")
    Observable<HttpResult<String>> upload(@Part MultipartBody.Part file);

    @Multipart
    @POST("api/file/upload")
    Observable<HttpResult<String>> uploadMore(@Part ArrayList<MultipartBody.Part> files);

    /**
     * 获取当前院区下所有科室
     * POST
     *
     * @param
     * @return
     */
    @GET("user/hospitalDept/listDeptsByHospitalArea")
    Observable<HttpResult<List<DeptBean>>> getDept(@QueryMap Map<String, Object> maps);


    /**
     * 删除公告
     *
     * @param body
     * @return
     */
    @POST("user/notice/deleteNotice")
    Observable<HttpResult<String>> deleteNotice(@Body RequestBody body);

    /**
     * 全局搜索
     *
     * @param maps
     * @return
     */
    @GET("user/search")
    Observable<HttpResult<SearchResultBean>> search(@QueryMap Map<String, Object> maps);

    @GET("user/doctorSearch")
    Observable<HttpResult<SearchResultBean>> doctorSearch(@QueryMap Map<String, Object> maps);

    @GET("user/pageSearch")
    Observable<HttpResult<List<JSONObject>>> pageSearch(@QueryMap Map<String, Object> maps);
}
