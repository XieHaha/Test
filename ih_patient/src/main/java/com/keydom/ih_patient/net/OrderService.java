package com.keydom.ih_patient.net;

import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.ih_patient.bean.DateInfo;
import com.keydom.ih_patient.bean.DepartmentSchedulingBean;
import com.keydom.ih_patient.bean.DocAndDepSearchBean;
import com.keydom.ih_patient.bean.DoctorSchedulingBean;
import com.keydom.ih_patient.bean.ExaminationInfo;
import com.keydom.ih_patient.bean.HospitalAreaInfo;
import com.keydom.ih_patient.bean.HospitalCureInfo;
import com.keydom.ih_patient.bean.HospitaldepartmentsInfo;
import com.keydom.ih_patient.bean.PaymentOrderBean;
import com.keydom.ih_patient.bean.PhysicalExaCommentsInfo;
import com.keydom.ih_patient.bean.PhysicalExaInfo;
import com.keydom.ih_patient.bean.RegistrationRecordInfo;
import com.keydom.ih_patient.bean.SoruInfo;

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
 * 体检订单接口
 */
public interface OrderService {

    /**
     * 获取全部预约
     */
    @GET("reservation/inspectAppointment/findAppointmentAll")
    Observable<HttpResult<PageBean<ExaminationInfo>>> findAppointmentAll(@QueryMap Map<String, Object> body);

    /**
     * 根据医院查找地区id
     */
    @GET("reservation/inspectAppointment/gethospitalAreaList")
    Observable<HttpResult<List<HospitalAreaInfo>>> findHospitalAreaList(@Query("hospitalId") long hospitalId);

    /**
     * 查询检查预约日期列表
     */
    @GET("reservation/inspectAppointment/findSoruDate")
    Observable<HttpResult<List<DateInfo>>> findSoruDate(@QueryMap Map<String, Object> body);

    /**
     * 查询检查排班
     */
    @GET("reservation/inspectAppointment/findSoruInfo")
    Observable<HttpResult<SoruInfo>> findSoruInfo(@QueryMap Map<String, Object> body);

    /**
     * 提交体检
     */
    @GET("reservation/inspectAppointment/inspectMakeAppointment")
    Observable<HttpResult<ExaminationInfo>> inspectMakeAppointment(@QueryMap Map<String, Object> body);

    /**
     * 获取体检详情
     */
    @GET("reservation/healthCheckCombo/findhealthCheckComboAll")
    Observable<HttpResult<List<PhysicalExaInfo>>> findhealthCheckComboAll(@Query("hospitalId") long hospitalCode);

    /**
     * 体检套餐详情描述查询
     */
    @GET("reservation/healthCheckCombo/findhealthCheckCheckDetai")
    Observable<HttpResult<String>> findhealthCheckCheckDetai(@Query("id") long id);
    /**
     * 体检套餐流程查询
     */
    @GET("reservation/healthCheckCombo/findhealthCheckCheckProcess")
    Observable<HttpResult<String>> findhealthCheckCheckProcess(@Query("id") long id);

    /**
     * 体检套餐评论查询
     */
    @GET("reservation/healthCheckCombo/medicalReservationCommentList")
    Observable<HttpResult<List<PhysicalExaCommentsInfo>>> medicalReservationCommentList(@QueryMap Map<String, Object> body);

    /**
     * 获取就诊卡列表
     */
    @GET("reservation/admissionCard/getAdmissionCardList")
    Observable<HttpResult<List<HospitalCureInfo>>> getAdmissionCardList(@Query("id") long id, @Query("hospitalId") long hospitalId);

    /**
     * 发起入院申请
     */
    @POST("reservation/admissionApply/save")
    Observable<HttpResult<Object>> applyHospitalCure(@Body RequestBody body);

    /**
     * 入院申请查询
     */
    @GET("reservation/admissionApply/query")
    Observable<HttpResult<HospitalCureInfo>> queryHospitalCure(@QueryMap Map<String, Object> body);

    /**
     * 是否准时报道
     */
    @GET("reservation/admissionApply/updateStatus")
    Observable<HttpResult<Object>> updateStatus(@QueryMap Map<String, Object> body);

    /**
     * 取消入院申请
     */
    @GET("reservation/admissionApply/cancellationApply")
    Observable<HttpResult<Object>> cancellationApply(@QueryMap Map<String, Object> body);

    /**
     * 预约挂号获取科室列表
     */
    @GET("reservation/hospitalArea/getList")
    Observable<HttpResult<List<HospitaldepartmentsInfo>>> getDepartmentList(@QueryMap Map<String, Object> body);

    /**
     * 模糊查询科室下的医生
     */
    @GET("reservation/hospitalRegisFee/getUserList")
    Observable<HttpResult<List<DepartmentSchedulingBean>>> searchDoctor(@QueryMap Map<String, Object> body);

    /**
     * 模糊查询科室下的医生
     */
    @GET("reservation/hospitalDept/getList")
    Observable<HttpResult<DocAndDepSearchBean>> searchDoctorOrDepartments(@QueryMap Map<String, Object> body);


    /**
     * 查询预约挂号日期列表
     */
    @GET("reservation/hospitalRegisFee/getDateList")
    Observable<HttpResult<List<DateInfo>>> getDateList(@QueryMap Map<String, Object> body);

    /**
     * 查询排班医生列表
     */
    @GET("reservation/hospitalRegisFee/getList")
    Observable<HttpResult<List<DepartmentSchedulingBean>>> getDoctorList(@QueryMap Map<String, Object> body);

    /**
     * 查询医生排班表
     */
    @GET("reservation/hospitalRegisFee/getUser")
    Observable<HttpResult<List<DoctorSchedulingBean>>> getDoctorSchedul(@QueryMap Map<String, Object> body);

    /**
     * 预约挂号退号
     */
    @GET("reservation/registrationRecord/backno")
    Observable<HttpResult<Object>> backno(@QueryMap Map<String, Object> body);

    /**
     * 挂号订单详情
     */
    @GET("reservation/registrationRecord/detail")
    Observable<HttpResult<RegistrationRecordInfo>> detail(@QueryMap Map<String, Object> body);

    /**
     * 挂号订单列表
     */
    @GET("reservation/registrationRecord/list")
    Observable<HttpResult<PageBean<RegistrationRecordInfo>>> list(@QueryMap Map<String, Object> body);

    /**
     * 生成挂号订单号
     */
    @POST("reservation/hospitalRegisFee/userOrderNumber")
    Observable<HttpResult<PaymentOrderBean>> userOrderNumber(@Body RequestBody body);

    /**
     * 挂号订单支付
     */
    @POST("reservation/hospitalRegisFee/hospitalFeeByOrderNumber")
    Observable<HttpResult<String>> hospitalFeeByOrderNumber(@Body RequestBody body);
}
