package com.keydom.mianren.ih_patient.net;

import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.mianren.ih_patient.bean.ChildHealthProjectBean;
import com.keydom.mianren.ih_patient.bean.ChildHealthRootBean;
import com.keydom.mianren.ih_patient.bean.HealthCardResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author 顿顿
 * 儿保预约
 */
public interface ChildHealthService {

    /**
     * 儿保预约记录
     */
    @GET("user/childProject/getChildHistory")
    Observable<HttpResult<ChildHealthRootBean>> getChildHistory(@Query("eleCardNumber") String eleCardNumber);


    /**
     * 儿保预约提交
     */
    @GET("user/childProject/childAppointSubmit")
    Observable<HttpResult<List<HealthCardResponse>>> childAppointSubmit(@Query("ehealthCardId") String idCard);


    /**
     * 获取儿保项目列表
     */
    @POST("user/childProject/childProList")
    Observable<HttpResult<List<ChildHealthProjectBean>>> childProjectList();


}
