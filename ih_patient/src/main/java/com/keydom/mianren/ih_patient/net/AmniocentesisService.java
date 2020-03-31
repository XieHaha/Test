package com.keydom.mianren.ih_patient.net;

import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.mianren.ih_patient.bean.PainlessDeliveryBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * @date 20/3/4 16:19
 * @des 羊水穿刺预约
 */
public interface AmniocentesisService {

    /**
     * 羊水穿刺评估
     */
    @POST("medicine/applyAmniocentesis/estimate")
    Observable<HttpResult<String>> amniocentesisEvaluate(@Body RequestBody body);

    /**
     * 羊水穿刺预约
     */
    @POST("medicine/applyAmniocentesis/apply")
    Observable<HttpResult<String>> amniocentesisApply(@Body RequestBody body);

    /**
     * 无痛分娩预约列表
     */
    @GET("medicine/medicinePainlessLabor/list")
    Observable<HttpResult<PageBean<PainlessDeliveryBean>>> getPainlessDeliveryList();

}
