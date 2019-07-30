package com.keydom.ih_doctor.net;

import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.ih_doctor.bean.LoginBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * @Name：com.keydom.ih_doctor.net
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/15 下午7:09
 * 修改人：xusong
 * 修改时间：18/11/15 下午7:09
 */
public interface LoginApiService {

    /**
     * 登录
     *
     * @return
     */
    @POST("user/hosptaluser/login")
    Observable<HttpResult<LoginBean>> login(@Body RequestBody body);

    /**
     * 获取图片验证码<Br/>
     *
     * @param maps 请求参数
     * @return
     */
    @POST("user/hosptaluser/sendCodeThreeTime")
    Observable<HttpResult<String>> loginSendCode(@QueryMap Map<String, Object> maps);


    /**
     * 获取验证码
     *
     * @param maps
     * @return
     */
    @GET("user/hosptaluser/sendCode")
    Observable<HttpResult<String>> sendCode(@QueryMap Map<String, Object> maps);

    /**
     * 校验验证码
     *
     * @param maps
     * @return
     */
    @POST("user/hosptaluser/verificationCode")
    Observable<HttpResult<String>> verificationCode(@QueryMap Map<String, Object> maps);

    /**
     * 校验登录验证码
     *
     * @param maps
     * @return
     */
    @POST("user/hosptaluser/updateTimes")
    Observable<HttpResult<String>> loginVerificationCode(@QueryMap Map<String, Object> maps);


    /**
     * 更新密码
     * @param body
     * @return
     */
    @POST("user/hosptaluser/updatePassword")
    Observable<HttpResult<LoginBean>> updatePassword(@Body RequestBody body);


}
