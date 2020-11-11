package com.keydom.mianren.ih_patient.net;

import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.mianren.ih_patient.bean.AmniocentesisBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @date 20/3/4 16:19
 * @des 羊水穿刺预约
 */
public interface AmniocentesisService {

    /**
     * 羊水穿刺评估
     */
    @POST("medicine/applyAmniocentesis/estimate")
    Observable<HttpResult<String>> amniocentesisEvaluate(@Body RequestBody body);

    /**
     * 羊水穿刺预约
     */
    @POST("medicine/applyAmniocentesis/apply")
    Observable<HttpResult<AmniocentesisBean>> amniocentesisApply(@Body RequestBody body);

    /**
     * 羊水穿刺预约列表
     */
    @POST("medicine/applyAmniocentesis/list")
    Observable<HttpResult<PageBean<AmniocentesisBean>>> getAmniocentesisList(@Body RequestBody body);

    /**
     * 羊水穿刺预约取消
     */
    @GET("medicine/applyAmniocentesis/cancel")
    Observable<HttpResult<PageBean<AmniocentesisBean>>> cancelAmniocentesis(@Query("id") int id,
                                                                            @Query("refusedReason") String refusedReason);

    /**
     * 羊水穿刺预约详情
     */
    @GET("medicine/applyAmniocentesis/detail")
    Observable<HttpResult<AmniocentesisBean>> getAmniocentesisDetail(@Query("id") int id);

    /**
     * 羊水穿刺预约 验证码
     */
    @GET("medicine/applyAmniocentesis/sendCode")
    Observable<HttpResult<String>> amniocentesisSendCode(@Query("phoneNumber") String phoneNumber);

}
