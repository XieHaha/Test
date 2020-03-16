package com.keydom.mianren.ih_doctor.net;

import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.mianren.ih_doctor.bean.DoctorPrescriptionDetailBean;
import com.keydom.mianren.ih_doctor.bean.DrugUseConfigBean;
import com.keydom.mianren.ih_doctor.bean.PrescriptionBean;
import com.keydom.mianren.ih_doctor.bean.PrescriptionDetailBean;
import com.keydom.mianren.ih_doctor.bean.PrescriptionMessageBean;

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
 * @Description：处方审核
 * @Author：song
 * @Date：18/11/15 下午7:09
 * 修改人：xusong
 * 修改时间：18/11/15 下午7:09
 */
public interface PrescriptionService {

    /**
     * 药师处方列表
     * state -1或者不传是未审核 0,1已审核(0未通过 1通过)2,3配送 (2自取,3配送)4过期
     *
     * @param map
     * @return
     */
    @GET("user/prescription/list")
    Observable<HttpResult<List<PrescriptionBean>>> getDrugsPrescriptionList(@QueryMap Map<String, Object> map);


    /**
     * 获取医生处方列表
     * state -1或者不传是未审核 0,1已审核(0未通过 1通过)2,3配送 (2自取,3配送)4过期
     *
     * @param map
     * @return
     */
    @GET("user/prescription/listDoctorPage")
    Observable<HttpResult<List<PrescriptionBean>>> getDoctorPrescriptionList(@QueryMap Map<String, Object> map);


    /**
     * 获取处方
     * @param map
     * @return
     */
    @GET("user/prescription/getDetailById")
    Observable<HttpResult<PrescriptionDetailBean>> getDrugControlPrescriptionDetail(@QueryMap Map<String, Object> map);

    /**
     * 获取处方详情
     * @param map
     * @return
     */
    @GET("user/prescription/getDetailById")
    Observable<HttpResult<DoctorPrescriptionDetailBean>> getDetailById(@QueryMap Map<String, Object> map);

    /**
     * 处方审核
     * @param body
     * @return
     */
    @POST("user/prescription/audit")
    Observable<HttpResult<String>> audit(@Body RequestBody body);


    /**
     * 处方提交
     * @param body
     * @return
     */
    @POST("user/prescription/save")
    Observable<HttpResult<PrescriptionMessageBean>> save(@Body RequestBody body);

    /**
     * 处方提交存为模版
     * @param body
     * @return
     */
    @POST("user/prescription/saveAndTemplate")
    Observable<HttpResult<PrescriptionMessageBean>> saveAndTemplate(@Body RequestBody body);

    /**
     * 获取给药途径、计量单位、用药频率、开方单位
     *
     * @return
     */
    @GET("user/prescription/drugsFrequencyList")
    Observable<HttpResult<DrugUseConfigBean>> drugsFrequencyList();

}