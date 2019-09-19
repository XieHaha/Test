package com.keydom.ih_patient.activity.im;

import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.ih_patient.bean.InquiryBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * 问诊接口
 */
public interface InquiryService {

    /**
     * 查询与患者的问诊订单状态
     *
     * @param userId 用户ID
     * @param type   查询类型 1.患者查询  0.医生查询
     * @return
     */
    @GET("user/online/doctorGetOrderInfoByUserId")
    Observable<HttpResult<InquiryBean>> getOrderDetails(@Query("userId") String userId, @Query("type") String type, @Query("doctorCode") String doctorCode);


    /**
     * 患者确认是否结束问诊
     *
     * @param type -1 取消 1确认
     * @return
     */
    @GET("user/online/patientEndInquisition")
    Observable<HttpResult<Object>> patientEndInquisition(@Query("orderId") long orderId, @Query("type") int type);

    /**
     * 或者主动结束问诊
     *
     * @param orderId 问诊id
     * @return
     */
    @GET("user/online/patientFinishInquisition")
    Observable<HttpResult<Object>> patientFinishInquisition(@Query("orderId") long orderId);

    /**
     * 患者是否同意转诊
     *
     * @param body
     * @return
     */
    @POST("user/referral/userOperate")
    Observable<HttpResult<Object>> userOperateReferral(@Body RequestBody body);

    /**
     * 判断订单是否支付
     *
     * @param orderId
     * 0,3是未支付状态
     * @return
     */
    @GET("user/online/isPay")
    Observable<HttpResult<Integer>> isPay(@Query("orderId") String orderId);

    /**
     * 时都同意换诊
     *
     * @return
     */
    @GET("user/online/confirmChangeDoctor")
    Observable<HttpResult<Object>> confirmChangeDoctor(@QueryMap Map<String, Object> maps);

}
