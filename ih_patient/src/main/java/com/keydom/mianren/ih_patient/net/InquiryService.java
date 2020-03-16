package com.keydom.mianren.ih_patient.net;

import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.mianren.ih_patient.bean.DiagnoseIndexBean;
import com.keydom.mianren.ih_patient.bean.RecommendDocAndNurBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * 问诊接口
 */
public interface InquiryService {

    /**
     *  在线问诊首页
     */
    @GET("user/online/home")
    Observable<HttpResult<DiagnoseIndexBean>> getHomeData(@QueryMap Map<String, Object> body);

    /**
     * 问诊医生列表
     */
    @GET("user/hospitalDept/listHomeRecommendDoctor")
    Observable<HttpResult<PageBean<RecommendDocAndNurBean>>> getListHomeRecommendDoctor(@QueryMap Map<String, Object> body);
}