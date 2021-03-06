package com.keydom.mianren.ih_doctor.net;

import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.mianren.ih_doctor.bean.ChangeDiagnoseRecoderBean;
import com.keydom.mianren.ih_doctor.bean.DeptDoctorBean;
import com.keydom.mianren.ih_doctor.bean.DiagnoseCaseBean;
import com.keydom.mianren.ih_doctor.bean.DiagnoseFillOutResBean;
import com.keydom.mianren.ih_doctor.bean.DiagnoseOrderDetailBean;
import com.keydom.mianren.ih_doctor.bean.DiagnoseRecoderBean;
import com.keydom.mianren.ih_doctor.bean.GroupDoctorBean;
import com.keydom.mianren.ih_doctor.bean.GroupInfoBean;
import com.keydom.mianren.ih_doctor.bean.GroupInfoRes;
import com.keydom.mianren.ih_doctor.bean.InquiryBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * @Name：com.keydom.ih_doctor.net
 * @Description：医生协作
 * @Author：song
 * @Date：18/11/15 下午7:09
 * 修改人：xusong
 * 修改时间：18/11/15 下午7:09
 */
public interface GroupCooperateApiService {

    /**
     * 新建医生团队
     */
    @POST("Inquiry/ihGroup/add")
    Observable<HttpResult<GroupInfoBean>> ihGroupAdd(@Body RequestBody body);

    /**
     * 新增团队成员
     */
    @POST("Inquiry/ihGroup/addTeamMembers")
    Observable<HttpResult<String>> ihGroupAddTeamMembers(@Body RequestBody body);

    /**
     * 修改团队信息
     */
    @POST("Inquiry/ihGroup/edit")
    Observable<HttpResult<String>> ihGroupEdit(@Body RequestBody body);


    /**
     * 查询用户医生团队
     */
    @GET("Inquiry/ihGroup/getUserIhGroup")
    Observable<HttpResult<GroupInfoRes>> ihGroupGetUserIhGroup(@QueryMap Map<String, Object> maps);

    /**
     * 查询医生用户科室下所有医生
     */
    @GET("Inquiry/ihGroup/queryDoctorDepAllUser")
    Observable<HttpResult<List<GroupDoctorBean>>> ihGroupQueryDoctorDepAllUser(@QueryMap Map<String, Object> maps);


    /**
     * 查询用户医生团队下所有医生
     */
    @GET("user/hospitalDept/queryDoctorTeamAllUser")
    Observable<HttpResult<List<DeptDoctorBean>>> ihGroupQueryDoctorTeamAllUser(@QueryMap Map<String, Object> maps);

    /**
     * 查询用户医生团队下所有医生
     */
    @GET("user/hospitalDept/queryDoctorTeamAllUserAuthorization")
    Observable<HttpResult<List<DeptDoctorBean>>> queryDoctorTeamAllUserAuthorization(@QueryMap Map<String, Object> maps);


    //*****************************************

    /**
     * 转诊操作
     */
    @POST("user/referral/operate")
    Observable<HttpResult<String>> operate(@Body RequestBody body);


    /**
     * 提交转诊申请
     */
    @POST("user/referral/save")
    Observable<HttpResult<DiagnoseFillOutResBean>> save(@Body RequestBody body);

    /**
     * 获取问诊记录
     */
    @GET("user/inquisitionRecord/getInquisitionRecordByDocId")
    Observable<HttpResult<List<DiagnoseRecoderBean>>> getInquisitionRecordByDocId(@QueryMap Map<String, Object> maps);


    /**
     * 获取病历详情
     */
    @GET("user/online/getMedicalRecordInfo")
    Observable<HttpResult<DiagnoseCaseBean>> getMedicalRecordInfo(@QueryMap Map<String, Object> maps);


    /**
     * 获取转诊详情
     */
    @GET("user/referral/getDetail")
    Observable<HttpResult<DiagnoseOrderDetailBean>> getDetail(@QueryMap Map<String, Object> maps);


    /**
     * 获取问诊病人信息
     */
    @GET("user/referral/getInquisitionPatientByNumber")
    Observable<HttpResult<String>> getInquisitionPatientByNumber(@QueryMap Map<String, Object> maps);

    /**
     * 获取转诊记录
     */
    @GET("user/referral/list")
    Observable<HttpResult<List<ChangeDiagnoseRecoderBean>>> list(@QueryMap Map<String, Object> maps);

    /**
     * 获取医生问诊单
     */
    @GET("user/referral/listDoctorInquisition")
    Observable<HttpResult<List<InquiryBean>>> listDoctorInquisition(@QueryMap Map<String, Object> maps);


    /**
     * 获取科室下面所有医生
     */
    @GET("user/hospitalDept/listDoctor")
    Observable<HttpResult<PageBean<DeptDoctorBean>>> listDoctor(@QueryMap Map<String, Object> maps);


}
