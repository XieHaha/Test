package com.keydom.ih_common.service;

import com.keydom.ih_common.bean.SpeakLimitBean;
import com.keydom.ih_common.net.result.HttpResult;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SpeakService {

    /**
     * 获取当前问诊单自己问诊发言权限
     *
     * @param userOrderId 订单id
     */
    @GET("user/inquiryDoctorJurisdiction/getJurisdictionList")
    Observable<HttpResult<List<SpeakLimitBean>>> getJurisdictionList(@Query("userOrderId") String userOrderId);
}
