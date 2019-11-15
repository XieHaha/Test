package com.keydom.ih_patient.net;

import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.ih_patient.bean.PackageData;
import com.keydom.ih_patient.bean.UserInfo;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * 登录接口
 */
public interface LoginService {


    /**
     * 发送验证码
     */
    @GET("user/appuser/sendCode")
    Observable<HttpResult<Object>> sendCode(@Query("phoneNumber") String phoneNumber,@Query("type") String type);

    /**
     * 验证码
     */
    @GET("user/appuser/sendValidate")
    Observable<HttpResult<Object>> sendValidate(@Query("accountMobile") String phoneNumber);

    /**
     * 验证验证码
     */
    @GET("user/appuser/verificationCode")
    Observable<HttpResult<Object>> verificationCode(@QueryMap Map<String, String> body);

    /**
     * 注册
     */
    @POST("user/appuser/reg")
    Observable<HttpResult<UserInfo>> doRegister(@Body RequestBody body);

    /**
     * 登录
     */
    @POST("user/appuser/login")
    Observable<HttpResult<UserInfo>> doLogin(@Body RequestBody body);

    /**
     * 获取包名
     */
    @GET("user/patient/infoPackage")
    Observable<HttpResult<PackageData>> getPackageData(@Query("version") String versionCode);

    /**
     * 更新密码
     */
    @POST("user/appuser/updatePassword")
    Observable<HttpResult<Object>> updatePassword(@Body RequestBody body);

    /**
     * 更新时间
     */
    @GET("user/appuser/updateTimes")
    Observable<HttpResult<Object>> updateTimes(@QueryMap Map<String, Object> body);

    /**
     * 验证码
     */
    @GET("user/appuser/verificationCodeTeral")
    Observable<HttpResult<UserInfo>> verificationCodeTeral(@QueryMap Map<String, Object> body);
}
