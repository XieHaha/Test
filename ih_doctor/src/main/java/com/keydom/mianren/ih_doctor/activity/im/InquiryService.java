package com.keydom.mianren.ih_doctor.activity.im;

import com.keydom.ih_common.bean.SpeakLimitBean;
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
     * @param userId  用户ID
     * @param orderId 订单id
     * @param type    查询类型 1.患者查询  0.医生查询
     * @return
     */
    @GET("user/online/doctorGetOrderInfoByUserId")
    Observable<HttpResult<InquiryBean>> getOrderDetails(@Query("userId") String userId, @Query(
            "orderId") long orderId, @Query("type") String type);

    /**
     * 获取当前问诊单自己问诊发言权限
     *
     * @param userOrderId 订单id
     * @return
     */
    @GET("user/inquiryDoctorJurisdiction/getDoctorLimit")
    Observable<HttpResult<SpeakLimitBean>> getDoctorLimit(@Query("userOrderId") String userOrderId);

    //    /**
    //     * 查询与患者的问诊订单状态
    //     *
    //     * @param userId 用户ID
    //     * @param type   查询类型 1.患者查询  0.医生查询
    //     * @return
    //     */
    //    @GET("user/online/getPatientInquisitionById")
    //    Observable<HttpResult<InquiryBean>> getOrderDetails(@Query("userId") String userId,
    //    @Query(
    //            "type") String type);

    /**
     * 医生接诊
     *
     * @param body
     * @return
     */
    @POST("user/online/acceptInquisition")
    Observable<HttpResult<Object>> acceptInquisition(@Body RequestBody body);

    /**
     * 医生接诊  群聊
     *
     * @param body
     * @return
     */
    @POST("user/online/pcAcceptInquisition")
    Observable<HttpResult<Object>> pcAcceptInquisition(@Body RequestBody body);

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

    /**
     * 判断订单是否支付
     *
     * @param orderId 0,3是未支付状态
     * @return
     */
    @GET("user/online/isPay")
    Observable<HttpResult<Integer>> isPay(@Query("orderId") String orderId);
}
