package com.keydom.ih_patient.net;

import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.net.result.HttpResult;
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


    @GET("medicine/antepartum/app/pregnantCare/detail")
    Observable<HttpResult<Object>> getPregnancyDetail(@Query("cardNumber") String cardNumber, @Query("date") String date);
}
