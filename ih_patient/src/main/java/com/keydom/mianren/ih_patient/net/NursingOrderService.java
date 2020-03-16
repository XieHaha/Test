package com.keydom.mianren.ih_patient.net;

import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.mianren.ih_patient.bean.NursingOrderBean;
import com.keydom.mianren.ih_patient.bean.NursingOrderChargeBackBean;
import com.keydom.mianren.ih_patient.bean.NursingOrderDetailBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * created date: 2018/12/19 on 10:33
 * des:护理订单接口
 * author: HJW HP
 */
public interface NursingOrderService {
    /**
     * 获取护理订单列表
     */
    @GET("nursing/nursingServiceOrder/getPatientOrderByPatientAndState")
    Observable<HttpResult<PageBean<NursingOrderBean>>> getNursingOrderData(@QueryMap Map<String, Object> body);

    /**
     * 获取护理详情
     */
    @GET("nursing/nursingServiceOrder/getPatientOrderDetailByIdAndState")
    Observable<HttpResult<NursingOrderDetailBean>> getNursingOrderDetail(@Query("id") long id, @Query("state") int state);

    /**
     * 获取退单详情
     */
    @POST("nursing/nursingServiceOrder/patientChargeBackAllSituationByOrderNumber")
    Observable<HttpResult<NursingOrderChargeBackBean>> getNursingOrderChargeBack(@Body RequestBody body);

    /**
     * 护理订单退单
     */
    @POST("nursing/nursingServiceOrder/patientChargeBackByOrderNumberAndId")
    Observable<HttpResult<Object>> nursingOrderChargeBack(@Body RequestBody body);

    /**
     * 支付订单
     */
    @POST("nursing/nursingServiceOrder/patientPayByOrderNumber")
    Observable<HttpResult<String>> nursingOrderPay(@Body RequestBody body);

    /**
     * 评价订单
     */
    @POST("nursing/nursingServiceEvaluate/patientOrderComment")
    Observable<HttpResult<Object>> nursingOrderEvaluate(@Body RequestBody body);
}
