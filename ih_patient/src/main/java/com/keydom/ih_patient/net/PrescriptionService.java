package com.keydom.ih_patient.net;

import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.ih_patient.bean.PrescriptionDetailBean;
import com.keydom.ih_patient.bean.PrescriptionTitleBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * created date: 2019/1/18 on 10:34
 * des:电子处方接口
 */
public interface PrescriptionService {
    /**
     *    //获取患者处方订单列表
     */
    @GET("user/prescription/listPatient")
    Observable<HttpResult<List<PrescriptionTitleBean>>> prescriptionListPatient(@QueryMap Map<String,Object> map);

    /**
     * 获取患者处方详情
     */
    @GET("user/prescription/getDetailById")
    Observable<HttpResult<PrescriptionDetailBean>> getDetailById(@Query("id")String id);
}
