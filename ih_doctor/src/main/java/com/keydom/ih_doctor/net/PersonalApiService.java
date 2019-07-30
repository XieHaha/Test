package com.keydom.ih_doctor.net;

import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.ih_doctor.bean.ArticleItemBean;
import com.keydom.ih_doctor.bean.AttentionBean;
import com.keydom.ih_doctor.bean.EvaluationRes;
import com.keydom.ih_doctor.bean.HomeMsgBean;
import com.keydom.ih_doctor.bean.MyIncomeBean;
import com.keydom.ih_doctor.bean.PersonalHomeBean;
import com.keydom.ih_doctor.bean.PersonalInfoBean;
import com.keydom.ih_doctor.bean.ServiceItemBean;
import com.keydom.ih_doctor.bean.UserCard;

import java.util.ArrayList;
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
public interface PersonalApiService {

    /**
     * 获取医生二维码
     *
     * @param maps
     * @return
     */
    @GET("user/hospitalUserCenter/card")
    Observable<HttpResult<UserCard>> getCard(@QueryMap Map<String, Object> maps);


    /**
     * 我的关注
     *
     * @param maps
     * @return
     */
    @GET("user/hospitalUserCenter/attention")
    Observable<HttpResult<ArrayList<AttentionBean>>> getAttention(@QueryMap Map<String, Object> maps);


    /**
     * 患者评价
     *
     * @param maps
     * @return
     */
    @GET("user/hospitalUserCenter/patientComment")
    Observable<HttpResult<EvaluationRes>> patientComment(@QueryMap Map<String, Object> maps);


    /**
     * 我的收藏
     *
     * @param maps
     * @return
     */
    @GET("user/hospitalUserCenter/collect")
    Observable<HttpResult<ArrayList<ArticleItemBean>>> getMyCollect(@QueryMap Map<String, Object> maps);


    /**
     * 删除我的文章
     *
     * @param body
     * @return
     */
    @POST("user/hospitalUserCenter/deleteArticle")
    Observable<HttpResult<String>> deleteArticle(@Body RequestBody body);


    /**
     * 我的文章
     *
     * @param maps
     * @return
     */
    @GET("user/hospitalUserCenter/article")
    Observable<HttpResult<ArrayList<ArticleItemBean>>> getMyArticle(@QueryMap Map<String, Object> maps);


    /**
     * 我的服务
     *
     * @param maps
     * @return
     */
    @GET("user/hospitalUserCenter/service")
    Observable<HttpResult<ArrayList<ServiceItemBean>>> getMySrevice(@QueryMap Map<String, Object> maps);

    /**
     * 开启服务
     *
     * @param maps
     * @return
     */
    @POST("user/hospitalUserCenter/enabledService")
    Observable<HttpResult<String>> enabledService(@QueryMap Map<String, Object> maps);

    /**
     * 关闭服务
     *
     * @param maps
     * @return
     */
    @POST("user/hospitalUserCenter/disabledService")
    Observable<HttpResult<String>> disabledService(@QueryMap Map<String, Object> maps);


    /**
     * 我的信息
     *
     * @param maps
     * @return
     */
    @GET("user/hospitalUserCenter/info")
    Observable<HttpResult<PersonalInfoBean>> info(@QueryMap Map<String, Object> maps);

    /**
     * 修改个人信息
     *
     * @return
     */
    @POST("user/hospitalUserCenter/editInfo")
    Observable<HttpResult<String>> editInfo(@Body RequestBody body);


    /**
     * 获取实名认证验证码
     *
     * @param maps
     * @return
     */
    @GET("user/hosptaluser/sendAutonymCode")
    Observable<HttpResult<String>> sendAutonymCode(@QueryMap Map<String, Object> maps);


    /**
     * 实名认证
     *
     * @param body
     * @return
     */
    @POST("user/hospitalUserCenter/autonymAuth")
    Observable<HttpResult<String>> autonymAuth(@Body RequestBody body);


    /**
     * 个人中心首页
     *
     * @param maps
     * @return
     */
    @GET("user/hospitalUserCenter/home")
    Observable<HttpResult<PersonalHomeBean>> home(@QueryMap Map<String, Object> maps);

    /**
     * 个人中心－反馈
     *
     * @param body
     * @return
     */
    @POST("user/hospitalUserCenter/feedBack")
    Observable<HttpResult<String>> feedBack(@Body RequestBody body);


    @GET("user/hospitalUserCenter/wallet")
    Observable<HttpResult<MyIncomeBean>> wallet(@QueryMap Map<String, Object> maps);

    /**
     * 查询未读总条数
     */
    @GET("api/messageNotification/countMessage")
    Observable<HttpResult<MyIncomeBean>> countMessage(@Query("userId") String userId);

    /**
     * 更新消息状态 rowId
     */
    @POST("api//messageNotification/updateMessageStatus")
    Observable<HttpResult<MyIncomeBean>> updateMessageStatus(@Body RequestBody body);

    /**
     * 查询消息列表
     */
    @GET("api/messageNotification/userMessageInfos")
    Observable<HttpResult<MyIncomeBean>> userMessageInfos(@Query("userId") String userId);

    /**
     * 查询是否存在未读服务
     */
    @GET("user/hosptaluser/home")
    Observable<HttpResult<HomeMsgBean>> homeCountMessage(@Query("roleId") String roleId);
}
