package com.keydom.ih_patient.net;

import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.ih_patient.bean.HealthKnowledgeBean;
import com.keydom.ih_patient.bean.IndexData;
import com.keydom.ih_patient.bean.IndexFunction;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * 首页接口
 */
public interface IndexService {

    /**
     * 主页接口
     */
    @GET("user/patient/homePage")
    Observable<HttpResult<IndexData>> initIndexData(@QueryMap Map<String, Object> body);

    /**
     * 主页获取健康知识
     */
    @GET("user/patient/getHealthKnowledgeLimit")
    Observable<HttpResult<HealthKnowledgeBean>> getHealthKnowledgeLimit(@QueryMap Map<String, Object> body);

    /**
     * 根据用户获取菜单接口
     */
    @GET("user/hosptaluser/listMenuByUser")
    Observable<HttpResult<List<IndexFunction>>> initFunctionData(@QueryMap Map<String, Object> body);
}
