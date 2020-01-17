package com.keydom.ih_patient.net;

import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.ih_patient.bean.PregnancyDetailBean;
import com.keydom.ih_patient.bean.PregnancyRecordItem;

import io.reactivex.Observable;
import retrofit2.http.GET;
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
    Observable<HttpResult<Object>> getCheckProjects();


    /**
     * 获取产检时间列表
     */
    @GET("medicine/antepartum/listAntepartumTime")
    Observable<HttpResult<Object>> getCheckProjectsTimes(@Query("projectId") String projectId);


}
