package com.keydom.mianren.ih_doctor.activity.im;

import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.mianren.ih_doctor.bean.InquiryBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface InquiryService {

    /**
     * 查询与患者的问诊订单状态
     *
     * @param userId 用户ID
     * @param type   查询类型 1.患者查询  0.医生查询
     * @return
     */
    @GET("user/online/doctorGetOrderInfoByUserId")
    Observable<HttpResult<InquiryBean>> getOrderDetails(@Query("userId") String userId, @Query("type") String type);

    /**
     * 医生接诊
     *
     * @param body
     * @return
     */
    @POST("user/online/acceptInquisition")
    Observable<HttpResult<Object>> acceptInquisition(@Body RequestBody body);

    /**
     * 医生申请结束问诊
     *
     * @param body
     * @return
     */
    @POST("user/online/endInquisition")
    Observable<HttpResult<Object>> endInquisition(@Body RequestBody body);

    /**
     * 医生取消转诊
     *
     * @param id
     * @return
     */
    @GET("user/referral/cancel")
    Observable<HttpResult<Object>> stopReferral(@Query("id") long id);
}
