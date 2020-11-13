package com.keydom.mianren.ih_patient.net;

import com.keydom.ih_common.bean.DoctorInfo;
import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.mianren.ih_patient.bean.DepartmentInfo;
import com.keydom.mianren.ih_patient.bean.HospitalAppointmentBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author 顿顿
 * @date 20/9/2 16:25
 * @des
 */
public interface HospitalAppointmentService {

    /**
     * 科室
     */
    @GET("user/hospitalDept/getInHospitalDeptList")
    Observable<HttpResult<List<DepartmentInfo>>> getInHospitalDeptList();

    /**
     * 手术医生、麻醉师
     */
    @GET("user/hospitalDept/getSurgeryDoctor")
    Observable<HttpResult<List<DoctorInfo>>> getDeptDoctor(@Query("deptId") String deptId,
                                                           @Query("state") int state);

    /**
     * 住院预约提交
     */
    @POST("medicine/obstetric/submit")
    Observable<HttpResult<String>> submit(@Body RequestBody body);

    /**
     * 获取住院预约列表
     */
    @GET("medicine/obstetric/getObsByCardNo")
    Observable<HttpResult<List<HospitalAppointmentBean>>> getObsByCardNo(@Query("eleCardNo") String eleCardNo);
}
