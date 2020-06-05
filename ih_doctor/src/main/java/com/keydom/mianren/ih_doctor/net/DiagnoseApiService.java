package com.keydom.mianren.ih_doctor.net;

import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.mianren.ih_doctor.bean.BodyCheckDetailInfo;
import com.keydom.mianren.ih_doctor.bean.CheckItemListBean;
import com.keydom.ih_common.bean.CheckOutGroupBean;
import com.keydom.mianren.ih_doctor.bean.DiagnoseHandleBean;
import com.keydom.mianren.ih_doctor.bean.DiagnosePatientInfoBean;
import com.keydom.mianren.ih_doctor.bean.DrugBean;
import com.keydom.mianren.ih_doctor.bean.DrugEntity;
import com.keydom.mianren.ih_doctor.bean.ICD10Bean;
import com.keydom.mianren.ih_doctor.bean.InquiryBean;
import com.keydom.mianren.ih_doctor.bean.InspectionDetailInof;
import com.keydom.mianren.ih_doctor.bean.MaterialBean;
import com.keydom.mianren.ih_doctor.bean.MedicalRecordTempletBean;
import com.keydom.mianren.ih_doctor.bean.OrderApplyResponse;
import com.keydom.mianren.ih_doctor.bean.PrescriptionDrugDetailBean;
import com.keydom.mianren.ih_doctor.bean.PrescriptionTempletBean;

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
     */
    @GET("user/online/listInquisition")
    Observable<HttpResult<PageBean<InquiryBean>>> listInquisition(@QueryMap Map<String, Object> maps);


    /**
     * 添加服务项目
     */
    @POST("nursing/nursingServiceSubOrder/nurseAddProjectToSubOrder")
    Observable<HttpResult<String>> nurseAddProjectToSubOrder(@Body RequestBody body);


    /**
     * 提交检验、检查申请单
     */
    @POST("user/insCheckApplication/saveInsOrCheck")
    Observable<HttpResult<List<OrderApplyResponse>>> saveCheckout(@Body RequestBody body);


    /**
     * 获取检验、检查单详情
     */
    @GET("user/insCheckApplication/getInsCheckOrder")
    Observable<HttpResult<CheckItemListBean>> getCheckoutDetail(@Query("orderId") long id,
                                                                @Query("type") int type);

    /**
     * 取消检验、检查单订单
     */
    @GET("user/insCheckApplication/canOrder")
    Observable<HttpResult<String>> cancelCheckout(@Query("applicationId") String id);

    /**
     * 检验、检查单订单状态
     */
    @GET("user/insCheckApplication/getApplicationById")
    Observable<HttpResult<String>> getCheckOutOrderStatus(@Query("applicationId") String id, @Query(
            "updateTime") String updateTime);


    /**
     * 病历模板问诊订单列表
     */
    @GET("user/prescription/medicalTemplateList")
    Observable<HttpResult<PageBean<MedicalRecordTempletBean>>> listMedicalTemplate(@QueryMap Map<String, Object> maps);

    /**
     * 病历模板明细
     */
    @GET("user/prescription/medicalTemplateInfo")
    Observable<HttpResult<MedicalRecordTempletBean>> detailMedicalTemplate(@Query("id") long id);

    /**
     * 处方模板列表
     *
     * @param maps 0 个人 1 医院
     */
    @GET("user/prescription/prescriptionTemplateList")
    Observable<HttpResult<List<PrescriptionTempletBean>>> getPrescriptionTemplateList(@QueryMap Map<String, Object> maps);

    /**
     * 处方模板明细列表
     */
    @GET("user/prescription/prescriptionTemplateItemList")
    Observable<HttpResult<PrescriptionDrugDetailBean>> getPrescriptionTemplateItemList(@Query("id"
    ) long id);

    /**
     * 获取检验检查目录（一级）  1 检验 2检查 3 治疗
     */
    @GET("reservation/insCheckCategory/getInsCheckCategory")
    Observable<HttpResult<List<CheckOutGroupBean>>> checkoutCategoryList(@Query("type") int id);

    /**
     * 获取检验检查项目（二级）  1 检验 2检查 3 治疗
     */
    @GET("reservation/insCheckCategory/getInsCheckItem")
    Observable<HttpResult<List<CheckOutGroupBean>>> checkoutItemList(@Query("type") int id,
                                                                     @Query("cateCode") String code);

    /**
     * 获取检查项目
     */
    @GET("user/online/inspectList")
    Observable<HttpResult<List<CheckOutGroupBean>>> inspectList(@Query("hospitalId") long id);


    /**
     * 外延处方获取药品
     */
    @POST(Const.OUTER + "/drugsSpec/findByParam")
    Observable<HttpResult<DrugEntity>> WaiYanDrugsList(@Body RequestBody body);

    /**
     * 获取药品里诶包
     */
    @GET("user/prescription/drugsList")
    Observable<HttpResult<List<DrugBean>>> drugsList(@QueryMap Map<String, Object> maps);

    /**
     * 获取所有用法配置
     */
    @GET("nursing/eisai/getAllConsumableItems")
    Observable<HttpResult<List<MaterialBean>>> getAllConsumableItems(@QueryMap Map<String,
            Object> maps);


    /**
     * 医生开具处置意见
     */
    @GET("user/online/doctorHandleSuggest")
    Observable<HttpResult<DiagnoseHandleBean>> doctorHandleSuggest(@QueryMap Map<String, Object> maps);

    /**
     * 获取门诊记录
     */
    @GET("user/online/getconsultRecord")
    Observable<HttpResult<DiagnosePatientInfoBean>> getconsultRecord(@Query("patientId") long id);


    /**
     * 删除检查／检验订单
     */
    @POST("user/online/deleteInquisition")
    Observable<HttpResult<String>> deleteInquisition(@Body RequestBody body);

    /**
     * 获取检查报告详情
     */
    @GET("user/inspectRecord/inspectResultInfo")
    Observable<HttpResult<BodyCheckDetailInfo>> getInspectResultInfo(@Query("applyNumber") String patientNumber);

    /**
     * 获取检验报告详情
     */
    @GET("user/checkoutRecord/checkoutResultInfo")
    Observable<HttpResult<InspectionDetailInof>> getCheckoutResultInfo(@Query("applyNumber") String patientNumber);

    /**
     * IDC－10搜索
     */
    @GET("user/prescription/idcCateList")
    Observable<HttpResult<List<ICD10Bean>>> idcCateList(@Query("keyword") String patientNumber,
                                                        @Query("currentPage") int currentPage,
                                                        @Query("pageSize") int pageSize);


}
