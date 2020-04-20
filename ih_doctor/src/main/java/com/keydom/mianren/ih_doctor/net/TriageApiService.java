package com.keydom.mianren.ih_doctor.net;

import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.bean.TriageBean;
import com.keydom.ih_common.net.result.HttpResult;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * @date 20/3/26 15:47
 * @des 分诊
 */
public interface TriageApiService {
    /**
     * 分诊申请
     */
    @POST("user/triageApply/save")
    Observable<HttpResult<String>> triageOrderApply(@Body RequestBody body);
    /**
     * 分诊接收
     */
    @POST("user/triageApply/doctorTriageConfirm")
    Observable<HttpResult<String>> doctorTriageConfirm(@Body RequestBody body);

    /**
     * 分诊记录
     */
    @GET("user/triageApply/triageApplyList")
    Observable<HttpResult<PageBean<TriageBean>>> triageOrderApplyList(@QueryMap Map<String, Object> map);
}
