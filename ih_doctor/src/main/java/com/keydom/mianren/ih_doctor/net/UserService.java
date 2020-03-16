package com.keydom.mianren.ih_doctor.net;

import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.mianren.ih_doctor.bean.AgreementBean;
import com.keydom.mianren.ih_doctor.bean.DoctorEvaluateItem;
import com.keydom.mianren.ih_doctor.bean.DoctorMainBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * 用户操作接口
 */
public interface UserService {
    /**
     * //获取医生/护士主页  doctorCode  type
     */
    @GET("user/online/getDoctorHome")
    Observable<HttpResult<DoctorMainBean>> getMyFollowDoctorDetail(@QueryMap Map<String, Object> map);

    /**
     * //获取医生/护士评价  doctorCode  type
     */
    @GET("user/online/listDoctorComment")
    Observable<HttpResult<List<DoctorEvaluateItem>>> getDoctorEvaluates(@QueryMap Map<String, Object> map);

    /**
     * //点赞/取消点赞
     */
    @POST("user/online/doCommentLike")
    Observable<HttpResult<Object>> doCommentLike(@Body RequestBody body);

    /**
     * //关注/取消关注
     */
    @GET("user/hospitalUserCenter/attentionDoctor")
    Observable<HttpResult<Object>> attentionDoctor(@QueryMap Map<String, Object> map);

    @GET("user/appuser/logout")
    Observable<HttpResult<Object>> logout();

    /*
    * 获取用户协议
    * */
    @GET("api/officialDispatch/getOfficialDispatchAllMsgByCode")
    Observable<HttpResult<AgreementBean>> getOfficialDispatchAllMsgByCode(@QueryMap Map<String, Object> map);
}
