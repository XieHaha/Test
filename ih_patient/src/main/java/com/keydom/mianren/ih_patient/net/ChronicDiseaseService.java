package com.keydom.mianren.ih_patient.net;

import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.mianren.ih_patient.bean.HealthDataBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * @author 顿顿
 * @date 21/3/29 15:34
 * @des 健康管理
 */
public interface ChronicDiseaseService {
    /**
     * 获取健康值
     */
    @GET("medicine/chronicDiseaseManage/getHeathValue")
    Observable<HttpResult<HealthDataBean>> getHeathValue(@QueryMap Map<String, String> map);

    /**
     * 新增或者修改健康值
     */
    @POST("medicine/chronicDiseaseManage/insertOrUpdateHeathValue")
    Observable<HttpResult<String>> insertOrUpdateHeathValue(@Body RequestBody body);

}
