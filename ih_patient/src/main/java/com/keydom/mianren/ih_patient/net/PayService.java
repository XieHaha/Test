package com.keydom.mianren.ih_patient.net;

import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.mianren.ih_patient.bean.PayRecordBean;
import com.keydom.mianren.ih_patient.bean.PayRecordDetailBean;
import com.keydom.mianren.ih_patient.bean.PaymentOrderBean;

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
 * created date: 2019/1/15 on 19:40
 * des:缴费记录接口
 */
public interface PayService {
    /**
     * 获取缴费列表
     */
    @GET("pay/consultationPay/getConsultationPayList")
    Observable<HttpResult<List<PayRecordBean>>> getConsultationPayList(@QueryMap Map<String, Object> body);

    /**
     * 获取缴费详情
     */
    @GET("pay/consultationPay/getConsultationPayInfo")
    Observable<HttpResult<PayRecordDetailBean>> getConsultationPayInfo(@Query("documentNo") String documentNo);

    /**
     * 创建支付订单
     */
    @POST("pay/consultationPay/generateOrder")
    Observable<HttpResult<PaymentOrderBean>> generateOrder(@Body RequestBody body);

    /**
     * 发起支付
     */
    @POST("pay/consultationPay/patientPayByOrderNumber")
    Observable<HttpResult<String>> patientPayByOrderNumber(@Body RequestBody body);

    /**
     * 支付宝登录token获取
     */
    @GET("pay/aliPayAuthToken/token")
    Observable<HttpResult<String>> aliPayAuthToken();

    /**
     * 获取配送费用
     */
    @GET("user/patient/getDeliveryCost")
    Observable<HttpResult<String>> getDeliveryCost(@QueryMap Map<String, Object> map);

}
