package com.keydom.ih_patient.net;

import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.ih_patient.bean.CheckItemListBean;
import com.keydom.ih_patient.bean.DiagnoseCaseBean;
import com.keydom.ih_patient.bean.DiagnoseOrderDetailBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * @Description：检验接口
 * @Author：song
 * @Date：18/11/15 下午7:09
 * 修改人：xusong
 * 修改时间：18/11/15 下午7:09
 */
public interface DiagnoseApiService {

    /**
     * 获取检验单详情
     */
    @GET("user/online/getCheckoutDetail")
    Observable<HttpResult<CheckItemListBean>> getCheckoutDetail(@Query("id") long id);


    /**
     * 获取检查单详情
     */
    @GET("user/online/getInspectDetail")
    Observable<HttpResult<CheckItemListBean>> getInspectDetail(@Query("id") long id);


    /**
     * 获取转诊详情
     */
    @GET("user/referral/getDetail")
    Observable<HttpResult<DiagnoseOrderDetailBean>> getDetail(@QueryMap Map<String, Object> maps);

    /**
     * 获取病历详情
     *
     * @param maps
     * @return
     */
    @GET("user/online/getMedicalRecordInfo")
    Observable<HttpResult<DiagnoseCaseBean>> getMedicalRecordInfo(@QueryMap Map<String, Object> maps);


}
