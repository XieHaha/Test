package com.keydom.ih_doctor.net;

import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.ih_doctor.bean.CategoryBean;
import com.keydom.ih_doctor.bean.CommonNurseServiceOrderDetailBean;
import com.keydom.ih_doctor.bean.HeadNurseServiceOrderDetailBean;
import com.keydom.ih_doctor.bean.NurseBean;
import com.keydom.ih_doctor.bean.NurseServiceListBean;
import com.keydom.ih_doctor.bean.NursingProjectInfo;
import com.keydom.ih_doctor.bean.OrderStatisticBean;

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
public interface NurseServiceApiService {

    /**
     * 护士长订单列表
     *
     * @param maps
     * @return
     */
    @GET("nursing/nursingServiceOrder/getNursingOrderByUserIAndState")
    Observable<HttpResult<List<NurseServiceListBean>>> getHeadNurseServiceOrderList(@QueryMap Map<String, Object> maps);


    /**
     * 获取护士长护理服务详情
     *
     * @param maps
     * @return
     */
    @GET("nursing/nursingServiceOrder/getNursingOrderDetailById")
    Observable<HttpResult<HeadNurseServiceOrderDetailBean>> getNursingOrderDetailById(@QueryMap Map<String, Object> maps);


    /**
     * 普通护士订单列表
     *
     * @param maps
     * @return
     */
    @GET("nursing/nursingServiceOrder/getNursingServiceRecordByNurseIdAndState")
    Observable<HttpResult<List<NurseServiceListBean>>> getCommonNurseServiceOrderList(@QueryMap Map<String, Object> maps);

    /**
     * 护士长获取为接单数量
     *
     * @param maps
     * @return
     */
    @GET("nursing/nursingServiceOrder/getOrderStatistic")
    Observable<HttpResult<OrderStatisticBean>> getOrderStatistic(@QueryMap Map<String, Object> maps);

    /**
     * 普通护士获取详情
     *
     * @param maps
     * @return
     */
    @GET("nursing/nursingServiceOrder/getNursingServiceRecordDetailByNurseIdAndState")
    Observable<HttpResult<CommonNurseServiceOrderDetailBean>> getNursingServiceRecordDetailByNurseIdAndState(@QueryMap Map<String, Object> maps);

    /**
     * 获取护理服务子订单详情
     *
     * @param subOrderNumber 子订单号
     * @return
     */
    @GET("nursing/nursingServiceSubOrder/getSubOrderProjectsBySubOrderNumber")
    Observable<HttpResult<List<NursingProjectInfo>>> getSubOrderProjectsBySubOrderNumber(@Query("subOrderNumber") String subOrderNumber);
    /**
     * 退单和退回修改
     *
     * @param
     * @return
     */
    @POST("nursing/nursingServiceOrder/updateNursingOrderStateById")
    Observable<HttpResult<String>> updateNursingOrderStateById(@QueryMap Map<String, Object> maps);


    /**
     * 护士长接单和订单指派
     *
     * @return
     */
    @POST("nursing/nursingServiceReceiptRecord/saveNursingByNursingOrderIdAndNursingId")
    Observable<HttpResult<String>> saveNursingByNursingOrderIdAndNursingId(@QueryMap Map<String, Object> maps);


    /**
     * 护士接收指派订单
     *
     * @return
     */
    @POST("nursing/nursingServiceRecord/confirmNursingServiceRecordById")
    Observable<HttpResult<String>> confirmNursingServiceRecordById(@QueryMap Map<String, Object> maps);

    /**
     * 收受转单
     *
     * @return
     */
    @POST("nursing/nursingServiceRecord/acceptOrder")
    Observable<HttpResult<String>> acceptOrder(@Body RequestBody body);

    /**
     * 取消转单
     *
     * @return
     */
    @POST("nursing/nursingServiceRecord/cancelTtransferOrder")
    Observable<HttpResult<String>> cancelTtransferOrder(@Body RequestBody body);



    /**
     * 护士转单
     *
     * @return
     */
    @POST("nursing/nursingServiceRecord/transferNurseOrder2")
    Observable<HttpResult<String>> transferNurseOrder(@Body RequestBody body);


    /**
     * 护士出发和到达
     *
     * @return
     */
    @POST("nursing/nursingServiceRecord/confirmDepartmentTimeOrArriveTimeServiceRecordByIdAndType")
    Observable<HttpResult<String>> confirmDepartmentTimeOrArriveTimeServiceRecordByIdAndType(@QueryMap Map<String, Object> maps);


    /**
     * 获取科室护士列表
     *
     * @param maps
     * @return
     */
    @GET("nursing/nurseHospitalUser/getNurserByHospitalIdOrDeptId")
    Observable<HttpResult<List<NurseBean>>> getNurserByHospitalIdOrDeptId(@QueryMap Map<String, Object> maps);

    /**
     * 获取基础护理或者专科护理或产后护理
     *
     * @param bodyMap
     * @return
     */
    @GET("nursing/nurseServiceProject/getNurseServiceProjectByCateId")
    Observable<HttpResult<List<NursingProjectInfo>>> getNurseServiceProjectByCateId(@QueryMap Map<String, Object> bodyMap);


    /**
     * 完成服务和添加药品
     *
     * @param body
     * @return
     */
    @POST("nursing/nursingServiceOrder/NurseFinishOrderOrAndItem")
    Observable<HttpResult<String>> NurseFinishOrderOrAndItem(@Body RequestBody body);


    /**
     * 添加服务项目
     *
     * @param body
     * @return
     */
    @POST("nursing/nursingServiceSubOrder/nurseAddProjectToSubOrder")
    Observable<HttpResult<String>> nurseAddProjectToSubOrder(@Body RequestBody body);

    /**
     * 修改服务项目
     *
     * @param body
     * @return
     */
    @POST("nursing/nursingServiceSubOrder/editSubOrder")
    Observable<HttpResult<String>> editSubOrder(@Body RequestBody body);


    /**
     * 判断是否有子单未支付
     *
     * @return
     */
    @GET("nursing/nursingServiceOrder/nurseSearchPatientSubOrderIsPayOrHasSubOrder")
    Observable<HttpResult<String>> nurseSearchPatientSubOrderIsPayOrHasSubOrder(@Query("orderNumber") String orderNumber);


    /**
     * 删除子订单
     *
     * @return
     */
    @POST("nursing/nursingServiceSubOrder/deleteSubOrder")
    Observable<HttpResult<String>> nurseDeleteProjectToSubOrder(@QueryMap Map<String, Object> maps);

    @GET("nursing/nurseServiceCate/getAllNurseServiceCategoryByHospitalId")
    Observable<HttpResult<List<CategoryBean>>> getAllNurseServiceCategoryByHospitalId(@Query("hospitalId") long hospitalId);


}
