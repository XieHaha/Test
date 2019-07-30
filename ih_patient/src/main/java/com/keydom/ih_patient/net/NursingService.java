package com.keydom.ih_patient.net;

import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.ih_patient.bean.BaseNurseFeeBean;
import com.keydom.ih_patient.bean.ChooseNursingBean;
import com.keydom.ih_patient.bean.HospitalLocationInfo;
import com.keydom.ih_patient.bean.NursingIndexInfo;
import com.keydom.ih_patient.bean.NursingProjectInfo;
import com.keydom.ih_patient.bean.NursingServiceOrderInfo;

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
 * 护理服务接口
 */
public interface NursingService {
    /**
     * 获取护理服务首页
     */
    @GET("nursing/nursingServiceOrder/getPatientHomePageByUserId")
    Observable<HttpResult<NursingIndexInfo>> getPatientHomePageByUserId(@QueryMap Map<String,Object> bodyMap);

    /**
     * 获取基础护理或者专科护理或产后护理
     */
    @GET("nursing/nurseServiceProject/getNurseServiceProjectByCateId")
    Observable<HttpResult<List<NursingProjectInfo>>> getNurseServiceProjectByCateId(@QueryMap Map<String,Object> bodyMap);

    /**
     * 获取服务区域内医院坐标
     */
    @GET("nursing/hospital/getHospital")
    Observable<HttpResult<List<HospitalLocationInfo>>> getHospital(@QueryMap Map<String,Object> bodyMap);

    /**
     *  获取护理项目详情
     */
    @GET("nursing/nurseServiceProject/getNurseServiceProjectDetailById")
    Observable<HttpResult<NursingProjectInfo>> getNurseServiceProjectDetailById(@Query("id") String nursingProjectId);

    @POST("nursing/nursingServiceOrder/patientCreateReservationOrder")
    Observable<HttpResult<NursingServiceOrderInfo>> patientCreateReservationOrder(@Body RequestBody body);

    /**
     * 人脸识别
     */
    @POST("nursing/registerUser/personVerify")
    Observable<HttpResult<Boolean>> personVerify(@Body RequestBody body);
    @POST("nursing/nursingServiceOrder/patientPayByOrderNumber")
    Observable<HttpResult<String>> patientPayByOrderNumber(@Body RequestBody body);

    /**
     * 查询基础服务费
     */
    @GET("nursing/nursingServiceBaseFee/getBaseFee")
    Observable<HttpResult<BaseNurseFeeBean>> getBaseFee(@Query("hospitalId") long hospitalId);

    /**
     * 修改护理订单
     */
    @POST("nursing/nursingServiceOrder/patientRewriteOrderAndSubmit")
    Observable<HttpResult<Object>> patientRewriteOrderAndSubmit(@Body RequestBody body);

    /**
     * 修改护理订单
     */
    @POST("nursing/nurseServiceCate/getAllNurseServiceCategoryByHospitalId")
    Observable<HttpResult<List<ChooseNursingBean>>> getAllNurseServiceCategoryByHospitalId(@Query("hospitalId") long hospitalId);
}
