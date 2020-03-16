package com.keydom.mianren.ih_patient.net;

import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.mianren.ih_patient.bean.LocationInfo;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * 地址接口
 */
public interface LocationService {

    /**
     * 获取用户地址
     */
    @GET("user/patient/getAddressList")
    Observable<HttpResult<PageBean<LocationInfo>>> getAddressList(@QueryMap Map<String, Object> body);

    /**
     * 添加地址
     */
    @POST("user/patient/appendAddress")
    Observable<HttpResult<Object> >addAddress(@Body RequestBody body);

    /**
     * 编辑地址
     */
    @POST("user/patient/updateAdress")
    Observable<HttpResult<Object>> editAddress(@Body RequestBody body);

    /**
     * 删除地址
     */
    @GET("user/patient/deleteAddress")
    Observable<HttpResult<Object> >deleteAddress(@QueryMap Map<String, Object> body);

    /**
     * 更新默认地址
     */
    @GET("user/patient/updateDefault")
    Observable<HttpResult<Object>> updateDefault(@QueryMap Map<String, Object> body);

}
