package com.keydom.mianren.ih_patient.net;

import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.mianren.ih_patient.bean.CheckProjectRootBean;
import com.keydom.mianren.ih_patient.bean.PregnancyDetailBean;
import com.keydom.mianren.ih_patient.bean.PregnancyOrderBean;
import com.keydom.mianren.ih_patient.bean.PregnancyOrderTime;
import com.keydom.mianren.ih_patient.bean.PregnancyRecordItem;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PregnancyService {


    /**
     * 续约记录
     */
    @GET("medicine/antepartum/app/listPersonInspectionRecord")
    Observable<HttpResult<PageBean<PregnancyRecordItem>>> listPersonInspectionRecord(@Query("cardNumber") String cardNumber, @Query("currentPage") int currentPage, @Query("pageSize") int pageSize);


    /**
     * 获取宝妈关怀信息
     */
    @GET("medicine/antepartum/app/pregnantCare/detail")
    Observable<HttpResult<PregnancyDetailBean>> getPregnancyDetail(@Query("cardNumber") String cardNumber);


    /**
     * 获取产检检验检查项目
     */
    @GET("medicine/antepartum/listInspectionProject")
    Observable<HttpResult<CheckProjectRootBean>> getCheckProjects();


    /**
     * 获取产检时间列表
     */
    @GET("medicine/antepartum/listAntepartumTime")
    Observable<HttpResult<List<PregnancyOrderTime>>> getCheckProjectsTimes(@Query("projectId") String projectId);


    /**
     * 产检预约
     */
    @POST("medicine/antepartum/app/appointProductInspection")
    Observable<HttpResult<Object>> commitPregnancy(@Body RequestBody body);


    /**
     * 获取产检详情
     */
    @GET("medicine/antepartum/detailProductInspection")
    Observable<HttpResult<PregnancyOrderBean>> getDetailProductInspection(@Query("recordId") String recordId);

}
