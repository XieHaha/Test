package com.keydom.mianren.ih_patient.net;

import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.mianren.ih_patient.bean.PrescriptionDetailBean;
import com.keydom.mianren.ih_patient.bean.PrescriptionTitleBean;
import com.keydom.mianren.ih_patient.bean.entity.GetDrugBean;
import com.keydom.mianren.ih_patient.bean.entity.GetLogisicBean;
import com.keydom.mianren.ih_patient.bean.entity.LogisticsEntity;
import com.keydom.mianren.ih_patient.bean.entity.PharmacyEntity;
import com.keydom.mianren.ih_patient.bean.entity.pharmacy.PharmacyBean;

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
 * created date: 2019/1/18 on 10:34
 * des:电子处方接口
 */
public interface PrescriptionService {
    /**
     * //获取患者处方订单列表
     */
    @GET("user/prescription/listPatient")
    Observable<HttpResult<PageBean<PrescriptionTitleBean>>> prescriptionListPatient(@QueryMap Map<String, Object> map);

    /**
     * 获取患者处方详情
     */
    @GET("user/prescription/getDetailById")
    Observable<HttpResult<PrescriptionDetailBean>> getDetailById(@Query("id") String id);

    /**
     * 取药收药订单列表
     */
    @GET(Const.OUTER + "/prescription/getListPageApi")
    Observable<HttpResult<GetDrugBean>> getPrescriptionIdList(@QueryMap Map<String, Object> map);

    /**
     * 处方详情
     */
    @GET("/api/prescription/getPrescriptionDetails")
    Observable<HttpResult<PharmacyEntity>> getPrescriptionDetails(@QueryMap Map<String, Object> map);

    /**
     * 根据运单号查询物流列表
     */
    @GET(Const.OUTER + "/logistics/getLogisticInfoByWaybill")
    Observable<HttpResult<LogisticsEntity>> getLogisticsList(@QueryMap Map<String, Object> map);
    /**
     * 根根据处方单号查询相应的物流详情
     */
    @GET(Const.OUTER + "/logistics/getLogisticInfoByPrescriptionId")
    Observable<HttpResult<LogisticsEntity>> getLogisticInfoByPrescriptionId(@QueryMap Map<String, Object> map);

    /**
     * 根据userid查询物流列表
     */
    @GET(Const.OUTER + "/logistics/getLogisticsListByUserId")
    Observable<HttpResult<GetLogisicBean>> getLogistics(@QueryMap Map<String, Object> map);

    /**
     * 根据运单号查询物流列表
     */
    @POST(Const.OUTER + "/logistics/getLogisticList")
    Observable<HttpResult<GetLogisicBean>> getLogisticsByWallBill(@QueryMap Map<String, Object> map);

    /**
     * 根据处方明细列表
     */
    @GET(Const.OUTER + "/prescriptionItem/getList")
    Observable<HttpResult<List<PharmacyBean>>> getprescriptionItemList(@QueryMap Map<String, Object> map);

    /**
     * 根据经纬度和药品信息查询药店
     */
    @POST(Const.OUTER + "/prescription/findDrugstoresByStore")
    Observable<HttpResult<List<PharmacyBean>>> getFindDrugstores(@Body RequestBody body);

    /**
     * 根据药店名字取配送费
     */
    @POST(Const.OUTER + "/prescription/findDrugstoresByDistribution")
    Observable<HttpResult<List<PharmacyBean>>> getFindDrugstoresByDistribution(@Body RequestBody body);


    /**
     * 根据处方明细列表
     */
    @GET(Const.OUTER + "/prescription/createQRCode")
    Observable<HttpResult<List<PharmacyBean>>>getprescriptionCreateQRCode(@QueryMap Map<String, Object> map);


    /**
     * 更新处方订单
     */
    @POST("/user/prescription/updateOrder")
    Observable<HttpResult<String>>updatePrescriptionOrder(@Body RequestBody body);


    /**
     * 根据处方详情基本信息
     */
    @GET(Const.OUTER + "/prescription/getPrescriptionById")
    Observable<HttpResult<PharmacyEntity>>getPrescriptionByIdOrCode(@QueryMap Map<String, Object> map);


    /**
     * 获取处方列表
     */
    @GET("/api/prescriptionItem/getList")
    Observable<HttpResult<List<PharmacyBean>>>getPrescriptionItemList(@QueryMap Map<String, Object> map);

    /**
     * 确认收货
     */
    @GET(Const.OUTER + "/logistics/confirmOrder")
    Observable<HttpResult<String>>confirmOrder(@QueryMap Map<String, Object> map);

    /**
     * 拒绝收货
     */
    @GET(Const.OUTER + "/logistics/refuseOrder")
    Observable<HttpResult<String>>refuseOrder(@QueryMap Map<String, Object> map);

}
