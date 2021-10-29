package com.keydom.mianren.ih_doctor.net;

import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.mianren.ih_doctor.bean.SignRegInfoBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * @Name：com.keydom.ih_doctor.net
 * @Description：门诊排班
 * @Author：song
 * @Date：18/11/15 下午7:09
 * 修改人：xusong
 * 修改时间：18/11/15 下午7:09
 */
public interface SignService {

    /**
     * 注册电子签名
     *
     * @param body
     * @return
     */
    @POST("user/electronics/addTrustedUsert")
    Observable<HttpResult<String>> addTrustedUsert(@Body RequestBody body);


    /**
     * 获取电子签名任务ID
     */
    @POST("user/electronics/addAuthJob")
    Observable<HttpResult<String>> addAuthJob(@Body RequestBody body);


    @POST("user/electronics/changeMobile")
    Observable<HttpResult<String>> changeMobile(@Body RequestBody body);


    @GET("user/electronics/getUserElectronicsInfo")
    Observable<HttpResult<SignRegInfoBean>> getUserElectronicsInfo(@QueryMap Map<String, Object> maps);

    /**
     * CA统计
     *
     * @param type 0 保存处方 1修改处方 2审核通过 3处方驳回
     */
    @GET("user/caCount/insert")
    Observable<HttpResult<String>> caCount(@Query("type") int type);

    /***********************2021年9月13日 14:17:29* 新增四川CA**********************************************/

    /**
     * 是否绑定签章
     */
    @POST("user/scca/isSign")
    Observable<HttpResult<String>> isSign();

    /**
     * 获取签章图片地址
     */
    @GET("user/scca/getSign")
    Observable<HttpResult<String>> getSign();

    /**
     * 申请证书并绑定签章
     */
    @POST("user/scca/certEnrollAndUserSignConfig")
    Observable<HttpResult<String>> certEnrollAndUserSignConfig(@Body RequestBody body);
    /**
     * 重置pin
     */
    @POST("user/scca/resetPin")
    Observable<HttpResult<String>> resetPin(@Body RequestBody body);

    /**
     * 签章文件
     */
    @Multipart
    @POST("user/scca/signPdf")
    Observable<HttpResult<String>> signPdf(@Part List<MultipartBody.Part> part);

    /**
     * 下载签章文件 根据文件id查询
     */
    @GET("user/scca/fetchSignedFile")
    Observable<HttpResult<String>> fetchSignedFile(@Query("fileId") String fileId, @Query("userId") String userId);
    /**
     * 下载签章文件 根据处方id查询
     * @param type 1 医生的 2 药师的
     */
    @GET("user/scca/fetchSignedFileByPreId")
    Observable<HttpResult<String>> fetchSignedFileByPreId(@Query("prescriptionId") String prescriptionId, @Query("type") String type);

}
