package com.keydom.mianren.ih_doctor.net;

import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.mianren.ih_doctor.bean.ConsultationAdviceBean;
import com.keydom.mianren.ih_doctor.bean.ConsultationBean;
import com.keydom.mianren.ih_doctor.bean.ConsultationDetailBean;
import com.keydom.ih_common.bean.DoctorInfo;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * @date 20/3/26 15:47
 * @des 会诊
 */
public interface ConsultationService {
    /**
     * 会诊申请
     */
    @POST("user/mdt/application/create")
    Observable<HttpResult<String>> consultationOrderApply(@Body RequestBody body);

    /**
     * 获取mdt排班医生
     */
    @GET("user/mdtSchedule/getMdtSchDoctor")
    Observable<HttpResult<List<DoctorInfo>>> getMdtSchDoctor();

    /**
     * 可以会诊的医生
     */
    @GET("user/mdt/doctors")
    Observable<HttpResult<String>> consultationDoctorList();

    /**
     * 会诊记录
     */
    @GET("user/mdt/application/list")
    Observable<HttpResult<PageBean<ConsultationBean>>> consultationOrderApplyList(@QueryMap Map<String, Object> maps);

    /**
     * 会诊记录
     */
    @GET("user/mdt/application/listNew")
    Observable<HttpResult<PageBean<ConsultationBean>>> consultationOrderApplyListNew(@QueryMap Map<String, Object> maps);

    /**
     * 会诊状态变更
     */
    @GET("user/mdt/application/start")
    Observable<HttpResult<String>> changeConsultationOrderStatus(@Query("id") String id);

    /**
     * 会诊 接收
     */
    @GET("user/mdt/application/accept")
    Observable<HttpResult<String>> consultationOrderAccept(@Query("id") String id);

    /**
     * 会诊 详情
     */
    @GET("user/mdt/application/get")
    Observable<HttpResult<ConsultationDetailBean>> consultationOrderDetail(@Query("id") String id);

    /**
     * 提交会诊意见
     */
    @POST("user/mdt/record/comment/create")
    Observable<HttpResult<String>> consultationOrderCreateAdvice(@Body RequestBody body);

    /**
     * 提交会诊意见(问诊单)
     */
    @POST("user/inquiryRecordContent/saveComment")
    Observable<HttpResult<String>> diagnosisOrderCreateAdvice(@Body RequestBody body);

    /**
     * 会诊意见列表
     */
    @GET("user/mdt/record/comment/list")
    Observable<HttpResult<List<ConsultationAdviceBean>>> consultationOrderAdviceList(@Query(
            "recordId") String id);

    /**
     * 会诊意见列表（问诊单）
     */
    @GET("user/inquiryRecordContent/list")
    Observable<HttpResult<List<ConsultationAdviceBean>>> diagnosisOrderAdviceList(@Query(
            "userOrderId") long id);

    /**
     * 会诊室 病历资料
     */
    @GET("user/mdt/application/form")
    Observable<HttpResult<ConsultationDetailBean>> consultationOrderInfo(@Query("id") String id);

    /**
     * 结束会诊
     */
    @GET("user/mdt/application/ending")
    Observable<HttpResult<String>> endConsultationOrder(@Query("recordId") String id, @Query(
            "videoUrl") String videoUrl);

    /**
     * 上传会诊语音
     */
    @POST("user/mdt/record/audio/save")
    Observable<HttpResult<String>> uploadConsultationVoice(@Body RequestBody body);
    /**
     * 上传诊疗语音
     */
    @POST("user/inquiryRecordContent/saveAudio")
    Observable<HttpResult<String>> uploadDiagnosisVoice(@Body RequestBody body);

    /**
     * 申请加入会诊
     */
    @POST("user/mdt/application/apply/join")
    Observable<HttpResult<String>> applyJoinConsultation(@Body RequestBody body);

    /**
     * 处理会诊加入申请
     */
    @POST("user/mdt/application/apply/audit")
    Observable<HttpResult<String>> dealConsultationApply(@Body RequestBody body);
}
