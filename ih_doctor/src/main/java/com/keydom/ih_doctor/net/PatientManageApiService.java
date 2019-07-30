package com.keydom.ih_doctor.net;

import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.ih_doctor.bean.GroupResBean;
import com.keydom.ih_doctor.bean.ImPatientInfo;
import com.keydom.ih_doctor.bean.PatientInfoBean;
import com.keydom.ih_doctor.bean.WarrantDetailBean;
import com.keydom.ih_doctor.bean.WarrantDoctorBean;

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
public interface PatientManageApiService {

    /**
     * 获取患者管理所有人
     *
     * @param maps
     * @return
     */
    @GET("Inquiry/groupChat/getRegList")
    Observable<HttpResult<List<ImPatientInfo>>> getRegList(@QueryMap Map<String, Object> maps);


    /**
     * 创建群聊
     *
     * @param
     * @return
     */
    @POST("Inquiry/groupChat/foundGroupChat")
    Observable<HttpResult<String>> foundGroupChat(@Body RequestBody body);

    /**
     * 修改群资料
     *
     * @param
     * @return
     */
    @POST("Inquiry/groupChat/updateGroupChat")
    Observable<HttpResult<String>> updateGroupChat(@Body RequestBody body);

    /**
     * 获取群资料
     *
     * @param
     * @return
     */
    @GET("Inquiry/groupChat/seeGroupChatInfo")
    Observable<HttpResult<GroupResBean>> seeGroupChatInfo(@QueryMap Map<String, Object> maps);


    /**
     * 授权
     *
     * @param
     * @return
     */
    @POST("Inquiry/registerUserAuthorization/addRegisterUserAuthorizationService")
    Observable<HttpResult<String>> addRegisterUserAuthorizationService(@Body RequestBody body);

    /**
     * 获取患者信息
     *
     * @param maps
     * @return
     */
    @GET("user/online/getconsultRecordByUserId")
    Observable<HttpResult<PatientInfoBean>> getRegUserInfo(@QueryMap Map<String, Object> maps);

    @POST("Inquiry/groupChat/updateRegUserInfo")
    Observable<HttpResult<Object>> updateRegUserInfo(@Body RequestBody body);

    /**
     * @param body
     * @return
     */
    @POST("nursing/nursingServiceReceiptRecord/saveNursingByNursingOrderIdAndNursingId")
    Observable<HttpResult<String>> saveNursingByNursingOrderIdAndNursingId(@Body RequestBody body);


    /**
     * 获取已授权医生列表
     *
     * @param creatorId
     * @return
     */
    @GET("Inquiry/registerUserAuthorization/authorizedList")
    Observable<HttpResult<List<WarrantDoctorBean>>> getAuthorizedList(@Query("creatorId") long creatorId );

    /**
     * 获取已授权医生授权详情
     *
     * @param creatorId
     * @param creatorId
     * @return
     */
    @GET("Inquiry/registerUserAuthorization/editInfo")
    Observable<HttpResult<WarrantDetailBean>> getEditInfo(@Query("creatorId") long creatorId, @Query("doctorId") String doctorId);

}
