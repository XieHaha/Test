package com.keydom.ih_doctor.net;

import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.ih_doctor.bean.SignRegInfoBean;

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
public interface SignService {

    /**
     * 注册电子签名
     *
     * @param body
     * @return
     */
    @POST("user/electronics/addTrustedUsert")
    Observable<HttpResult<String>> addTrustedUsert(@Body RequestBody body);


    /**
     * 获取电子签名任务ID
     *
     * @param maps
     * @return
     */
    @POST("user/electronics/addAuthJob")
    Observable<HttpResult<String>> addAuthJob(@Body RequestBody body);


    @POST("user/electronics/changeMobile")
    Observable<HttpResult<String>> changeMobile(@Body RequestBody body);


    @GET("user/electronics/getUserElectronicsInfo")
    Observable<HttpResult<SignRegInfoBean>> getUserElectronicsInfo(@QueryMap Map<String, Object> maps);
}
