package com.keydom.mianren.ih_patient.net;

import com.alibaba.fastjson.JSONObject;
import com.keydom.ih_common.bean.DoctorInfo;
import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.bean.SearchResultBean;
import com.keydom.ih_common.bean.UpdateVersionBean;
import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.mianren.ih_patient.bean.BannerBean;
import com.keydom.mianren.ih_patient.bean.BodyCheckDetailInfo;
import com.keydom.mianren.ih_patient.bean.ChildOrderBean;
import com.keydom.mianren.ih_patient.bean.CityBean;
import com.keydom.mianren.ih_patient.bean.CommonDocumentBean;
import com.keydom.mianren.ih_patient.bean.DiagnoseIndexBean;
import com.keydom.mianren.ih_patient.bean.DiagnosesAndNurDepart;
import com.keydom.mianren.ih_patient.bean.DiagnosesOrderBean;
import com.keydom.mianren.ih_patient.bean.DoctorEvaluateItem;
import com.keydom.mianren.ih_patient.bean.DoctorMainBean;
import com.keydom.mianren.ih_patient.bean.DoctorOrNurseBean;
import com.keydom.mianren.ih_patient.bean.HistoryListBean;
import com.keydom.mianren.ih_patient.bean.HospitalAreaInfo;
import com.keydom.mianren.ih_patient.bean.IdCardInfo;
import com.keydom.mianren.ih_patient.bean.InspectionDetailBean;
import com.keydom.mianren.ih_patient.bean.InspectionRecordBean;
import com.keydom.mianren.ih_patient.bean.ManagerUserBean;
import com.keydom.mianren.ih_patient.bean.MedicalRecordBean;
import com.keydom.mianren.ih_patient.bean.PackageData;
import com.keydom.mianren.ih_patient.bean.PayOrderBean;
import com.keydom.mianren.ih_patient.bean.RecommendDocAndNurBean;
import com.keydom.mianren.ih_patient.bean.UserInfo;

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
 * 用户操作接口
 */
public interface UserService {
    /**
     * 获取版本信息
     */
    @POST("user/versionControl/getVersion")
    Observable<HttpResult<UpdateVersionBean>> getVersion(@Body RequestBody body);
    /**
     * 上传信息接口
     */
    @GET("user/patient/updateInfo")
    Observable<HttpResult<Object>> upLoadRegion(@Query("id") String id,
                                                @Query("provinceCode") String provinceCode,
                                                @Query("cityCode") String cityCode, @Query(
            "countyCode") String countryCode);

    /**
     * 更新数据接口
     */
    @POST("user/patient/updateInfoa")
    Observable<HttpResult<Object>> upLoadInfo(@Body RequestBody body);

    /**
     * 实名认证接口
     */
    @POST("user/authentication/patientIdCardCheck")
    Observable<HttpResult<Object>> realNameCertificate(@Body RequestBody body);

    /**
     * 初始化数据
     */
    @GET("user/patient/personalInformation")
    Observable<HttpResult<UserInfo>> initUserData(@Query("id") String id);

    /**
     * 获取检验、检查报告详情
     */
    @GET("reservation/insCheckCategory/getInsCheckResultDetail")
    Observable<HttpResult<InspectionDetailBean>> getCheckoutResultInfo(@Query("reportID") String reportID, @Query("reportType") int reportType);

    /**
     * 获取检验、检查报告列表
     */
    @POST("reservation/insCheckCategory/getInsCheckResult")
    Observable<HttpResult<List<InspectionRecordBean>>> getCheckoutRecordPage(@Body RequestBody map);

    /**
     * 获取检验报告详情
     */
    @GET("user/inspectRecord/inspectResultInfo")
    Observable<HttpResult<BodyCheckDetailInfo>> getInspectResultInfo(@Query("applyNumber") String patientNumber);

    /**
     * 获取既往病史
     */
    @GET("user/patient/getMedicalHistory")
    Observable<HttpResult<HistoryListBean>> getHistoryList();

    /**
     * 查询就诊人列表
     */
    @GET("user/patient/list")
    Observable<HttpResult<List<ManagerUserBean>>> getManagerUserList(@Query("registerUserId") long registerUserId);

    /**
     * 保存就诊人
     */
    @POST("user/patient/save")
    Observable<HttpResult<Object>> addManagerUser(@Body RequestBody body);

    /**
     * 保存就诊人
     */
    @POST("user/electronicCard/addPatient")
    Observable<HttpResult<Object>> addElectronicPatient(@Body RequestBody body);

    /**
     * 删除就诊人
     */
    @POST("user/patient/delete")
    Observable<HttpResult<Object>> deleteManagerUser(@Body RequestBody body);

    /**
     * 获取地区列表
     */
    @GET("user/patient/listProvinceList")
    Observable<HttpResult<List<PackageData.ProvinceBean>>> getRegionList();

    /**
     * 获取国籍列表
     */
    @GET("user/patient/indCountryAll")
    Observable<HttpResult<List<PackageData.CountryBean>>> getCountryList();

    /**
     * 获取名族列表
     */
    @GET("user/patient/findNationDictAll")
    Observable<HttpResult<List<PackageData.NationBean>>> getNationList(@Query("pcode") String pcCode);

    /**
     * 获取病历记录列表
     */
    @GET("user/online/listMedicalRecord")
    Observable<HttpResult<PageBean<MedicalRecordBean>>> getIndCountryAlLList(@QueryMap Map<String
            , Object> map);

    /**
     * 获取门诊病历
     */
    @POST("user/medicalHistory/medicalRecord")
    Observable<HttpResult<List<MedicalRecordBean>>> getMedicalRecord(@Body RequestBody body);

    /**
     * 获取医生/护士列表
     */
    @GET("user/registerUserDoctor/getMyDoctor")
    Observable<HttpResult<PageBean<DoctorOrNurseBean>>> getMyFollowList(@QueryMap Map<String,
            Object> map);

    /**
     * 获取医生/护士主页  doctorCode  type
     */
    @GET("user/online/getDoctorHome")
    Observable<HttpResult<DoctorMainBean>> getMyFollowDoctorDetail(@QueryMap Map<String, Object> map);

    @GET("user/online/getUserIsPlaceOrder")
    Observable<HttpResult<Integer>> getUserIsPlaceOrder(@QueryMap Map<String, Object> map);

    /**
     * 获取医生/护士评价  doctorCode  type
     */
    @GET("user/online/listDoctorComment")
    Observable<HttpResult<List<DoctorEvaluateItem>>> getDoctorEvaluates(@QueryMap Map<String,
            Object> map);

    /**
     * 点赞/取消点赞
     */
    @POST("user/online/doCommentLike")
    Observable<HttpResult<Object>> doCommentLike(@Body RequestBody body);

    /**
     * 关注/取消关注
     */
    @GET("user/appuser/attentionDoctor")
    Observable<HttpResult<Object>> attentionDoctor(@QueryMap Map<String, Object> map);

    /**
     * 通过关键字查询城市列表
     */
    @GET("user/city/findOrderByPinyin")
    Observable<HttpResult<CityBean>> findOrderByPinyin(@Query("keyword") String keyword);

    /**
     * 绑定定位城市与服务器城市数据
     */
    @GET("user/city/findByKeyword")
    Observable<HttpResult<List<CityBean.itemBean>>> findByKeyword(@Query("keyword") String keyword);

    /**
     * 通过城市获取医院列表
     */
    @GET("user/hospitalDept/listAllHospital")
    Observable<HttpResult<List<HospitalAreaInfo>>> getHospitalList(@Query("cityCode") String cityCode, @Query("userId") long userId);

    /**
     * 问诊订单列表查询
     */
    @GET("user/online/listPatientInquisition")
    Observable<HttpResult<PageBean<DiagnosesOrderBean>>> getlistPatientInquisition(@QueryMap Map<String, Object> map);

    /**
     * 获取医院下的所有科室
     */
    @GET("user/hospitalDept/listCateDeptByHospital")
    Observable<HttpResult<List<DiagnosesAndNurDepart>>> getListCateDeptByHospital(@QueryMap Map<String, Object> map);

    /**
     * 第三方登录
     */
    @POST("user/appuser/loginTrilateral")
    Observable<HttpResult<UserInfo>> loginTrilateral(@Body RequestBody body);

    /**
     * 第三方登录注册发送验证码
     */
    @POST("user/appuser/sendCodeTrilateral")
    Observable<HttpResult<Object>> sendCodeTrilateral(@QueryMap Map<String, Object> map);

    /**
     * dialog_check_icon
     */
    @POST("user/hospitalUserCenter/feedBack")
    Observable<HttpResult<String>> feedBack(@Body RequestBody body);

    /**
     * 创建图文或者视频问诊单
     */
    @POST("user/online/saveInquisition")
    Observable<HttpResult<PayOrderBean>> saveInquisition(@Body RequestBody body);


    /**
     * 问诊订单支付
     */
    @POST("user/online/inquiryPay")
    Observable<HttpResult<String>> inquiryPay(@Body RequestBody body);

    /**
     * 问诊订单评论
     */
    @POST("user/online/doComment")
    Observable<HttpResult<Object>> doComment(@Body RequestBody body);


    /**
     * 问诊订单退诊
     */
    @POST("user/online/returnedInquisition")
    Observable<HttpResult<Object>> returnedInquisition(@Body RequestBody body);

    /**
     * 获取顶部Banner图
     */
    @GET("user/patient/getPicture")
    Observable<HttpResult<List<BannerBean>>> getPicture(@QueryMap Map<String, Object> map);

    /**
     * 获取登录验证图片码
     */
    @GET("user/appuser/sendCodeThreeTime")
    Observable<HttpResult<String>> sendCodeThreeTime(@Query("accountMobile") String accountMobile);

    /**
     * 获取子订单号群和子订单类型是否包含处方
     */
    @GET("user/online/getUnPaySubOrderInfo")
    Observable<HttpResult<ChildOrderBean>> getUnPaySubOrderInfo(@Query("inquiryId") long inquiryId);

    /**
     * 问诊咨询搜索医生或者护士
     */
    @GET("user/hospitalDept/listHomeRecommendDoctor")
    Observable<HttpResult<PageBean<RecommendDocAndNurBean>>> getListHomeRecommendDoctor(@QueryMap Map<String, Object> map);

    /**
     * 在线问诊首页(普通用户)
     */
    @GET("user/online/home")
    Observable<HttpResult<DiagnoseIndexBean>> getHomeData(@QueryMap Map<String, Object> body);

    /**
     * 获取手势密码
     */
    @GET("user/gesturePassword/getPassword")
    Observable<HttpResult<String>> getPassword(@Query("account") long inquiryId);

    /**
     * 设置手势密码
     */
    @POST("user/gesturePassword/setPassword")
    Observable<HttpResult<Object>> setPassword(@Body RequestBody body);

    /**
     * 获取文书维护内容
     */
    @GET("api/officialDispatch/getOfficialDispatchAllMsgByCode")
    Observable<HttpResult<CommonDocumentBean>> getOfficialDispatchAllMsgByCode(@QueryMap Map<String, Object> map);

    /**
     * 全局搜索
     *
     * @return
     */
    @GET("user/search")
    Observable<HttpResult<SearchResultBean>> search(@QueryMap Map<String, Object> maps);


    @GET("user/pageSearch")
    Observable<HttpResult<List<JSONObject>>> pageSearch(@QueryMap Map<String, Object> maps);

    @GET("user/appuser/logout")
    Observable<HttpResult<Object>> logout();


    /**
     * 实名认证后台接口
     */
    @POST("user/authentication/patientValidateByIdCard")
    Observable<HttpResult<Object>> patientValidateByIdCard(@Body RequestBody body);

    /**
     * 获取已实名的信息
     */
    @GET("user/applyElectronicCard/queryUserInformation")
    Observable<HttpResult<IdCardInfo>> queryUserInformation(@QueryMap Map<String, Object> maps);

    /**
     * 获取是否已经办过卡
     */
    @GET("user/applyElectronicCard/isApplyElectronicCard")
    Observable<HttpResult<String>> isApplyElectronicCard(@QueryMap Map<String, Object> maps);

    /**
     * 获取指定接待医生或护士 接待预付费用户
     */
    @GET("user/hospitalDept/getReceptionDoctor")
    Observable<HttpResult<List<DoctorInfo>>> getReceptionDoctor();
}
