package com.keydom.ih_doctor.net;

import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.ih_doctor.bean.BodyCheckDetailInfo;
import com.keydom.ih_doctor.bean.CheckItemListBean;
import com.keydom.ih_doctor.bean.CheckOutItemBean;
import com.keydom.ih_doctor.bean.DiagnoseHandleBean;
import com.keydom.ih_doctor.bean.DiagnosePatientInfoBean;
import com.keydom.ih_doctor.bean.DrugBean;
import com.keydom.ih_doctor.bean.ICD10Bean;
import com.keydom.ih_doctor.bean.InquiryBean;
import com.keydom.ih_doctor.bean.InspectionDetailInof;
import com.keydom.ih_doctor.bean.MaterialBean;
import com.keydom.ih_doctor.bean.MedicalRecordTempletBean;
import com.keydom.ih_doctor.bean.OrderApplyResponse;
import com.keydom.ih_doctor.bean.PrescriptionDrugDetailBean;
import com.keydom.ih_doctor.bean.PrescriptionTempletBean;

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
 * @Name：com.keydom.ih_doctor.net
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/15 下午7:09
 * 修改人：xusong
 * 修改时间：18/11/15 下午7:09
 */
public interface DiagnoseApiService {

    /**
     * 获取问诊订单列表
     *
     * @param maps
     * @return
     */
    @GET("user/online/listInquisition")
    Observable<HttpResult<List<InquiryBean>>> listInquisition(@QueryMap Map<String, Object> maps);


    /**
     * 添加服务项目
     *
     * @param body
     * @return
     */
    @POST("nursing/nursingServiceSubOrder/nurseAddProjectToSubOrder")
    Observable<HttpResult<String>> nurseAddProjectToSubOrder(@Body RequestBody body);


    /**
     * 提交检验申请单
     *
     * @param body
     * @return
     */
    @POST("user/online/saveCheckout")
    Observable<HttpResult<List<OrderApplyResponse>>> saveCheckout(@Body RequestBody body);

    /**
     * 提交检查申请单
     *
     * @param body
     * @return
     */
    @POST("user/online/saveInspect")
    Observable<HttpResult<List<OrderApplyResponse>>> saveInspect(@Body RequestBody body);


    /**
     * 获取检验单详情
     *
     * @param id
     * @return
     */
    @GET("user/online/getCheckoutDetail")
    Observable<HttpResult<CheckItemListBean>> getCheckoutDetail(@Query("id") long id);


    /**
     * 获取检查单详情
     *
     * @param id
     * @return
     */
    @GET("user/online/getInspectDetail")
    Observable<HttpResult<CheckItemListBean>> getInspectDetail(@Query("id") long id);


    /**
     * 病历模板问诊订单列表
     *
     * @param maps
     * @return
     */
    @GET("user/prescription/medicalTemplateList")
    Observable<HttpResult<List<MedicalRecordTempletBean>>> listMedicalTemplate(@QueryMap Map<String, Object> maps);

    /**
     * 病历模板明细
     *
     * @param id
     * @return
     */
    @GET("user/prescription/medicalTemplateInfo")
    Observable<HttpResult<MedicalRecordTempletBean>> detailMedicalTemplate(@Query("id") long id);

    /**
     * 处方模板列表
     *
     * @param type 0 个人 1 医院
     * @return
     */
    @GET("user/prescription/prescriptionTemplateList")
    Observable<HttpResult<List<PrescriptionTempletBean>>> getPrescriptionTemplateList(@Query("type") String type);

    /**
     * 处方模板明细列表
     *
     * @param id
     * @return
     */
    @GET("user/prescription/prescriptionTemplateItemList")
    Observable<HttpResult<PrescriptionDrugDetailBean>> getPrescriptionTemplateItemList(@Query("id") long id);

    /**
     * 获取检验项目
     *
     * @param id
     * @return
     */
    @GET("user/online/checkoutList")
    Observable<HttpResult<List<CheckOutItemBean>>> checkoutList(@Query("hospitalId") long id);

    /**
     * 获取检查项目
     *
     * @param id
     * @return
     */
    @GET("user/online/inspectList")
    Observable<HttpResult<List<CheckOutItemBean>>> inspectList(@Query("hospitalId") long id);


    /**
     * 获取药品里诶包
     *
     * @param maps
     * @return
     */
    @GET("user/prescription/drugsList")
    Observable<HttpResult<List<DrugBean>>> drugsList(@QueryMap Map<String, Object> maps);

    /**
     * 获取所有用法配置
     *
     * @param maps
     * @return
     */
    @GET("nursing/eisai/getAllConsumableItems")
    Observable<HttpResult<List<MaterialBean>>> getAllConsumableItems(@QueryMap Map<String, Object> maps);


    /**
     * 医生开具处置意见
     *
     * @param maps
     * @return
     */
    @GET("user/online/doctorHandleSuggest")
    Observable<HttpResult<DiagnoseHandleBean>> doctorHandleSuggest(@QueryMap Map<String, Object> maps);

    /**
     * 获取门诊记录
     *
     * @param id
     * @return
     */
    @GET("user/online/getconsultRecord")
    Observable<HttpResult<DiagnosePatientInfoBean>> getconsultRecord(@Query("patientId") long id);


    /**
     * 删除检查／检验订单
     *
     * @param body
     * @return
     */
    @POST("user/online/deleteInquisition")
    Observable<HttpResult<String>> deleteInquisition(@Body RequestBody body);

    /**
     * 获取检查报告详情
     *
     * @param patientNumber
     * @return
     */
    @GET("user/inspectRecord/inspectResultInfo")
    Observable<HttpResult<BodyCheckDetailInfo>> getInspectResultInfo(@Query("applyNumber") String patientNumber);

    /**
     * 获取检验报告详情
     *
     * @param patientNumber
     * @return
     */
    @GET("user/checkoutRecord/checkoutResultInfo")
    Observable<HttpResult<InspectionDetailInof>> getCheckoutResultInfo(@Query("applyNumber") String patientNumber);

    /**
     * IDC－10搜索
     *
     * @param patientNumber
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GET("user/prescription/idcCateList")
    Observable<HttpResult<List<ICD10Bean>>> idcCateList(@Query("keyword") String patientNumber, @Query("currentPage") int currentPage, @Query("pageSize") int pageSize);


}
