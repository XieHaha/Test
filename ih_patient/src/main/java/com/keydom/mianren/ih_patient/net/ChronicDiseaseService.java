package com.keydom.mianren.ih_patient.net;

import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.mianren.ih_patient.bean.EatItemBean;
import com.keydom.mianren.ih_patient.bean.EatRecordBean;
import com.keydom.mianren.ih_patient.bean.HealthDataBean;
import com.keydom.mianren.ih_patient.bean.LifestyleRootBean;
import com.keydom.mianren.ih_patient.bean.SleepRecordBean;
import com.keydom.mianren.ih_patient.bean.SportsItemBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * @author 顿顿
 * @date 21/3/29 15:34
 * @des 健康管理
 */
public interface ChronicDiseaseService {
    /**
     * 获取健康值
     */
    @GET("medicine/chronicDiseaseManage/getHeathValue")
    Observable<HttpResult<HealthDataBean>> getHeathValue(@QueryMap Map<String, String> map);

    /**
     * 新增或者修改健康值
     */
    @POST("medicine/chronicDiseaseManage/insertOrUpdateHeathValue")
    Observable<HttpResult<String>> insertOrUpdateHeathValue(@Body RequestBody body);

    /**
     * 查询患者就餐记录
     */
    @GET("medicine/chronicDiseaseManage/foodRecordList")
    Observable<HttpResult<EatRecordBean>> foodRecordList(@QueryMap Map<String, String> map);

    /**
     * 查询患者睡眠记录
     */
    @GET("medicine/chronicDiseaseManage/sleepRecordList")
    Observable<HttpResult<List<SleepRecordBean>>> sleepRecordList(@QueryMap Map<String, String> map);

    /**
     * 获取食物库列表
     */
    @POST("api/healthChronicDiseaseManage/foodBankList")
    Observable<HttpResult<LifestyleRootBean<EatItemBean>>> foodBankList(@Body RequestBody body);

    /**
     * 获取运动库列表
     */
    @POST("api/healthChronicDiseaseManage/exerciseBankList")
    Observable<HttpResult<LifestyleRootBean<SportsItemBean>>> exerciseBankList(@Body RequestBody body);

    /**
     * 新增或者修改就餐记录
     */
    @POST("medicine/chronicDiseaseManage/insertOrUpdateFoodRecord")
    Observable<HttpResult<String>> insertOrUpdateFoodRecord(@Body RequestBody body);

    /**
     * 删除就餐记录
     */
    @DELETE("medicine/chronicDiseaseManage/deleteFoodRecord")
    Observable<HttpResult<String>> deleteFoodRecord(@Query("foodRecordId") String foodRecordId);

    /**
     * 新增或者修改睡眠记录
     */
    @POST("medicine/chronicDiseaseManage/insertOrUpdateSleepRecord")
    Observable<HttpResult<String>> insertOrUpdateSleepRecord(@Body RequestBody body);

    /**
     * 删除运动睡眠记录
     */
    @DELETE("medicine/chronicDiseaseManage/deleteExerciseAndSleepRecord")
    Observable<HttpResult<String>> deleteExerciseAndSleepRecord(@QueryMap Map<String, String> map);

    /**
     * 新增或者修改运动记录
     */
    @POST("medicine/chronicDiseaseManage/insertOrUpdateExerciseRecord")
    Observable<HttpResult<EatRecordBean>> insertOrUpdateExerciseRecord(@QueryMap Map<String,
            String> map);

    /**
     * 删除运动记录
     */
    @GET("medicine/chronicDiseaseManage/deleteExerciseRecord")
    Observable<HttpResult<EatRecordBean>> deleteExerciseRecord(@QueryMap Map<String, String> map);
}
