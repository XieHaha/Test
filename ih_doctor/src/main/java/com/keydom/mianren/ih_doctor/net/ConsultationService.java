package com.keydom.mianren.ih_doctor.net;

import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.mianren.ih_doctor.bean.InquiryBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * @date 20/3/26 15:47
 * @des 会诊
 */
public interface ConsultationService {
    /**
     * 会诊申请
     */
    @POST("user/mdt/application/create")
    Observable<HttpResult<String>> consultationOrderApply(@Body RequestBody body);

    /**
     * 会诊记录
     */
    @GET("user/mdt/application/list")
    Observable<HttpResult<PageBean<InquiryBean>>> consultationOrderApplyList(@QueryMap Map<String, Object> maps);
}
