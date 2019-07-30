package com.keydom.ih_patient.net;

import com.alibaba.fastjson.JSONObject;
import com.keydom.ih_common.bean.SearchResultBean;
import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.ih_patient.bean.BannerBean;
import com.keydom.ih_patient.bean.BodyCheckDetailInfo;
import com.keydom.ih_patient.bean.BodyCheckRecordInfo;
import com.keydom.ih_patient.bean.ChildOrderBean;
import com.keydom.ih_patient.bean.CommonDocumentBean;
import com.keydom.ih_patient.bean.DiagnosesOrderBean;
import com.keydom.ih_patient.bean.DoctorEvaluateItem;
import com.keydom.ih_patient.bean.DoctorMainBean;
import com.keydom.ih_patient.bean.DoctorOrNurseBean;
import com.keydom.ih_patient.bean.CityBean;
import com.keydom.ih_patient.bean.DiagnosesAndNurDepart;
import com.keydom.ih_patient.bean.HistoryListBean;
import com.keydom.ih_patient.bean.HospitalAreaInfo;
import com.keydom.ih_patient.bean.InspectionDetailInof;
import com.keydom.ih_patient.bean.InspectionRecordInfo;
import com.keydom.ih_patient.bean.ManagerUserBean;
import com.keydom.ih_patient.bean.MedicalRecordBean;
import com.keydom.ih_patient.bean.PackageData;
import com.keydom.ih_patient.bean.PayOrderBean;
import com.keydom.ih_patient.bean.RecommendDocAndNurBean;
import com.keydom.ih_patient.bean.UserInfo;

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
     * 上传信息接口
     */
    @GET("user/patient/updateInfo")
    Observable<HttpResult<Object>> upLoadRegion(@Query("id") String id,@Query("provinceCode") String provinceCode,@Query("cityCode") String cityCode,@Query("countyCode") String countryCode);

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
     * 获取检验报告详情
     */
    @GET("user/checkoutRecord/checkoutResultInfo")
    Observable<HttpResult<InspectionDetailInof>> getCheckoutResultInfo(@Query("applyNumber") String patientNumber);

    /**
     * 获取检验报告列表
     */
    @GET("user/checkoutRecord/getCheckoutRecordPage")
    Observable<HttpResult<List<InspectionRecordInfo>>> getCheckoutRecordPage(@Query("partientNumber") String patientNumber);

    /**
     * 获取检查报告列表
     */
    @GET("user/inspectRecord/getInspectRecordPage")
    Observable<HttpResult<List<BodyCheckRecordInfo>>> getInspectRecordPage(@Query("partientNumber") String patientNumber);

    /**
     * 获取检验报告详情
     */
    @GET("user/inspectRecord/inspectResultInfo")
    Observable<HttpResult<BodyCheckDetailInfo>> getInspectResultInfo(@Query("applyNumber") String patientNumber);

    /**
     * //获取既往病史
     */
    @GET("user/patient/getMedicalHistory")
    Observable<HttpResult<HistoryListBean>> getHistoryList();

    /**
     *     //查询就诊人列表
     */
    @GET("user/patient/list")
    Observable<HttpResult<List<ManagerUserBean>>> getManagerUserList(@Query("registerUserId") long registerUserId);

    /**
     *     //保存就诊人
     */
    @POST("user/patient/save")
    Observable<HttpResult<Object>> addManagerUser(@Body RequestBody body);

    /**
     *     //删除就诊人
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
    Observable<HttpResult<List<MedicalRecordBean>>> getIndCountryAlLList(@QueryMap Map<String,Object> map);

    /**
     * 获取病历详情
     */
    @GET("user/online/getMedicalRecordInfo")
    Observable<HttpResult<MedicalRecordBean>> getMedicalRecordInfo(@Query("medicalId") long medicalId);

    /**
     *     //获取医生/护士列表
     */
    @GET("user/registerUserDoctor/getMyDoctor")
    Observable<HttpResult<List<DoctorOrNurseBean>>> getMyFollowList(@QueryMap Map<String,Object> map);

    /**
     *     //获取医生/护士主页  doctorCode  type
     */
    @GET("user/online/getDoctorHome")
    Observable<HttpResult<DoctorMainBean>> getMyFollowDoctorDetail(@QueryMap Map<String,Object> map);

    /**
     *     //获取医生/护士评价  doctorCode  type
     */
    @GET("user/online/listDoctorComment")
    Observable<HttpResult<List<DoctorEvaluateItem>>> getDoctorEvaluates(@QueryMap Map<String,Object> map);

    /**
     *     //点赞/取消点赞
     */
    @POST("user/online/doCommentLike")
    Observable<HttpResult<Object>> doCommentLike(@Body RequestBody body);

    /**
     *     //关注/取消关注
     */
    @GET("user/appuser/attentionDoctor")
    Observable<HttpResult<Object>> attentionDoctor(@QueryMap Map<String,Object> map);

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
    Observable<HttpResult<List<HospitalAreaInfo>>> getHospitalList(@Query("cityCode") String cityCode,@Query("userId") long userId);

    /**
     * 问诊订单列表查询
     */
    @GET("user/online/listPatientInquisition")
    Observable<HttpResult<List<DiagnosesOrderBean>>> getlistPatientInquisition(@QueryMap Map<String,Object> map);

    /**
     * 获取医院下的所有科室
     */
    @GET("user/hospitalDept/listCateDeptByHospital")
    Observable<HttpResult<List<DiagnosesAndNurDepart>>> getListCateDeptByHospital(@QueryMap Map<String,Object> map);

    /**
     * 第三方登录
     */
    @POST("user/appuser/loginTrilateral")
    Observable<HttpResult<UserInfo>> loginTrilateral(@Body RequestBody body);

    /**
     * 第三方登录注册发送验证码
     */
    @POST("user/appuser/sendCodeTrilateral")
    Observable<HttpResult<Object>> sendCodeTrilateral(@QueryMap Map<String,Object> map);

    /**
     *     //dialog_check_icon
     */
    @POST("user/hospitalUserCenter/feedBack")
    Observable<HttpResult<String>> feedBack(@Body RequestBody body);

    /**
     *     //创建图文或者视频问诊单
     */
    @POST("user/online/saveInquisition")
    Observable<HttpResult<PayOrderBean>> saveInquisition(@Body RequestBody body);


    /**
     *     //问诊订单支付
     */
    @POST("user/online/inquiryPay")
    Observable<HttpResult<String>> inquiryPay(@Body RequestBody body);

    /**
     *     //问诊订单评论
     */
    @POST("user/online/doComment")
    Observable<HttpResult<Object>> doComment(@Body RequestBody body);


    /**
     *     //问诊订单退诊
     */
    @POST("user/online/returnedInquisition")
    Observable<HttpResult<Object>> returnedInquisition(@Body RequestBody body);

    /**
     *     //获取顶部Banner图
     */
    @GET("user/patient/getPicture")
    Observable<HttpResult<List<BannerBean>>> getPicture(@QueryMap Map<String,Object> map);

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
     *     //问诊咨询搜索医生或者护士
     */
    @GET("user/hospitalDept/listHomeRecommendDoctor")
    Observable<HttpResult<List<RecommendDocAndNurBean>>> getListHomeRecommendDoctor(@QueryMap Map<String,Object> map);

    /**
     *     //获取手势密码
     */
    @GET("user/gesturePassword/getPassword")
    Observable<HttpResult<String>> getPassword(@Query("account") long inquiryId);

    /**
     *     //设置手势密码
     */
    @POST("user/gesturePassword/setPassword")
    Observable<HttpResult<Object>> setPassword(@Body RequestBody body);

    /**
     * 获取文书维护内容
     */
    @GET("api/officialDispatch/getOfficialDispatchAllMsgByCode")
    Observable<HttpResult<CommonDocumentBean>> getOfficialDispatchAllMsgByCode(@QueryMap Map<String,Object> map);

    /**
     * 全局搜索
     *
     * @param maps
     * @return
     */
    @GET("user/search")
    Observable<HttpResult<SearchResultBean>> search(@QueryMap Map<String, Object> maps);


    @GET("user/pageSearch")
    Observable<HttpResult<List<JSONObject>>> pageSearch(@QueryMap Map<String, Object> maps);

}
