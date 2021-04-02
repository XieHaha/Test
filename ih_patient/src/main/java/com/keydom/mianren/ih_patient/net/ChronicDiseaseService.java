package com.keydom.mianren.ih_patient.net;

import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.mianren.ih_patient.bean.EatRecordBean;
import com.keydom.mianren.ih_patient.bean.HealthDataBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
     * 新增或者修改就餐记录
     */
    @POST("medicine/chronicDiseaseManage/insertOrUpdateFoodRecord")
    Observable<HttpResult<EatRecordBean>> insertOrUpdateFoodRecord(@Body RequestBody body);

    /**
     * 删除就餐记录
     */
    @GET("medicine/chronicDiseaseManage/deleteFoodRecord")
    Observable<HttpResult<EatRecordBean>> deleteFoodRecord(@QueryMap Map<String, String> map);

    /**
     * 新增或者修改睡眠记录
     */
    @POST("medicine/chronicDiseaseManage/insertOrUpdateSleepRecord")
    Observable<HttpResult<EatRecordBean>> insertOrUpdateSleepRecord(@QueryMap Map<String, String> map);

    /**
     * 删除运动睡眠记录
     */
    @GET("medicine/chronicDiseaseManage/deleteExerciseAndSleepRecord")
    Observable<HttpResult<EatRecordBean>> deleteExerciseAndSleepRecord(@QueryMap Map<String,
            String> map);

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
