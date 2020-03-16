package com.keydom.mianren.ih_patient.net;

import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;

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
 * 就诊卡接口
 */
public interface CardService {

    /**
     * 获取新卡
     */
    @POST("user/applyElectronicCard/appForCard")
    Observable<HttpResult<Object>> newCard(@Body RequestBody body);

    /**
     * 解除绑定
     */
    @GET("user/electronicCard/editElectronicCard")
    Observable<HttpResult<Object>> unBindCard(@Query("uuid") long uuid, @Query("cardNumber") String cardNumber, @Query("type") String type);

    /**
     * 绑卡
     */
    @POST("user/electronicCard/bindingCard")
    Observable<HttpResult<Object>> bindingCard(@Body RequestBody body);

    /**
     * 获取就诊卡列表
     */
    @GET("user/electronicCard/electronicCardList")
    Observable<HttpResult<List<MedicalCardInfo>>> getCardList(@QueryMap Map<String, Object> body);
}
