package com.keydom.mianren.ih_patient.net;

import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.mianren.ih_patient.bean.HealthArchivesBean;
import com.keydom.mianren.ih_patient.bean.HealthConsultantBean;
import com.keydom.mianren.ih_patient.bean.HealthManagerMainBean;
import com.keydom.mianren.ih_patient.bean.HealthSummaryBean;
import com.keydom.mianren.ih_patient.bean.InterventionPlanBean;

import java.util.List;

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

    /**
     * 保存患者健康档案
     */
    @POST("medicine/patientHealthManage/savePatientInfo")
    Observable<HttpResult<String>> savePatientInfo(@Body RequestBody body);

    /**
     * 获取患者健康档案
     */
    @GET("medicine/patientHealthManage/patientInfo")
    Observable<HttpResult<HealthArchivesBean>> getPatientInfo(@Query("patientId") String patientId);

    /**
     * 健康总结列表
     */
    @POST("medicine/patientHealthManage/patientHealthConclusionList")
    Observable<HttpResult<PageBean<HealthSummaryBean>>> patientHealthConclusionList(@Body RequestBody body);

    /**
     * 健康总结详情
     */
    @GET("medicine/patientHealthManage/patientHealthConclusionDetail")
    Observable<HttpResult<HealthSummaryBean>> patientHealthConclusionDetail(@Query(
            "consultRecordId") String consultRecordId);

    /**
     * 获取干预方案列表
     */
    @POST("medicine/doctorHealthManage/interventionPlanList")
    Observable<HttpResult<PageBean<InterventionPlanBean>>> interventionPlanList(@Body RequestBody body);

    /**
     * 获取干预方案详情
     */
    @GET("medicine/patientHealthManage/patientInterventionPlanDetail")
    Observable<HttpResult<InterventionPlanBean>> interventionPlanDetail(@Query("planId") String planId);

    /**
     * 获取可咨询医生列表
     */
    @GET("medicine/patientHealthManage/healthDoctorList")
    Observable<HttpResult<List<HealthConsultantBean>>> healthDoctorList();

    /**
     * 修改咨询记录
     */
    @GET("medicine/patientHealthManage/updateConsultTimeAndRecord")
    Observable<HttpResult<String>> updateConsultTimeAndRecord(@Query("doctorId") long doctorId,
                                                              @Query("patientId") String patientId, @Query("type") int type);

}
