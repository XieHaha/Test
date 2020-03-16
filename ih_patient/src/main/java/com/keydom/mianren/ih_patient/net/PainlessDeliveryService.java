package com.keydom.mianren.ih_patient.net;

import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.mianren.ih_patient.bean.PainlessDeliveryBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @date 20/3/4 16:19
 * @des 无痛分娩
 */
public interface PainlessDeliveryService {

    /**
     * 无痛分娩预约
     */
    @POST("medicine/medicinePainlessLabor/insetMedicinePainlessLabor")
    Observable<HttpResult<String>> commitPainlessDelivery(@Body RequestBody body);

    /**
     * 无痛分娩预约列表
     */
    @GET("medicine/medicinePainlessLabor/list")
    Observable<HttpResult<PageBean<PainlessDeliveryBean>>> getPainlessDeliveryList();

    /**
     * 无痛分娩预约列表
     */
    @GET("medicine/medicinePainlessLabor/confirm")
    Observable<HttpResult<String>> cancelPainlessDelivery(@Query("medicinePainlessLaborId") String id);
}
