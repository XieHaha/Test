package com.keydom.mianren.ih_patient.net;

import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.mianren.ih_patient.bean.HealthManagerMainBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author 顿顿
 * @date 21/3/29 15:34
 * @des 健康管理
 */
public interface HealthManagerService {
    /**
     * 健康管理首页
     */
    @GET("medicine/patientHealthManage/patientHealthManageIndex")
    Observable<HttpResult<HealthManagerMainBean>> patientHealthManageIndex();

    /**
     * 开通健康管理
     */
    @GET("medicine/patientHealthManage/openHealthManage")
    Observable<HttpResult<String>> openHealthManager(@Query("eleCardNumber") String eleCardNumber);

    /**
     * 开通慢病管理
     */
    @POST("medicine/chronicDiseaseManage/openChronicDiseaseManage")
    Observable<HttpResult<String>> openChronicDiseaseManage(@Body RequestBody body);

}
