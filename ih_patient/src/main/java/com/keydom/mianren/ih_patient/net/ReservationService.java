package com.keydom.mianren.ih_patient.net;

import com.keydom.ih_common.bean.DoctorInfo;
import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.mianren.ih_patient.bean.PhysicalExaInfo;
import com.keydom.mianren.ih_patient.bean.SubscribeExaminationBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * created date: 2018/12/13 on 17:05
 * des:体检预约订单接口
 */
public interface ReservationService {
    /**
     *     //获取体检预约订单列表
     */
    @GET("reservation/healthCheckComboOrder/getAllList")
    Observable<HttpResult<PageBean<SubscribeExaminationBean>>> getExaminationOrderList(@QueryMap Map<String,Object> map);

    /**
     *     //删除体检预约订单
     */
    @GET("reservation/healthCheckComboOrder/deleteOrderbyId")
    Observable<HttpResult<Object>> deleteExaminationOrder(@Query("id") long id);

    /**
     *     //获取体检套餐详情
     */
    @GET("reservation/healthCheckCombo/findhealthCheckComboInfo")
    Observable<HttpResult<PhysicalExaInfo>> findhealthCheckComboInfo(@Query("id") long id);

    /**
     *     //退单
     */
    @POST("reservation/healthCheckComboOrder/refund")
    Observable<HttpResult<Object>> findhealthCheckComborefund(@Body RequestBody body);

    /**
     *     //生成订单
     */
    @GET("reservation/healthCheckComboOrder/lowerOrder")
    Observable<HttpResult<SubscribeExaminationBean>> findhealthCheckComborelowerOrder(@QueryMap Map<String,Object> map);

    /**
     * //支付
     */

    @POST("reservation/healthCheckComboOrder/patientPayByOrderNumber")
    Observable<HttpResult<String>> findhealthCheckComboreGoPay(@Body RequestBody body);

    /**
     *     //取消订单
     */
    @GET("reservation/healthCheckComboOrder/cancelOrder")
    Observable<HttpResult<Object>> findhealthCheckComboreCancel(@Query("id") long id);

    /**
     *     //提交评价
     */
    @POST("reservation/healthCheckComboOrder/healthCheckComboOrderEvaluate")
    Observable<HttpResult<Object>> healthCheckComboOrderEvaluate(@Body RequestBody body);

    /**
     *     //通过医生code获取挂号医生首页数据
     */
    @GET("reservation/hospitalRegisFee/doctorDetails")
    Observable<HttpResult<DoctorInfo>> doctorDetails(@Query("userCode") String userCode);
}
