package com.keydom.mianren.ih_patient.net;

import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.mianren.ih_patient.bean.DepartmentInfo;
import com.keydom.mianren.ih_patient.bean.HospitalCheckBean;
import com.keydom.mianren.ih_patient.bean.HospitalCountBean;
import com.keydom.mianren.ih_patient.bean.HospitalRecordRootBean;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author 顿顿
 * @date 20/9/11 15:30
 * @des
 */
public interface HospitalPaymentService {
    /**
     * 查询住院清单分类列表
     */
    @POST("user/inHospital/inHospitalCostType")
    Observable<HttpResult<List<HospitalCheckBean>>> getHospitalCostType(@Body RequestBody body);

    /**
     * 查询住院清单分类详情
     */
    @POST("user/inHospital/inHospitalCostTypeItem")
    Observable<HttpResult<List<DepartmentInfo>>> getHospitalCostTypeItem(@Body RequestBody body);

    /**
     * 创建住院预缴订单
     */
    @POST("user/inHospital/createInHospitalOrder")
    Observable<HttpResult<List<MedicalCardInfo>>> createInHospitalOrder(@Body RequestBody body);

    /**
     * 获取患者住院信息和订单信息
     */
    @GET("user/inHospital/getInHospitalRecord")
    Observable<HttpResult<HospitalRecordRootBean>> getInHospitalRecord(@Query("eleCardNumber") String value);

    /**
     * 获取所有次数住院信息
     */
    @GET("user/inHospital/inHospitalNoList")
    Observable<HttpResult<HospitalCountBean>> getInHospitalNoList(@Query("eleCardNumber") String value);
}
