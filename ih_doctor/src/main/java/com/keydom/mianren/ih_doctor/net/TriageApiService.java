package com.keydom.mianren.ih_doctor.net;

import com.keydom.ih_common.net.result.HttpResult;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * @date 20/3/26 15:47
 * @des 分诊
 */
public interface TriageApiService {
    /**
     * 分诊申请
     *
     * @param body
     */
    @POST("user/triageApply/save")
    Observable<HttpResult<String>> triageOrderApply(@Body RequestBody body);

    /**
     * 分诊记录
     *
     * @param body
     */
    @GET("user/triageApply/triageApplyList")
    Observable<HttpResult<String>> triageOrderApplyList(@Body RequestBody body);
}
