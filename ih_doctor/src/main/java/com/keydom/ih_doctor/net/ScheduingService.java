package com.keydom.ih_doctor.net;

import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.ih_doctor.bean.ConsultingBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * @Name：com.keydom.ih_doctor.net
 * @Description：门诊排班
 * @Author：song
 * @Date：18/11/15 下午7:09
 * 修改人：xusong
 * 修改时间：18/11/15 下午7:09
 */
public interface ScheduingService {

    /**
     * 获取门诊排班列表
     * @param maps
     * @return
     */
    @GET("user/scheduing/list")
    Observable<HttpResult<List<ConsultingBean>>> getConsultingList(@QueryMap Map<String, Object> maps);


    /**
     * 获取停诊列表
     * @param maps
     * @return
     */
    @GET("user/scheduing/stopList")
    Observable<HttpResult<List<ConsultingBean>>> getStopConsultingList(@QueryMap Map<String, Object> maps);


    /**
     * 获取循环排班列表
     * @param maps
     * @return
     */
    @GET("user/scheduing/loopList")
    Observable<HttpResult<List<ConsultingBean>>> getLoopList(@QueryMap Map<String, Object> maps);

    /**
     * 修改排班
     * @param body
     * @return
     */
    @POST("user/scheduing/edit")
    Observable<HttpResult<String>> updateConsulting(@Body RequestBody body);


    /**
     * 新增循环排班
     * @param body
     * @return
     */
    @POST("user/scheduing/addLoop")
    Observable<HttpResult<String>> addLoopConsulting(@Body RequestBody body);


    /**
     * 新增停诊
     * @param body
     * @return
     */
    @POST("user/scheduing/addStop")
    Observable<HttpResult<String>> addStopConsulting(@Body RequestBody body);


    /**
     * 删除停诊
     * @param body
     * @return
     */
    @POST("user/scheduing/deleteStop")
    Observable<HttpResult<String>> deleteStopConsulting(@Body RequestBody body);

    /**
     * 删除循环排班
     * @param body
     * @return
     */
    @POST("user/scheduing/deleteLoop")
    Observable<HttpResult<String>> deleteLoopConsulting(@Body RequestBody body);








}
