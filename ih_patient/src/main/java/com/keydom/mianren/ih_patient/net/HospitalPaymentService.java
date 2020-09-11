package com.keydom.mianren.ih_patient.net;

import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.mianren.ih_patient.bean.DepartmentInfo;
import com.keydom.mianren.ih_patient.bean.HospitalCheckBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author 顿顿
 * @date 20/9/11 15:30
 * @des
 */
public interface HospitalPaymentService {
    /**
     * 查询住院清单分类列表
     */
    @POST("user/inHospital/inHospitalCostType")
    Observable<HttpResult<List<HospitalCheckBean>>> getHospitalCostType(@Body RequestBody body);

    /**
     * 查询住院清单分类详情
     */
    @POST("user/inHospital/inHospitalCostTypeItem")
    Observable<HttpResult<List<DepartmentInfo>>> getHospitalCostTypeItem(@Body RequestBody body);
}
