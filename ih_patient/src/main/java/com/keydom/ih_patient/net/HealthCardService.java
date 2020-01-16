package com.keydom.ih_patient.net;

import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.ih_patient.bean.HealthCardResponse;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface HealthCardService {


    /**
     * 查询是否有绑定过健康卡
     */
    @GET("virdz_1214_war_exploded/virtual/user/getState")
    Observable<HttpResult<String>> getState(@Query("idCard") String idCard);


    /**
     * 查询 健康卡 二维码
     */
    @GET("virdz_1214_war_exploded/quick/response/applyText")
    Observable<HttpResult<List<HealthCardResponse>>> applyText(@Query("ehealthCardId") String idCard);


    /**
     * 添加健康卡信息
     */
    @POST("virdz_1214_war_exploded/virtual/user/addCard")
    Observable<HttpResult<Boolean>> addCard(@Body RequestBody body);



    /**
     * 查询绑定健康卡信息
     */
    @POST("virdz_1214_war_exploded/virtual/user/queryCard")
    Observable<HttpResult<List<HealthCardResponse>>> queryCard(@Body RequestBody body);

}
